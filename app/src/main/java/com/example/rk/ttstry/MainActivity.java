package com.example.rk.ttstry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, android.hardware.Camera.PictureCallback {
    private static final String TAG =null ;
    TextToSpeech tts;
    private android.hardware.Camera mCamera = null;
    private Cameraview mCameraView = null;
    FrameLayout camera_view;
    Context context;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();
    Bitmap bp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this);
        try {

            mCamera = android.hardware.Camera.open();


            //you can use open(int) to use different cameras

        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new Cameraview(this, mCamera);//create a SurfaceView to show camera data
            camera_view = (FrameLayout) findViewById(R.id.camera_view1);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

        //btn to close the application
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCamera.autoFocus(myAutoFocusCallback);
        File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Any Reader/patna2.jpg");
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        bp1 = BitmapFactory.decodeFile( "/storage/emulated/0/Any Reader/patna2.jpg", options);
        if (file.exists()){
            Toast.makeText(MainActivity.this,"Image captured",Toast.LENGTH_SHORT).show();
            camera_view.removeAllViews();
            camera_view.addView(mCameraView);

            Intent myIntent = new Intent(MainActivity.this,Main2Activity.class);
            MainActivity.this.startActivity(myIntent);
            }
        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.speak("Hi"+"\n"+"I am there to help and assist you"+"\n"+"So, firstly tap to take a picture!",TextToSpeech.QUEUE_FLUSH,null);
                // speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    android.hardware.Camera.PictureCallback mPicture=new android.hardware.Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
            File pictureFile = new File("/storage/emulated/0/Any Reader/patna2.jpg");
            if (pictureFile == null) {
                Log.d(TAG, "error saving file");

            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not there");
            } catch (IOException e) {
                Log.d(TAG, "data not written ");
            }
        }
    };

    @Override
    public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

    }
    android.hardware.Camera.AutoFocusCallback myAutoFocusCallback = new android.hardware.Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, android.hardware.Camera camera)
        {
            mCamera.takePicture(null,null,mPicture);
        }


    };}
