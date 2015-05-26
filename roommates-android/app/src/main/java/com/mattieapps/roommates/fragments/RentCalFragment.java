package com.mattieapps.roommates.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.fragments.mates.NewExpenseRentFragment;
import com.mattieapps.roommates.model.Calculator;
import com.mattieapps.roommates.model.state.CalculatorType;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

/**
 * Created by andrewmattie on 11/28/14.
 */
public class RentCalFragment extends Fragment {

    private EditText mRentPriceEditText, mPeopleCountEditText;
    private ImageButton mRentUpBtn, mRentDownBtn, mPeopleUpBtn, mPeopleDownBtn;
    private ImageButton mEqualFab;
    private Button mNewRentExpense;
    private TextView mOutputText;

    private View fragmentView;

    private double rentPrice = 0;
    private double peopleCount = 0;

    private double rentPriceBackup, numbOfPeopleBackup;
    private String rentOutputBackup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_rentcal, container, false);

        mEqualFab = (ImageButton) fragmentView.findViewById(R.id.calculateFabButton);
        mOutputText = (TextView) fragmentView.findViewById(R.id.outputTextView);

        mRentPriceEditText = (EditText) fragmentView.findViewById(R.id.rentPriceText);
        mPeopleCountEditText = (EditText) fragmentView.findViewById(R.id.peopleAmountText);

        mRentUpBtn = (ImageButton) fragmentView.findViewById(R.id.rentUpBtn);
        mRentDownBtn = (ImageButton) fragmentView.findViewById(R.id.rentDownBtn);
        mPeopleUpBtn = (ImageButton) fragmentView.findViewById(R.id.peopleUpBtn);
        mPeopleDownBtn = (ImageButton) fragmentView.findViewById(R.id.peopleDownBtn);
        mNewRentExpense = (Button) fragmentView.findViewById(R.id.makeRentExpense);

        mEqualFab.setImageResource(R.drawable.ic_equal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEqualFab.setBackgroundResource(R.drawable.circle_21);
        }


        mRentUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRentPriceEditText.getText().toString().equals("")) {
                    mRentPriceEditText.setText("0");
                } else {
                    rentPrice = Double.parseDouble(mRentPriceEditText.getText().toString());
                    mRentPriceEditText.setText(String.valueOf(Calculator.calculate(rentPrice, 1, CalculatorType.ADD)));//Convert out to string
                }
            }
        });

        mRentDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Double.parseDouble(mRentPriceEditText.getText().toString());
                mRentPriceEditText.setText(String.valueOf(Calculator.calculate(rentPrice, 1, CalculatorType.SUBTRACT)));//Convert out to string
            }
        });

        mPeopleUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Double.parseDouble(mPeopleCountEditText.getText().toString());
                mPeopleCountEditText.setText(String.valueOf(Calculator.calculate(peopleCount, 1, CalculatorType.ADD)));//Convert out to string
            }
        });

        mPeopleDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleCount = Double.parseDouble(mPeopleCountEditText.getText().toString());
                mPeopleCountEditText.setText(String.valueOf(Calculator.calculate(peopleCount, 1, CalculatorType.SUBTRACT)));//Convert out to string
            }
        });

        mEqualFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentPrice = Double.parseDouble(mRentPriceEditText.getText().toString());
                peopleCount = Double.parseDouble(mPeopleCountEditText.getText().toString());

                if (rentPrice != 0 && peopleCount != 0) {
                    mOutputText.setText(String.valueOf(Calculator.calculate(rentPrice, peopleCount, CalculatorType.DIVIDE)));
                    mNewRentExpense.setEnabled(true);
                } else {
                    Toast.makeText(getActivity(), "'Rent Price' or 'People Count' can not be zero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNewRentExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("rentOutput", mOutputText.getText().toString());
                bundle.putString("newName", "New Rent Expense");


                NewExpenseRentFragment fragment = new NewExpenseRentFragment();

                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return fragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_reset) {

            EditText rentprice = (EditText) fragmentView.findViewById(R.id.rentPriceText);
            EditText numbpeople = (EditText) fragmentView.findViewById(R.id.peopleAmountText);
            TextView output = (TextView) fragmentView.findViewById(R.id.outputTextView);

            if (rentprice.getText().toString().equals("")) {
                rentPriceBackup = 0;
            } else {
                rentPriceBackup = Double.valueOf(rentprice.getText().toString());
            }

            if (numbpeople.getText().toString().equals("")) {
                numbOfPeopleBackup = 0;
            } else {
                numbOfPeopleBackup = Double.valueOf(numbpeople.getText().toString());
            }

            rentOutputBackup = output.getText().toString();
            SnackbarManager.show(
                    Snackbar.with(getActivity()) // context
                            .text("Rent calculations cleared") // text to display
                            .actionLabel("UNDO")
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    EditText rentprice = (EditText) fragmentView.findViewById(R.id.rentPriceText);
                                    EditText numbpeople = (EditText) fragmentView.findViewById(R.id.peopleAmountText);
                                    TextView output = (TextView) fragmentView.findViewById(R.id.outputTextView);

                                    rentprice.setText(String.valueOf(rentPriceBackup));
                                    numbpeople.setText(String.valueOf(numbOfPeopleBackup));
                                    output.setText(rentOutputBackup);
                                }
                            }), getActivity());  // activity where it is displayed

            rentprice.setText("0");
            numbpeople.setText("0");
            output.setText("Output:");
        }

        return super.onOptionsItemSelected(item);
    }
}
