package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.activities.MainActivity;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnLoggedInListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

public class RegisterFragment extends Fragment implements OnLoggedInListener{


    private OnFragmentInteractionListener mListener;

    private View rootView;

    private Button link_back;
    private Button btnRegister;
    private ImageButton btnTakepicture;

    private EditText input_email;
    private EditText input_password;
    private EditText input_firstname;
    private EditText input_lastname;

    private ProgressDialog progressDialog;

    private UserSessionManager session;


    public static RegisterFragment getNewInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_register, container, false);

        link_back = (Button) rootView.findViewById(R.id.link_back);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnTakepicture = (ImageButton) rootView.findViewById(R.id.btnTakepicture);

        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);
        input_firstname = (EditText) rootView.findViewById(R.id.input_firstname);
        input_lastname = (EditText) rootView.findViewById(R.id.input_lastname);

        link_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.AUTHENTICATION, 0);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        btnTakepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


        session = new UserSessionManager(getActivity().getApplicationContext());

        return rootView;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            switch (resultCode){
                case Activity.RESULT_OK: break;
                case Activity.RESULT_CANCELED: break;
                default: break;
            }
        }
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

    private void register(){
        /*if(!validateFields()){
            return;
        }*/
        User.Participant participant = new User.Participant(input_firstname.getText().toString(),
                input_lastname.getText().toString());
        User user = new User(input_email.getText().toString(), participant);
        Repository.registerUser(user, input_password.getText().toString());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Laden...");
        progressDialog.show();

    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 0);
    }

}
