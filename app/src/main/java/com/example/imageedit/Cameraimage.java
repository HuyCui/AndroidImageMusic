package com.example.imageedit;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class Cameraimage extends AppCompatActivity {

    private String APIUrl = "http://api.map.baidu.com/telematics/v3/weather?location=淄博&output=json&ak=jF8GyQcsVOjNPpelfcZHjS2VEEbnT883";
    private TextView tview;

    private String result;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch(msg.what){
                case 0x12:
                    tview.setText(result);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraimage);
        tview = findViewById(R.id.textView);
        HeConfig.init("HE1906141533251956", "4c57161517764859826923212e781a82");
        HeConfig.switchToFreeServerNode();
        HeWeather.getWeatherNow(Cameraimage.this, "CN101120301", new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                System.out.println(e.toString());
                //Log.i(TAG, "onError: ", e);
            }

            @Override
            public void onSuccess(List<Now> dataObject) {
                System.out.println("-----------");
                System.out.println(new Gson().toJson(dataObject));
                try {
                    JSONArray wdata = new JSONArray(new Gson().toJson(dataObject));
                    JSONObject jobj = wdata.getJSONObject(0).getJSONObject("now");
                    System.out.println(jobj.getString("cond_txt"));
                    System.out.println(jobj.getString("wind_dir"));
                    System.out.println(jobj.getString("tmp"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tview.setText(new Gson().toJson(dataObject));
                //Log.i(TAG, " Weather Now onSuccess: " + new Gson().toJson(dataObject));
            }
        });

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
