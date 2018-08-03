package com.example.divinkas.contactstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.divinkas.contactstest.Adapters.ListContactsAdapter;
import com.example.divinkas.contactstest.DTO.LoadAPI;
import com.example.divinkas.contactstest.Data.ContactItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int LAYOUT_NAME = R.layout.activity_main;

    EditText etSearch;
    Button btnStartSearch;
    RecyclerView rvListContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle(R.string.app_name);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_NAME);

        initComponents();
    }
    private void initComponents(){
        findElements();
        btnStartSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.btnStartSearch:
                    String query_str = etSearch.getText().toString();
                    if(!query_str.isEmpty()){
                        List<ContactItem> list;

                        LoadAPI loadAPI = new LoadAPI();
                        loadAPI.execute(query_str);
                        list = loadAPI.get();
                        if(list == null){ Toast.makeText(this, "Введіть коректніші дані пошуку! Або перевірте ваше підключення до інтернету", Toast.LENGTH_LONG).show(); }
                        else { initRecycler(list); }
                    }
                    else { Toast.makeText(this, "Помилка! Введіть данні пошуку!", Toast.LENGTH_SHORT).show(); }
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void findElements(){
        etSearch = findViewById(R.id.searchString);
        btnStartSearch = findViewById(R.id.btnStartSearch);
        rvListContacts = findViewById(R.id.rvContacts);
    }

    private void initRecycler(List<ContactItem> list){
        LinearLayoutManager manager = new LinearLayoutManager( this, LinearLayout.VERTICAL, false);
        ListContactsAdapter adapter = new ListContactsAdapter(this, list);
        rvListContacts.setLayoutManager(manager);
        rvListContacts.setAdapter(adapter);
        rvListContacts.getAdapter().notifyDataSetChanged();
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
