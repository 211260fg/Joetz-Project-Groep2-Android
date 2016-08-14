package groep2.joetz.com.joetz_project_groep2_test.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.fragments.OnFragmentInteractionListener;
import groep2.joetz.com.joetz_project_groep2_test.model.Vakantie;

/**
 * Created by floriangoeteyn on 04-Jun-16.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {


    private List<Vakantie> items = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private static Context context;

    private RecyclerView rv;


    //doorgeven van de fragmentInteractionListener en items
    public RecyclerViewAdapter(List<Vakantie> items, OnFragmentInteractionListener mListener, Context context) {
        this.items = items;
        this.mListener = mListener;
        RecyclerViewAdapter.context = context;
    }

    public List<Vakantie> getItems() {
        return items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_vacation, viewGroup, false);
        return new ItemViewHolder(v);
    }


    public void setItems(List<Vakantie> items) {
        this.items = items;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int i) {

        //pas text en omschrijving in de itemviewholder aan voor elk item
        final Vakantie item = items.get(i);

        itemViewHolder.itemTitle.setText(item.getTitle());
        itemViewHolder.itemLocation.setText(item.getLocation());
        itemViewHolder.itemAge.setText("van " + item.getMinAge() + " t.e.m. " + item.getMaxAge() + " jaar");
        if (item.getStartDate() != null)
            itemViewHolder.itemStartDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(item.getStartDate()));
        if (item.getStartDate() != null)
            itemViewHolder.itemEndDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(item.getEndDate()));

            Glide.with(context).load(item.getHeader_img()).override(500, 200).centerCrop().into(itemViewHolder.itemImage);
            Glide.with(context).load(item.getCategory().getImg_src()).override(50, 50).centerCrop().into(itemViewHolder.itemCategory);

        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(OnFragmentInteractionListener.InteractedFragment.VACATIONS, itemViewHolder.getAdapterPosition());
            }
        });
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.rv = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    //itemviewholder: custom layout voor items
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView itemTitle;
        TextView itemLocation;
        TextView itemAge;
        TextView itemStartDate;
        TextView itemEndDate;
        ImageView itemImage;
        ImageView itemCategory;


        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.itemCardView);
            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            itemLocation = (TextView) itemView.findViewById(R.id.itemlocation);
            itemAge = (TextView) itemView.findViewById(R.id.itemage);
            itemStartDate = (TextView) itemView.findViewById(R.id.itemstartdate);
            itemEndDate = (TextView) itemView.findViewById(R.id.itemenddate);
            itemImage = (ImageView) itemView.findViewById(R.id.itemimage);
            itemCategory = (ImageView) itemView.findViewById(R.id.itemCategory);
        }


    }
}
