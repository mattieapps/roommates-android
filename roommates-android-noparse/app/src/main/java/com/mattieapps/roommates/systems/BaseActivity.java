package com.mattieapps.roommates.systems;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 12/20/14.
 */
public abstract class BaseActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme();

        super.onCreate(savedInstanceState);
    }

    public void setAppTheme(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String themePref = preferences.getString("customAppColor", "Black");

        if (themePref.equals("Black")){
            setTheme(R.style.AppTheme);
        }
        if (themePref.equals("White")){
            setTheme(R.style.AppTheme_White);
        }
        if (themePref.equals("Red")){
            setTheme(R.style.AppTheme_Red);
        }
        if (themePref.equals("Pink")){
            setTheme(R.style.AppTheme_Pink);
        }
        if (themePref.equals("Purple")){
            setTheme(R.style.AppTheme_Purple);
        }
        if (themePref.equals("Blue")){
            setTheme(R.style.AppTheme_Blue);
        }
        if (themePref.equals("Teal")){
            setTheme(R.style.AppTheme_Teal);
        }
        if (themePref.equals("Green")){
            setTheme(R.style.AppTheme_Green);
        }
        if (themePref.equals("Amber")){
            setTheme(R.style.AppTheme_Amber);
        }
    }
}
