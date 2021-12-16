package com.example.ninemenmorrisgroup6;

import android.graphics.Color;
import android.view.MenuItem;
import android.widget.ImageView;

public class ComputerPlayer extends Player{

    /**
     * Creates a computerPlayer which contains information for initializing the game.
     * @param name The name of the computer, usually just computer.
     * @param mode The game mode the user has selected.
     */

    public ComputerPlayer(String name, int mode){

        this.playerName = name;
        this.difficulty = mode;
    }

    /**
     * Creates a blank/default computerPlayer
     */

    public ComputerPlayer(){


    }
}
