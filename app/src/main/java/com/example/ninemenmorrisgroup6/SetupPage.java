package com.example.ninemenmorrisgroup6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;


public class SetupPage extends AppCompatActivity {

    private static Player playerOne = new HumanPlayer();
    private static Player playerTwo = new HumanPlayer();
    private static Player computer = new ComputerPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);

        Button playerOnePieceSelection = (Button) findViewById(R.id.playerOnePieceSelection);
        Button playerOneColourSelection = (Button) findViewById(R.id.playerOneColourSelection);
        Button playerTwoPieceSelection = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button playerTwoColourSelection = (Button) findViewById(R.id.playerTwoColourSelection);

        playerOnePieceSelection.setEnabled(false);
        playerOneColourSelection.setEnabled(false);
        playerTwoPieceSelection.setEnabled(false);
        playerTwoColourSelection.setEnabled(false);

        playerOne.setPlayerGamePiece(R.drawable.blank);
        playerTwo.setPlayerGamePiece(R.drawable.blank);

    }

    public void goToHomePage(View myView){

        Intent homePage = new Intent (this, MainActivity.class);

        startActivity(homePage);
    }//goToHomePage

    public void startGame(View myView){

        Intent start = new Intent (this, GamePage.class);

        //start.putExtra('a', playerOne);

        startActivity(start);
    }//startGame

    public void playAgainstHardComputer(View myView){

        if(myView == findViewById(R.id.hardComputerButton)) {

            computer.setDifficulty(0);

        }
        else{}

        singlePlayerButtonSetup();

        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstHardComputer

    public void playAgainstEasyComputer(View myView){

        if(myView == findViewById(R.id.easyComputerButton)) {

            computer.setDifficulty(1);


        }
        else{}

        singlePlayerButtonSetup();

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    public void playAgainstPlayer(View myView){

        if(myView == findViewById(R.id.pvpButton)) {

            computer.setDifficulty(1);

        }
        else{}

        multiPlayerButtonSetup();

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    public void piecePopPlayerOne(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerOne);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

    }

    public boolean onPieceMenuItemClickPlayerOne(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            ImageView circle = (ImageView) findViewById(R.id.playerOneImage);
            circle.setImageResource(R.drawable.circle);
            playerOne.setPlayerGamePiece(R.drawable.circle);
            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            ImageView diamond = (ImageView) findViewById(R.id.playerOneImage);
            diamond.setImageResource(R.drawable.diamond);
            playerOne.setPlayerGamePiece(R.drawable.diamond);

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            ImageView square = (ImageView) findViewById(R.id.playerOneImage);
            square.setImageResource(R.drawable.square);
            playerOne.setPlayerGamePiece(R.drawable.square);
            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            ImageView star = (ImageView) findViewById(R.id.playerOneImage);
            star.setImageResource(R.drawable.star);
            playerOne.setPlayerGamePiece(R.drawable.star);
            return true;

        }

        else if(id == R.id.menu_blank) {

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerOneImage);
            blank.setImageResource(R.drawable.blank);
            playerOne.setPlayerGamePiece(R.drawable.blank);
            return true;

        }
        else{

            return false;
        }

    }

    public void colourPopPlayerOne(View v) {

        if(playerOne.getPlayerGamePiece() == R.drawable.blank){

            Toast.makeText(this, "Pick a game piece before selecting a colour!", Toast.LENGTH_SHORT).show();

        }
        else {

            PopupMenu popup = new PopupMenu(this, v);
            popup.setOnMenuItemClickListener(this::onColourMenuItemClickPlayerOne);
            popup = colourCheckPlayerOne(popup);
            popup.show();

        }
    }

    public PopupMenu colourCheckPlayerOne(PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour_menu, popup.getMenu());
        Menu menu = popup.getMenu();
        MenuItem red = menu.findItem(R.id.menu_red);
        MenuItem blue = menu.findItem(R.id.menu_blue);
        MenuItem green = menu.findItem(R.id.menu_green);
        MenuItem yellow = menu.findItem(R.id.menu_yellow);

        if (playerTwo.getPlayerGamePiece() == R.drawable.circle_red |
                playerTwo.getPlayerGamePiece() == R.drawable.square_red |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_red |
                playerTwo.getPlayerGamePiece() == R.drawable.star_red){

            red.setVisible(false);
            return popup;

        }
        else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.square_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.star_blue){

            blue.setVisible(false);
            return popup;

        }
        else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_green |
                playerTwo.getPlayerGamePiece() == R.drawable.square_green |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_green|
                playerTwo.getPlayerGamePiece() == R.drawable.star_green){

            green.setVisible(false);
            return popup;

        }
        else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.square_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.star_yellow){

            yellow.setVisible(false);
            return popup;

        }

        return popup;
    }

    public boolean onColourMenuItemClickPlayerOne(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerOneImage);
            red.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Red"));
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerOneImage);
            blue.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Blue"));
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerOneImage);
            green.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Green"));
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerOneImage);
            yellow.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Yellow"));
            return true;

        }
        else{

            return false;
        }

    }

    public int setColourPlayerOne(int token, String colour){

        if (token == R.drawable.circle | token == R.drawable.circle_red | token == R.drawable.circle_blue
                | token == R.drawable.circle_green | token == R.drawable.circle_yellow){

            if (colour == "Red"){

                ImageView redCircle = (ImageView) findViewById(R.id.playerOneImage);
                redCircle.setImageResource(R.drawable.circle_red);
                playerOne.setPlayerGamePiece(R.drawable.circle_red);

                return R.drawable.circle_red;

            }
            else if (colour == "Blue"){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerOneImage);
                blueCircle.setImageResource(R.drawable.circle_blue);
                playerOne.setPlayerGamePiece(R.drawable.circle_blue);
                
                return R.drawable.circle_blue;

            }
            else if (colour == "Green"){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerOneImage);
                greenCircle.setImageResource(R.drawable.circle_green);
                playerOne.setPlayerGamePiece(R.drawable.circle_green);
                
                return R.drawable.circle_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerOneImage);
                yellowCircle.setImageResource(R.drawable.circle_yellow);
                playerOne.setPlayerGamePiece(R.drawable.circle_yellow);
                
                return R.drawable.circle_yellow;

            }

        }
        else if (token == R.drawable.diamond  | token == R.drawable.diamond_red | token == R.drawable.diamond_blue
                | token == R.drawable.diamond_green | token == R.drawable.diamond_yellow){

            if (colour == "Red"){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerOneImage);
                redDiamond.setImageResource(R.drawable.diamond_red);
                playerOne.setPlayerGamePiece(R.drawable.diamond_red);

                return R.drawable.diamond_red;

            }
            else if (colour == "Blue"){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerOneImage);
                blueDiamond.setImageResource(R.drawable.diamond_blue);
                playerOne.setPlayerGamePiece(R.drawable.diamond_blue);
                
                return R.drawable.diamond_blue;

            }
            else if (colour == "Green"){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerOneImage);
                greenDiamond.setImageResource(R.drawable.diamond_green);
                playerOne.setPlayerGamePiece(R.drawable.diamond_green);
                
                return R.drawable.diamond_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerOneImage);
                yellowDiamond.setImageResource(R.drawable.diamond_yellow);
                playerOne.setPlayerGamePiece(R.drawable.diamond_yellow);
                
                return R.drawable.diamond_yellow;

            }

        }
        else if (token == R.drawable.square  | token == R.drawable.square_red | token == R.drawable.square_blue
                | token == R.drawable.square_green | token == R.drawable.square_yellow){

            if (colour == "Red"){

                ImageView redSquare = (ImageView) findViewById(R.id.playerOneImage);
                redSquare.setImageResource(R.drawable.square_red);
                playerOne.setPlayerGamePiece(R.drawable.square_red);

                return R.drawable.square_red;

            }
            else if (colour == "Blue"){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerOneImage);
                blueSquare.setImageResource(R.drawable.square_blue);
                playerOne.setPlayerGamePiece(R.drawable.square_blue);
                
                return R.drawable.square_blue;

            }
            else if (colour == "Green"){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerOneImage);
                greenSquare.setImageResource(R.drawable.square_green);
                playerOne.setPlayerGamePiece(R.drawable.square_green);
                
                return R.drawable.square_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerOneImage);
                yellowSquare.setImageResource(R.drawable.square_yellow);
                playerOne.setPlayerGamePiece(R.drawable.square_yellow);
                
                return R.drawable.square_yellow;

            }

        }
        else if (token == R.drawable.star  | token == R.drawable.star_red | token == R.drawable.star_blue
                | token == R.drawable.star_green | token == R.drawable.star_yellow){

            if (colour == "Red"){

                ImageView redStar = (ImageView) findViewById(R.id.playerOneImage);
                redStar.setImageResource(R.drawable.star_red);
                playerOne.setPlayerGamePiece(R.drawable.star_red);

                return R.drawable.star_red;

            }
            else if (colour == "Blue"){

                ImageView blueStar = (ImageView) findViewById(R.id.playerOneImage);
                blueStar.setImageResource(R.drawable.star_blue);
                playerOne.setPlayerGamePiece(R.drawable.star_blue);
                
                return R.drawable.star_blue;

            }
            else if (colour == "Green"){

                ImageView greenStar = (ImageView) findViewById(R.id.playerOneImage);
                greenStar.setImageResource(R.drawable.star_green);
                playerOne.setPlayerGamePiece(R.drawable.star_green);
                
                return R.drawable.star_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerOneImage);
                yellowStar.setImageResource(R.drawable.star_yellow);
                playerOne.setPlayerGamePiece(R.drawable.star_yellow);
                
                return R.drawable.star_yellow;

            }

        }

        return 0;
    }

    //#############################################################################################

    public void piecePopPlayerTwo(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerTwo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

    }

    public boolean onPieceMenuItemClickPlayerTwo(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            ImageView circle = (ImageView) findViewById(R.id.playerTwoImage);
            circle.setImageResource(R.drawable.circle);
            playerTwo.setPlayerGamePiece(R.drawable.circle);
            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            ImageView diamond = (ImageView) findViewById(R.id.playerTwoImage);
            diamond.setImageResource(R.drawable.diamond);
            playerTwo.setPlayerGamePiece(R.drawable.diamond);

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            ImageView square = (ImageView) findViewById(R.id.playerTwoImage);
            square.setImageResource(R.drawable.square);
            playerTwo.setPlayerGamePiece(R.drawable.square);
            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            ImageView star = (ImageView) findViewById(R.id.playerTwoImage);
            star.setImageResource(R.drawable.star);
            playerTwo.setPlayerGamePiece(R.drawable.star);
            return true;

        }

        else if(id == R.id.menu_blank) {

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerTwoImage);
            blank.setImageResource(R.drawable.blank);
            playerTwo.setPlayerGamePiece(R.drawable.blank);
            return true;

        }
        else{

            return false;
        }

    }

    public void colourPopPlayerTwo(View v) {

        if(playerTwo.getPlayerGamePiece() == R.drawable.blank){

            Toast.makeText(this, "Pick a game piece before selecting a colour!", Toast.LENGTH_SHORT).show();

        }
        else {

            PopupMenu popup = new PopupMenu(this, v);
            popup.setOnMenuItemClickListener(this::onColourMenuItemClickPlayerOne);
            popup = colourCheckPlayerOne(popup);
            popup.show();

        }
    }

    public PopupMenu colourCheckPlayerTwo(PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour_menu, popup.getMenu());
        Menu menu = popup.getMenu();
        MenuItem red = menu.findItem(R.id.menu_red);
        MenuItem blue = menu.findItem(R.id.menu_blue);
        MenuItem green = menu.findItem(R.id.menu_green);
        MenuItem yellow = menu.findItem(R.id.menu_yellow);

        if (playerOne.getPlayerGamePiece() == R.drawable.circle_red |
                playerOne.getPlayerGamePiece() == R.drawable.square_red |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_red |
                playerOne.getPlayerGamePiece() == R.drawable.star_red){

            red.setVisible(false);
            return popup;

        }
        else if (playerOne.getPlayerGamePiece() == R.drawable.circle_blue |
                playerOne.getPlayerGamePiece() == R.drawable.square_blue |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_blue |
                playerOne.getPlayerGamePiece() == R.drawable.star_blue){

            blue.setVisible(false);
            return popup;

        }
        else if (playerOne.getPlayerGamePiece() == R.drawable.circle_green |
                playerOne.getPlayerGamePiece() == R.drawable.square_green |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_green|
                playerOne.getPlayerGamePiece() == R.drawable.star_green){

            green.setVisible(false);
            return popup;

        }
        else if (playerOne.getPlayerGamePiece() == R.drawable.circle_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.square_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.star_yellow){

            yellow.setVisible(false);
            return popup;

        }

        return popup;
    }

    public boolean onColourMenuItemClickPlayerTwo(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerTwoImage);
            red.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Red"));
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerTwoImage);
            blue.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Blue"));
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerTwoImage);
            green.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Green"));
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerTwoImage);
            yellow.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Yellow"));
            return true;

        }
        else{

            return false;
        }

    }

    public int setColourPlayerTwo(int token, String colour){

        if (token == R.drawable.circle | token == R.drawable.circle_red | token == R.drawable.circle_blue
                | token == R.drawable.circle_green | token == R.drawable.circle_yellow){

            if (colour == "Red"){

                ImageView redCircle = (ImageView) findViewById(R.id.playerTwoImage);
                redCircle.setImageResource(R.drawable.circle_red);
                playerTwo.setPlayerGamePiece(R.drawable.circle_red);

                return R.drawable.circle_red;

            }
            else if (colour == "Blue"){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerTwoImage);
                blueCircle.setImageResource(R.drawable.circle_blue);
                playerTwo.setPlayerGamePiece(R.drawable.circle_blue);

                return R.drawable.circle_blue;

            }
            else if (colour == "Green"){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerTwoImage);
                greenCircle.setImageResource(R.drawable.circle_green);
                playerTwo.setPlayerGamePiece(R.drawable.circle_green);

                return R.drawable.circle_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerTwoImage);
                yellowCircle.setImageResource(R.drawable.circle_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.circle_yellow);

                return R.drawable.circle_yellow;

            }

        }
        else if (token == R.drawable.diamond  | token == R.drawable.diamond_red | token == R.drawable.diamond_blue
                | token == R.drawable.diamond_green | token == R.drawable.diamond_yellow){

            if (colour == "Red"){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                redDiamond.setImageResource(R.drawable.diamond_red);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_red);

                return R.drawable.diamond_red;

            }
            else if (colour == "Blue"){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                blueDiamond.setImageResource(R.drawable.diamond_blue);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_blue);

                return R.drawable.diamond_blue;

            }
            else if (colour == "Green"){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                greenDiamond.setImageResource(R.drawable.diamond_green);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_green);

                return R.drawable.diamond_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                yellowDiamond.setImageResource(R.drawable.diamond_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_yellow);

                return R.drawable.diamond_yellow;

            }

        }
        else if (token == R.drawable.square  | token == R.drawable.square_red | token == R.drawable.square_blue
                | token == R.drawable.square_green | token == R.drawable.square_yellow){

            if (colour == "Red"){

                ImageView redSquare = (ImageView) findViewById(R.id.playerTwoImage);
                redSquare.setImageResource(R.drawable.square_red);
                playerTwo.setPlayerGamePiece(R.drawable.square_red);

                return R.drawable.square_red;

            }
            else if (colour == "Blue"){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerTwoImage);
                blueSquare.setImageResource(R.drawable.square_blue);
                playerTwo.setPlayerGamePiece(R.drawable.square_blue);

                return R.drawable.square_blue;

            }
            else if (colour == "Green"){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerTwoImage);
                greenSquare.setImageResource(R.drawable.square_green);
                playerTwo.setPlayerGamePiece(R.drawable.square_green);

                return R.drawable.square_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerTwoImage);
                yellowSquare.setImageResource(R.drawable.square_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.square_yellow);

                return R.drawable.square_yellow;

            }

        }
        else if (token == R.drawable.star  | token == R.drawable.star_red | token == R.drawable.star_blue
                | token == R.drawable.star_green | token == R.drawable.star_yellow){

            if (colour == "Red"){

                ImageView redStar = (ImageView) findViewById(R.id.playerTwoImage);
                redStar.setImageResource(R.drawable.star_red);
                playerTwo.setPlayerGamePiece(R.drawable.star_red);

                return R.drawable.star_red;

            }
            else if (colour == "Blue"){

                ImageView blueStar = (ImageView) findViewById(R.id.playerTwoImage);
                blueStar.setImageResource(R.drawable.star_blue);
                playerTwo.setPlayerGamePiece(R.drawable.star_blue);

                return R.drawable.star_blue;

            }
            else if (colour == "Green"){

                ImageView greenStar = (ImageView) findViewById(R.id.playerTwoImage);
                greenStar.setImageResource(R.drawable.star_green);
                playerTwo.setPlayerGamePiece(R.drawable.star_green);

                return R.drawable.star_green;

            }
            else if (colour == "Yellow"){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerTwoImage);
                yellowStar.setImageResource(R.drawable.star_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.star_yellow);

                return R.drawable.star_yellow;

            }

        }

        return 0;
    }

    public void singlePlayerButtonSetup(){

        Button playerOnePieceSelection = (Button) findViewById(R.id.playerOnePieceSelection);
        Button playerOneColourSelection = (Button) findViewById(R.id.playerOneColourSelection);
        Button playerTwoPieceSelection = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button playerTwoColourSelection = (Button) findViewById(R.id.playerTwoColourSelection);

        playerOnePieceSelection.setEnabled(true);
        playerOneColourSelection.setEnabled(true);
        playerTwoPieceSelection.setEnabled(false);
        playerTwoColourSelection.setEnabled(false);

    }

    public void multiPlayerButtonSetup(){

        Button playerOnePieceSelection = (Button) findViewById(R.id.playerOnePieceSelection);
        Button playerOneColourSelection = (Button) findViewById(R.id.playerOneColourSelection);
        Button playerTwoPieceSelection = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button playerTwoColourSelection = (Button) findViewById(R.id.playerTwoColourSelection);

        playerOnePieceSelection.setEnabled(true);
        playerOneColourSelection.setEnabled(true);
        playerTwoPieceSelection.setEnabled(true);
        playerTwoColourSelection.setEnabled(true);

    }

    public void checkOne(View myView){

        ImageView a = (ImageView) findViewById(R.id.playerOneCheck);
        a.setImageResource(playerOne.getPlayerGamePiece());

    }

    public void checkTwo(View myView){

        ImageView b = (ImageView) findViewById(R.id.playerTwoCheck);
        b.setImageResource(playerTwo.getPlayerGamePiece());

    }

}