package com.mattieapps.roommates.systems;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 12/20/14.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme();
        super.onCreate(savedInstanceState);
    }

    public void setAppTheme() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String themePref = preferences.getString("customAppColor", "RoomMates");

        switch (themePref) {
            case "Black":
                setTheme(R.style.AppTheme);
                break;
            case "White":
                setTheme(R.style.AppTheme_White);
                break;
            case "Red":
                setTheme(R.style.AppTheme_Red);
                break;
            case "Pink":
                setTheme(R.style.AppTheme_Pink);
                break;
            case "Purple":
                setTheme(R.style.AppTheme_Purple);
                break;
            case "Blue":
                setTheme(R.style.AppTheme_Blue);
                break;
            case "Teal":
                setTheme(R.style.AppTheme_Teal);
                break;
            case "Green":
                setTheme(R.style.AppTheme_Green);
                break;
            case "Amber":
                setTheme(R.style.AppTheme_Amber);
                break;
            case "Blue Grey":
                setTheme(R.style.AppTheme_BlueGray);
                break;
            case "RoomMates":
                setTheme(R.style.AppTheme_RoomMates);
                break;
            default:
                setTheme(R.style.AppTheme_RoomMates);
        }
    }
}
