package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.RecyclerViewAdapter;
import groep2.joetz.com.joetz_project_groep2_test.repository.OnItemsLoadedListener;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;


public class VacationsFragment extends Fragment implements OnItemsLoadedListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private View rootView;

    public static VacationsFragment getNewInstance() {
        return new VacationsFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_vacations, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.vacationsRecyclerview);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Repository.loadItems();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.maintheme);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);

        return rootView;

    }


    @Override
    public void onStart() {
        super.onStart();
        createItemView();
        Repository.registerListener(this);
        Repository.loadItems();
    }

    @Override
    public void onResume() {
        super.onResume();
        createItemView();
        Repository.registerListener(this);
        Repository.loadHistory();
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
        Repository.removeListener(this);
    }

    @Override
    public void onDestroy() {

        Glide.get(getActivity()).clearMemory();
        super.onDestroy();
    }

    private void createItemView() {
        if(getActivity()!=null) {
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                rv.setLayoutManager(new NpaLinearLayoutManager(getContext()));
            } else {
                rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            }

            adapter = new RecyclerViewAdapter(Repository.getItems(), mListener, getContext());
            rv.setAdapter(adapter);

            rv.setItemAnimator(new DefaultItemAnimator());
        }
    }



    @Override
    public void onItemsLoaded() {
        //TODO FIX
        createItemView();
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
        if(adapter!=null)
            adapter.notifyDataSetChanged();
        Log.d("vacations fragment", "items loaded, size: "+Repository.getItems().size());
    }

    @Override
    public void onLoadFailed() {
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(false);
        if(adapter!=null)
            adapter.notifyDataSetChanged();
        if(getActivity()!=null)
            Toast.makeText(getActivity(), "Kampen konden niet geladen worden", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemAdded() {

    }

    @Override
    public void onItemDeleted() {

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
