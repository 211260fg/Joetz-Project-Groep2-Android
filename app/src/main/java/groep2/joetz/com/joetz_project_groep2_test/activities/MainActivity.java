package groep2.joetz.com.joetz_project_groep2_test.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.fragments.CalendarFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.InfoFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.MainFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.OnFragmentInteractionListener;
import groep2.joetz.com.joetz_project_groep2_test.fragments.UserFragment;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private MainFragment mainFragment;
    private UserFragment userFragment;
    private InfoFragment infoFragment;
    private CalendarFragment calendarFragment;


    private UserSessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        session = new UserSessionManager(getApplicationContext());
        if (!session.checkLogin()) {
            finish();
            return;
        }


        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);

        setSupportActionBar(toolbar);

        mainFragment = MainFragment.getNewInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmenPane, mainFragment).commit();

        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmenPane, UserFragment.getNewInstance()).commit();*/

        createDrawerMenu();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (mainFragment != null && mainFragment.isVisible())
                    mainFragment.toggleTranslateFAB(slideOffset);

                //mDrawerList.bringToFront();
                //drawerLayout.requestLayout();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle.syncState();


        Repository.loadItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(InteractedFragment interactedFragment, int pos) {

        if (mainFragment != null && mainFragment.isVisible()) {
            mainFragment.onFragmentInteraction(interactedFragment, pos);
        }

    }

    /*@Override
    public void onBackPressed() {

        if (mainFragment!=null && mainFragment.isVisible()) {
            mainFragment.onBackPressed();
        }

        super.onBackPressed();
    }*/

    public void createDrawerMenu() {

        try {
            navView = (NavigationView) findViewById(R.id.nav_view);

            navView.setNavigationItemSelectedListener(this);
            View headerView = navView.getHeaderView(0);

            RelativeLayout drawerHeader = (RelativeLayout) headerView.findViewById(R.id.drawer_header);
            drawerHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navView.setCheckedItem(R.id.nav_profile);
                    onNavigationItemSelected(navView.getMenu().findItem(R.id.nav_profile));
                }
            });


            ImageView profilephoto = (ImageView) headerView.findViewById(R.id.user_profile_photo);
            TextView profilename = (TextView) headerView.findViewById(R.id.user_profile_name);
            TextView profiledetails = (TextView) headerView.findViewById(R.id.user_profile_details);

            Glide.with(this).load(Repository.getCurrentUser().getProfileimage()).centerCrop().into(profilephoto);
            profilename.setText(Repository.getCurrentUser().getFirstname() + " " + Repository.getCurrentUser().getLastname());
            //profilename.setText(Repository.getCurrentUser().getEmail());
            profiledetails.setText(Repository.getCurrentUser().getEmail());

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_profile:
                if (userFragment == null || !userFragment.isVisible()) {
                    userFragment = UserFragment.getNewInstance();
                    transaction.replace(R.id.fragmenPane, userFragment);
                    transaction.commit();
                }
                break;
            case R.id.nav_vacations:
                if (mainFragment == null || !mainFragment.isVisible()) {
                    mainFragment = MainFragment.getNewInstance();
                    transaction.replace(R.id.fragmenPane, mainFragment);
                    transaction.commit();
                }
                break;
            case R.id.nav_activities:
                if (calendarFragment == null || !calendarFragment.isVisible()) {
                    calendarFragment = CalendarFragment.getNewInstance();
                    transaction.replace(R.id.fragmenPane, calendarFragment);
                    transaction.commit();
                }
                break;
            case R.id.nav_info:
                if (infoFragment == null || !infoFragment.isVisible()) {
                    infoFragment = InfoFragment.getNewInstance();
                    transaction.replace(R.id.fragmenPane, infoFragment);
                    transaction.commit();
                }
                break;
            /*case R.id.nav_settings:
                break;*/
            case R.id.nav_logout:
                Repository.logoutUser();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
