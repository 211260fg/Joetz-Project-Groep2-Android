package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import groep2.joetz.com.joetz_project_groep2_test.R;

/**
 * Created by floriangoeteyn on 03-Jul-16.
 */
public class ContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_container, container, false);

        setVisibleFragment(VacationsFragment.getNewInstance());

        return view;
    }

    public void setVisibleFragment(Fragment fragment){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.replace(R.id.container_frame, fragment);

        transaction.commit();
    }


}
