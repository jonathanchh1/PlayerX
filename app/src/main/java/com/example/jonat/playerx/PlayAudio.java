package com.example.jonat.playerx;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by jonat on 5/19/2017.
 */

public class PlayAudio {
    private final String LOG_TAG = getClass().getSimpleName().toString();
    private boolean isPlaying = false;
    MediaPlayer mediaPlayer = null;

    void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            Log.i(LOG_TAG, "Audio Player released");
        }
    }

    void startPlaying(String mFileName){
        if(isPlaying){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                    isPlaying = false;
                }
            });

            try{
                mediaPlayer.setDataSource(mFileName);
                mediaPlayer.prepare();
                if(!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                Log.i(LOG_TAG, "Audio Player started");
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPlaying = true;
        }
    }

     void stopPlayer() {
         mediaPlayer.stop();
         mediaPlayer.reset();
         Log.i(LOG_TAG, "Audio Player Stopped");
         releaseMediaPlayer();
         isPlaying = false;
    }


    public boolean isPlaying() {
        return isPlaying;
    }
}
