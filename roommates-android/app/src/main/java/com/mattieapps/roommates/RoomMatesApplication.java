package com.mattieapps.roommates;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by andrewmattie on 1/23/15.
 */
public class RoomMatesApplication extends Application {

    public void onCreate() {
        Parse.initialize(this, getResources().getString(R.string.PARSE_APP_ID_POS1), getResources().getString(R.string.PARSE_CLIENT_KEY_POS2));
    }

}
