package com.user.gpjaunpur.pdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.gpjaunpur.R;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class PdfActivity extends AppCompatActivity {
    private RecyclerView pdfRecycle;
    private DatabaseReference reference;
    private List<pdfData> list;
    SwipeRefreshLayout swipeRefreshLayout;
    private pdfAdapter adapter;
private     EditText search;
  //  private ProgressBar progressBarr;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(PdfActivity.this,"bottom-to-up");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        search=findViewById(R.id.searhtext);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // pdf activity pr upr back button show hoga
        getSupportActionBar().setTitle("Official Notice");   // Activity la name upr GP jaunpur ke jgh pr show hoga
        pdfRecycle=findViewById(R.id.pdfRecycle);     //Mnifest file me jakr jis layout pr ana h use tru kr denge
     //  progressBarr=findViewById(R.id.progressBarr);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        swipeRefreshLayout=findViewById(R.id.referesh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pdfRecycle.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // dialoge show
        if(!isConnected(this))
        {
            buildDialog(this).show();
        }

        reference= FirebaseDatabase.getInstance().getReference().child("pdf");
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    pdfData data = snapshot1.getValue(pdfData.class);
                    list.add(0,data);
                }
                adapter=new pdfAdapter(PdfActivity.this,list);
                pdfRecycle.setLayoutManager(new LinearLayoutManager(PdfActivity.this));
              //  progressBarr.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                pdfRecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
              //  progressBarr.setVisibility(View.GONE);
                Toast.makeText(PdfActivity.this, "Database error don't get meassage", Toast.LENGTH_SHORT).show();

            }
        });
 search.addTextChangedListener(new TextWatcher() {
     @Override
     public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

     }

     @Override
     public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

     }

     @Override
     public void afterTextChanged(Editable editable) {
     filter(editable.toString());
     }
 });
    }

    private void filter(String text) {
        ArrayList<pdfData> filterlist=new ArrayList<>();
        for(pdfData item:list){
            if(item.getPdfTitle().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(item);
            }
        }
        adapter.Filteredlist(filterlist);
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
    // internet connection check inapplication
    public boolean isConnected(Context context)
    {
        ConnectivityManager manger= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manger.getActiveNetworkInfo();
        if(info !=null && info.isConnectedOrConnecting())
        {
            android.net.NetworkInfo wifi= manger.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile=manger.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))

                return true;
            else return false;
        }else
            return false;
    }
    public AlertDialog.Builder buildDialog(Context context)
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);


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