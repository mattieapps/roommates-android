package com.mattieapps.roommates;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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
                int price = Integer.parseInt(mCheckPriceEditText.getText().toString());
                int gratuity = Integer.parseInt(mGratuityEditText.getText().toString());
                int output = price * gratuity / 100;

                mOutputTipsText.setText("Tip Amount:\n\n" + Integer.toString(output));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //mTipsCalFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        return fragmentView;
    }
}
