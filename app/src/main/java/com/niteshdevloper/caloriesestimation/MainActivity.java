package com.niteshdevloper.caloriesestimation;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button Capture_button, Detect_button;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Capture_button = (Button) findViewById(R.id.Capture);
        Detect_button = (Button) findViewById(R.id.detect);
        this.imageView = (ImageView) findViewById(R.id.imageView3);
        Capture_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        Detect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anotheractivity = new Intent(MainActivity.this, Predict.class);
                startActivity(anotheractivity);


            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestcode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestcode, permissions, grantResults);
        if(requestcode == MY_CAMERA_PERMISSION_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission Granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else{
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {


        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode == CAMERA_REQUEST &&  resultcode == Activity.RESULT_OK){
            Bitmap photo  = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else{
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_LONG).show();
        }
    }

}