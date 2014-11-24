package com.mattieapps.roommates;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by andrewmattie on 11/19/14.
 */
public class TipCalActivity extends BaseActivity {

    Button mPickGratuityBtn;
    ImageButton mSwitchFab, mTipsCalFab;
    TextView mOutputTipsText;
    EditText mGratuityEditText, mCheckPriceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        mSwitchFab = (ImageButton) findViewById(R.id.tipsFabButton);
        mTipsCalFab = (ImageButton) findViewById(R.id.tipsCalFabButton);

        mCheckPriceEditText = (EditText) findViewById(R.id.priceEditText);
        mGratuityEditText = (EditText) findViewById(R.id.gratuityEditText);

        mOutputTipsText = (TextView) findViewById(R.id.outputTipsTextView);

        mPickGratuityBtn = (Button) findViewById(R.id.pickGratuityBtn);


        mSwitchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipCalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
            mSwitchFab.setBackground(getDrawable(R.drawable.circle_21));
            mTipsCalFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        mPickGratuityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuityDialog();
            }
        });
    }

    private void gratuityDialog() {
        new MaterialDialog.Builder(this)
                .title("Gratuity amount")
                .customView(R.layout.gratuity_dialog)
                .theme(Theme.LIGHT)  // the default is light, so you don't need this line
                .positiveText("Set")  // the default for textual dialogs (not list or custom view dialogs) is 'OK'
                .negativeText("Cancel")  // leaving this line out will remove the negative button
                .build()
                .show();
    }
}
