package com.maad.anothercameraapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Runtime permissions appeared starting from Android Marshmallow.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //If the permission is not granted, request it.
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = {
                        Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                requestPermissions(permissions, 2);
            }

        }

        //Android 11 extra code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Uri appPackage = Uri.fromParts("package", this.getPackageName(), null);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, appPackage);
            startActivity(intent);
        }

    }

    public void openCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap picture = (Bitmap) bundle.get("data");
            savePicture(picture);
        }

    }

    private void savePicture(Bitmap picture) {
        String state = Environment.getExternalStorageState();
        //Verify that storage is available
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            //Getting the root directory path
            String rootPath = Environment.getExternalStorageDirectory().toString();
            Log.d("trace", "Root Path: " + rootPath);

            /*Creating a new folder that contains the "rootPath"
            combined with a folder named "Another Camera App"*/
            File folder = new File(rootPath + "/Another Camera App");

            //If the folder doesn't exist, create a new one :)
            if (!folder.exists())
                folder.mkdir();

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); //Zero based (0 -> 11)
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            //int millis = calendar.get(Calendar.MILLISECOND);

            String name = "IMG_" + year + (month + 1) + day + '_' + hour + minute + second;

            File fullPath = new File(folder, name + ".jpg");
            Log.d("trace", "Full path: " + fullPath);

            try {
                FileOutputStream output = new FileOutputStream(fullPath);
                picture.compress(Bitmap.CompressFormat.JPEG, 100, output);
                Toast.makeText(this, "Picture Saved :)", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Log.d("trace", "Picture not saved: " + e.getLocalizedMessage());
            }

        }
    }

}