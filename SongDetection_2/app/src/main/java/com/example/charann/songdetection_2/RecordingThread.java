package com.example.charann.songdetection_2;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class RecordingThread {
    private static final String LOG_TAG = RecordingThread.class.getSimpleName();
    private static final int SAMPLE_RATE = 44100;
    private boolean mShouldContinue;
    private Thread mThread;
    public int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
    public byte[] buffer = new byte[bufferSize/2];

    public boolean isRecording(){
        return mThread!=null;
    }

    public void startRecording(){
        try{
            if(mThread == null){
                mShouldContinue = true;
                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Record();
                    }
                });
                mThread.start();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void stopRecording(){
        if(mThread!=null){
            mShouldContinue=false;
            mThread.stop();
            mThread=null;
        }
    }

    private void Record(){
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        if(bufferSize==AudioRecord.ERROR_BAD_VALUE || bufferSize==AudioRecord.ERROR){
            bufferSize = SAMPLE_RATE*2;
        }

        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        if(record.getState()!=AudioRecord.STATE_INITIALIZED){
            Log.e(LOG_TAG, "Audio Record can't initialize!");
            return;
        }

        record.startRecording();
        int num = 0;
        while (mShouldContinue) {
           num = record.read(buffer,0,buffer.length);
            // Data in the buffer is sent here to the Graph;
        }
        record.stop();
        record.release();
    }
}
