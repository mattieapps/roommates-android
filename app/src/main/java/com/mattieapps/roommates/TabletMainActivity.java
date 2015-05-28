package com.mattieapps.roommates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mattieapps.roommates.fragments.AboutFragment;
import com.mattieapps.roommates.fragments.LoanCalFragment;
import com.mattieapps.roommates.fragments.RentCalFragment;
import com.mattieapps.roommates.fragments.TipCalFragment;
import com.mattieapps.roommates.fragments.mates.MateFragment;
import com.mattieapps.roommates.systems.BaseActivity;
import com.mattieapps.roommates.systems.MattieCommonObjects;
import com.mattieapps.roommates.systems.adapters.DrawerListAdapter;

/**
 * Created by andrewmattie on 3/13/15.
 */
public class TabletMainActivity extends BaseActivity {

    private String[] mItemTitles;
    private String[] mSubItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ListView mSubDrawerList;
    private RelativeLayout mDrawerListLayout;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar, mCustomToolbar;

    private RentCalFragment rentCalFragment;
    private TipCalFragment tipCalFragment;
    private AboutFragment aboutFragment;
    private MateFragment mateFragment;
    private LoanCalFragment loanCalFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String[] sub_nav_drawer_items;
    private int[] sub_nav_drawer_icons;
    private String[] nav_drawer_items;
    private int[] nav_drawer_icons;

    private RelativeLayout drawer_list_item;

    private MattieCommonObjects mattieCommonObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mattieCommonObjects = new MattieCommonObjects(getApplicationContext(), fragmentManager, fragmentTransaction);

        nav_drawer_items = new String[]{
                "Expenses",
                "Rent Calculator",
                "Tip Calculator",
                "Loan Calculator"
        };

        nav_drawer_icons = new int[]{
                R.drawable.ic_home,
                R.drawable.ic_calculator,
                R.drawable.ic_calculator,
                R.drawable.ic_calculator,
        };

        sub_nav_drawer_items = new String[]{
                "About",
                "Share",
                "Settings"
        };

        sub_nav_drawer_icons = new int[]{
                R.drawable.ic_info,
                android.R.drawable.ic_menu_share,
                R.drawable.ic_action_settings
        };

        rentCalFragment = new RentCalFragment();
        tipCalFragment = new TipCalFragment();
        aboutFragment = new AboutFragment();
        mateFragment = new MateFragment();
        loanCalFragment = new LoanCalFragment();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String startFragment = preferences.getString("customHomeScreen", "Expenses");

        Intent goToExpensesIntent = getIntent();
        boolean gte = goToExpensesIntent.getBooleanExtra("goToExpenses", false);
        if (gte) {
            mattieCommonObjects.fragmentMethod(mateFragment);
        } else {
            switch (startFragment) {
                case "Expenses":
                    mattieCommonObjects.fragmentMethod(mateFragment);
                    break;
                case "Rent":
                    mattieCommonObjects.fragmentMethod(rentCalFragment);
                    break;
                case "Tip":
                    mattieCommonObjects.fragmentMethod(tipCalFragment);
                    break;
                case "Loan Calculator":
                    mattieCommonObjects.fragmentMethod(loanCalFragment);
                    break;
                default:
                    startFragment = "Expenses";
            }
        }

        //Begin Main Nav Drawer Code

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mCustomToolbar = (Toolbar) findViewById(R.id.tabletCustomToolbar);
        mCustomToolbar.setTitleTextColor(getResources().getColor(android.R.color.black));

        mTitle = getResources().getString(R.string.app_name);

        mCustomToolbar.setTitle(mTitle);

        mItemTitles = nav_drawer_items;
        mSubItemTitles = sub_nav_drawer_items;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mSubDrawerList = (ListView) findViewById(R.id.left_sub_drawer);
        mDrawerListLayout = (RelativeLayout) findViewById(R.id.left_drawer_layout);

        // Set the adapter for the list view
        DrawerListAdapter listAdapter = new DrawerListAdapter(getApplicationContext(), nav_drawer_items, nav_drawer_icons);
        DrawerListAdapter subListAdapter = new DrawerListAdapter(getApplicationContext(), sub_nav_drawer_items, sub_nav_drawer_icons);
        mDrawerList.setAdapter(listAdapter);
        mSubDrawerList.setAdapter(subListAdapter);

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mSubDrawerList.setOnItemClickListener(new SubDrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                mCustomToolbar.setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                mCustomToolbar.setTitle(mTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItemMain(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerListLayout);
    }

    private void selectItemSub(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mSubItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerListLayout);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        mCustomToolbar.setTitle(mTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItemMain(position);


            switch (position) {
                case 0:
                    mattieCommonObjects.fragmentMethod(mateFragment);
                    break;
                case 1:
                    mattieCommonObjects.fragmentMethod(rentCalFragment);
                    break;
                case 2:
                    mattieCommonObjects.fragmentMethod(tipCalFragment);
                    break;
                case 3:
                    mattieCommonObjects.fragmentMethod(loanCalFragment);
                    break;

                default:
                    position = 0;
                    break;
            }
        }
    }

    private class SubDrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItemSub(position);


            switch (position) {
                case 0:
                    mattieCommonObjects.fragmentMethod(aboutFragment);
                    break;
                case 1:
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "I found a new app that I think you'll love!");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download #RoomMates Life Assistant from the @GooglePlay store.\nbit.ly/roommatesandroid");
                    startActivity(Intent.createChooser(shareIntent, "Share RoomMates via"));
                    break;
                case 2:
                    mCustomToolbar.setTitle(getResources().getString(R.string.app_name));
                    Intent intent = new Intent(getApplication(), Settings.class);
                    startActivity(intent);
                    break;

                default:
                    position = 0;
                    break;
            }
        }
    }
}

