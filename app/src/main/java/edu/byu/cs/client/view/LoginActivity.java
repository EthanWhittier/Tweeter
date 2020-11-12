package edu.byu.cs.client.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import edu.byu.cs.client.R;
import edu.byu.cs.client.presenter.LoginPresenter;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast loginInToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addFragments();

    }

    /**
     * Called from LoginFragment To Inflate the Register Fragment
     */
    public void inflateRegisterView() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().detach(fragmentManager.findFragmentById(R.id.loginFrameLayout));
        RegisterFragment registerFragment = (RegisterFragment) fragmentManager.findFragmentById(R.id.registerFrameLayout);

        if(registerFragment == null) {
            registerFragment = createRegisterFragment(getString(R.string.app_name));
            fragmentManager.beginTransaction()
                    .add(R.id.registerFrameLayout, registerFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction().attach(registerFragment).commit();
        }



    }

    public void inflateLoginView() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().detach(fragmentManager.findFragmentById(R.id.registerFrameLayout)).commit();
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.loginFrameLayout);

        fragmentManager.beginTransaction().attach(loginFragment).commit();


    }


    // ** Fragment Helper Methods **

    private RegisterFragment createRegisterFragment(String title) {
        RegisterFragment registerFragment = new RegisterFragment();

        Bundle args = new Bundle();
        args.putString(RegisterFragment.ARG_TITLE, title);
        registerFragment.setArguments(args);

        return registerFragment;
    }

    private void addFragments() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.loginFrameLayout);

        if(loginFragment == null) {
            loginFragment = createLoginFragment(getString(R.string.app_name));
            fragmentManager.beginTransaction()
                    .add(R.id.loginFrameLayout, loginFragment)
                    .commit();
        }

    }

    private LoginFragment createLoginFragment(String title) {
        LoginFragment loginFragment = new LoginFragment();

        Bundle args = new Bundle();
        args.putString(LoginFragment.ARG_TITLE, title);
        loginFragment.setArguments(args);

        return loginFragment;
    }

}
