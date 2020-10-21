package edu.byu.cs.tweeter.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "LoginActivity";
    public static final String ARG_TITLE = "title";
    private LoginPresenter presenter;
    private Toast loginInToast;
    LoginTask.Observer observer;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        presenter = new LoginPresenter(this);
        observer = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        Button loginButton = (Button) getView().findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInToast = Toast.makeText(getContext(), "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest("dummyUserName", "dummyPassword");
                LoginTask loginTask = new LoginTask(presenter, observer);
                loginTask.execute(loginRequest);
            }
        });

        Button registerButton = (Button) getView().findViewById(R.id.NewAccount);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).inflateRegisterView();
            }
        });

    }


    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(getContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }


    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getContext(), "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }


    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getContext(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
