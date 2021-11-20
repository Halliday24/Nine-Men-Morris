package com.example.ninemenmorrisgroup6;

import android.view.MenuItem;

public class HumanPlayer extends Player{

    public HumanPlayer(String name, int score, MenuItem token, MenuItem colour){

        this.playerName = name;
        this.highScore = score;
        this.playerGamePiece = token;
        this.playerColour = colour;
    }

    public HumanPlayer(){


    }
}
