package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.RecyclerViewAdapter;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnItemsLoadedListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;


public class HistoryFragment extends Fragment implements OnItemsLoadedListener{
    private OnFragmentInteractionListener mListener;


    public static HistoryFragment getNewInstance() {
        return new HistoryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //mListener = (OnFragmentInteractionListener) getActivity();
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
    public void onItemsLoaded() {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onItemAdded() {

    }

    @Override
    public void onItemDeleted() {

    }
}
