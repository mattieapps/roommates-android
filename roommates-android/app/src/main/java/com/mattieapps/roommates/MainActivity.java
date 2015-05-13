package com.mattieapps.roommates;

import android.content.Intent;
import android.os.Bundle;

import com.mattieapps.roommates.systems.BaseActivity;


public class MainActivity extends BaseActivity {

    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        Intent intent = getIntent();
        boolean gie = intent.getBooleanExtra("goToExpenses", false);
        if (gie) {
            if (!isTablet) {
                Intent phoneIntent = new Intent(getApplicationContext(), PhoneMainActivity.class);
                phoneIntent.putExtra("goToExpenses", true);
                startActivity(phoneIntent);
            } else {
                Intent tabletIntent = new Intent(getApplicationContext(), TabletMainActivity.class);
                tabletIntent.putExtra("goToExpenses", true);
                startActivity(tabletIntent);
            }
        } else {
            if (!isTablet) {
                Intent phoneIntent = new Intent(getApplicationContext(), PhoneMainActivity.class);
                phoneIntent.putExtra("goToExpenses", false);
                startActivity(phoneIntent);
            } else {
                Intent tabletIntent = new Intent(getApplicationContext(), TabletMainActivity.class);
                tabletIntent.putExtra("goToExpenses", false);
                startActivity(tabletIntent);
            }
        }
    }
}