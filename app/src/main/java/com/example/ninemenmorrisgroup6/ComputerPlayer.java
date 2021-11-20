package com.example.ninemenmorrisgroup6;

import android.graphics.Color;
import android.view.MenuItem;
import android.widget.ImageView;

public class ComputerPlayer extends Player{

    public ComputerPlayer(String name, ImageView token, Color colour, int mode){

        this.playerName = name;
        this.computerGamePiece = token;
        this.computerColour = colour;
        this.difficulty = mode;
    }

    public ComputerPlayer(){


    }
}
