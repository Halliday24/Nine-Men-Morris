package com.example.ninemenmorrisgroup6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.widget.TextView;


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

        if(myView == findViewById(R.id.hardComputer)) {

            HumanPlayer player1 = new HumanPlayer();
            ComputerPlayer computer = new ComputerPlayer();
            computer.setDifficulty(0);
            //findViewById(R.id.editTextNumber).setText(computer.getDifficulty());

        }
        else{}

        findViewById(R.id.easyComputer).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.coop).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.hardComputer).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstHardComputer

    public void playAgainstEasyComputer(View myView){

        //TextView a = (TextView)findViewById(R.id.editTextNumber);

        if(myView == findViewById(R.id.easyComputer)) {

            HumanPlayer player1 = new HumanPlayer();
            ComputerPlayer computer = new ComputerPlayer();
            computer.setDifficulty(1);
            //a.setText(computer.getDifficulty());

        }
        else{}

        findViewById(R.id.hardComputer).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.coop).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputer).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer
}