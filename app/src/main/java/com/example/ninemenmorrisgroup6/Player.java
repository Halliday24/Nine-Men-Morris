package com.example.ninemenmorrisgroup6;

import android.graphics.Color;
import android.view.MenuItem;
import android.widget.ImageView;

public class Player extends SetupPage{

    //DATA



    protected int highScore;
    protected String playerName;
    protected ImageView computerGamePiece;
    protected Color computerColour;
    protected MenuItem playerGamePiece;
    protected MenuItem playerColour;
    protected int difficulty;

    //GETTERS

    public int getHighScore(){

        return highScore;
    }//getHighScore

    public String getPlayerName(){

        return playerName;
    }//getPlayerName

    public MenuItem getPlayerGamePiece(){

        return playerGamePiece;
    }//getPlayerGamePiece

    public MenuItem getPlayerColour(){

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

    public MenuItem setPlayerGamePiece(MenuItem token){

        this.playerGamePiece = token;

        return playerGamePiece;
    }//setPlayerGamePiece

    public MenuItem setPlayerColour(MenuItem colour){

        this.playerColour = colour;

        return playerColour;
    }//setPlayerColour

    public int setDifficulty(int mode){

        this.difficulty = mode;

        return difficulty;
    }


}
