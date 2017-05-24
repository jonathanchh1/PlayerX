package com.example.jonat.playerx;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String LOG_TAG = getClass().getSimpleName().toString();
    String mFileName = null;
    RecordAudio recordAudio = new RecordAudio();
    PlayAudio playAudio = new PlayAudio();

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecord.3gp";

        final FloatingActionButton fab_rec = (FloatingActionButton) findViewById(R.id.fab_rec);
        textView = (TextView) findViewById(R.id.txt);

        fab_rec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textView.setText("Recording Started");
                        getPermission();
                        break;
                    case MotionEvent.ACTION_UP:
                        textView.setText("Press and hold record button to record");
                        recordAudio.stopRecording();
                        break;
                }
                return true;
            }

        });

        FloatingActionButton fab_play = (FloatingActionButton) findViewById(R.id.fab_play);
        fab_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playAudio.isPlaying()) {
                    textView.setText("Press and hold record button to record/playing");
                    playAudio.stopPlayer();
                } else {
                    textView.setText("Audio play started");
                    playAudio.startPlaying(mFileName);
                }
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO },
                    10);
        } else {
            recordAudio.startRecording(mFileName);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        recordAudio.releaseMediaRecorder();
        playAudio.releaseMediaPlayer();
    }
}
