package com.maad.aliensapp;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle(R.string.list_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alien", null);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(0));
            names.add(cursor.getString(1));
        }
        cursor.close();

        ArrayAdapter adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        ListView list = findViewById(R.id.lv);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListActivity.this, EditActivity.class);
                i.putExtra("name", names.get(position));
                i.putExtra("id", ids.get(position));
                startActivity(i);
            }
        });

    }

}