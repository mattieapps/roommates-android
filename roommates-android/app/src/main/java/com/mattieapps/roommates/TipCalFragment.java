package com.mattieapps.roommates;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by andrewmattie on 11/28/14.
 */
public class TipCalFragment extends Fragment {

    Button mPickGratuityBtn;
    ImageButton mTipsCalFab;
    TextView mOutputTipsText;
    EditText mGratuityEditText, mCheckPriceEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipcal, container, false);

        mTipsCalFab = (ImageButton) view.findViewById(R.id.tipsCalFabButton);

        mCheckPriceEditText = (EditText) view.findViewById(R.id.priceEditText);
        mGratuityEditText = (EditText) view.findViewById(R.id.gratuityEditText);

        mOutputTipsText = (TextView) view.findViewById(R.id.outputTipsTextView);

        mPickGratuityBtn = (Button) view.findViewById(R.id.pickGratuityBtn);

        mTipsCalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = Integer.parseInt(mCheckPriceEditText.getText().toString());
                int gratuity = Integer.parseInt(mGratuityEditText.getText().toString());
                int output = price * gratuity/100;

                mOutputTipsText.setText("Tip Amount:\n\n" + Integer.toString(output));
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //mTipsCalFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        mPickGratuityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuityDialog();
            }
        });

        return view;
    }

    private void gratuityDialog() {
        new MaterialDialog.Builder(getActivity())
                .title("Gratuity amount")
                .customView(R.layout.dialog_gratuity)
                .theme(Theme.LIGHT)  // the default is light, so you don't need this line
                .positiveText("Set")  // the default for textual dialogs (not list or custom view dialogs) is 'OK'
                .negativeText("Cancel")  // leaving this line out will remove the negative button
                .build()
                .show();
    }
}
