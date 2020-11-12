package edu.byu.cs.client.view.main.followers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.client.R;
import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.client.presenter.FollowersPresenter;
import edu.byu.cs.client.view.asyncTasks.GetFollowersTask;
import edu.byu.cs.client.view.util.ImageUtils;

public class FollowersFragment extends Fragment implements FollowersPresenter.View {


    private static final String LOG_TAG = "FollowersFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private FollowersPresenter presenter;

    private FollowersRecyclerViewAdapter followersRecyclerViewAdapter;




    public static FollowersFragment newInstance(User user, AuthToken authToken) {
        FollowersFragment followersFragment = new FollowersFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        followersFragment.setArguments(args);
        return followersFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowersPresenter(this);

        RecyclerView recyclerView = view.findViewById(R.id.followersRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        followersRecyclerViewAdapter = new FollowersRecyclerViewAdapter();
        recyclerView.setAdapter(followersRecyclerViewAdapter);

        recyclerView.addOnScrollListener(new FollowersRecyclerViewPaginationScroller(layoutManager));

        return view;
    }



    private class FollowersHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FollowersHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * Binds the user's data to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }

    }


    private class FollowersRecyclerViewAdapter extends RecyclerView.Adapter<FollowersHolder> implements GetFollowersTask.Observer {

        private final List<User> users = new ArrayList<>();
        private User lastFollower;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowersRecyclerViewAdapter() {loadMoreItems();}

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FollowersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowersFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }
            return new FollowersHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowersHolder holder, int position) {
            if(!isLoading) {
                holder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowersTask getFollowersTask = new GetFollowersTask(presenter, this);
            FollowersRequest request = new FollowersRequest(user, PAGE_SIZE, lastFollower);
            getFollowersTask.execute(request);
        }

        @Override
        public void followersRetrieved(FollowersResponse followersResponse) {
            List<User> followers = followersResponse.getFollowers();

            lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            hasMorePages = followersResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followersRecyclerViewAdapter.addItems(followers);
        }

        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    private class FollowersRecyclerViewPaginationScroller extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowersRecyclerViewPaginationScroller(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followersRecyclerViewAdapter.isLoading && followersRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followersRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }









}
