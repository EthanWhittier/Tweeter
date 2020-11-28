package edu.byu.cs.client.view.main.feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.client.R;
import tweeter.model.domain.AuthToken;
import tweeter.model.domain.Feed;
import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import edu.byu.cs.client.presenter.FeedPresenter;
import edu.byu.cs.client.view.asyncTasks.GetFeedTask;
import edu.byu.cs.client.view.main.ProfileActivity;
import edu.byu.cs.client.view.util.ImageUtils;

public class FeedFragment extends Fragment implements FeedPresenter.View {


    private static final String LOG_TAG = "StatusFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private boolean allLoaded = false;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;


    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment feedFragment = new FeedFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        feedFragment.setArguments(args);
        return feedFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.FeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));
        feedRecyclerView.addItemDecoration(new DividerItemDecoration(feedRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }


    private class FeedHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView timestamp;
        private final TextView statusView;


        public FeedHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userStatusImage);
            userAlias = itemView.findViewById(R.id.StatusUserAlias);
            userName = itemView.findViewById(R.id.StatusUserName);
            timestamp = itemView.findViewById(R.id.statusTimeStamp);
            statusView = itemView.findViewById(R.id.Status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'s status.", Toast.LENGTH_SHORT).show();
                }
            });

        }


        void bindStatus(Status status) {
            User author = status.getAuthor();
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(author.getImageBytes()));
            userAlias.setText(author.getAlias());
            userName.setText(author.getName());
            timestamp.setText(status.getCreatedDate());
            statusView.setText(createMentionLinks(status));
            statusView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        private SpannableStringBuilder createMentionLinks(Status status) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(status.getMessage());
            if(status.getMentions() != null) {
                for(String mention: status.getMentions()) {
                    ssb.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            Intent intent = new Intent(getContext(), ProfileActivity.class);
                            intent.putExtra(USER_KEY, user);
                            intent.putExtra(AUTH_TOKEN_KEY, new AuthToken());
                            startActivity(intent);
                        }
                    }, firstIndex(status.getMessage(), mention), lastIndex(status.getMessage(), mention), 0);
                }
            }
            if(status.getUrls() != null) {
                for(String url : status.getUrls()) {
                    ssb.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        }
                    }, status.getMessage().indexOf(url), (status.getMessage().indexOf(url) + (url.length())), 0);
                }
            }

            return ssb;
        }

        private int firstIndex(String message, String mention) {
            return message.indexOf(mention) - 1;
        }

        private int lastIndex(String message, String mention) {
            return message.indexOf(mention) + mention.length() + 1;
        }

    }


    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements GetFeedTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private boolean hasMorePages;
        private boolean isLoading = false;
        private Status lastStatus;


        FeedRecyclerViewAdapter() {loadMoreItems();}


        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeChanged(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }


        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new FeedHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedHolder holder, int position) {
            if(!isLoading) {
                holder.bindStatus(statuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return statuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest feedRequest = new FeedRequest(user, PAGE_SIZE, lastStatus);
            getFeedTask.execute(feedRequest);
        }

        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            List<Status> statuses = feedResponse.getStatuses();
            lastStatus = statuses.size() > 0 ? statuses.get(statuses.size() - 1) : null;
            hasMorePages = feedResponse.getHasMorePages();
            if(!hasMorePages) {
                allLoaded = true;
            }
            isLoading = false;
            removeLoadingFooter();

            feedRecyclerViewAdapter.addItems(statuses);

        }

        @Override
        public void handleException(Exception exception) {

        }

        private void addLoadingFooter() {addItem(new Status("Loading", null, LocalDateTime.now().toString(), null, null));}


        private void removeLoadingFooter() {removeItem(statuses.get(statuses.size() - 1));}
    }


    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            if (!allLoaded) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                    if ((visibleItemCount + firstVisibleItemPosition) >=
                            totalItemCount && firstVisibleItemPosition >= 0) {
                        feedRecyclerViewAdapter.loadMoreItems();
                    }
                }
            }
        }
    }

}
