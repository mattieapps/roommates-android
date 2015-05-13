package com.mattieapps.roommates.fragments.mates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mattieapps.roommates.MainActivity;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.systems.database.Expense;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/2/15.
 */
public class ViewExpenseFragment extends Fragment {

    private String name, price, status, parties;
    private long expenseId;
    private int rowIndex;

    private EditText mNameEditText, mPriceEditText, mPartiesEditText;
    private Spinner mStatusSpinner, mAlarmFreqSpinner;
    private Button alarmTimeBtn;
    private TextView reminderFreqText;
    private Expense expense;
    private Realm realm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_newexpense, container, false);

        Bundle bundle = this.getArguments();
        name = bundle.getString("expenseName", "");
        price = bundle.getString("expensePrice", "");
        status = bundle.getString("expenseStatus", "");
        parties = bundle.getString("expenseParties", "");
        expenseId = bundle.getLong("expenseId", 0);
        rowIndex = bundle.getInt("expenseRowIndex");

        realm = Realm.getInstance(getActivity());
        RealmQuery<Expense> query = realm.where(Expense.class);
        final RealmResults<Expense> realmResults = query.findAll();
        expense = realmResults.get(rowIndex);

        mNameEditText = (EditText) fragmentView.findViewById(R.id.expenseNameEditText);
        mPriceEditText = (EditText) fragmentView.findViewById(R.id.expensePriceEditText);
        mPartiesEditText = (EditText) fragmentView.findViewById(R.id.expenseInvolvedPartiesEditText);
        mStatusSpinner = (Spinner) fragmentView.findViewById(R.id.expenseStatusSpinner);
        mAlarmFreqSpinner = (Spinner) fragmentView.findViewById(R.id.expenseAlarmFreqSpinner);
        reminderFreqText = (TextView) fragmentView.findViewById(R.id.reminderFreqText);
        alarmTimeBtn = (Button) fragmentView.findViewById(R.id.newExpenseSetReminderTimeBtn);

        mNameEditText.setText(name);
        mPriceEditText.setText(price);
        mPartiesEditText.setText(parties);

        //TODO Remove when alarm code works
        mAlarmFreqSpinner.setVisibility(View.GONE);
        reminderFreqText.setVisibility(View.GONE);
        alarmTimeBtn.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.statusArray, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> alarmSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.alarmArray, android.R.layout.simple_spinner_dropdown_item);

        mStatusSpinner.setAdapter(spinnerAdapter);
        mAlarmFreqSpinner.setAdapter(alarmSpinnerAdapter);

        switch (status) {
            case "I owe":
                mStatusSpinner.setSelection(0);
                break;
            case "They owe":
                mStatusSpinner.setSelection(1);
                break;
            case "Settled":
                mStatusSpinner.setSelection(2);
                break;
            default:
                status = "I owe";

        }

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        status = "I owe";
                        break;
                    case 1:
                        status = "They owe";
                        break;
                    case 2:
                        status = "Settled";
                        break;
                    default:
                        status = "I owe";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mAlarmFreqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        return;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        position = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        mSaveFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                name = mNameEditText.getText().toString();
//                price = mPriceEditText.getText().toString();
//                //status defined in spinner
//                parties = mPartiesEditText.getText().toString();
//
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        expense.setName(name);
//                        expense.setPrice(price);
//                        expense.setInvolvedParties(parties);
//                        expense.setStatus(status);
//                    }
//                });
//
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("goToExpenses", true);
//                startActivity(intent);
//            }
//        });

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_viewexpense, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_deleteexpense:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Delete Expense");
                dialogBuilder.setMessage("Are you sure you want to delete " + name + "? This cannot be reversed.");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                expense.removeFromRealm();
                            }
                        });

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("goToExpenses", true);
                        startActivity(intent);
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                return true;
            case R.id.action_saveexpense:
                name = mNameEditText.getText().toString();
                price = mPriceEditText.getText().toString();
                //status defined in spinner
                parties = mPartiesEditText.getText().toString();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        expense.setName(name);
                        expense.setPrice(price);
                        expense.setInvolvedParties(parties);
                        expense.setStatus(status);
                    }
                });

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("goToExpenses", true);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
