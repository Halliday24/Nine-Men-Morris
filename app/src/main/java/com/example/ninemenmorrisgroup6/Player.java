package com.example.ninemenmorrisgroup6;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.Serializable;

public class Player extends SetupPage implements Serializable {

    //DATA

    protected int highScore;
    protected String playerName;
    protected ImageView computerGamePiece;
    protected Color computerColour;
    protected int playerGamePiece;
    protected String playerColour;
    protected int difficulty;

    //GETTERS

    public int getHighScore(){

        return highScore;
    }//getHighScore

    public String getPlayerName(){

        return playerName;
    }//getPlayerName

    public int getPlayerGamePiece(){

        return playerGamePiece;
    }//getPlayerGamePiece

    public String getPlayerColour(){

        return playerColour;
    }//getPlayerColour

    public int getDifficulty(){

        return difficulty;
    }//getDifficulty

    //SETTERS

    public int setHighScore(int score){

        this.highScore = score;

        return highScore;
    }//setHighScore

    public String setPlayerName(String name){

        this.playerName = name;

        return playerName;
    }//setPlayerName

    public int setPlayerGamePiece(int token){

        this.playerGamePiece = token;

        return playerGamePiece;
    }//setPlayerGamePiece

    public String setPlayerColour(String colour){

        this.playerColour = colour;

        return playerColour;
    }//setPlayerColour

    public int setDifficulty(int mode){

        this.difficulty = mode;

        return difficulty;
    }


}
