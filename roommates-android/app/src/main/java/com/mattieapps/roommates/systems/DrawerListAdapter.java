package com.mattieapps.roommates.systems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 11/28/14.
 */
public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private String[] mtitle;
    private int[] micon;
    private LayoutInflater inflater;

    public  DrawerListAdapter(Context context, String title[], int icon[])
    {
        this.context=context;
        this.mtitle=title;
        this.micon=icon;

    }
    @Override
    public int getCount() {
        return mtitle.length;
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup parent) {

        TextView title;
        //TextView subtitle;
        ImageView icon;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.drawer_item, parent, false);

        title = (TextView) itemView.findViewById(R.id.drawerItemTextView);
        icon = (ImageView) itemView.findViewById(R.id.drawerItemImageView);

        title.setText(mtitle[position]);
        icon.setImageResource(micon[position]);

        return itemView;

    }

}