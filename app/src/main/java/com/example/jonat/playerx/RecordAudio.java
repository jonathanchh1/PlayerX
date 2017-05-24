package com.example.jonat.playerx;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by jonat on 5/19/2017.
 */

public class RecordAudio {
    private final String LOG_TAG = getClass().getSimpleName().toString();
    MediaRecorder mediaRecorder = null;
    private boolean isRecording = false;

    void releaseMediaRecorder(){
        if(mediaRecorder != null){
            mediaRecorder.release();
            mediaRecorder = null;
            Log.i(LOG_TAG, "Recorder released");
        }
        isRecording = false;
    }

    void startRecording(String mFilename){
        if(!isRecording()){
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(mFilename);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try{
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaRecorder.start();
            Log.i(LOG_TAG, "Recording Started");
            isRecording = true;
        }
    }

    void stopRecording(){
        try{
            if(mediaRecorder != null){
                mediaRecorder.stop();
                Log.i(LOG_TAG, "Recording Stopped");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mediaRecorder != null)
                mediaRecorder.reset();
            releaseMediaRecorder();
        }
    }

    boolean isRecording(){
        return isRecording;
    }
}
