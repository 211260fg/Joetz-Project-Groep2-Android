package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

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
        Repository.removeListener(this);
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
        mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.AUTHENTICATION, 0);
    }

    private void setupfbLogin(){

        String[] permissions = {"email", "public_profile" };
        btnFBLogin.setReadPermissions(Arrays.asList(permissions));
        btnFBLogin.setFragment(this);


        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String userID = object.getString("id");
                                    String email = object.getString("email");
                                    String firstname = object.optString("first_name");
                                    String lastname = object.optString("last_name");
                                    String name = object.optString("name");
                                    String[] namearray = name.split("\\s+");
                                    //String birthday = object.getString("birthday"); // 01/31/1980 format
                                    String profilepic = "https://graph.facebook.com/" + userID + "/picture?type=large";

                                    User fbUser = new User(email, namearray[0], namearray[1], profilepic);

                                    onLoginSuccess(fbUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Log.v("Facebook login", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("Facebook login", exception.getCause().toString());
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

        Intent i = new Intent(getActivity(), MainActivity.class);
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
