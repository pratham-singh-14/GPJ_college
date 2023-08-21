package com.user.gpjaunpur;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import maes.tech.intentanim.CustomIntent;
//#6D72CA
public class splashscreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 1500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(splashscreen.this,MainActivity.class));
                CustomIntent.customType(splashscreen.this,"left-to-right");

        finish();

            }
        }, SPLASH_DISPLAY_TIME);
    }
}
