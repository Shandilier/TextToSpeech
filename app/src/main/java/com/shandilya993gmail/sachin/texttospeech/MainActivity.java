package com.shandilya993gmail.sachin.texttospeech;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int result=textToSpeech.setLanguage(Locale.US);
                if(result==TextToSpeech.LANG_MISSING_DATA ||result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS","this language is not supported");
                }
                }
                else{
                    Log.e("TTS","Initialization Failed");
                }
            }
        });
        editText=(EditText) findViewById(R.id.edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 if (actionId== EditorInfo.IME_ACTION_GO){
                     speak(editText.getText().toString());
                    return true;
                 }
             return false;
            }
        });
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speak(editText.getText().toString());
            }
        });

    }

    void speak(String text){
        if(!textToSpeech.isSpeaking()){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
            }
            else{
                textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }

    @Override
    protected void onDestroy() {
if(textToSpeech!=null)   {
textToSpeech.stop();
    textToSpeech.shutdown();
}
        super.onDestroy();
    }
}
