package com.example.divinkas.contactstest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PDF_Rader_Activity extends AppCompatActivity {
    Context context;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf__rader_);
        context = this;
        String link = getIntent().getStringExtra("link_pdf");
        pdfView = findViewById(R.id.pdf_view);

        new RetviewerPDFStream().execute(link);

    }
    public class RetviewerPDFStream extends AsyncTask<String, Void, InputStream>{
        InputStream inputStream;
        @Override
        protected InputStream doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                if(connection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(connection.getInputStream());
                }
                else {
                    Intent intent = new Intent(context, NotConnectionActivity.class);
                    intent.putExtra("link_pdf", url);
                    context.startActivity(intent);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }
}
