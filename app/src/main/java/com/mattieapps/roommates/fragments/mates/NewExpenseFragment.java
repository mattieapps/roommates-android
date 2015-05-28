package com.mattieapps.roommates.fragments.mates;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mattieapps.roommates.MainActivity;
import com.mattieapps.roommates.systems.MattieCommonObjects;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.model.database.Expense;
import com.mattieapps.roommates.model.state.AlarmState;

import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;

/**
 * Created by andrewmattie on 3/2/15.
 */
public class NewExpenseFragment extends Fragment {

    private EditText mNameEditText, mPriceEditText, mPartiesEditText;
    private Spinner mStatusSpinner, mAlarmFreqSpinner;
    private Button alarmTimeBtn;
    private MattieCommonObjects mattieCommonObjects;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    public static TextView reminderFreqText;

    private String name, price, status, parties;
    public static AlarmState alarmFreq = AlarmState.NONE;
    public static String buildDate, buildTime;
    public static int alarmDay, alarmMonth, alarmYear, alarmHour, alarmMinute;
    private int alarmType;
    private boolean showButton = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("New Expense");
        final View fragmentView = inflater.inflate(R.layout.fragment_newexpense, container, false);

        mattieCommonObjects = new MattieCommonObjects(getActivity());
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mNameEditText = (EditText) fragmentView.findViewById(R.id.expenseNameEditText);
        mPriceEditText = (EditText) fragmentView.findViewById(R.id.expensePriceEditText);
        mPartiesEditText = (EditText) fragmentView.findViewById(R.id.expenseInvolvedPartiesEditText);
        mStatusSpinner = (Spinner) fragmentView.findViewById(R.id.expenseStatusSpinner);
        mAlarmFreqSpinner = (Spinner) fragmentView.findViewById(R.id.expenseAlarmFreqSpinner);
        alarmTimeBtn = (Button) fragmentView.findViewById(R.id.newExpenseSetReminderTimeBtn);
        reminderFreqText = (TextView) fragmentView.findViewById(R.id.reminderFreqText);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPriceEditText.setText(bundle.getString("rentOutput"));
            mNameEditText.setText(bundle.getString("newName"));
        }

        //TODO Remove if alarm code doesn't work
//        mAlarmFreqSpinner.setVisibility(View.GONE);
//        reminderFreqText.setVisibility(View.GONE);
//        alarmTimeBtn.setVisibility(View.GONE);


        parties = bundle.getString("involvedMate");
        mPartiesEditText.setText(parties);

        ArrayAdapter<CharSequence> statusSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.statusArray, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> alarmSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.alarmArray, android.R.layout.simple_spinner_dropdown_item);

        mStatusSpinner.setAdapter(statusSpinnerAdapter);
        mAlarmFreqSpinner.setAdapter(alarmSpinnerAdapter);

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
                        alarmFreq = AlarmState.NONE;
                        enableDateTimeBtn(false);
                        return;
                    case 1:
                        alarmFreq = AlarmState.ONE_TIME;
                        enableDateTimeBtn(true);
                        setReminderFreqTextString(position);
                    case 2:
                        alarmFreq = AlarmState.DAILY;
                        enableDateTimeBtn(true);
                        setReminderFreqTextString(position);
                        break;
                    case 3:
                        alarmFreq = AlarmState.WEEKLY;
                        enableDateTimeBtn(true);
                        setReminderFreqTextString(position);
                        break;
                    case 4:
                        alarmFreq = AlarmState.MONTHLY;
                        enableDateTimeBtn(true);
                        setReminderFreqTextString(position);
                        break;
                    case 5:
                        alarmFreq = AlarmState.YEARLY;
                        enableDateTimeBtn(true);
                        setReminderFreqTextString(position);
                        break;
                    default:
                        position = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alarmTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        return fragmentView;
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            NewExpenseFragment.alarmHour = hourOfDay;
            NewExpenseFragment.alarmMinute = minute;
            NewExpenseFragment.buildTime = hourOfDay + ":" + minute;
            NewExpenseFragment.reminderFreqText.setText("Reminder Frequency: " + alarmFreq
                    + " starting on " + buildDate + " at " + buildTime);
        }
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

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            NewExpenseFragment.alarmDay = day;
            NewExpenseFragment.alarmMonth = month + 1;
            NewExpenseFragment.alarmYear = year;
            NewExpenseFragment.buildDate = month + 1 + "/" + day + "/" + year;
            NewExpenseFragment.reminderFreqText.setText("Reminder Frequency: " + alarmFreq
                    + " starting on " + buildDate + " at " + buildTime);

            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "timePicker");
        }
    }

    public void enableDateTimeBtn(boolean shouldShowButton) {
        this.showButton = shouldShowButton;
        if (showButton)
            alarmTimeBtn.setVisibility(View.VISIBLE);
        if (!showButton)
            alarmTimeBtn.setVisibility(View.INVISIBLE);
    }

    public void setReminderFreqTextString(int alarmTypeUSEPos) {
        reminderFreqText.setText("Reminder Frequency: " + alarmFreq + " starting on " + buildDate
                + " at " + buildTime);
        alarmType = alarmTypeUSEPos;
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
        getActivity().getMenuInflater().inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                name = mNameEditText.getText().toString();
                price = mPriceEditText.getText().toString();
                //status defined in spinner
                parties = mPartiesEditText.getText().toString();

                Realm realm = Realm.getInstance(getActivity());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Expense expense = realm.createObject(Expense.class);
                        int autoIncrementId = (int) (realm.where(Expense.class).maximumInt("id")) + 1;
                        Random random = new Random();
                        int randomId = random.nextInt();
                        expense.setId(autoIncrementId);
                        expense.setName(name);
                        expense.setInvolvedParties(parties);
                        expense.setPrice(price);
                        expense.setStatus(status);
                        if (showButton) {
                            expense.setAlarmId(randomId);
                            expense.setHasAlarmSet(true);
                            mattieCommonObjects.setAlarm(name, price, alarmDay, alarmMonth, alarmYear
                                    , alarmHour, alarmMinute, alarmType, randomId);
                        } else {
                            expense.setAlarmId(0);
                            expense.setHasAlarmSet(false);
                        }
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
