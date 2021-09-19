package com.maad.aliensapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        setTitle(R.string.insert_activity);
    }

    public void insertAlien(View view) {

        EditText nameET = findViewById(R.id.et_name);
        String writtenName = nameET.getText().toString();

        //Create a new map of values, where column names are the keys
        ContentValues content = new ContentValues();
        content.put("name", writtenName);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert("Alien", null, content);
        if (id != -1)
            Toast.makeText(this, R.string.alien_inserted, Toast.LENGTH_SHORT).show();

    }

}