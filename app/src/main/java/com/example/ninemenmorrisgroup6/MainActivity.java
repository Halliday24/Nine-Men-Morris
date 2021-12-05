package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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

    /**
     * This Creates the popup for the rules
     */
    public void displayPopupWindow() {
        View layout = getLayoutInflater().inflate(R.layout.activity_rules_popup,null);
        final PopupWindow popup = new PopupWindow(this);
        TextView popupText = (TextView) findViewById(R.id.Rules);
        //setting background dim when showing popup
        LinearLayout dim_layout = (LinearLayout) findViewById(R.id.dim_layout);
        popup.setContentView(layout);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setWidth(1000);
        popup.setHeight(1500);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        dim_layout.setVisibility(View.VISIBLE);
        popup.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button button = (Button) layout.findViewById(R.id.Return);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View layout) {
                popup.dismiss();
                dim_layout.setVisibility(View.GONE);
            }
        });

    }

    /**
     * opens the popup page page for the rules
     * @param view
     */
    public void RulesButton(View view) {
        displayPopupWindow();
    }
}

