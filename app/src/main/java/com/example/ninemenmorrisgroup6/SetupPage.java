package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;


public class SetupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);

    }

    public void goToHomePage(View myView){

        Intent homePage = new Intent (this, MainActivity.class);

        startActivity(homePage);
    }//goToHomePage

    public void startGame(View myView){

        Intent start = new Intent (this, GamePage.class);

        startActivity(start);
    }//startGame

    public void playAgainstHardComputer(View myView){

        //TextView a = (TextView)findViewById(R.id.editTextNumber);

        if(myView == findViewById(R.id.hardComputerButton)) {

            HumanPlayer player1 = new HumanPlayer();
            ComputerPlayer computer = new ComputerPlayer();
            computer.setDifficulty(0);
            //findViewById(R.id.editTextNumber).setText(computer.getDifficulty());

        }
        else{}

        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstHardComputer

    public void playAgainstEasyComputer(View myView){

        //TextView a = (TextView)findViewById(R.id.editTextNumber);

        if(myView == findViewById(R.id.easyComputerButton)) {

            HumanPlayer player1 = new HumanPlayer();
            ComputerPlayer computer = new ComputerPlayer();
            computer.setDifficulty(1);
            //a.setText(computer.getDifficulty());

        }
        else{}

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer
}