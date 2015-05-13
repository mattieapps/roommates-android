package com.mattieapps.roommates.systems.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mattieapps.roommates.R;
import com.mattieapps.roommates.systems.database.Mate;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 2/20/15.
 */

public class MateCursorAdapter extends RealmBaseAdapter<Mate> implements ListAdapter {

    private Context mContext;

    static class ViewHolder {
        TextView nameText;
        TextView priceText;
        TextView statusText;
    }

    public MateCursorAdapter(Context context, RealmResults<Mate> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mate_list_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.nameText = (TextView) convertView.findViewById(R.id.mateName);
            viewHolder.priceText = (TextView) convertView.findViewById(R.id.matePrice);
            viewHolder.statusText = (TextView) convertView.findViewById(R.id.expenseStatus);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Mate mate = realmResults.get(position);
        viewHolder.nameText.setText(mate.getName());

        return convertView;
    }

    public RealmResults<Mate> getRealmResults() {
        return realmResults;
    }
}

