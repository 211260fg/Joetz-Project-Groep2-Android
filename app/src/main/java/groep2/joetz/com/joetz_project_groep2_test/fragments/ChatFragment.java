package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import groep2.joetz.com.joetz_project_groep2_test.R;

public class ChatFragment extends Fragment{


    public static ChatFragment getNewInstance() {
        return new ChatFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        transaction.replace(R.id.container_frame, ContactsFragment.getNewInstance());

        transaction.commit();

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}