package com.example.ninemenmorrisgroup6;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.versionedparcelable.ParcelField;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class SetupPage extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();

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

        //setPlayerOneToDefault();
        //setPlayerTwoToDefault();

        //playerOne.setPlayerGamePiece(R.drawable.blank);
        //playerTwo.setPlayerGamePiece(R.drawable.blank);


    }

    public void goToHomePage(View myView){

        Intent homePage = new Intent (this, MainActivity.class);

        startActivity(homePage);
    }//goToHomePage

    public void startGame(View myView){

        Intent start = new Intent (this, GamePage.class);

        start.putExtra("playerOne", playerOne);
        start.putExtra("playerTwo", playerTwo);
        start.putExtra("computer", computer);

        startActivity(start);
    }//startGame

    public void playAgainstHardComputer(View myView){

        if(myView == findViewById(R.id.hardComputerButton)) {

            computer.setDifficulty(0);

        }
        else{}

        singlePlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

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
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

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
        setPlayerOneToDefault();
        setPlayerTwoToDefault();

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    public void setPlayerOneToDefault(){

        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerOnePieceSelection);
        ImageView playerOneImage = (ImageView) findViewById(R.id.playerOneImage);

        playerOneImage.setImageResource(R.drawable.circle_white);
        pieceButton.setText("CIRCLE");
        colourButton.setText("WHITE");

        playerOne.setPlayerGamePiece(R.drawable.circle_white);

    }

    public void setPlayerTwoToDefault(){

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.circle_black);
        pieceButton.setText("CIRCLE");
        colourButton.setText("BLACK");

        playerTwo.setPlayerGamePiece(R.drawable.circle_black);

    }

    public void setPlayerTwoToEmpty(){

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.blank);
        pieceButton.setText("PIECES");
        colourButton.setText("COLOUR");

        playerTwo.setPlayerGamePiece(R.drawable.blank);

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

    public void customToast(String text){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(text);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();


    }

    //########################## PLAYER ONE #######################################################

    public void piecePopPlayerOne(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerOne);
        popup = pieceCheckPlayerOne(popup);
        popup.show();

    }

    public boolean onPieceMenuItemClickPlayerOne(@NonNull MenuItem item) {

        Button pieceButton = (Button) findViewById(R.id.playerOnePieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            ImageView circle = (ImageView) findViewById(R.id.playerOneImage);
            circle.setImageResource(R.drawable.circle);
            playerOne.setPlayerGamePiece(R.drawable.circle);
            pieceButton.setText("CIRCLE");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            ImageView diamond = (ImageView) findViewById(R.id.playerOneImage);
            diamond.setImageResource(R.drawable.diamond);
            playerOne.setPlayerGamePiece(R.drawable.diamond);
            pieceButton.setText("DIAMOND");
            colourButton.setText("COLOUR");

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            ImageView square = (ImageView) findViewById(R.id.playerOneImage);
            square.setImageResource(R.drawable.square);
            playerOne.setPlayerGamePiece(R.drawable.square);
            pieceButton.setText("SQUARE");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            ImageView star = (ImageView) findViewById(R.id.playerOneImage);
            star.setImageResource(R.drawable.star);
            playerOne.setPlayerGamePiece(R.drawable.star);
            pieceButton.setText("STAR");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_blank) {

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerOneImage);
            blank.setImageResource(R.drawable.blank);
            playerOne.setPlayerGamePiece(R.drawable.blank);
            pieceButton.setText("PIECES");
            colourButton.setText("COLOUR");
            return true;

        }
        else{

            return false;
        }

    }

    public PopupMenu pieceCheckPlayerOne(@NonNull PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        Menu menu = popup.getMenu();

        MenuItem circle = menu.findItem(R.id.menu_circle);
        MenuItem diamond = menu.findItem(R.id.menu_diamond);
        MenuItem square = menu.findItem(R.id.menu_square);
        MenuItem star = menu.findItem(R.id.menu_star);
        MenuItem blank = menu.findItem(R.id.menu_blank);

        if(playerOne.getPlayerGamePiece() == R.drawable.circle_red |
                playerOne.getPlayerGamePiece() == R.drawable.circle_blue |
                playerOne.getPlayerGamePiece() == R.drawable.circle_green |
                playerOne.getPlayerGamePiece() == R.drawable.circle_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.circle){

            circle.setVisible(false);
            return popup;

        }

        else if(playerOne.getPlayerGamePiece() == R.drawable.diamond_red |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_blue |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_green |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.diamond){

            diamond.setVisible(false);
            return popup;

        }
        else if(playerOne.getPlayerGamePiece() == R.drawable.square_red |
                playerOne.getPlayerGamePiece() == R.drawable.square_blue |
                playerOne.getPlayerGamePiece() == R.drawable.square_green |
                playerOne.getPlayerGamePiece() == R.drawable.square_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.square){

            square.setVisible(false);
            return popup;

        }
        else if(playerOne.getPlayerGamePiece() == R.drawable.star_red |
                playerOne.getPlayerGamePiece() == R.drawable.star_blue |
                playerOne.getPlayerGamePiece() == R.drawable.star_green |
                playerOne.getPlayerGamePiece() == R.drawable.star_yellow |
                playerOne.getPlayerGamePiece() == R.drawable.star){

            star.setVisible(false);
            return popup;

        }
        else if(playerOne.getPlayerGamePiece() == R.drawable.blank){

            blank.setVisible(false);
            return popup;

        }

        return popup;

    }

    public void colourPopPlayerOne(View v) {

        if(playerOne.getPlayerGamePiece() == R.drawable.blank){

            customToast("Pick a game piece before selecting a colour");
            //Toast.makeText(this, "Pick a game piece before selecting a colour!", Toast.LENGTH_SHORT).show();

        }
        else {

            PopupMenu popup = new PopupMenu(this, v);
            popup.setOnMenuItemClickListener(this::onColourMenuItemClickPlayerOne);
            popup = colourCheckPlayerOne(popup);
            popup.show();

        }
    }

    public boolean onColourMenuItemClickPlayerOne(@NonNull MenuItem item) {

        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerOneImage);
            red.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Red"));
            colourButton.setText("Red");
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerOneImage);
            blue.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Blue"));
            colourButton.setText("Blue");
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerOneImage);
            green.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Green"));
            colourButton.setText("Green");
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerOneImage);
            yellow.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "Yellow"));
            colourButton.setText("Yellow");
            return true;

        }
        else if(id == R.id.menu_noColour) {

            Toast.makeText(this, "You picked no colour!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerOneImage);
            noColour.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "noColour"));
            colourButton.setText("Colour");
            return true;

        }
        else{

            return false;
        }

    }

    public PopupMenu colourCheckPlayerOne(@NonNull PopupMenu popup){

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
        else if (playerTwo.getPlayerGamePiece() == R.drawable.blank) {

            yellow.setVisible(true);
            red.setVisible(true);
            blue.setVisible(true);
            green.setVisible(true);
        }

        return popup;
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
            else if (colour == "noColour"){

                ImageView noColourCircle = (ImageView) findViewById(R.id.playerOneImage);
                noColourCircle.setImageResource(R.drawable.circle);
                playerOne.setPlayerGamePiece(R.drawable.circle);

                return R.drawable.circle;

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
            else if (colour == "noColour"){

                ImageView noColourDiamond = (ImageView) findViewById(R.id.playerOneImage);
                noColourDiamond.setImageResource(R.drawable.diamond);
                playerOne.setPlayerGamePiece(R.drawable.diamond);

                return R.drawable.diamond;

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
            else if (colour == "noColour"){

                ImageView noColourSquare = (ImageView) findViewById(R.id.playerOneImage);
                noColourSquare.setImageResource(R.drawable.square);
                playerOne.setPlayerGamePiece(R.drawable.square);

                return R.drawable.square;

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
            else if (colour == "noColour"){

                ImageView noColourStar = (ImageView) findViewById(R.id.playerOneImage);
                noColourStar.setImageResource(R.drawable.star);
                playerOne.setPlayerGamePiece(R.drawable.star);

                return R.drawable.star;

            }

        }

        return 0;
    }

    //########################## PLAYER Two #######################################################

    public void piecePopPlayerTwo(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerTwo);
        popup = pieceCheckPlayerTwo(popup);
        popup.show();

    }

    public boolean onPieceMenuItemClickPlayerTwo(@NonNull MenuItem item) {

        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            ImageView circle = (ImageView) findViewById(R.id.playerTwoImage);
            circle.setImageResource(R.drawable.circle);
            playerTwo.setPlayerGamePiece(R.drawable.circle);
            pieceButton.setText("CIRCLE");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            ImageView diamond = (ImageView) findViewById(R.id.playerTwoImage);
            diamond.setImageResource(R.drawable.diamond);
            playerTwo.setPlayerGamePiece(R.drawable.diamond);
            pieceButton.setText("DIAMOND");
            colourButton.setText("COLOUR");

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            ImageView square = (ImageView) findViewById(R.id.playerTwoImage);
            square.setImageResource(R.drawable.square);
            playerTwo.setPlayerGamePiece(R.drawable.square);
            pieceButton.setText("SQUARE");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            ImageView star = (ImageView) findViewById(R.id.playerTwoImage);
            star.setImageResource(R.drawable.star);
            playerTwo.setPlayerGamePiece(R.drawable.star);
            pieceButton.setText("STAR");
            colourButton.setText("COLOUR");
            return true;

        }

        else if(id == R.id.menu_blank) {

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerTwoImage);
            blank.setImageResource(R.drawable.blank);
            playerTwo.setPlayerGamePiece(R.drawable.blank);
            pieceButton.setText("PIECES");
            colourButton.setText("COLOUR");
            return true;

        }
        else{

            return false;
        }

    }
    
    public PopupMenu pieceCheckPlayerTwo(@NonNull PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        Menu menu = popup.getMenu();
        
        MenuItem circle = menu.findItem(R.id.menu_circle);
        MenuItem diamond = menu.findItem(R.id.menu_diamond);
        MenuItem square = menu.findItem(R.id.menu_square);
        MenuItem star = menu.findItem(R.id.menu_star);
        MenuItem blank = menu.findItem(R.id.menu_blank);
        
        if(playerTwo.getPlayerGamePiece() == R.drawable.circle_red | 
                playerTwo.getPlayerGamePiece() == R.drawable.circle_blue | 
                playerTwo.getPlayerGamePiece() == R.drawable.circle_green | 
                playerTwo.getPlayerGamePiece() == R.drawable.circle_yellow | 
                playerTwo.getPlayerGamePiece() == R.drawable.circle){
            
            circle.setVisible(false);
            return popup;
            
        }

        else if(playerTwo.getPlayerGamePiece() == R.drawable.diamond_red |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_green |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.diamond){

            diamond.setVisible(false);
            return popup;

        }
        else if(playerTwo.getPlayerGamePiece() == R.drawable.square_red |
                playerTwo.getPlayerGamePiece() == R.drawable.square_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.square_green |
                playerTwo.getPlayerGamePiece() == R.drawable.square_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.square){

            square.setVisible(false);
            return popup;

        }
        else if(playerTwo.getPlayerGamePiece() == R.drawable.star_red |
                playerTwo.getPlayerGamePiece() == R.drawable.star_blue |
                playerTwo.getPlayerGamePiece() == R.drawable.star_green |
                playerTwo.getPlayerGamePiece() == R.drawable.star_yellow |
                playerTwo.getPlayerGamePiece() == R.drawable.star){

            star.setVisible(false);
            return popup;

        }
        else if(playerTwo.getPlayerGamePiece() == R.drawable.blank){

            blank.setVisible(false);
            return popup;

        }
        
        return popup;
        
    }

    public void colourPopPlayerTwo(View v) {

        if(playerTwo.getPlayerGamePiece() == R.drawable.blank){

            Toast.makeText(this, "Pick a game piece before selecting a colour!", Toast.LENGTH_SHORT).show();

        }
        else {

            PopupMenu popup = new PopupMenu(this, v);
            popup.setOnMenuItemClickListener(this::onColourMenuItemClickPlayerTwo);
            popup = colourCheckPlayerTwo(popup);
            popup.show();

        }
    }

    public boolean onColourMenuItemClickPlayerTwo(@NonNull MenuItem item) {

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerTwoImage);
            red.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Red"));
            colourButton.setText("Red");
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerTwoImage);
            blue.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Blue"));
            colourButton.setText("Blue");
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerTwoImage);
            green.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Green"));
            colourButton.setText("Green");
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerTwoImage);
            yellow.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "Yellow"));
            colourButton.setText("Yellow");
            return true;

        }

        else if(id == R.id.menu_noColour) {

            Toast.makeText(this, "You picked no colour!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerTwoImage);
            noColour.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "noColour"));
            colourButton.setText("Colour");
            return true;

        }
        else{

            return false;
        }

    }

    public PopupMenu colourCheckPlayerTwo(@NonNull PopupMenu popup){

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
        else if (playerOne.getPlayerGamePiece() == R.drawable.blank) {

            yellow.setVisible(true);
            red.setVisible(true);
            blue.setVisible(true);
            green.setVisible(true);
        }

        return popup;
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
            else if (colour == "noColour"){

                ImageView noColourCircle = (ImageView) findViewById(R.id.playerTwoImage);
                noColourCircle.setImageResource(R.drawable.circle);
                playerTwo.setPlayerGamePiece(R.drawable.circle);

                return R.drawable.circle;

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
            else if (colour == "noColour"){

                ImageView noColourDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                noColourDiamond.setImageResource(R.drawable.diamond);
                playerTwo.setPlayerGamePiece(R.drawable.diamond);

                return R.drawable.diamond;

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
            else if (colour == "noColour"){

                ImageView noColourSquare = (ImageView) findViewById(R.id.playerTwoImage);
                noColourSquare.setImageResource(R.drawable.square);
                playerTwo.setPlayerGamePiece(R.drawable.square);

                return R.drawable.square;

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
            else if (colour == "noColour"){

                ImageView noColourStar = (ImageView) findViewById(R.id.playerTwoImage);
                noColourStar.setImageResource(R.drawable.star);
                playerTwo.setPlayerGamePiece(R.drawable.star);

                return R.drawable.star;

            }

        }

        return 0;
    }
    
    //#############################################################################################

}