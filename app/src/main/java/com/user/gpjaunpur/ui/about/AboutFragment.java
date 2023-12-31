package com.user.gpjaunpur.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.user.gpjaunpur.R;

public class AboutFragment extends Fragment {
    private ImageView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        //   ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About us");  Action barr show

        map = view.findViewById(R.id.map);
        // ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("About");
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });


        return view;


    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Government Polytechnic Jaunpur");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}