package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.activities.AuthenticationActivity;
import groep2.joetz.com.joetz_project_groep2_test.activities.MainActivity;
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

    private View rootView;

    UserSessionManager session;

    public static boolean testIsLoggedIn=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        link_signup = (TextView) rootView.findViewById(R.id.link_signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //TODO VERWIJDEREN NA UITWERKING LOGIN
        Toast.makeText(getActivity(), "TEST: klik op aanmelden om de startpagina te bekijken", Toast.LENGTH_LONG).show();


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Repository.registerLoginListener(this);
    }

    private void login(){
        if(!validateFields()){
            return;
        }
        Repository.loginUser(input_email.getText().toString(), input_password.getText().toString());
    }


    public void onLoginSuccess(){
        session.createUserLoginSession(input_email.getText().toString(), input_password.getText().toString());

        Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    public void onLoginFailed(){
        Toast.makeText(getActivity(), "NO USER FOUND", Toast.LENGTH_LONG).show();
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
