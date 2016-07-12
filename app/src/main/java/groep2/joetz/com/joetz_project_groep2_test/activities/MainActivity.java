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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

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
import groep2.joetz.com.joetz_project_groep2_test.fragments.VacationFragment;
import groep2.joetz.com.joetz_project_groep2_test.repository.Repository;
import groep2.joetz.com.joetz_project_groep2_test.session.UserSessionManager;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private AlertDialog dialog;

    private MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserSessionManager session = new UserSessionManager(getApplicationContext());
        if(!session.checkLogin()&& LoginFragment.testIsLoggedIn) {
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



        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(mainFragment!=null&&mainFragment.isVisible())
                    mainFragment.toggleTranslateFAB(slideOffset);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle.syncState();

        //createTestDataFromAssets();
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

        if(mainFragment!=null && mainFragment.isVisible()){
            mainFragment.onFragmentInteraction(pos);
        }

    }

    @Override
    public void onBackPressed() {

        if (mainFragment!=null && mainFragment.isVisible()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            mainFragment.onBackPressed();
        }

        super.onBackPressed();
    }


    //TODO: TEST METHOD FOR TEMP DATA
    private void createTestDataFromAssets(){
        AssetManager assetManager = getAssets();
            InputStream input;
            try {
                input = assetManager.open("testdata");

                int size = input.available();
                byte[] buffer = new byte[size];
                input.read(buffer);
                input.close();

                String text = new String(buffer);

                Repository.loadTestData(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
