package com.mattieapps.roommates.systems.adapters;

/**
 * Created by andrewmattie on 3/9/15.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.systems.database.Expense;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/31/15.
 */
public class ExpensesCursorAdapter extends RealmBaseAdapter<Expense> implements ListAdapter {

    private Context mContext;

    static class ViewHolder {
        TextView nameText;
        TextView priceText;
        TextView statusText;
        TextView partiesText;
    }

    public ExpensesCursorAdapter(Context context, RealmResults<Expense> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.viewmate_list_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.nameText = (TextView) convertView.findViewById(R.id.expenseName);
            viewHolder.priceText = (TextView) convertView.findViewById(R.id.expensePrice);
            viewHolder.statusText = (TextView) convertView.findViewById(R.id.expenseStatus);
            viewHolder.partiesText = (TextView) convertView.findViewById(R.id.expenseParties);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expense expense = realmResults.get(position);
        viewHolder.nameText.setText(expense.getName());
        viewHolder.priceText.setText(expense.getPrice());
        viewHolder.statusText.setText(expense.getStatus());
        viewHolder.partiesText.setText(expense.getInvolvedParties());

        switch (expense.getStatus()) {
            case "I owe":
                viewHolder.statusText.setTextColor(Color.RED);
                break;
            case "They owe":
                viewHolder.statusText.setTextColor(Color.parseColor("#009e60"));
                break;
            case "Settled":
                viewHolder.nameText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            default: //txt color black
        }

        return convertView;
    }

    public RealmResults<Expense> getRealmResults() {
        return realmResults;
    }
}
