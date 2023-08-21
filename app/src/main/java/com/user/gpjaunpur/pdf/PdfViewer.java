package com.user.gpjaunpur.pdf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.user.gpjaunpur.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PdfViewer extends AppCompatActivity {
   private String url;
   private PDFView pdfView;
   private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        progressbar=findViewById(R.id.progressbar);
        url=getIntent().getStringExtra("pdfUrl");
        pdfView=findViewById(R.id.pdfView);
        new PdfDownload().execute(url);
    }
    private class PdfDownload extends AsyncTask<String,Void, InputStream>{



        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    progressbar.setVisibility(View.VISIBLE);
                    progressbar.setClickable(false);
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }




        @Override
        protected void onPostExecute(InputStream inputStream) {

            progressbar.setVisibility(View.GONE);
            pdfView.fromStream(inputStream).load();
        }
    }


}