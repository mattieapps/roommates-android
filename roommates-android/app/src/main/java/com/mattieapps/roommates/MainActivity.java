package com.mattieapps.roommates;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerListLayout;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    private RentCalFragment homeFragment;
    private TipCalFragment aboutFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String[] nav_drawer_items = null;
    private int[] nav_drawer_icons = null;

    private RelativeLayout drawer_list_item;

    int isFragmentNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_drawer_items = new String[] {
                "Rent Calculator",
                "Tip Calculator",
               // "Settings"
        };

        nav_drawer_icons = new int[] {
                R.drawable.ic_home,
                R.drawable.ic_calculator,
               // R.drawable.ic_user
        };

        homeFragment = new RentCalFragment();
        aboutFragment = new TipCalFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, homeFragment);
        fragmentTransaction.commit();

        //Begin Main Nav Drawer Code

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mTitle = getResources().getString(R.string.app_name);

        mItemTitles = nav_drawer_items;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerListLayout = (LinearLayout) findViewById(R.id.left_drawer_layout);

        // Set the adapter for the list view
        DrawerListAdapter listAdapter = new DrawerListAdapter(getApplicationContext(), nav_drawer_items, nav_drawer_icons);
        mDrawerList.setAdapter(listAdapter);

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        // Handle your other action bar items...
        int id = item.getItemId();
        if (isFragmentNumber == 0) {
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_reset) {
                EditText rentprice = (EditText) findViewById(R.id.rentPriceText);
                EditText numbpeople = (EditText) findViewById(R.id.peopleAmountText);
                TextView output = (TextView) findViewById(R.id.outputTextView);

                rentprice.setText("");
                numbpeople.setText("");
                output.setText("");
            }
        }
        else if (isFragmentNumber == 1) {
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_reset) {
                EditText price = (EditText) findViewById(R.id.priceEditText);
                EditText gratuity = (EditText) findViewById(R.id.gratuityEditText);
                TextView output = (TextView) findViewById(R.id.outputTipsTextView);

                price.setText("");
                gratuity.setText("");
                output.setText("");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mItemTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerListLayout);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            switch (position){
                case 0:
                    fragmentTransaction.replace(R.id.content_frame, homeFragment);
                    fragmentTransaction.commit();

                    isFragmentNumber = 0;
                    break;
                case 1:
                    fragmentTransaction.replace(R.id.content_frame, aboutFragment);
                    fragmentTransaction.commit();

                    isFragmentNumber = 1;
                    break;
                case 2:
                    //TODO: Settings intent
                    break;

                default: position = 0;
                    break;
            }
        }
    }

}