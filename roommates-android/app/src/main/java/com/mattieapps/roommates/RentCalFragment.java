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
public class RentCalFragment extends Fragment {

    EditText mRentPriceEditText, mPeopleCountEditText;
    ImageButton mEqualFab, mRentUpBtn, mRentDownBtn, mPeopleUpBtn, mPeopleDownBtn;
    TextView mOutputText;

    int rentPrice = 0;
    int peopleCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rentcal, container, false);

        mEqualFab = (ImageButton) view.findViewById(R.id.calculateFabButton);
        mOutputText = (TextView) view.findViewById(R.id.outputTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            mEqualFab.setBackground(getDrawable(R.drawable.circle_21));
        }

        mRentPriceEditText = (EditText) view.findViewById(R.id.rentPriceText);
        mPeopleCountEditText = (EditText) view.findViewById(R.id.peopleAmountText);

        mRentUpBtn = (ImageButton) view.findViewById(R.id.rentUpBtn);
        mRentDownBtn = (ImageButton) view.findViewById(R.id.rentDownBtn);
        mPeopleUpBtn = (ImageButton) view.findViewById(R.id.peopleUpBtn);
        mPeopleDownBtn = (ImageButton) view.findViewById(R.id.peopleDownBtn);


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

        return view;
    }

    //TODO: Fix this
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_main, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_reset){
//            mRentPriceEditText.setText("0");
//            mPeopleCountEditText.setText("0");
//            mOutputText.setText("Output:");
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
