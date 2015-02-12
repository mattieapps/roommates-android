package com.mattieapps.roommates.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 1/21/15.
 */
public class BitcoinFragment extends Fragment {

    TextView mPriceOfCoinsText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_bitcoin, container, false);

        mPriceOfCoinsText = (TextView) fragmentView.findViewById(R.id.priceOfCoinText);

        StringBuilder stringBuilder = new StringBuilder("https://bitpay.com/rates/");
        stringBuilder.append("usd");

        return fragmentView;
    }
}
