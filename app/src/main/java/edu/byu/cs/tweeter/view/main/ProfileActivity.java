package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.MainBarPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.UnfollowTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ProfileActivity extends AppCompatActivity implements MainBarPresenter.View, FollowTask.Observer, UnfollowTask.Observer {

    public static final String PROFILE_USER_KEY = "UserKey";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String LOG_TAG = "ProfileActivity";

    private User usersProfile;
    private MainBarPresenter presenter;

    //TODO I think it would be great to load in the follow relationship if exists using asynctask on creation for backend

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        presenter = new MainBarPresenter(this);

        usersProfile = (User) getIntent().getSerializableExtra(PROFILE_USER_KEY);
        if(usersProfile == null) {
            throw new RuntimeException("User was never passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), usersProfile, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.removeTabAt(0);

        Button followButton = (Button) findViewById(R.id.followUnfollow);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(followButton.getText().toString().equals("Follow")) {
                    FollowRequest followRequest = new FollowRequest(new Follow(usersProfile, usersProfile));
                    FollowTask followTask = new FollowTask(presenter, ProfileActivity.this);
                    followTask.execute(followRequest);
                } else {
                    UnfollowRequest unfollowRequest = new UnfollowRequest(new Follow(usersProfile, usersProfile));
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






