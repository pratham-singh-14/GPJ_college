package com.user.gpjaunpur.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.user.gpjaunpur.R;
import com.user.gpjaunpur.SliderAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private int[] images;
    private String[] text;
    private SliderAdapter adapter;
    private SliderView sliderView;
    private ImageView map;
    private ViewPager viewPager;
    private courseAdapter adapterr;
    private List<courseModel> list;


/*
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 5000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;*/




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        list=new ArrayList<>();
      //  ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("GP Jaunpur");
        list.add(new courseModel(R.drawable.computerr,"Computer Science & Engineering","We offer Diploma in Computer Science And Engineering with total number of 75 seats.👉👉"));
        list.add(new courseModel(R.drawable.electronics,"Electronics Engineering","We offer Diploma in Electronics Engineering with total number of 75 seats. 👉👉"));
        list.add(new courseModel(R.drawable.pharmacy,"Pharmacy"," We offer Diploma in Pharmacy with total number of 44 seats."));
        adapterr=new courseAdapter(getContext(),list);
        viewPager=view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterr);

/*
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int NUM_PAGES=3;
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread 
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        
        */
        
        

        sliderView=view.findViewById(R.id.sliderView);
        images=new int[]{R.drawable.s4,R.drawable.s1,R.drawable.s2,R.drawable.s3};

        text=new String[]{"First","Second","third","4"};
        adapter=new SliderAdapter(images,text);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.startAutoCycle();


        map=view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });


        return view;
    }

    private void openMap() {
        Uri uri=Uri.parse("geo:0, 0?q=Government Polytechnic Jaunpur");
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}