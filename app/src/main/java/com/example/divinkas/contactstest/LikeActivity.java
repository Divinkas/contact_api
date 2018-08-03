package com.example.divinkas.contactstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.divinkas.contactstest.Adapters.ListContactsLikeAdapter;
import com.example.divinkas.contactstest.DTO.GetLikeContacts;

public class LikeActivity extends AppCompatActivity {

    RecyclerView rvLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        rvLike = findViewById(R.id.rvLike);
        GetLikeContacts likeContacts = new GetLikeContacts(this);
        LinearLayoutManager manager = new LinearLayoutManager( this, LinearLayout.VERTICAL, false);
        ListContactsLikeAdapter adapter = new ListContactsLikeAdapter(this, likeContacts.getList());
        rvLike.setLayoutManager(manager);
        rvLike.setAdapter(adapter);
        rvLike.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (item.getItemId()){
            case R.id.searchMenuItem:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.likeMenuItem:
                intent = new Intent(this, LikeActivity.class);
                break;
        }
        startActivity(intent);
        return true;
    }
}
