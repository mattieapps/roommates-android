package com.mattieapps.roommates.fragments.mates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mattieapps.roommates.MainActivity;
import com.mattieapps.roommates.MattieCommonObjects;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.VPFragmentActivity;
import com.mattieapps.roommates.systems.adapters.ExpensesCursorAdapter;
import com.mattieapps.roommates.systems.database.Expense;
import com.mattieapps.roommates.systems.database.Mate;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/2/15.
 */
public class ViewMateFragment extends Fragment {

    private TextView mMateNameText, mMateEmailText;
    private ListView mExpenseListView;
    private ImageButton mNewExpenseFab;
    private ExpensesCursorAdapter expensesCursorAdapter;

    private String name, email;
    private long mateId;
    private int rowIndex;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MattieCommonObjects mattieCommonObjects;
    private SharedPreferences mSharedPreferences;

    private Realm realm;
    private RealmResults<Mate> mateRealmResults;
    private RealmResults<Expense> expenseRealmResults;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("View Mate");
        final View fragmentView = inflater.inflate(R.layout.fragment_viewmate, container, false);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mattieCommonObjects = new MattieCommonObjects(getActivity(), fragmentManager, fragmentTransaction);

        Bundle bundle = this.getArguments();
        name = bundle.getString("mateName", "");
        email = bundle.getString("mateEmail", "");
        mateId = bundle.getLong("mateId", 0);
        rowIndex = bundle.getInt("rowIndex", 0);

        realm = Realm.getInstance(getActivity());

        mateRealmResults = realm.where(Mate.class).equalTo("id", mateId).findAll();


        mMateNameText = (TextView) fragmentView.findViewById(R.id.viewMateNameText);
        mMateEmailText = (TextView) fragmentView.findViewById(R.id.viewMateEmailText);
        mExpenseListView = (ListView) fragmentView.findViewById(R.id.viewMateListView);
        mNewExpenseFab = (ImageButton) fragmentView.findViewById(R.id.newExpenseFab);
        mSharedPreferences = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getActivity());

        mNewExpenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VPFragmentActivity.class);
                intent.putExtra("fragmentToLoad", 2);
                intent.putExtra("involvedMate", mMateNameText.getText().toString());
                startActivity(intent);
            }
        });

        mMateNameText.setText(name);
        mMateEmailText.setText(email);

        expenseRealmResults = realm.where(Expense.class).equalTo("involvedParties", name).findAll();

        expensesCursorAdapter = new ExpensesCursorAdapter(getActivity(), expenseRealmResults, true);

        mExpenseListView.setAdapter(expensesCursorAdapter);
        mExpenseListView.setEmptyView(fragmentView.findViewById(R.id.empty));

        mExpenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewExpenseFragment fragment = new ViewExpenseFragment();

                Expense expense = expenseRealmResults.get(position);

                String name = expense.getName();
                String price = expense.getPrice();
                String status = expense.getStatus();
                String parties = expense.getInvolvedParties();
                long expenseId = expense.getId();

                Bundle bundle = new Bundle();
                bundle.putString("expenseName", name);
                bundle.putString("expensePrice", price);
                bundle.putString("expenseStatus", status);
                bundle.putString("expenseParties", parties);
                bundle.putLong("expenseId", expenseId);
                fragment.setArguments(bundle);

                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, fragment, true);

//                http://stackoverflow.com/questions/13903013/how-to-get-the-listitem-text-value-when-click-on-it
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_viewmate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editmate:

                EditMateFragment fragment = new EditMateFragment();

                Bundle bundle = new Bundle();
                bundle.putString("mateName", name);
                bundle.putString("mateEmail", email);
                bundle.putLong("mateId", mateId);
                bundle.putInt("rowIndex", rowIndex);
                fragment.setArguments(bundle);

                mattieCommonObjects.fragmentMethod(R.id.vp_content_frame, fragment, true);


                break;
            case R.id.action_deletemate:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Delete Mate");
                dialogBuilder.setMessage("Are you sure you want to delete " + name + "? This cannot be reversed.");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Mate mate = mateRealmResults.first();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                mate.removeFromRealm();
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

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
