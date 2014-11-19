package com.mattieapps.roommates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by andrewmattie on 11/10/14.
 */
public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
