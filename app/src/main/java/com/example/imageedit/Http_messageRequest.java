package com.example.imageedit;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public class Http_messageRequest implements Runnable {
    private String Url;
    private URL apiURL;
    public Http_messageRequest(String url) {
        try {
            apiURL = new URL(url);
        } catch (MalformedURLException e) {
            //网络连接错误
            System.out.println("network error");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {

            HttpURLConnection httpconn = (HttpURLConnection) apiURL.openConnection();
            httpconn.setRequestProperty("accept", "*/*");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            httpconn.setConnectTimeout(5000);
            httpconn.connect();
            //int stat = httpconn.getResponseCode();
            int stat = 200;
            String ss = httpconn.getRequestMethod();
            Log.i("Tag", "CODE:" + stat);
            String msg = "";
            if (stat == 200) {
                br = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                msg = br.readLine();
                Log.i("Tag", "msg" + msg);
            } else {
                msg = "请求失败";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
