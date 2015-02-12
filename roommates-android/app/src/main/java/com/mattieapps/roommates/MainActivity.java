package com.mattieapps.roommates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mattieapps.roommates.fragments.BitcoinFragment;
import com.mattieapps.roommates.fragments.CurrencyConversionFragment;
import com.mattieapps.roommates.fragments.RentCalFragment;
import com.mattieapps.roommates.fragments.TipCalFragment;
import com.mattieapps.roommates.systems.BaseActivity;
import com.mattieapps.roommates.systems.DrawerListAdapter;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.parse.ParseInstallation;
import com.parse.ParsePush;


public class MainActivity extends BaseActivity {

    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private LinearLayout mDrawerListLayout;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    private RentCalFragment rentCalFragment;
    private TipCalFragment tipCalFragment;
    private CurrencyConversionFragment currencyConversionFragment;
    private BitcoinFragment bitcoinFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String[] nav_drawer_items;
    private int[] nav_drawer_icons;

    private RelativeLayout drawer_list_item;

    int isFragmentNumber = 0;

    int rentPriceBackup;
    int numbOfPeopleBackup;
    String rentOutputBackup;
    int checkAmountBackup;
    int gratuityBackup;
    String tipOutputBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean wantsUpdates = sharedPreferences.getBoolean("receiveNotifications", true);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        if (wantsUpdates) {
            ParsePush.subscribeInBackground("wantsUpdates");
        } else {
            ParsePush.unsubscribeInBackground("wantsUpdates");
        }

        nav_drawer_items = new String[] {
                "Rent Calculator",
                "Tip Calculator",
                "BitCoin Prices",
              //  "Currency Conversion",
                "Settings"
        };

        nav_drawer_icons = new int[] {
                R.drawable.ic_home,
                R.drawable.ic_calculator,
                R.drawable.ic_action_bitcoin,
               // 0,
                R.drawable.ic_action_settings
        };

        rentCalFragment = new RentCalFragment();
        tipCalFragment = new TipCalFragment();
        currencyConversionFragment = new CurrencyConversionFragment();
        bitcoinFragment = new BitcoinFragment();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String startFragment = preferences.getString("customHomeScreen", "Rent");

        if (startFragment.equals("Rent")) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, rentCalFragment);
            fragmentTransaction.commit();

            isFragmentNumber = 0;
        }

        if (startFragment.equals("Tip")) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, tipCalFragment);
            fragmentTransaction.commit();

            isFragmentNumber = 1;
        }

        if (startFragment.equals("Currency")) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, currencyConversionFragment);
            fragmentTransaction.commit();

            isFragmentNumber = 2;
        }

        if (startFragment.equals("BitCoin")){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, bitcoinFragment);
            fragmentTransaction.commit();

            isFragmentNumber = 3;
        }

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

                rentPriceBackup = Integer.valueOf(rentprice.getText().toString());
                numbOfPeopleBackup = Integer.valueOf(numbpeople.getText().toString());
                rentOutputBackup = output.getText().toString();
                SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text("Rent calculations cleared") // text to display
                        .actionLabel("UNDO")
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                EditText rentprice = (EditText) findViewById(R.id.rentPriceText);
                                EditText numbpeople = (EditText) findViewById(R.id.peopleAmountText);
                                TextView output = (TextView) findViewById(R.id.outputTextView);

                                rentprice.setText(String.valueOf(rentPriceBackup));
                                numbpeople.setText(String.valueOf(numbOfPeopleBackup));
                                output.setText(rentOutputBackup);
                            }
                        }), this);  // activity where it is displayed

//                Toast.makeText(getApplicationContext(), "Numbs: " + rentPriceBackup + numbOfPeopleBackup + rentOutputBackup, Toast.LENGTH_SHORT).show();

                rentprice.setText("0");
                numbpeople.setText("0");
                output.setText("Output:");
            }
        }
        else if (isFragmentNumber == 1) {
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_reset) {
                EditText price = (EditText) findViewById(R.id.priceEditText);
                EditText gratuity = (EditText) findViewById(R.id.gratuityEditText);
                TextView output = (TextView) findViewById(R.id.outputTipsTextView);

                checkAmountBackup = Integer.valueOf(price.getText().toString());
                gratuityBackup = Integer.valueOf(gratuity.getText().toString());
                tipOutputBackup = output.getText().toString();

                SnackbarManager.show(
                Snackbar.with(getApplicationContext()) // context
                        .text("Tip calculations cleared")
                        .actionLabel("UNDO")
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                EditText price = (EditText) findViewById(R.id.priceEditText);
                                EditText gratuity = (EditText) findViewById(R.id.gratuityEditText);
                                TextView output = (TextView) findViewById(R.id.outputTipsTextView);

                                price.setText(String.valueOf(checkAmountBackup));
                                gratuity.setText(String.valueOf(gratuityBackup));
                                output.setText(tipOutputBackup);
                            }
                        }), this); // activity where it is displayed

                price.setText("0");
                gratuity.setText("0");
                output.setText("Tip Amount:");
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
                    fragmentTransaction.replace(R.id.content_frame, rentCalFragment);
                    fragmentTransaction.commit();

                    isFragmentNumber = 0;
                    break;
                case 1:
                    fragmentTransaction.replace(R.id.content_frame, tipCalFragment);
                    fragmentTransaction.commit();

                    isFragmentNumber = 1;
                    break;
                case 2:
                    fragmentTransaction.replace(R.id.content_frame, bitcoinFragment);
                    fragmentTransaction.commit();

                    isFragmentNumber = 2;
                    break;
                case 3:
                    Intent intent = new Intent(getApplication(), Settings.class);
                    startActivity(intent);
                    break;

                default: position = 0;
                    break;
            }
        }
    }

}