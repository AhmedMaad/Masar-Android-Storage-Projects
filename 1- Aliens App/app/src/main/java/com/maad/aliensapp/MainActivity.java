package com.maad.aliensapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.home_activity);
    }

    public void openInsertActivity(View view) {
        startActivity(new Intent(this, InsertActivity.class));
    }

    public void openListActivity(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }

}