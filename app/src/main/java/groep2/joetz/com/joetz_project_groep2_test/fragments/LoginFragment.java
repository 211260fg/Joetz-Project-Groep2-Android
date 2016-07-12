package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.activities.AuthenticationActivity;
import groep2.joetz.com.joetz_project_groep2_test.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private Button btnLogin;

    private View rootView;

    public static boolean testIsLoggedIn=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);*/
                testIsLoggedIn=true;
                getActivity().finish();
            }
        });

        //TODO
        Toast.makeText(getActivity(), "TEST: klik op aanmelden om de startpagina te bekijken", Toast.LENGTH_LONG).show();


        return rootView;
    }


}
