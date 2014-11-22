package com.mattieapps.roommates;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends BaseActivity {

    EditText mRentPriceText, mPeopleCountText;
    Button mRentUpBtn, mRentDownBtn, mPeopleUpBtn, mPeopleDownBtn;

    int rentPrice = 0;
    int peopleCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_flip))
                .withButtonColor(Color.WHITE)
                .withGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .withMargins(0, 0, 0, 16)
                .create();

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipCalActivity.class);
                startActivity(intent);
            }
        });

        mRentPriceText = (EditText) findViewById(R.id.rentPriceText);
        mPeopleCountText = (EditText) findViewById(R.id.peopleAmountText);

        mRentUpBtn = (Button) findViewById(R.id.rentUpBtn);
        mRentDownBtn = (Button) findViewById(R.id.rentDownBtn);
        mPeopleUpBtn = (Button) findViewById(R.id.peopleUpBtn);
        mPeopleDownBtn = (Button) findViewById(R.id.peopleDownBtn);


        mRentUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRentPriceText.getText().toString().equals("")){
                    mRentPriceText.setText("0");
                } else {
                    rentPrice = Integer.parseInt(mRentPriceText.getText().toString());
                    int output = rentPrice + 1;
                    mRentPriceText.setText(String.valueOf(output));//Convert out to string
                }
            }
        });

        mRentDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Integer.parseInt(mRentPriceText.getText().toString());
                int output = rentPrice-1;
                mRentPriceText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountText.getText().toString());
                int output = peopleCount+1;
                mPeopleCountText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountText.getText().toString());
                int output = peopleCount-1;
                mPeopleCountText.setText(String.valueOf(output));//Convert out to string
            }
        });
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

        return super.onOptionsItemSelected(item);
    }
}
