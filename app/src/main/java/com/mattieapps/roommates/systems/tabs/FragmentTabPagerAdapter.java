package com.mattieapps.roommates.systems.tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mattieapps.roommates.fragments.mates.pages.ExpenseListFragment;
import com.mattieapps.roommates.fragments.mates.pages.MateListFragment;

/**
 * Created by andrewmattie on 3/23/15.
 */
public class FragmentTabPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Mates", "All Expenses"};
    private Context context;

    public FragmentTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MateListFragment();
            case 1:
                return new ExpenseListFragment();
            default:
                return new MateListFragment();
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}