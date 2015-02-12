package com.mattieapps.roommates.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 11/28/14.
 */
public class TipCalFragment extends Fragment {

    ImageButton mTipsCalFab;
    TextView mOutputTipsText;
    EditText mGratuityEditText, mCheckPriceEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_tipcal, container, false);

        mTipsCalFab = (ImageButton) fragmentView.findViewById(R.id.tipsCalFabButton);

        mCheckPriceEditText = (EditText) fragmentView.findViewById(R.id.priceEditText);
        mGratuityEditText = (EditText) fragmentView.findViewById(R.id.gratuityEditText);

        mOutputTipsText = (TextView) fragmentView.findViewById(R.id.outputTipsTextView);

        mTipsCalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = Integer.parseInt(mCheckPriceEditText.getText().toString());
                double gratuity = Integer.parseInt(mGratuityEditText.getText().toString());

                if (price != 0 && gratuity != 0) {
                    double output = price * gratuity / 100;
                    mOutputTipsText.setText("Tip Amount:\n\n" + Double.toString(output));
                } else {
                    Toast.makeText(getActivity(), "Price or Gratuity can not be Zero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTipsCalFab.setBackground(getActivity().getDrawable(R.drawable.circle_21));
        }

        return fragmentView;
    }

}