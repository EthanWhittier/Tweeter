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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "LoginActivity";
    public static final String ARG_TITLE = "title";
    private RegisterPresenter registerPresenter;
    private RegisterTask.Observer observer;
    private Toast registerToast;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        registerPresenter = new RegisterPresenter(this);
        observer = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, parent, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Button registerButton = (Button) getView().findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(getContext(), "Registering", Toast.LENGTH_LONG);
                registerToast.show();

                RegisterRequest registerRequest = new RegisterRequest("Ethan", "Whittier", "eiwhitt", "password");
                RegisterTask registerTask = new RegisterTask(registerPresenter, observer);
                registerTask.execute(registerRequest);
            }
        });

        Button returnToLogin = (Button) getView().findViewById(R.id.LoginRegisterButton);
        returnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoginActivity) getActivity()).inflateLoginView();
            }
        });

    }


    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param registerResponse the response from the login request.
     */
    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(getContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, registerResponse.getAuthToken());

        registerToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param registerResponse the response from the login request.
     */
    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(getContext(), "Failed to register. " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param ex the exception.
     */
    @Override
    public void handleException(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
        Toast.makeText(getContext(), "Failed to register because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
