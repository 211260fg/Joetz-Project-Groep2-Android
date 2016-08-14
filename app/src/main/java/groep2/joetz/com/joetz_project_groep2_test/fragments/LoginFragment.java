package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.activities.AuthenticationActivity;
import groep2.joetz.com.joetz_project_groep2_test.activities.MainActivity;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnLoggedInListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements OnLoggedInListener{



    private EditText input_email;
    private EditText input_password;
    private Button btnLogin;
    private TextView link_signup;
    private LoginButton btnFBLogin;

    private ProgressDialog progressDialog;

    private View rootView;

    private OnFragmentInteractionListener mListener;

    private UserSessionManager session;

    private CallbackManager callbackManager;


    public static LoginFragment getNewInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnFBLogin = (LoginButton) rootView.findViewById(R.id.btnFBLogin);
        link_signup = (TextView) rootView.findViewById(R.id.link_signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        setupfbLogin();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Repository.registerLoginListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    private void login(){
        if(!validateFields()){
            return;
        }
        Repository.loginUser(input_email.getText().toString(), input_password.getText().toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Laden...");
        progressDialog.show();

    }

    private void signup(){
        mListener.onFragmentInteraction(0);
    }

    private void setupfbLogin(){

        String[] permissions = {"email", "public_profile" };
        btnFBLogin.setReadPermissions(Arrays.asList(permissions));
        btnFBLogin.setFragment(this);

        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onLoginSuccess(User user){

        if(progressDialog!=null)
            progressDialog.dismiss();

        session.createUserLoginSession(input_email.getText().toString(), input_password.getText().toString());
        session.saveCurrentUser(user);

        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onLoginFailed(){

        if(progressDialog!=null)
            progressDialog.dismiss();

        //TODO: juiste boodschap weergeven
        Toast.makeText(getActivity(), "LOGIN ERROR", Toast.LENGTH_LONG).show();
    }


    private boolean validateFields() {
        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("geen geldig e-mailadres");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            input_password.setError("tussen 4 en 20 characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }


}
