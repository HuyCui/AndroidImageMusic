package com.example.imageedit;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MediaService extends Service {
    private MediaPlayer mpMediaPlayer;
    public MediaService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        mpMediaPlayer = MediaPlayer.create(this, R.raw.back);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        play();

        return super.onStartCommand(intent, flags, startId);
    }
    protected void play() {
        System.out.println("play");
        //mpMediaPlayer = MediaPlayer.create(this, R.raw.back);
        try {
//            mpMediaPlayer.prepare();

            if(mpMediaPlayer.isPlaying()){
                mpMediaPlayer.stop();
                mpMediaPlayer.release();
                mpMediaPlayer = MediaPlayer.create(this, R.raw.back);
            }

            mpMediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        mpMediaPlayer.start();
        mpMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//设置重复播放
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mpMediaPlayer.start();
                mpMediaPlayer.setLooping(true);
            }
        });
    }
    @Override
    public void onDestroy() {
        if(null!=mpMediaPlayer) {
            mpMediaPlayer.stop();
            mpMediaPlayer.release();
        }
        super.onDestroy();
    }
}
