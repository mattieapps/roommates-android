package com.mattieapps.roommates;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by andrewmattie on 11/19/14.
 */
public class TipCalActivity extends BaseActivity {

    EditText mCheckAmountEditText, mGratuityEditText;
    Button mPickGratuityBtn;
    ImageButton mSwitchFab, mCalculateTipFab;
    TextView mOutputText;
    RadioGroup mGratuityRadioGroup;
    RadioButton mGratuityRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        mSwitchFab = (ImageButton) findViewById(R.id.tipsFabButton);
        mCalculateTipFab = (ImageButton) findViewById(R.id.calculateTipFabButton);

        mCheckAmountEditText = (EditText) findViewById(R.id.priceEditText);
        mGratuityEditText = (EditText) findViewById(R.id.gratuityEditText);

        mOutputText = (TextView) findViewById(R.id.outputTipTextView);

        mGratuityRadioGroup = (RadioGroup) findViewById(R.id.gratuityRadioGroup);

        mSwitchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipCalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mSwitchFab.setBackground(getDrawable(R.drawable.circle_21));
            mCalculateTipFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        mPickGratuityBtn = (Button) findViewById(R.id.pickGratuityBtn);
        mPickGratuityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gratuityDialog();
            }
        });

        mCalculateTipFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gratuity = Integer.parseInt(mGratuityEditText.getText().toString());
                int checkAmount = Integer.parseInt(mCheckAmountEditText.getText().toString());
                int output = checkAmount * gratuity/100;
                mOutputText.setText("Output:\n\n" + Integer.toString(output));
            }
        });
    }

    private void gratuityDialog() {
        new MaterialDialog.Builder(this)
                .title("Gratuity amount")
                .customView(R.layout.gratuity_dialog)
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog materialDialog) {

                    }

                    @Override
                    public void onNegative(MaterialDialog materialDialog) {}
                })
                .theme(Theme.LIGHT)  // the default is light, so you don't need this line
                .positiveText("Set")  // the default for textual dialogs (not list or custom view dialogs) is 'OK'
                .negativeText("Cancel")  // leaving this line out will remove the negative button
                .build()
                .show();
    }
}
