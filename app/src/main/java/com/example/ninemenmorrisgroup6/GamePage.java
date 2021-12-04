package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

public class GamePage extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        playerOne = (Player) getIntent().getSerializableExtra("playerOne");
        playerTwo = (Player) getIntent().getSerializableExtra("playerTwo");
        computer = (Player) getIntent().getSerializableExtra("computer");

        initializeGamePieces();

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

    public void initializeGamePieces(){
        
        ImageView playerOne1 = (ImageView) findViewById(R.id.blackChecker1);
        ImageView playerOne2 = (ImageView) findViewById(R.id.blackChecker2);
        ImageView playerOne3 = (ImageView) findViewById(R.id.blackChecker3);
        ImageView playerOne4 = (ImageView) findViewById(R.id.blackChecker4);
        ImageView playerOne5 = (ImageView) findViewById(R.id.blackChecker5);
        ImageView playerOne6 = (ImageView) findViewById(R.id.blackChecker6);
        ImageView playerOne7 = (ImageView) findViewById(R.id.blackChecker7);
        ImageView playerOne8 = (ImageView) findViewById(R.id.blackChecker8);
        ImageView playerOne9 = (ImageView) findViewById(R.id.blackChecker9);

        ImageView playerTwo1 = (ImageView) findViewById(R.id.whiteChecker1);
        ImageView playerTwo2 = (ImageView) findViewById(R.id.whiteChecker2);
        ImageView playerTwo3 = (ImageView) findViewById(R.id.whiteChecker3);
        ImageView playerTwo4 = (ImageView) findViewById(R.id.whiteChecker4);
        ImageView playerTwo5 = (ImageView) findViewById(R.id.whiteChecker5);
        ImageView playerTwo6 = (ImageView) findViewById(R.id.whiteChecker6);
        ImageView playerTwo7 = (ImageView) findViewById(R.id.whiteChecker7);
        ImageView playerTwo8 = (ImageView) findViewById(R.id.whiteChecker8);
        ImageView playerTwo9 = (ImageView) findViewById(R.id.whiteChecker9);

        if(computer.getDifficulty() == 0){

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo2.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo3.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo4.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo5.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo6.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo7.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo8.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo9.setImageResource(playerTwo.getPlayerGamePiece());

        }
        
        else if(computer.getDifficulty() == 1){

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());

        }
        
        else if(computer.getDifficulty() == 2){

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());

        }

    }

//    public void checkPlayer(int a, int b){
//        TextView diffCheck = (TextView) findViewById(R.id.textView123);
//        ImageView playerCheck = (ImageView) findViewById(R.id.viewCheck);
//
//        diffCheck.setText(Integer.toString(b));
//        playerCheck.setImageResource(a);
//
//    }


}