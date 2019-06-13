package com.example.imageedit;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Cameraimage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraimage);


    }
    @Override
    protected void onStop() {
        Intent intent = new Intent(Cameraimage.this,MediaService.class);
        stopService(intent);
        super.onStop();
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(Cameraimage.this,MediaService.class);
        startService(intent);
        super.onResume();
    }
}
