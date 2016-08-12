package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.model.User;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    private View rootView;

    private User currentuser;


    private ImageButton profilephoto;
    private TextView profilename;
    private TextView profileDetails;


    public static UserFragment getNewInstance() {
        return new UserFragment();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user, container, false);

        profilephoto = (ImageButton) rootView.findViewById(R.id.user_profile_photo);
        profilename = (TextView) rootView.findViewById(R.id.user_profile_name);
        profileDetails = (TextView) rootView.findViewById(R.id.user_profile_details);

        currentuser = Repository.getCurrentUser();

        setupProfile();

        return rootView;

    }


    private void setupProfile(){
        Glide.with(getContext()).load(currentuser.getProfileimage()).centerCrop().into(profilephoto);
        profilename.setText(currentuser.getFirstname()+" "+currentuser.getLastname());
        profileDetails.setText(currentuser.getEmail());
    }

}
