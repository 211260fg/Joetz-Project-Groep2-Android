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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container_frame, new HollydaysFragment());

        transaction.commit();

        return view;
    }

}
