package com.example.rk.ttstry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import android.speech.tts.TextToSpeech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Locale;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;



public class Main2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = null;
    private ProgressBar progress;
    private TextView text;
    TextView text1;
    ImageView imgv1;
    Bitmap bq;
    Button btn;
    private ClarifaiClient client;
    String result;
    String a1;
    String a2;
    String a3;
    String a4;
    String a5;
    String a6;
    String a7;
    String a8;
    String a9;
    String a10;
    String a11;
    String a12;
    String a13;
    String a14;

    TextToSpeech tts;
    String kk;
    String kk1;
    String kk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        bq= BitmapFactory.decodeFile("/storage/emulated/0/Any Reader/patna2.jpg");

        client=new ClarifaiBuilder("9h5-CeSyX9xu6FynqcA9iC7srzyrv3oeDnsdFkgI", "fkqgu2o52MWaBfmUgiggPVNdigQEXYaE4mZjknIK").buildSync();

        Toast.makeText(Main2Activity.this,"Process Start",Toast.LENGTH_LONG).show();
        onImagePicked();
        Toast.makeText(Main2Activity.this,"Process Stop",Toast.LENGTH_LONG).show();

        tts=new TextToSpeech(this,this);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Main2Activity.this,"Process Start",Toast.LENGTH_LONG).show();
//                onImagePicked();
//                Toast.makeText(Main2Activity.this,"Process Stop",Toast.LENGTH_LONG).show();
//            }
//        });

//        tts=new TextToSpeech(this,this);
    }


    private void onImagePicked() {

        new AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
            @Override protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {

                final ConceptModel generalModel = client.getDefaultModels().generalModel();

                return generalModel.predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(img(bq))))
                        .executeSync();


            }

            @Override protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {

                if (!response.isSuccessful()) {

                    Log.d(TAG,"error 1");
                    Toast.makeText(Main2Activity.this,"Error 1",Toast.LENGTH_LONG).show();
                    return;
                }
                final List<ClarifaiOutput<Concept>> predictions = response.get();
                if (predictions.isEmpty()) {
                    Log.d(TAG,"error 2");
                    Toast.makeText(Main2Activity.this,"Error 2",Toast.LENGTH_LONG).show();
                    return;
                }


//                imgv1.setImageBitmap(BitmapFactory.decodeByteArray(img(bq), 0, img(bq).length));
                Log.d(TAG,"Starting the result");
                Toast.makeText(Main2Activity.this,"Result in",Toast.LENGTH_LONG).show();
                Log.d(TAG, String.valueOf(predictions.get(0).data()));
                Log.d(TAG,"Starting the result out");
                Toast.makeText(Main2Activity.this,"Result out",Toast.LENGTH_LONG).show();
//                text1.setText(response.toString());


                result=String.valueOf(predictions.get(0).data());
                a1 = result.replace("Concept","");
                a2=a1.replace("id=","\"id\":\"");
                a3=a2.replace("name=",",\"name\":\"");
                a4=a3.replace("createdAt=",",\"createdAt\":\"");
                a5=a4.replace("appID=",",\"appID\":\"");
                a6=a5.replace("value=",",\"value\":\"");
                a7=a6.replace("language=",",\"language\":\"");
                a8=a7.replace(",","\",");
                a9=a8.replace("}\",","},");
                a10=a9.replace("}","\"}");
                a11=a10.replace("]\"}","]}");
                a12="{\"concept\":"+a11;
                a13=a12.replace("\",\"","\"");
                a14=a13+"}";


                generateNoteOnSD(String.valueOf(a14));

                jso(String.valueOf(a14));

//                tts1();


            }


        }.execute();

        tts=new TextToSpeech(this,this);

        tts1();

    }


    public static byte[] img(Bitmap bp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }



    public void generateNoteOnSD(String abcd) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "stop.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(abcd);
            writer.flush();
            writer.close();
            Toast.makeText(Main2Activity.this, "Saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jso(String abcd) {
        try {

            JSONObject parentobject=new JSONObject(abcd);
            Toast.makeText(Main2Activity.this,"TRY 1",Toast.LENGTH_LONG).show();
            JSONArray parentarray=parentobject.getJSONArray("concept");
            JSONObject finalobject=parentarray.getJSONObject(0);
            JSONObject finalobject1=parentarray.getJSONObject(1);
            JSONObject finalobject2=parentarray.getJSONObject(2);
            kk=finalobject.getString("name");
            kk1=finalobject1.getString("name");
            kk2=finalobject2.getString("name");
            Toast.makeText(Main2Activity.this,"TRY 2",Toast.LENGTH_LONG).show();
            Toast.makeText(Main2Activity.this,kk,Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            Toast.makeText(Main2Activity.this,"CATCH",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }

    public void tts1() {

        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.US);
        tts.speak("Text to say aloud", TextToSpeech.QUEUE_ADD, null);

    }

    @Override
    public void onInit(int i) {
        tts.speak("What it looks to me like is:"+"\n"+kk+"\n"+" or "+"\n"+kk1+"\n"+" or "+"\n"+kk2,TextToSpeech.QUEUE_FLUSH,null);
    }
}