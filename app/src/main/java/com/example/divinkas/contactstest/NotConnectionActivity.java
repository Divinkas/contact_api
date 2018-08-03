package com.example.divinkas.contactstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRefresh;
    String old_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connection);

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        old_link = getIntent().getStringExtra("link_pdf");

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PDF_Rader_Activity.class);
        intent.putExtra("link_pdf", old_link);
        startActivity(intent);
        finish();
    }
}
