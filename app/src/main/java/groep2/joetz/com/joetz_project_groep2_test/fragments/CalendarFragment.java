package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import groep2.joetz.com.joetz_project_groep2_test.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    public static CalendarFragment getNewInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
