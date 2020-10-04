package com.example.firefly;

import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class UploadActivity<Latitude> extends AppCompatActivity {
    private File img;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String latitude;
    private String longitude;
    private Button button3;

    // ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        // The following follows https://www.youtube.com/watch?v=ondCeqlAwEI
        Button cameraBtn = (Button) findViewById(R.id.cameraButton);
        //imageView = (ImageView)findViewById(R.id.imageView); // For displaying image after

        System.out.println("1");

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
                System.out.println("2");
            }
        });

        Button locationBtn = (Button) findViewById(R.id.locationButton);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acquireLocation();
            }
        });

        button3 = (Button) findViewById(R.id.locationButton);
        final TextView textView = (TextView) findViewById(R.id.textView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textView.append("\n" + location.getLatitude() + " " + location.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent3 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent3);


            }
        };

        System.out.print("Latitude: " + latitude);
        System.out.print("Longitude: " + longitude);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        FileOutputStream out;
        img = null;
        try {
            img = File.createTempFile("tmpimg.png", null, MainActivity.context.getCacheDir());
            out = new FileOutputStream(img);
            System.out.println((bitmap==null) + " " + out == null);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //uploadData();
            System.out.println("3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(bitmap); //For displaying the image after
    }

    public void acquireLocation() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = "" + location.getLatitude();
                longitude = "" + location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);


            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
    }






    public byte[] convertBitmaptoBytes(Bitmap bitmap) {
        ByteBuffer bb = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(bb);
        return bb.array();
    }
}
