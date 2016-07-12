package groep2.joetz.com.joetz_project_groep2_test.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.MyFragmentPagerAdapter;

public class MainFragment extends Fragment  implements OnFragmentInteractionListener, TabHost.OnTabChangeListener{

    private TabHost tabHost;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    int i = 0;

    private AlertDialog dialog;
    private VacationFragment vacationFragment;

    private FloatingActionButton mFAB;
    private FloatingActionMenu mFABMenu;
    private static final String TAG_SORT_AGE = "sortAge";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_EXTRA = "sortExtra";

    private final CharSequence[] periodes = {"Zomervakantie", "Herfstvakantie", "Kerstvakantie"};
    private final Pair<String, CharSequence[]> filter_periode = new Pair<>("Periode", periodes);
    private final CharSequence[] extras = {"Themavakanties ", "Inleefreizen ", "Zee ", "Buitenland ", "Ardennen ", "Taalstages ", "Gezondheidsvakanties "};
    private final Pair<String, CharSequence[]> filter_extra = new Pair<>("Extra filter", extras);


    private View rootView;

    private OnFragmentInteractionListener mListener;



    public static MainFragment getNewInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // init tabhost
        initializeTabHost();

        // init ViewPager
        initializeViewPager();

        setupFAB();

        return rootView;
    }


    private void setupFAB() {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(getContext());
        iconFAB.setImageResource(R.drawable.ic_search_white_24dp);

        //set the appropriate background for the main floating action button along with its icon
        mFAB = new FloatingActionButton.Builder(getActivity())
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.floatingactionbutton)
                .build();

        //define the icons for the sub action buttons
        ImageView iconSortAge = new ImageView(getContext());
        iconSortAge.setImageResource(R.drawable.ic_action_age);
        ImageView iconSortDate = new ImageView(getContext());
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortExtra = new ImageView(getContext());
        iconSortExtra.setImageResource(R.drawable.ic_action_important);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button));


        //build the sub buttons
        SubActionButton buttonSortAge = itemBuilder.setContentView(iconSortAge).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortExtra = itemBuilder.setContentView(iconSortExtra).build();

        //to determine which button was clicked, set Tags on each button
        buttonSortAge.setTag(TAG_SORT_AGE);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortExtra.setTag(TAG_SORT_EXTRA);


        buttonSortAge.setOnClickListener(new OnFABClickListener());
        buttonSortDate.setOnClickListener(new OnFABClickListener());
        buttonSortExtra.setOnClickListener(new OnFABClickListener());

        //add the sub buttons to the main floating action button
        mFABMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(buttonSortAge)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortExtra)
                .attachTo(mFAB)
                .build();
    }

    public void toggleTranslateFAB(float slideOffset) {
        if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            mFAB.setTranslationX(slideOffset * 200);
        }
    }

    private void toggleFABVisibility(boolean isVisible){
        if(isVisible){
            mFAB.animate().alpha(1.0f);
            mFAB.animate().translationY(0);
            mFAB.setEnabled(true);
        }else {
            mFAB.animate().alpha(0.0f);
            mFAB.animate().translationY(mFAB.getHeight());
            mFAB.setEnabled(false);
        }
    }



    /*@Override
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
    }*/

    @Override
    public void onFragmentInteraction(int pos) {

        /*if (vacationFragment == null || !(vacationFragment.isVisible())) {

            vacationFragment = VacationFragment.getNewInstance();

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(R.id.viewPager, vacationFragment).commit();
        }*/

        if(pos>-1) {

            vacationFragment = VacationFragment.getNewInstance(pos);

            FragmentTransaction trans = getFragmentManager().beginTransaction();

            trans.replace(R.id.container_frame, vacationFragment);

            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            trans.addToBackStack(null);
            trans.commit();

            toggleFABVisibility(false);
        }else{
            getFragmentManager().popBackStackImmediate();
            toggleFABVisibility(true);
        }

    }

    public void onBackPressed() {

        if (vacationFragment!=null && vacationFragment.isVisible()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            toggleFABVisibility(true);
        }
    }


    private AlertDialog.Builder builder;

    private void buildFilterDialog(String title, CharSequence[] items) {

        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom))
                .setTitle(title)
                .setView(null)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            seletedItems.add(indexSelected);
                        } else if (seletedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                });
        createDialog();

    }

    private void buildDatePickerDialog() {

        DatePicker picker = new DatePicker(getContext());
        picker.setCalendarViewShown(false);
        builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom))
                .setTitle("Filter op leeftijd")
                .setView(picker);
        createDialog();
    }

    private void createDialog() {

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //  Your code when user clicked on OK
            }})
                .setNegativeButton("Terug", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel
                                mFABMenu.open(true);
                            }}
                )
                .setCancelable(false);

        dialog = builder.create();

        dialog.show();


        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(getResources().getColor(R.color.maintheme));
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.maintheme));
    }


    private void initializeViewPager() {
        List<android.support.v4.app.Fragment> fragments = new Vector<>();

        //fragments.add(HollydaysFragment.getNewInstance());
        fragments.add(new ContainerFragment());
        fragments.add(HistoryFragment.getNewInstance());
        fragments.add(ChatFragment.getNewInstance());

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), fragments);
        this.viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(myFragmentPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);

            }
        });


    }

    private void initializeTabHost() {
        int[] resources = {R.drawable.ic_view_list_white_24dp, R.drawable.ic_history_white_24dp, R.drawable.ic_chat_bubble_outline_white_24dp};
        tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabHost.setup();

        for (int resource: resources) {
            TabHost.TabSpec tabSpec  = tabHost.newTabSpec("Tab " + i);
            tabSpec.setIndicator("", getContext().getDrawable(resource));
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    View v = new View(getContext());
                    v.setMinimumHeight(0);
                    v.setMinimumWidth(0);
                    return v;
                }
            });
            tabHost.addTab(tabSpec);
        }

        for(int i=0; i<resources.length; i++) {
            View v = tabHost.getTabWidget().getChildAt(i);
            v.getLayoutParams().height = 200;
            v.setPadding(0,78,0,0);
        }

        tabHost.setOnTabChangedListener(this);

        TabWidget widget = tabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tabhost);
        }

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);

        HorizontalScrollView hScrollView = (HorizontalScrollView) rootView.findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }

    private class OnFABClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mFABMenu.close(true);
            if (v.getTag().equals(TAG_SORT_AGE)) {
                buildDatePickerDialog();
            } else if (v.getTag().equals(TAG_SORT_DATE)) {
                buildFilterDialog(filter_periode.first, filter_periode.second);
            } else if (v.getTag().equals(TAG_SORT_EXTRA)) {
                buildFilterDialog(filter_extra.first, filter_extra.second);
            }

        }
    }



}
