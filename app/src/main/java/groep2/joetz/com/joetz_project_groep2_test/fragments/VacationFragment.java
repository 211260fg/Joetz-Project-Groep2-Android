package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.model.Vacation;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;

public class VacationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public static final String ITEM_SELECTED_POSITION = "ItemPos";
    private int currentPosition=-1;
    private View rootView;
    private Vacation vacation;


    public static VacationFragment getNewInstance(int index) {
        VacationFragment newFragment = new VacationFragment();
        Bundle args = new Bundle();
        args.putInt(ITEM_SELECTED_POSITION, index);

        newFragment.setCurrentPosition(index);
        newFragment.setArguments(args);
        return newFragment;
    }


    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_vacation, container, false);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(ITEM_SELECTED_POSITION);
            Log.d("savedinstance", ""+savedInstanceState.getInt(ITEM_SELECTED_POSITION));
        }
        else{
            Log.d("savedinstance", "null");
        }
        vacation = Repository.getItems().get(currentPosition);

        setVacationDetails();
        setButtonProperties();

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


    private void setVacationDetails() {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());

        if (rootView != null) {
            TextView title = (TextView) rootView.findViewById(R.id.vactionTitle);
            TextView description = (TextView) rootView.findViewById(R.id.vactionDescription);
            TextView  startdate = (TextView) rootView.findViewById(R.id.itemstartdate);
            TextView enddate = (TextView) rootView.findViewById(R.id.itemenddate);
            TextView age = (TextView) rootView.findViewById(R.id.itemage);
            TextView location = (TextView) rootView.findViewById(R.id.itemlocation);

            title.setText(vacation.getTitle());
            description.setText(vacation.getExtra_info());
            startdate.setText(dateFormat.format(vacation.getStartDate()));
            enddate.setText(dateFormat.format(vacation.getEndDate()));
            age.setText(vacation.getMinAge()+" tot "+vacation.getMaxAge()+" jaar");
            //age.setText(vacation.getMinAge());
            location.setText(vacation.getLocation());

        }else{
            Log.d("rootview", "null");
        }
    }

    private void setButtonProperties(){
        Button btnGoBack = (Button) rootView.findViewById(R.id.btnGoBack);
        Button btnRegisterVacation = (Button) rootView.findViewById(R.id.btnRegisterVacation);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.VACATIONS, -1);
            }
        });

        btnRegisterVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog d = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom))
                        .setTitle("Inschrijving")
                        .setMessage("Bent u zeker dat u wilt inschrijven voor "+vacation.getTitle()+"?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO implement
                            }
                        })
                        .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();


                Button nbutton = d.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(getResources().getColor(R.color.maintheme));
                Button pbutton = d.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(getResources().getColor(R.color.maintheme));
            }
        });


    }

}
