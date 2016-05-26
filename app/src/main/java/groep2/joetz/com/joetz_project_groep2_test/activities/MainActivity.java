package groep2.joetz.com.joetz_project_groep2_test.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.fragments.HollydaysFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private AlertDialog dialog;

    private FloatingActionButton mFAB;
    private FloatingActionMenu mFABMenu;
    private static final String TAG_SORT_AGE = "sortAge";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_EXTRA = "sortExtra";


    private final CharSequence[] periodes = {"Zomervakantie", "Herfstvakantie", "Kerstvakantie"};
    private final Pair<String, CharSequence[]> filter_periode = new Pair<>("Periode", periodes);
    private final CharSequence[] extras = {"Themavakanties ", "Inleefreizen ", "Zee ", "Buitenland ", "Ardennen ", "Taalstages ", "Gezondheidsvakanties "};
    private final Pair<String, CharSequence[]> filter_extra = new Pair<>("Extra filter", extras);


    protected FrameLayout singlePaneLayout;

    private HollydaysFragment hollydaysFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singlePaneLayout = (FrameLayout) findViewById(R.id.fragmentlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle.syncState();


        if (singlePaneLayout != null && (hollydaysFragment == null || !(hollydaysFragment.isVisible())))
            hollydaysFragment = HollydaysFragment.getNewInstance();

        if (singlePaneLayout != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentlayout, hollydaysFragment).commit();

        setupFAB();

    }


    private void setupFAB() {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(this);
        iconFAB.setImageResource(R.drawable.ic_search_white_24dp);

        //set the appropriate background for the main floating action button along with its icon
        mFAB = new FloatingActionButton.Builder(this)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.floatingactionbutton)
                .build();

        //define the icons for the sub action buttons
        ImageView iconSortAge = new ImageView(this);
        iconSortAge.setImageResource(R.drawable.ic_action_age);
        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);
        ImageView iconSortExtra = new ImageView(this);
        iconSortExtra.setImageResource(R.drawable.ic_action_important);

        //set the background for all the sub buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));


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
        mFABMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortAge)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortExtra)
                .attachTo(mFAB)
                .build();
    }

    private void toggleTranslateFAB(float slideOffset) {
        if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            mFAB.setTranslationX(slideOffset * 200);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int pos) {

    }


    private AlertDialog.Builder builder;

    private void buildFilterDialog(String title, CharSequence[] items) {

        // arraylist to keep the selected items
        final ArrayList seletedItems = new ArrayList();
        builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogCustom))
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

        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);
        builder = new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogCustom))
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


    private class OnFABClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

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
