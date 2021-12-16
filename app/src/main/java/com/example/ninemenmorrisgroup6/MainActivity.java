package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.example.ninemenmorrisgroup6.Helps.Music;

public class MainActivity extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int musicCheck = 1;

        //Change status bar to black
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        //Allows the music to start looping the first time the app is opened and prevents it from
        //starting again if user comes back to homepage so long as the intent sets
        //musicInitialization to 2.

        if(musicCheck == Music.musicInitialization){

            Music.backgroundMusic = MediaPlayer.create(this, R.raw.mvp);
            Music.startMusic();

        }
        else{

        }

        musicCheckMain();

    }

    /**
     * Pauses the music when the app is minimized.
     */

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(MainActivity.this,MainActivity.class));
        Music.backgroundMusic.pause();// pause music
    }

    /**
     * Restarts the music when the app is opened back up.
     */

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(MainActivity.this,MainActivity.class));
        Music.backgroundMusic.start();//resume playing
    }

    /**
     * Send the user to the setupPage from the home page.
     * @param myView The button this is assigned to.
     */

    public void goToSetupPage(View myView){

        Intent setupPage = new Intent (this, SetupPage.class);
        Music.musicInitialization = 2;

        onResume();

        startActivity(setupPage);
    }

    /**
     * Send the user straight to the gamePage for a player vs player game; Sets default settings
     * to Player One, Player Two for names and white/black circle pieces respectively.
     * @param myView The button this is assigned to.
     */

    public void quickPlay(View myView){

        playerOne.setPlayerName("Player One");
        playerOne.setPlayerGamePiece(R.drawable.white_circle);

        playerTwo.setPlayerName("Player Two");
        playerTwo.setPlayerGamePiece(R.drawable.black_circle);

        computer.setDifficulty(0);

        Intent quickPlay = new Intent(this, GamePage.class);

        quickPlay.putExtra("playerOne", playerOne);
        quickPlay.putExtra("playerTwo", playerTwo);
        quickPlay.putExtra("computer", computer);

        startActivity(quickPlay);

    }

    /**
     * Mutes the sound when on the home page.
     * @param myView The button that this is assigned to.
     */

    public void muteSoundMain(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonMain);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonMain);

        //Music.backgroundMusic.pause();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
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

        //Music.backgroundMusic.start();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,false);
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

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_rules_popup);
        dialog.setTitle("Dialog box");

        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
        dialog.getWindow().setLayout(700, 750);
    }

    /**
     * Shows a popup containing a method of contacting the team.
     * @param view The button that this is assigned to.
     */

    public void feedbackPopup(View view) {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_feedback_popup);
        dialog.setTitle("Dialog box");

        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
        dialog.getWindow().setLayout(700, 800);
    }

}

