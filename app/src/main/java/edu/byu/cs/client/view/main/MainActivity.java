package edu.byu.cs.client.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.client.R;
import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.client.presenter.MainBarPresenter;
import edu.byu.cs.client.presenter.PostStatusPresenter;
import edu.byu.cs.client.view.LoginActivity;
import edu.byu.cs.client.view.asyncTasks.LogoutTask;
import edu.byu.cs.client.view.asyncTasks.PostStatusTask;
import edu.byu.cs.client.view.util.ImageUtils;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * The main activity for the application. Contains tabs for edu.byu.cs.tweeter.view.main.feed, story, following, and edu.byu.cs.tweeter.view.main.followers.
 */
public class MainActivity extends AppCompatActivity implements MainBarPresenter.View, LogoutTask.Observer, PostStatusPresenter.View, PostStatusTask.Observer {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String LOG_TAG = "MainActivity";

    private User user;
    private MainBarPresenter presenter;
    private PostStatusPresenter postStatusPresenter;
    LogoutTask.Observer observer;
    PostStatusTask.Observer postStatusObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());


        presenter = new MainBarPresenter(this);
        postStatusPresenter = new PostStatusPresenter(this);
        postStatusObserver = this;
        observer = this;

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        Button button = (Button) findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutRequest logoutRequest = new LogoutRequest(user, new AuthToken());
                LogoutTask logoutTask = new LogoutTask(observer, presenter);
                logoutTask.execute(logoutRequest);
            }
        });

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStatusDialog(view);
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Following: " + "-42");

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Followers: " + "-42");
    }

    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        Toast.makeText(getApplicationContext(), "Failed to logout." + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleException(Exception exception) {

        Toast.makeText(getApplicationContext(), "Failed to logout because of the following exception, " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e(LOG_TAG, exception.getMessage());
    }

    private void showStatusDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        final View customLayout = getLayoutInflater().inflate(R.layout.write_status, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        setUpDialogView(dialog);


    }

    private void setUpDialogView(AlertDialog dialog) {
        Button cancel = dialog.findViewById(R.id.CancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button tweet = dialog.findViewById(R.id.TweetButton);
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText statusText = dialog.findViewById(R.id.StatusMessage);

                PostStatusTask postStatusTask = new PostStatusTask(postStatusPresenter, postStatusObserver);
                postStatusTask.execute(new PostStatusRequest(statusText.getText().toString(), user));

                dialog.dismiss();
            }
        });
    }


    @Override
    public void postStatusSuccessful(PostStatusResponse postStatusResponse) {
        Toast.makeText(getApplicationContext(), "Status Posted!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postStatusUnsuccessful(PostStatusResponse postStatusResponse) {
        Toast.makeText(getApplicationContext(), "Failed to Post Status." + postStatusResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleStatusException(Exception exception) {
        Log.e(LOG_TAG, "Failed to post edu.byu.cs.tweeter.view.main.status because of exception, " + exception.getMessage());
    }
}