package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

public class GamePage extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);



        playerOne = (Player) getIntent().getSerializableExtra("playerOne");
        checkPlayer(playerOne.getPlayerGamePiece());

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

    public void checkPlayer(int a){

        ImageView playerCheck = (ImageView) findViewById(R.id.viewCheck);
        playerCheck.setImageResource(a);

    }


}