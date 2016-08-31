package groep2.joetz.com.joetz_project_groep2_test.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.fragments.OnFragmentInteractionListener;
import groep2.joetz.com.joetz_project_groep2_test.model.User;

/**
 * Created by Florian on 14/08/2016.
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder> {


    private List<User> contacts = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private static Context context;

    private RecyclerView rv;


    //doorgeven van de fragmentInteractionListener en items
    public ContactsRecyclerViewAdapter(List<User> contacts, OnFragmentInteractionListener mListener, Context context) {
        this.contacts = contacts;
        this.mListener = mListener;
        ContactsRecyclerViewAdapter.context = context;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_contact, viewGroup, false);
        return new ContactsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder contactsViewHolder, int i) {
        final User contact = contacts.get(i);

        contactsViewHolder.contactName.setText(contact.getEmail());

        contactsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.CONTACTS, contactsViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.rv = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView contactName;


        ContactsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.contactsCardView);
            contactName = (TextView) itemView.findViewById(R.id.contact_name);
        }


    }

}
