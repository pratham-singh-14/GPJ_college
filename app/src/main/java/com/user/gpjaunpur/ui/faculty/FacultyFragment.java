package com.user.gpjaunpur.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.gpjaunpur.R;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {
    private RecyclerView csDepartment,ecDepartment,pharmacyDepartment,scienceDepartment,workshopDepartment,libraryDepartment,ministrialDepartment,peonDepartment;
    private LinearLayout csNoData,ecNoData,pharmacyNoData,scienceNoData,workshopNoData,libraryNoData,ministrialNoData,peonNoData;
    private List<TeacherData> list1,list2,list3,list4,list5,list6,list7,list8;
    private DatabaseReference reference ,dbRef;
    private TeacherAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty_fragment, container, false);
        csNoData=view.findViewById(R.id.csNoData);
        ecNoData=view.findViewById(R.id.ecNoData);
      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Faculty");
        swipeRefreshLayout=view.findViewById(R.id.referesh);
        pharmacyNoData=view.findViewById(R.id.pharmacyNoData);
        scienceNoData=view.findViewById(R.id.scienceNoData);
        workshopNoData=view.findViewById(R.id.workshopNoData);
        libraryNoData=view.findViewById(R.id.libraryNoData);
        ministrialNoData=view.findViewById(R.id.ministrialNoData);
        peonNoData=view.findViewById(R.id.peonNoData);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  csDepartment();
                  ecDepartment();
                  pharmacyDepartment();
                  workshopDepartment();
                  ministrialDepartment();
                  peonDepartment();
                  scienceDepartment();

                swipeRefreshLayout.setRefreshing(false);

            }
        });



        csDepartment=view.findViewById(R.id.csDepartment);
        ecDepartment=view.findViewById(R.id.ecDepartment);
        pharmacyDepartment=view.findViewById(R.id.pharmacyDepartment);
        scienceDepartment=view.findViewById(R.id.scienceDepartment);
        workshopDepartment=view.findViewById(R.id.workshopDepartment);
        libraryDepartment=view.findViewById(R.id.libraryDepartment);
        ministrialDepartment=view.findViewById(R.id.ministrialDepartment);
        peonDepartment=view.findViewById(R.id.peonDepartment);

        reference= FirebaseDatabase.getInstance().getReference().child("Faculty");



        csDepartment();
        ecDepartment();
        pharmacyDepartment();
        libraryDepartment();
        workshopDepartment();
        scienceDepartment();
        ministrialDepartment();
        peonDepartment();

return view;


    }

    private void csDepartment() {
        dbRef=reference.child("Computer Science & Engineering");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list1,getContext());

                    csDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void ecDepartment() {
        dbRef=reference.child("Electronics Engineering");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    ecNoData.setVisibility(View.VISIBLE);
                    ecDepartment.setVisibility(View.GONE);
                }else {
                    ecNoData.setVisibility(View.GONE);
                    ecDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    ecDepartment.setHasFixedSize(true);
                    ecDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list2,getContext());
                    ecDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pharmacyDepartment() {
        dbRef=reference.child("Pharmacy");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    pharmacyNoData.setVisibility(View.VISIBLE);
                    pharmacyDepartment.setVisibility(View.GONE);
                }else {
                    pharmacyNoData.setVisibility(View.GONE);
                    pharmacyDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    pharmacyDepartment.setHasFixedSize(true);
                    pharmacyDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list3,getContext());

                    pharmacyDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scienceDepartment() {
        dbRef=reference.child("Pure Science & Humanities");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    scienceNoData.setVisibility(View.VISIBLE);
                    scienceDepartment.setVisibility(View.GONE);
                }else {
                    scienceNoData.setVisibility(View.GONE);
                    scienceDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    scienceDepartment.setHasFixedSize(true);
                    scienceDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list4,getContext());

                    scienceDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void workshopDepartment() {
        dbRef=reference.child("Workshop");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    workshopNoData.setVisibility(View.VISIBLE);
                    workshopDepartment.setVisibility(View.GONE);
                }else {
                    workshopNoData.setVisibility(View.GONE);
                    workshopDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    workshopDepartment.setHasFixedSize(true);
                    workshopDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list5,getContext());

                    workshopDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void libraryDepartment() {
        dbRef=reference.child("Library");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list6=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    libraryNoData.setVisibility(View.VISIBLE);
                    libraryDepartment.setVisibility(View.GONE);
                }else {
                    libraryNoData.setVisibility(View.GONE);
                    libraryDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list6.add(data);
                    }
                    libraryDepartment.setHasFixedSize(true);
                    libraryDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list6,getContext());

                    libraryDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ministrialDepartment() {
        dbRef=reference.child("Ministrial staff");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list7=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    ministrialNoData.setVisibility(View.VISIBLE);
                    ministrialDepartment.setVisibility(View.GONE);
                }else {
                    ministrialNoData.setVisibility(View.GONE);
                    ministrialDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list7.add(data);
                    }
                    ministrialDepartment.setHasFixedSize(true);
                    ministrialDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list7,getContext());

                    ministrialDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void peonDepartment() {
        dbRef=reference.child("Class-4 Group");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list8=new ArrayList<>();
                if (!dataSnapshot.exists())
                {
                    peonNoData.setVisibility(View.VISIBLE);
                    peonDepartment.setVisibility(View.GONE);
                }else {
                    peonNoData.setVisibility(View.GONE);
                    peonDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list8.add(data);
                    }
                    peonDepartment.setHasFixedSize(true);
                    peonDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list8,getContext());

                    peonDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Database Error Don't get Meassage", Toast.LENGTH_SHORT).show();
            }
        });
    }


}