package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.example.ninemenmorrisgroup6.Helps.Music;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int musicCheck = 1;

        //Allows the music to start looping the first time the app is opened and prevents it from
        //starting again if user comes back to homepage.
        if(musicCheck == Music.musicInitialization){

            Music.backgroundMusic = MediaPlayer.create(this, R.raw.mvp);
            Music.startMusic();

        }
        else{

        }

        musicCheckMain();

        //volume slider
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volControl = (SeekBar)findViewById(R.id.volControl);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(MainActivity.this,MainActivity.class));
        Music.backgroundMusic.pause();// pause music
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(MainActivity.this,MainActivity.class));
        Music.backgroundMusic.start();//resume playing
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Music.backgroundMusic.pause();
//    }

    /**
     * Send the user to the setupPage from the home page.
     * @param myView The button this is assigned to.
     */

    public void goToSetupPage(View myView){

        Intent setupPage = new Intent (this, SetupPage.class);
        Music.musicInitialization = 2;

        Music.backgroundMusic.start();

        startActivity(setupPage);
    }

    /**
     * Mutes the sound when on the home page.
     * @param myView The button that this is assigned to.
     */

    public void muteSoundMain(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        Music.backgroundMusic.pause();
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Unmutes the sound when on the home page.
     * @param myView
     */

    public void unmuteSoundMain(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        Music.backgroundMusic.start();
        Music.setMuteStatus("UNMUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Checks to see if the mute or unmute ImageButton should be displayed.
     */

    public void musicCheckMain(){

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        if(Music.getMuteStatus().equals("MUTED")){

            unmuteBtn.setVisibility(View.VISIBLE);
            muteBtn.setVisibility(View.INVISIBLE);

        }
        else if(Music.getMuteStatus().equals("UNMUTED")){

            unmuteBtn.setVisibility(View.INVISIBLE);
            muteBtn.setVisibility(View.VISIBLE);

        }

    }


    /**
     * Shows a popup containing the rules for nine men morris.
     * @param view The button that this is assigned to.
     */

    public void rulesPopupMain(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_rules_popup, null);

        // create the popup window
        int width = 1000;
        int height = 1500;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        LinearLayout dim_layout = (LinearLayout) findViewById(R.id.dim_layout_main);
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        dim_layout.setVisibility(View.VISIBLE);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                dim_layout.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }

    /**
     * Shows a popup containing a method of contacting the team.
     * @param view The button that this is assigned to.
     */

    public void feedbackPopup(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_feedback_popup, null);

        // create the popup window
        int width = 1000;
        int height = 1500;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        LinearLayout dim_layout = (LinearLayout) findViewById(R.id.dim_layout_main);
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        dim_layout.setVisibility(View.VISIBLE);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                dim_layout.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }

}

