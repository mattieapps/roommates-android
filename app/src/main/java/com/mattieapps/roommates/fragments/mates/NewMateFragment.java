package com.mattieapps.roommates.fragments.mates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mattieapps.roommates.MainActivity;
import com.mattieapps.roommates.systems.MattieCommonObjects;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.model.database.Mate;

import io.realm.Realm;

/**
 * Created by andrewmattie on 2/28/15.
 */
public class NewMateFragment extends Fragment {

    private EditText mNameEditText, mEmailEditText;
    private MattieCommonObjects mattieCommonObjects;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("New Mate");
        final View fragmentView = inflater.inflate(R.layout.fragment_newmate, container, false);

        mattieCommonObjects = new MattieCommonObjects(getActivity());
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mNameEditText = (EditText) fragmentView.findViewById(R.id.mateNameEditText);
        mEmailEditText = (EditText) fragmentView.findViewById(R.id.mateEmailEditText);

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
        getActivity().getMenuInflater().inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                final String name = mNameEditText.getText().toString();
                final String email = mEmailEditText.getText().toString();


                Realm realm = Realm.getInstance(getActivity());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int autoIncrementId = (int) (realm.where(Mate.class).maximumInt("id")) + 1;
                        Mate mate = realm.createObject(Mate.class);
                        mate.setId(autoIncrementId);
                        mate.setName(name);
                        mate.setEmail(email);
                        mate.setStatus("");
                        mate.setPrice("");
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
