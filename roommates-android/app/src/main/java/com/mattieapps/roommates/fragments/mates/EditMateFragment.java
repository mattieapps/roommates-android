package com.mattieapps.roommates.fragments.mates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mattieapps.roommates.MainActivity;
import com.mattieapps.roommates.R;
import com.mattieapps.roommates.model.database.Mate;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by andrewmattie on 3/10/15.
 */
public class EditMateFragment extends Fragment {

    private String name, email;
    private long mateId;
    private int rowIndex;
    private EditText mNameEditText, mEmailEditText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Edit Mate");
        final View fragmentView = inflater.inflate(R.layout.fragment_newmate, container, false);

        mNameEditText = (EditText) fragmentView.findViewById(R.id.mateNameEditText);
        mEmailEditText = (EditText) fragmentView.findViewById(R.id.mateEmailEditText);

        Bundle bundle = this.getArguments();
        name = bundle.getString("mateName");
        email = bundle.getString("mateEmail");
        mateId = bundle.getLong("mateId");
        rowIndex = bundle.getInt("rowIndex");

        mNameEditText.setText(name);
        mEmailEditText.setText(email);

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
                name = mNameEditText.getText().toString();
                email = mEmailEditText.getText().toString();

                Realm realm = Realm.getInstance(getActivity());
                RealmResults<Mate> realmResults = realm.where(Mate.class).equalTo("id", mateId).findAll();
                final Mate mate = realmResults.get(rowIndex);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        mate.setName(name);
                        mate.setPrice("");
                        mate.setStatus("");
                        mate.setEmail(email);
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