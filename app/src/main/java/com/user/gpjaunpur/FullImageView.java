package com.user.gpjaunpur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class FullImageView extends AppCompatActivity {
    private PhotoView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);
        String image;
        image=getIntent().getStringExtra("image");
        imageView=findViewById(R.id.imageView);




    Glide.with(this).load(image).into(imageView);
    }
}