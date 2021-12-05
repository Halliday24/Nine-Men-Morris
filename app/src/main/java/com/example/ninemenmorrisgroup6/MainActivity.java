package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cali);
        backgroundMusic.setLooping(true);
        //backgroundMusic.start();


    }

    public void goToSetupPage(View myView){

        Intent setupPage = new Intent (this, SetupPage.class);

        startActivity(setupPage);
    }


    public void muteSound(View myView) {
        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButton);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButton);

        backgroundMusic.pause();

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }


    public void unmuteSound(View myView) {
        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButton);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButton);

        backgroundMusic.start();

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }


    @Override
    //Pauses music when app goes into background
    protected void onPause() {
        super.onPause();
        backgroundMusic.pause();
    }



    @Override
    //Unpauses music when app comes back from background
    protected void onResume() {
        super.onResume();
        //backgroundMusic.start();
    }


}

