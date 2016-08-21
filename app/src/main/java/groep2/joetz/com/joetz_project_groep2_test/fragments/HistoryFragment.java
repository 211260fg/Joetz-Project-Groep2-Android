package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.RecyclerViewAdapter;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnItemsLoadedListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;


public class HistoryFragment extends Fragment{
    private OnFragmentInteractionListener mListener;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;

    private View rootView;

    private SwipeRefreshLayout swipeRefreshLayout;

    public static HistoryFragment getNewInstance() {
        return new HistoryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_vacations, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.vacationsRecyclerview);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.maintheme);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);

        createItemView();

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
    public void onDestroy() {

        Glide.get(getActivity()).clearMemory();
        super.onDestroy();
    }

    private void createItemView() {
        if (getActivity() != null) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv.setLayoutManager(new NpaLinearLayoutManager(getContext()));
            } else {
                rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            adapter = new RecyclerViewAdapter(Repository.getCurrentUser().getHistory(), mListener, getContext());
            rv.setAdapter(adapter);

            rv.setItemAnimator(new DefaultItemAnimator());
        }
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