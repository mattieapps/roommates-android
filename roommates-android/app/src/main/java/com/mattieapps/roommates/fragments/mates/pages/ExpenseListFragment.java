package com.mattieapps.roommates.fragments.mates.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.VPFragmentActivity;
import com.mattieapps.roommates.fragments.mates.ViewExpenseFragment;
import com.mattieapps.roommates.systems.adapters.ExpensesCursorAdapter;
import com.mattieapps.roommates.model.database.Expense;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/23/15.
 */
public class ExpenseListFragment extends Fragment {

    private ListView mAllExpenseListView;
    private ImageButton mNewExpenseFab;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragpage_expenselist, container, false);

        mAllExpenseListView = (ListView) fragmentView.findViewById(R.id.allExpenseListView);
        mNewExpenseFab = (ImageButton) fragmentView.findViewById(R.id.newExpenseFab);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final Realm realm = Realm.getInstance(getActivity());
        RealmQuery<Expense> query = realm.where(Expense.class);
        final RealmResults<Expense> realmResults = query.findAll();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNewExpenseFab.setBackgroundResource(R.drawable.circle_21);
        }

        mAllExpenseListView.setAdapter(new ExpensesCursorAdapter(getActivity(), realmResults, true));

        mNewExpenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VPFragmentActivity.class);
                intent.putExtra("fragmentToLoad", 2);
                startActivity(intent);
            }
        });

        mAllExpenseListView.setEmptyView(fragmentView.findViewById(R.id.empty));

        mAllExpenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewExpenseFragment fragment = new ViewExpenseFragment();

                Expense expense = realmResults.get(position);

                String name = expense.getName();
                String price = expense.getPrice();
                String status = expense.getStatus();
                String parties = expense.getInvolvedParties();
                long expenseId = expense.getId();

                Intent intent = new Intent(getActivity(), VPFragmentActivity.class);
                intent.putExtra("expenseName", name);
                intent.putExtra("expensePrice", price);
                intent.putExtra("expenseStatus", status);
                intent.putExtra("expenseParties", parties);
                intent.putExtra("expenseId", expenseId);
                intent.putExtra("fragmentToLoad", 3);
                intent.putExtra("expenseRowIndex", position);
                startActivity(intent);

            }
        });

        return fragmentView;
    }
}
