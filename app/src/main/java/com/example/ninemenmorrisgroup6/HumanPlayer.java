package com.example.ninemenmorrisgroup6;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;

public class HumanPlayer extends Player{

    /**
     * Creates a humanPlayer which can store settings for initializing the game.
     * @param name The name of the player.
     * @param token The game piece of the player.
     * @param colour The colour of the players game piece.
     */

    public HumanPlayer(String name, int token, String colour){

        this.playerName = name;
        this.playerGamePiece = token;
        this.playerColour = colour;
    }

    /**
     * Creates a blank, default humanPlayer.
     */

    public HumanPlayer(){


    }
}
