package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GamePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
    }

    /**
     * Doesn't do anything yet but it will open a pop-up window where the players can change their
     * pieces
     * @param view
     */
    public void ChangePieces(View view) {
    }

    /**
     * Links the Exit game button to the home page.
     * @param view
     */
    public void ExitGame(View view) {
        Intent intent = new Intent(this, SetupPage.class);
        startActivity(intent);
    }

    /**
     * Doesn't do anything yet but it will give action to the reset button
     * @param view
     */
    public void ResetGame(View view) {
    }
}