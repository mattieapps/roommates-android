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
public class RentCalFragment extends Fragment {

    EditText mRentPriceEditText, mPeopleCountEditText;
    ImageButton mEqualFab, mRentUpBtn, mRentDownBtn, mPeopleUpBtn, mPeopleDownBtn;
    TextView mOutputText;

    double rentPrice = 0;
    double peopleCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_rentcal, container, false);

        mEqualFab = (ImageButton) fragmentView.findViewById(R.id.calculateFabButton);
        mOutputText = (TextView) fragmentView.findViewById(R.id.outputTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mEqualFab.setBackground(getActivity().getDrawable(R.drawable.circle_21));
        }

        mRentPriceEditText = (EditText) fragmentView.findViewById(R.id.rentPriceText);
        mPeopleCountEditText = (EditText) fragmentView.findViewById(R.id.peopleAmountText);

        mRentUpBtn = (ImageButton) fragmentView.findViewById(R.id.rentUpBtn);
        mRentDownBtn = (ImageButton) fragmentView.findViewById(R.id.rentDownBtn);
        mPeopleUpBtn = (ImageButton) fragmentView.findViewById(R.id.peopleUpBtn);
        mPeopleDownBtn = (ImageButton) fragmentView.findViewById(R.id.peopleDownBtn);


        mRentUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRentPriceEditText.getText().toString().equals("")){
                    mRentPriceEditText.setText("0");
                } else {
                    rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                    double output = rentPrice + 1;
                    mRentPriceEditText.setText(String.valueOf(output));//Convert out to string
                }
            }
        });

        mRentDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                double output = rentPrice-1;
                mRentPriceEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());
                double output = peopleCount+1;
                mPeopleCountEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mPeopleDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());
                double output = peopleCount-1;
                mPeopleCountEditText.setText(String.valueOf(output));//Convert out to string
            }
        });

        mEqualFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Integer.parseInt(mRentPriceEditText.getText().toString());
                peopleCount = Integer.parseInt(mPeopleCountEditText.getText().toString());

                if (rentPrice != 0 && peopleCount != 0) {
                    double output = rentPrice / peopleCount;
                    mOutputText.setText("Output:\n\n" + output);
                } else {
                    Toast.makeText(getActivity(), "Rent Price or People Count can not be Zero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragmentView;
    }
}
