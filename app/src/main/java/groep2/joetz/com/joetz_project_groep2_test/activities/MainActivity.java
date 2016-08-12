package groep2.joetz.com.joetz_project_groep2_test.activities;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import groep2.joetz.com.joetz_project_groep2_test.R;
import groep2.joetz.com.joetz_project_groep2_test.adapter.MyFragmentPagerAdapter;
import groep2.joetz.com.joetz_project_groep2_test.fragments.ChatFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.ContainerFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.HistoryFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.HollydaysFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.LoginFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.MainFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.OnFragmentInteractionListener;
import groep2.joetz.com.joetz_project_groep2_test.fragments.UserFragment;
import groep2.joetz.com.joetz_project_groep2_test.fragments.VacationFragment;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private MainFragment mainFragment;
    private UserFragment userFragment;

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

                mDrawerList.bringToFront();
                drawerLayout.requestLayout();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(int pos) {

        if (mainFragment != null && mainFragment.isVisible()) {
            mainFragment.onFragmentInteraction(pos);
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
        mDrawerList = (ListView) drawerLayout.findViewById(R.id.left_drawer);
        addDrawerItems();

    }



    //TODO UITWERKEN
    private void addDrawerItems() {
        String username = UserSessionManager.getUserDetails().get(UserSessionManager.KEY_NAME);
        //String username = UserSessionManager.getCurrentUser().getUsername();
        final String[] osArray = {username, "Vakanties", "Activiteiten", "Meer info", "Instellingen", "Log uit"};
        mAdapter = new ArrayAdapter<>(this, R.layout.layout_drawer_listitem, R.id.list_content, osArray);

        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this, osArray[position] + " clicked", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        if (userFragment == null || !userFragment.isVisible()) {
                            userFragment = UserFragment.getNewInstance();
                            transaction.replace(R.id.fragmenPane, userFragment);
                            transaction.commit();
                        }
                        break;

                    case 1:
                        if (mainFragment == null || !mainFragment.isVisible()) {
                            mainFragment = MainFragment.getNewInstance();
                            transaction.replace(R.id.fragmenPane, mainFragment);
                            transaction.commit();
                        }
                        break;

                    case 5:
                        Repository.logoutUser();
                        break;
                }
            }
        });

    }

}
