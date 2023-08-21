package com.user.gpjaunpur.ui.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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


public class NoticeFragment extends Fragment {
    // private ProgressBar progressBar;
    private RecyclerView updateNoticeRecyclerView;
    // private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;
    private DatabaseReference reference;
    ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout csNoData;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        updateNoticeRecyclerView = view.findViewById(R.id.updateNoticeRecycler);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout = view.findViewById(R.id.referesh);
        csNoData = view.findViewById(R.id.csNoData);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                updateNoticeRecyclerView.setAdapter(
                        adapter
                );
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        // dialoge show
        if (!isConnected(getContext())) {
            buildDialog(getContext()).show();

        }
        //   progressBar=view.findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        updateNoticeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateNoticeRecyclerView.setHasFixedSize(true);
        getNotice();

        return view;
    }

    private void getNotice() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<NoticeData> list = new ArrayList<>();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    NoticeData data = snapshot.getValue(NoticeData.class);

                    list.add(0, data);

                }

                adapter = new NoticeAdapter(getContext(), list);
                adapter.notifyDataSetChanged();

                //  progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                updateNoticeRecyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //   progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

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

    //check internet connection
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
        //   builder.setIcon(R.drawable.cross);
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