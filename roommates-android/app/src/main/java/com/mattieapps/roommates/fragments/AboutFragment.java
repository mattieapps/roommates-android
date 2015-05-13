package com.mattieapps.roommates.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mattieapps.roommates.R;

/**
 * Created by andrewmattie on 2/20/15.
 */
public class AboutFragment extends Fragment {

    private Button freepikBtn, flaticonBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("About");
        final View fragmentView = inflater.inflate(R.layout.fragment_about, container, false);

        freepikBtn = (Button) fragmentView.findViewById(R.id.freepikBtn);
        flaticonBtn = (Button) fragmentView.findViewById(R.id.flaticonBtn);

        freepikBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://freepik.com"));
                startActivity(intent);
            }
        });

        flaticonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://flaticon.com"));
                startActivity(intent);
            }
        });

        return fragmentView;
    }
}
