package com.mattieapps.roommates.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.model.state.LoanTermState;

import java.util.Calendar;

/**
 * Created by andrewmattie on 4/15/15.
 */
public class LoanCalFragment extends Fragment {
    private View fragmentView;
    private EditText loanAmountEditText, loanIntrestEditText, loanTermEditText;
    private Spinner loanTermSpinner;
    private Button loanStartDateBtn;
    private LoanTermState loanTermState;
    private PieChart pieChart;
    private ImageButton mEqualFab;

    public static int loanStartYear, loanStartMonth, loanStartDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_loancal, container, false);

        loanAmountEditText = (EditText) fragmentView.findViewById(R.id.loanAmountEditText);
        loanIntrestEditText = (EditText) fragmentView.findViewById(R.id.loanIntrestEditText);
        loanTermEditText = (EditText) fragmentView.findViewById(R.id.loanTermEditText);
        loanTermSpinner = (Spinner) fragmentView.findViewById(R.id.loanTermSpinner);
        loanStartDateBtn = (Button) fragmentView.findViewById(R.id.loanStartDateBtn);
        pieChart = (PieChart) fragmentView.findViewById(R.id.loanPieChart);
        mEqualFab = (ImageButton) fragmentView.findViewById(R.id.fab);

        mEqualFab.setImageResource(R.drawable.ic_equal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEqualFab.setBackgroundResource(R.drawable.circle_21);
        }

        loanTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        loanTermState = LoanTermState.MONTH;
                        break;
                    case 1:
                        loanTermState = LoanTermState.YEAR;
                        break;
                    default:
                        loanTermState = LoanTermState.MONTH;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> loanTermAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.loan_array, android.R.layout.simple_spinner_dropdown_item);
        loanTermSpinner.setAdapter(loanTermAdapter);

        loanStartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        mEqualFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return fragmentView;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            LoanCalFragment.loanStartDay = day;
            LoanCalFragment.loanStartMonth = month;
            LoanCalFragment.loanStartYear = year;

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
