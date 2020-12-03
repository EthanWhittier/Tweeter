package edu.byu.cs.client.view.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.DataCache;
import edu.byu.cs.client.R;
import edu.byu.cs.client.view.SectionsPagerAdapterProfile;
import tweeter.model.domain.AuthToken;
import tweeter.model.domain.Follow;
import tweeter.model.domain.User;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.client.presenter.MainBarPresenter;
import edu.byu.cs.client.view.asyncTasks.FollowTask;
import edu.byu.cs.client.view.asyncTasks.UnfollowTask;
import edu.byu.cs.client.view.util.ImageUtils;

public class ProfileActivity extends AppCompatActivity implements MainBarPresenter.View, FollowTask.Observer, UnfollowTask.Observer {

    public static final String PROFILE_USER_KEY = "UserKey";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String LOG_TAG = "ProfileActivity";
    public static final String IS_FOLLOWING = "FollowingKey";

    private User usersProfile;
    private MainBarPresenter presenter;
    private boolean isFollowing;

    //TODO I think it would be great to load in the follow relationship if exists using asynctask on creation for backend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        presenter = new MainBarPresenter(this);

        usersProfile = DataCache.getDataCache().getCurrentProfile();
        isFollowing = getIntent().getBooleanExtra(IS_FOLLOWING, false);

        if(usersProfile == null) {
            throw new RuntimeException("User was never passed to activity");
        }

      //AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        AuthToken authToken = new AuthToken();
        SectionsPagerAdapterProfile sectionsPagerAdapterProfile = new SectionsPagerAdapterProfile(this, getSupportFragmentManager(), usersProfile, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapterProfile);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Button followButton = (Button) findViewById(R.id.followUnfollow);

        if(isFollowing) {
            followButton.setText("Unfollow");
        } else {
            followButton.setText("Follow");
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(followButton.getText().toString().equals("Follow")) {
                    FollowRequest followRequest = new FollowRequest(DataCache.getInstance().getLoggedInUser().getAlias(), usersProfile.getAlias());
                    FollowTask followTask = new FollowTask(presenter, ProfileActivity.this);
                    followTask.execute(followRequest);
                } else {
                    UnfollowRequest unfollowRequest = new UnfollowRequest(DataCache.getInstance().getLoggedInUser().getAlias(), usersProfile.getAlias());
                    UnfollowTask unfollowTask = new UnfollowTask(presenter, ProfileActivity.this);
                    unfollowTask.execute(unfollowRequest);
                }
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(usersProfile.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(usersProfile.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(usersProfile.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Following: " + "-42");

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Followers: " + "-42");
    }

    @Override
    public void followSuccessful(FollowResponse followResponse) {
        Button followButton = (Button) findViewById(R.id.followUnfollow);
        followButton.setText("Unfollow");
    }

    @Override
    public void unfollowSuccessful(UnfollowResponse unfollowResponse) {
        Button followButton = (Button) findViewById(R.id.followUnfollow);
        followButton.setText("Follow");
    }

    @Override
    public void handleException() {

    }
}






