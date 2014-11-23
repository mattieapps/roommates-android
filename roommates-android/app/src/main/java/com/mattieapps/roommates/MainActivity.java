package com.mattieapps.roommates;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

    EditText mRentPriceEditText, mPeopleCountEditText;
    Button mRentUpBtn, mRentDownBtn, mPeopleUpBtn, mPeopleDownBtn;
    ImageButton mSwitchFab, mEqualFab;
    TextView mOutputText;

    int rentPrice = 0;
    int peopleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchFab = (ImageButton) findViewById(R.id.fabButton);
        mEqualFab = (ImageButton) findViewById(R.id.calculateFabButton);
        mOutputText = (TextView) findViewById(R.id.outputTextView);

        mSwitchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipCalActivity.class);
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mSwitchFab.setBackground(getDrawable(R.drawable.circle_21));
            mEqualFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        mRentPriceEditText = (EditText) findViewById(R.id.rentPriceText);
        mPeopleCountEditText = (EditText) findViewById(R.id.peopleAmountText);

        mRentUpBtn = (Button) findViewById(R.id.rentUpBtn);
        mRentDownBtn = (Button) findViewById(R.id.rentDownBtn);
        mPeopleUpBtn = (Button) findViewById(R.id.peopleUpBtn);
        mPeopleDownBtn = (Button) findViewById(R.id.peopleDownBtn);


        mRentUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRentPriceEditText.getText().toString().equals("")){
                    mRentPriceEditText.setText("0");
                } else {
                    rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                    int output = rentPrice + 1;
                    mRentPriceEditText.setText(String.valueOf(output));//Convert out to string
                }
            }
        });

        mRentDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                int output = rentPrice-1;
                mRentPriceEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());
                int output = peopleCount+1;
                mPeopleCountEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());
                int output = peopleCount-1;
                mPeopleCountEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mEqualFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());

                int output = rentPrice / peopleCount;
                mOutputText.setText("Output:\n\n" + output);
            }
        });

        closeKeyboard();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset){
            mRentPriceEditText.setText("0");
            mPeopleCountEditText.setText("0");
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mRentPriceEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(mPeopleCountEditText.getWindowToken(), 0);
    }
}
