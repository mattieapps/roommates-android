package com.mattieapps.roommates.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mattieapps.roommates.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

/**
 * Created by andrewmattie on 11/28/14.
 */
public class TipCalFragment extends Fragment {

    private ImageButton mCheckUpBtn, mCheckDownBtn;
    private ImageButton mTipsCalFab;
    private TextView mOutputTipsText;
    private EditText mCheckPriceEditText;
    private Spinner mGratuitySpinner;
    private View fragmentView;

    private double checkPrice = 0;
    private double tipAmount = 0;
    private double checkAmountBackup;
    private String tipOutputBackup;
    private double gratuity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_tipcal, container, false);

        mTipsCalFab = (ImageButton) fragmentView.findViewById(R.id.tipsCalFabButton);
        mCheckUpBtn = (ImageButton) fragmentView.findViewById(R.id.checkUpBtn);
        mCheckDownBtn = (ImageButton) fragmentView.findViewById(R.id.checkDownBtn);

        mCheckPriceEditText = (EditText) fragmentView.findViewById(R.id.priceEditText);

        mOutputTipsText = (TextView) fragmentView.findViewById(R.id.outputTipsTextView);

        mGratuitySpinner = (Spinner) fragmentView.findViewById(R.id.gratuitySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gratuity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGratuitySpinner.setAdapter(adapter);

        mGratuitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        gratuity = 14;
                        break;
                    case 1:
                        gratuity = 15;
                        break;
                    case 2:
                        gratuity = 16;
                        break;
                    case 3:
                        gratuity = 17;
                        break;
                    case 4:
                        gratuity = 18;
                        break;
                    case 5:
                        gratuity = 19;
                        break;
                    case 6:
                        gratuity = 20;
                        break;
                    case 7:
                        gratuity = 21;
                        break;
                    case 8:
                        gratuity = 22;
                        break;
                    default:
                        position = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mTipsCalFab.setImageResource(R.drawable.ic_equal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTipsCalFab.setBackgroundResource(R.drawable.circle_21);
        }

        mCheckUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckPriceEditText.getText().toString().equals("")) {
                    mCheckPriceEditText.setText("0");
                } else {
                    checkPrice = Double.parseDouble(mCheckPriceEditText.getText().toString());
                    double output = checkPrice + 1;
                    mCheckPriceEditText.setText(String.valueOf(output));//Convert out to string
                }
            }
        });

        mCheckDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckPriceEditText.getText().toString().equals("")) {
                    mCheckPriceEditText.setText("0");
                } else {
                    checkPrice = Double.parseDouble(mCheckPriceEditText.getText().toString());
                    double output = checkPrice - 1;
                    mCheckPriceEditText.setText(String.valueOf(output));//Convert out to string
                }
            }
        });

        mTipsCalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = Double.parseDouble(mCheckPriceEditText.getText().toString());

                if (price != 0 && gratuity != 0) {
                    double output = price * gratuity / 100;
                    mOutputTipsText.setText("Tip Amount:\n\n" + Double.toString(output));
                } else {
                    Toast.makeText(getActivity(), "'Check Amount' can not be Zero", Toast.LENGTH_SHORT).show();
                }
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
            EditText price = (EditText) fragmentView.findViewById(R.id.priceEditText);
            TextView output = (TextView) fragmentView.findViewById(R.id.outputTipsTextView);

            checkAmountBackup = Double.valueOf(price.getText().toString());
            tipOutputBackup = output.getText().toString();

            SnackbarManager.show(
                    Snackbar.with(getActivity()) // context
                            .text("Tip calculations cleared")
                            .actionLabel("UNDO")
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    EditText price = (EditText) fragmentView.findViewById(R.id.priceEditText);
                                    TextView output = (TextView) fragmentView.findViewById(R.id.outputTipsTextView);

                                    price.setText(String.valueOf(checkAmountBackup));
                                    output.setText(tipOutputBackup);
                                }
                            }), getActivity()); // activity where it is displayed

            price.setText("0");
            output.setText("Tip Amount:");
        }

        return super.onOptionsItemSelected(item);
    }
}