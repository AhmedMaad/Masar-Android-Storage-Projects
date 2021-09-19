package com.maad.aliensapp;

import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private int receivedID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(R.string.edit_activity);
        receivedID = getIntent().getIntExtra("id", 0);
        EditText nameET = findViewById(R.id.et_name);
        nameET.setText(getIntent().getStringExtra("name"));
    }

    public void updateAlien(View view) {
        EditText nameET = findViewById(R.id.et_name);
        String updatedName = nameET.getText().toString();

        ContentValues content = new ContentValues();
        content.put("name", updatedName);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] whereArgs = {String.valueOf(receivedID)};
        int affectedRows = db.update("Alien", content, "_id = ?", whereArgs);
        if (affectedRows != 0) {
            Toast.makeText(this, R.string.alien_updated, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void deleteAlien(View view) {

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] whereArgs = {String.valueOf(receivedID)};
        int deletedRows = db.delete("Alien", "_id = ?", whereArgs);
        if (deletedRows != 0) {
            Toast.makeText(this, R.string.alien_deleted, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}