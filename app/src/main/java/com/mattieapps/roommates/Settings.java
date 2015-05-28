package com.mattieapps.roommates;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by andrewmattie on 12/16/14.
 */
public class Settings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Legacy);
        super.onCreate(savedInstanceState);
        setTitle("Settings");
        addPreferencesFromResource(R.xml.settings);
    }
}
