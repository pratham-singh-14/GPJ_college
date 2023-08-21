package com.user.gpjaunpur;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import maes.tech.intentanim.CustomIntent;


public class Webview extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // pdf activity pr upr back button show hoga
        getSupportActionBar().setTitle("Website");
        webView=findViewById(R.id.webview);
        progressBar=findViewById(R.id.progressBar);

        // dialoge show
        if(!isConnected(this))
        {
            buildDialog(this).show();
        }

        String url="https://gpjaunpur.org.in/";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
            CustomIntent.customType(Webview.this,"bottom-to-up");
        }
    }
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
        builder.setIcon(R.drawable.cross);
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