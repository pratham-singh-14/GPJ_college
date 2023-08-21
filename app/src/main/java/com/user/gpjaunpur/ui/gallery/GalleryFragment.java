package com.user.gpjaunpur.ui.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.gpjaunpur.R;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {
    RecyclerView others;
    GalleryAdapter adapter;
    DatabaseReference reference;
    //ProgressBar progressbar;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        swipeRefreshLayout = view.findViewById(R.id.referesh);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        others = view.findViewById(R.id.otherss);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                others.setAdapter(adapter);


                swipeRefreshLayout.setRefreshing(false);

            }
        });


        // dialoge show
        if (!isConnected(getContext())) {
            buildDialog(getContext()).show();
        }
        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getOthers();
        return view;

    }

    private void getOthers() {

            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> imageList = new ArrayList<>();

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        String data = (String) snapshot1.getValue();


                        imageList.add(0, data);
                    }
                    adapter = new GalleryAdapter(getContext(), imageList);
                    others.setLayoutManager(new GridLayoutManager(getContext(), 4));
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                        others.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    Toast.makeText(getContext(), "Database error don't get meassage", Toast.LENGTH_SHORT).show();

                }
            });

    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    public boolean isConnected(Context context) {
        ConnectivityManager manger = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manger.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = manger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = manger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))

                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection!");
        builder.setMessage("Please Check Your Internet Connection.");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        return builder;
    }
}