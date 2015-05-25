package com.mattieapps.roommates.fragments.mates.pages;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mattieapps.roommates.systems.MattieCommonObjects;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.VPFragmentActivity;
import com.mattieapps.roommates.fragments.mates.ViewMateFragment;
import com.mattieapps.roommates.systems.adapters.MateCursorAdapter;
import com.mattieapps.roommates.model.database.Mate;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/23/15.
 */
public class MateListFragment extends Fragment {

    private ListView mListView;
    private ImageButton mNewMateFab;
    private MateCursorAdapter mateCursorAdapter;
    private MattieCommonObjects mattieCommonObjects;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragpage_matelist, container, false);

        mattieCommonObjects = new MattieCommonObjects(getActivity());
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mListView = (ListView) fragmentView.findViewById(R.id.listview);
        mNewMateFab = (ImageButton) fragmentView.findViewById(R.id.newMateFab);

        Realm realm = Realm.getInstance(getActivity());
        final RealmResults<Mate> realmResults = realm.where(Mate.class).findAll();

        mListView.setEmptyView(fragmentView.findViewById(R.id.empty));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNewMateFab.setBackgroundResource(R.drawable.circle_21);
        }

        mNewMateFab.setImageResource(R.drawable.ic_action_mate_add);

        mateCursorAdapter = new MateCursorAdapter(getActivity(), realmResults, true);
        mListView.setAdapter(mateCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewMateFragment fragment = new ViewMateFragment();

                Mate mate = realmResults.get(position);

                String name = mate.getName();
                String email = mate.getEmail();
                long mateId = mate.getId();

                Bundle bundle = new Bundle();
                bundle.putString("mateName", name);
                bundle.putString("mateEmail", email);
                bundle.putLong("mateId", mateId);
                fragment.setArguments(bundle);

                Intent intent = new Intent(getActivity(), VPFragmentActivity.class);
                intent.putExtra("fragmentToLoad", 0);

                intent.putExtra("matelistfragment_nameName", name);
                intent.putExtra("matelistfragment_mateEmail", email);
                intent.putExtra("matelistfragment_mateId", mateId);
                startActivity(intent);
            }
        });

        mNewMateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VPFragmentActivity.class);
                intent.putExtra("fragmentToLoad", 1);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public int dipToPixel(int size) {
        Resources resources = getResources();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, resources.getDisplayMetrics());

    }
}
