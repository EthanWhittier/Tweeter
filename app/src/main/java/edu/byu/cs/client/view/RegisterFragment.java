package edu.byu.cs.client.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import edu.byu.cs.client.DataCache;
import edu.byu.cs.client.R;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.client.presenter.RegisterPresenter;
import edu.byu.cs.client.view.asyncTasks.RegisterTask;
import edu.byu.cs.client.view.main.MainActivity;

public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "LoginActivity";
    public static final String ARG_TITLE = "title";
    public final static int PICK_PHOTO_CODE = 1046;
    private RegisterPresenter registerPresenter;
    private RegisterTask.Observer observer;
    private Toast registerToast;


    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;


    private byte[] newUserImage;

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
        firstName = view.findViewById(R.id.FirstNameEdit);
        lastName = view.findViewById(R.id.LastNameEdit);
        username = view.findViewById(R.id.UsernameEdit);
        password = view.findViewById(R.id.PasswordEdit);

        Button registerButton = (Button) getView().findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(getContext(), "Registering", Toast.LENGTH_LONG);
                registerToast.show();

                RegisterRequest registerRequest = new RegisterRequest(firstName.getText().toString(), lastName.getText().toString(),
                        username.getText().toString(), password.getText().toString(), newUserImage);

                RegisterTask registerTask = new RegisterTask(registerPresenter, observer);
                registerTask.execute(registerRequest);
            }
        });

        Button cameraButton = (Button) getView().findViewById(R.id.CameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto(view);
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
        DataCache dataCache = DataCache.getInstance();
        dataCache.setAuthToken(registerResponse.getAuthToken());
        dataCache.setLoggedInUser(registerResponse.getUser());

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

    /**
     * Allows the user to add a photo from their gallery as their profile picture
     * @param view
     */
    private void pickPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();
            newUserImage = loadFromUri(photoUri);
        }
    }

    private byte [] loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        image.recycle();
        return byteArray;

    }

}
