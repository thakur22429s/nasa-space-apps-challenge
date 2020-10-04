package com.example.firefly;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = getApplicationContext();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArchiveActivity();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpload();
            }
        });
    }

    public void openArchiveActivity(){
        Intent intent = new Intent(this, ArchiveActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_find_your_bike);
    }

    public void openUpload(){
        Intent intent2 = new Intent(this, UploadActivity.class);
        startActivity(intent2);
        //setContentView(R.layout.activity_upload);
    }

}