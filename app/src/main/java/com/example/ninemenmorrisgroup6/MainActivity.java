package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.ninemenmorrisgroup6.Helps.Music;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Music.backgroundMusic = MediaPlayer.create(this, R.raw.cali);
        Music.startMusic();

    }

    public void goToSetupPage(View myView){

        Intent setupPage = new Intent (this, SetupPage.class);

        startActivity(setupPage);
    }

    public void muteSoundMain(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        Music.backgroundMusic.pause();
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }


    public void unmuteSoundMain(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        Music.backgroundMusic.start();
        Music.setMuteStatus("UNMUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }
}

