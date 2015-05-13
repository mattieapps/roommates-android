package com.mattieapps.roommates.fragments.mates;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.systems.tabs.FragmentTabPagerAdapter;
import com.mattieapps.roommates.systems.tabs.SlidingTabLayout;

/**
 * Created by andrewmattie on 2/20/15.
 */
public class MateFragment extends Fragment {

    private ActionBar mActionBar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String themePref;
    private boolean isTablet;
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_financetracker_mate, container, false);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) fragmentView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentTabPagerAdapter(getChildFragmentManager(),
                getActivity()));

        // Give the SlidingTabLayout the ViewPager
        slidingTabLayout = (SlidingTabLayout) fragmentView.findViewById(R.id.sliding_tabs);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        themePref = preferences.getString("customAppColor", "RoomMates");

        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                switch (themePref) {
                    case "Black":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.black_900));
                            return getResources().getColor(R.color.standard_gray);
                        }
                    case "White":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.gray_400));
                            return Color.BLACK;
                        }
                    case "Red":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.red_500));
                            return getResources().getColor(R.color.red_700);
                        }
                    case "Pink":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.pink_500));
                            return getResources().getColor(R.color.pink_700);
                        }
                    case "Purple":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.purple_500));
                            return getResources().getColor(R.color.purple_700);
                        }
                    case "Blue":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue_500));
                            return getResources().getColor(R.color.blue_accent);
                        }
                    case "Teal":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.teal_500));
                            return getResources().getColor(R.color.teal_accent);
                        }
                    case "Green":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.green_500));
                            return getResources().getColor(R.color.green_700);
                        }
                    case "Amber":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.amber_500));
                            return getResources().getColor(R.color.amber_accent);
                        }
                    case "Blue Grey":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.bluegrey_500));
                            return getResources().getColor(R.color.bluegrey_accent);
                        }
                    case "RoomMates":
                        if (!isTablet) {
                            slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.roommates_500));
                            return getResources().getColor(R.color.roommates_accent);
                        }
                    default:
                        return getResources().getColor(R.color.black_900);
                }
            }
        });

        if (isTablet) {
            slidingTabLayout.setBackgroundColor(Color.parseColor("#CFD8DC"));
            slidingTabLayout.setViewPager(viewPager, getResources().getColor(R.color.black_900));
        }
        if (!isTablet) {
            if (themePref.equals("White") || themePref.equals("Green") || themePref.equals("Amber") || themePref.equals("RoomMates")) {
                slidingTabLayout.setViewPager(viewPager, Color.BLACK);
            } else {
                slidingTabLayout.setViewPager(viewPager, Color.WHITE);
            }
        }

        return fragmentView;
    }

}
