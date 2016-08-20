package groep2.joetz.com.joetz_project_groep2_test.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.ContactsRecyclerViewAdapter;
import groep2.joetz.com.joetz_project_groep2_test.adapter.RecyclerViewAdapter;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnContactsLoadedListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment implements OnContactsLoadedListener{

    private OnFragmentInteractionListener mListener;

    private View rootview;

    private RecyclerView rv;
    private ContactsRecyclerViewAdapter adapter;

    public static ContactsFragment getNewInstance() {
        return new ContactsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_contacts, container, false);

        rv = (RecyclerView) rootview.findViewById(R.id.contactsRecyclerview);


        return rootview;
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
    public void onStart() {
        super.onStart();
        createContactsView();
        Repository.registerContactsListener(this);
        Repository.loadContacts();
    }

    private void createContactsView() {
        if(getActivity()!=null) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv.setLayoutManager(new NpaLinearLayoutManager(getContext()));
            } else {
                rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            adapter = new ContactsRecyclerViewAdapter(Repository.getContacts(), mListener, getContext());
            rv.setAdapter(adapter);

            rv.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void onContactsLoaded() {
        createContactsView();
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFailed() {
        if(getActivity()!=null)
            Toast.makeText(getActivity(), "contacts load failed", Toast.LENGTH_LONG).show();
    }

    /**
     * helper class to solve a bug in recyclerview
     */
    private static class NpaLinearLayoutManager extends LinearLayoutManager {

        public NpaLinearLayoutManager(Context context) {
            super(context);
        }

        /**
         * Disable predictive animations. There is a bug in RecyclerView which causes views that
         * are being reloaded to pull invalid ViewHolders from the internal recycler stack if the
         * adapter size has decreased since the ViewHolder was recycled.
         */
        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

}
