package com.mattieapps.roommates;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.mattieapps.roommates.fragments.mates.NewExpenseFragment;
import com.mattieapps.roommates.fragments.mates.NewMateFragment;
import com.mattieapps.roommates.fragments.mates.ViewExpenseFragment;
import com.mattieapps.roommates.fragments.mates.ViewMateFragment;
import com.mattieapps.roommates.systems.BaseActivity;

/**
 * Created by andrewmattie on 4/1/15.
 */
public class VPFragmentActivity extends BaseActivity {

    private int fragmentToLoad;
    private MattieCommonObjects mattieCommonObjects;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar mToolbar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpfragment);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mattieCommonObjects = new MattieCommonObjects(getApplicationContext(), fragmentManager, fragmentTransaction);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_close);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        intent = getIntent();
        fragmentToLoad = intent.getIntExtra("fragmentToLoad", 0);

        switch (fragmentToLoad) {
            case 0:
                //Get intent vals
//                Send in bundle
                String mateName = intent.getStringExtra("matelistfragment_nameName");
                String mateEmail = intent.getStringExtra("matelistfragment_mateEmail");
                long mateId = intent.getLongExtra("matelistfragment_mateId", 0);

                ViewMateFragment mateFragment = new ViewMateFragment();

                Bundle viewMateBundle = new Bundle();
                viewMateBundle.putString("mateName", mateName);
                viewMateBundle.putString("mateEmail", mateEmail);
                viewMateBundle.putLong("mateId", mateId);
                mateFragment.setArguments(viewMateBundle);

                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, mateFragment);
                break;
            case 1:
                NewMateFragment newMateFragment = new NewMateFragment();
                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, newMateFragment);
                break;
            case 2:
                NewExpenseFragment newExpenseFragment = new NewExpenseFragment();

                Bundle newExpenseBundle = new Bundle();
                newExpenseBundle.putString("involvedMate", intent.getStringExtra("involvedMate"));
                newExpenseFragment.setArguments(newExpenseBundle);

                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, newExpenseFragment);
                break;
            case 3:
                ViewExpenseFragment viewExpenseFragment = new ViewExpenseFragment();

                String name = intent.getStringExtra("expenseName");
                String price = intent.getStringExtra("expensePrice");
                String status = intent.getStringExtra("expenseStatus");
                String involvedParties = intent.getStringExtra("expenseParties");
                long expenseId = intent.getLongExtra("expenseId", 0);

                Bundle viewExpenseBundle = new Bundle();
                viewExpenseBundle.putString("expenseName", name);
                viewExpenseBundle.putString("expensePrice", price);
                viewExpenseBundle.putString("expenseStatus", status);
                viewExpenseBundle.putString("expenseParties", involvedParties);
                viewExpenseBundle.putLong("expenseId", expenseId);
                viewExpenseBundle.putInt("expenseRowIndex", intent.getIntExtra("expenseRowIndex", 0));

                viewExpenseFragment.setArguments(viewExpenseBundle);

                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, viewExpenseFragment);
                break;
            default:
                fragmentToLoad = 0;
        }

    }
}
