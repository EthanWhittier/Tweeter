package edu.byu.cs.tweeter.view.main.story;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.main.ProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class StoryFragment extends Fragment implements StoryPresenter.View {


    private static final String LOG_TAG = "StatusFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    StoryPresenter storyPresenter;

    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    public static StoryFragment newInstance(User user, AuthToken authToken) {
        StoryFragment storyFragment = new StoryFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        storyFragment.setArguments(args);
        return storyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        storyPresenter = new StoryPresenter(this);

        RecyclerView storyRecyclerView = view.findViewById(R.id.StoryRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new StoryRecyclerViewPaginationScrollListener(layoutManager));
        storyRecyclerView.addItemDecoration(new DividerItemDecoration(storyRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }



    private class StoryHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView timestamp;
        private final TextView statusView;

        public StoryHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userStatusImage);
            userAlias = itemView.findViewById(R.id.StatusUserAlias);
            userName = itemView.findViewById(R.id.StatusUserName);
            timestamp = itemView.findViewById(R.id.statusTimeStamp);
            statusView = itemView.findViewById(R.id.Status);

        }

        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
            timestamp.setText(status.formatDate());
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

    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryHolder> implements GetStoryTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private boolean hasMorePages;
        private boolean isLoading = false;
        private Status lastStatus;


        StoryRecyclerViewAdapter() {loadMoreItems();}

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
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryHolder holder, int position) {
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

            GetStoryTask storyTask = new GetStoryTask(storyPresenter, this);
            StoryRequest storyRequest = new StoryRequest(user, PAGE_SIZE, lastStatus);
            storyTask.execute(storyRequest);
        }

        @Override
        public void storyRetrieved(StoryResponse storyResponse) {
            List<Status> statuses = storyResponse.getUserStatuses();

            lastStatus = statuses.size() > 0 ? statuses.get(statuses.size() - 1) : null;
            hasMorePages = storyResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();

            storyRecyclerViewAdapter.addItems(statuses);
        }



        private void addLoadingFooter() {addItem(new Status("Loading", null, LocalDateTime.now(), null, null));}

        private void removeLoadingFooter() {removeItem(statuses.get(statuses.size() - 1));}

        @Override
        public void handleException(Exception ex) {

        }
    }

    private class StoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        StoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if(!storyRecyclerViewAdapter.isLoading && storyRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    storyRecyclerViewAdapter.loadMoreItems();
                }
            }
        }



    }



}
