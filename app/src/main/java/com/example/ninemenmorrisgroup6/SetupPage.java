package com.example.ninemenmorrisgroup6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ninemenmorrisgroup6.Helps.Music;

public class SetupPage extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);

        //disables the colour and piece selection buttons right off the bat, they will be
        //enabled in the correct manner when a game mode is chosen.

        Button playerOnePieceSelection = (Button) findViewById(R.id.playerOnePieceSelection);
        Button playerOneColourSelection = (Button) findViewById(R.id.playerOneColourSelection);
        Button playerTwoPieceSelection = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button playerTwoColourSelection = (Button) findViewById(R.id.playerTwoColourSelection);

        playerOnePieceSelection.setEnabled(false);
        playerOneColourSelection.setEnabled(false);
        playerTwoPieceSelection.setEnabled(false);
        playerTwoColourSelection.setEnabled(false);

        computer.setDifficulty(3);
        musicCheckSetup();

    }

    /**
     * Sets the intent and sends the user back to the home page.
     * @param myView The button that this is assigned to.
     */

    public void goToHomePage(View myView){

        Intent homePage = new Intent (this, MainActivity.class);

        startActivity(homePage);
    }//goToHomePage

    /**
     * Sets the intent and sends the user to the game page. Will check to see that the correct
     * settings have been selected first by using checkForInitialization();.
     * @param myView The button that this is assigned to.
     */

    public void startGame(View myView){

        if(checkForInitialization() == true){

            Intent start = new Intent (this, GamePage.class);

            start.putExtra("playerOne", playerOne);
            start.putExtra("playerTwo", playerTwo);
            start.putExtra("computer", computer);

            startActivity(start);

        }
        else{

        }

    }//startGame

    /**
     * Sets the computer difficulty initializer to the correct value, initializes the colour and
     * piece choices appropriately and dims the Easy computer and pvp buttons.
     * @param myView The button this is assigned to.
     */

    public void playAgainstHardComputer(View myView){

        computer.setDifficulty(2);
        computer.setPlayerGamePiece(R.drawable.circle_black);

        singlePlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

        //Dims the easyComputerButton and pvpButton.
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstHardComputer

    /**
     * Sets the computer difficulty initializer to the correct value, initializes the colour and
     * piece choices appropriately and dims the Hard computer and pvp buttons.
     * @param myView The button this is assigned to.
     */

    public void playAgainstEasyComputer(View myView){

        computer.setDifficulty(1);
        computer.setPlayerGamePiece(R.drawable.circle_black);

        singlePlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

        //Dims the hardComputerButton and the pvpButton.
        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    /**
     * Sets the computer difficulty initializer to the correct value, initializes the colour and
     * piece choices appropriately and dims the Hard computer and Easy computer buttons.
     * @param myView The button this is assigned to.
     */

    public void playAgainstPlayer(View myView){

        computer.setDifficulty(0);
        computer.setPlayerGamePiece(R.drawable.blank);

        multiPlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToDefault();

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    /**
     * Sets playerOne to a white circle game piece as its default game piece, and changes the
     * playerOne colour and piece selection buttons text to reflect that.
     */

    public void setPlayerOneToDefault(){

        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerOnePieceSelection);
        ImageView playerOneImage = (ImageView) findViewById(R.id.playerOneImage);

        playerOneImage.setImageResource(R.drawable.circle_white);
        pieceButton.setText("CIRCLE");
        colourButton.setText("WHITE");

        playerOne.setPlayerGamePiece(R.drawable.circle_white);
        playerOne.setPlayerColour("WHITE");

    }

    /**
     * Sets playerTwo to a black circle game piece as its default game piece, and changes the
     * playerTwo colour and piece selection buttons text to reflect that.
     */

    public void setPlayerTwoToDefault(){

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.circle_black);
        pieceButton.setText("CIRCLE");
        colourButton.setText("BLACK");

        playerTwo.setPlayerGamePiece(R.drawable.circle_black);
        playerTwo.setPlayerColour("BLACK");

    }

    /**
     * Sets playerTwo's piece selection to blank and it's colour to "EMPTY". Used when playerOne
     * is going to play against the computer.
     */

    public void setPlayerTwoToEmpty(){

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);
        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.blank);
        pieceButton.setText("PIECES");
        colourButton.setText("COLOUR");

        playerTwo.setPlayerColour("EMPTY");
        playerTwo.setPlayerGamePiece(R.drawable.blank);

    }

    /**
     * Disables the colour and piece selection buttons for playerTwo and enables them for playerOne
     * for when playerOne is going to play against the computer.
     */

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

    /**
     * Enables the colour and piece selection buttons for playerOne and playerTwo when they are
     * going to play against each other.
     */

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

    /**
     * Code for enabling a custom toast; Appears at the top of the screen in a custom drawable; Will
     * show the toast, no need to add .show() to the method.
     * @param text The message that you want the custom toast to display.
     */

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

    /**
     * Checks to make sure that the appropriate settings have been selected for the selected game
     * mode before sending the user to the gamePage.
     * @return Returns true if settings are correct, false otherwise.
     */

    public boolean checkForInitialization(){


        //Player vs player mode
        if(computer.getDifficulty() == 0){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() != R.drawable.blank){

                return true;

            }

            else{

                Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        //Player vs easy computer mode
        else if(computer.getDifficulty() == 1){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() == R.drawable.blank){

                return true;

            }

            else{

                Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        //Player vs hard computer mode
        else if(computer.getDifficulty() == 2){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() == R.drawable.blank){

                return true;

            }

            else{

                Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        else if(computer.getDifficulty() == 3){

            Toast.makeText(this, "Select a game mode first!", Toast.LENGTH_SHORT).show();
            return false;

        }

        Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void muteSoundSetup(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonSetup);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonSetup);

        Music.backgroundMusic.pause();
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }

    public void unmuteSoundSetup(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonSetup);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonSetup);

        Music.backgroundMusic.start();
        Music.setMuteStatus("UNMUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }

    public void musicCheckSetup(){

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonSetup);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonSetup);

        if(Music.getMuteStatus().equals("MUTED")){

            unmuteBtn.setVisibility(View.VISIBLE);
            muteBtn.setVisibility(View.INVISIBLE);

        }
        else if(Music.getMuteStatus().equals("UNMUTED")){

            unmuteBtn.setVisibility(View.INVISIBLE);
            muteBtn.setVisibility(View.VISIBLE);

        }

    }

    //########################## PLAYER ONE METHODS ################################################

    /**
     * Brings up the options menu for playerOne's piece selection.
     * @param v The button this is assigned to.
     */

    public void piecePopPlayerOne(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerOne);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

    }

    /**
     * Assigns to playerOne the game piece that they selected from the options menu; Kicks out a
     * toast message alerting the user to their choice; Uses playerOnePreserveColourChoice() to
     * preserve the users colour choice.
     * @param item The menu option that was click/selected.
     * @return Returns true if a menu item was selected, false otherwise.
     */

    public boolean onPieceMenuItemClickPlayerOne(@NonNull MenuItem item) {

        Button pieceButton = (Button) findViewById(R.id.playerOnePieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("CIRCLE");

            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("DIAMOND");

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("SQUARE");

            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("STAR");

            return true;

        }

        else if(id == R.id.menu_blank) { //Sets the users colour to "EMPTY" and their piece to blank.

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerOneImage);
            blank.setImageResource(R.drawable.blank);
            playerOne.setPlayerGamePiece(R.drawable.blank);
            playerOne.setPlayerColour("EMPTY");
            pieceButton.setText("PIECES");
            colourButton.setText("COLOUR");
            return true;

        }

        else{

            return false;
        }

    }

    /**
     * Brings up the options menu for playerOne's piece selection; Uses colourCheckPlayerOne to
     * remove from the menu the colour that playerTwo has selected.
     * @param v The button that this is assigned to.
     */

    public void colourPopPlayerOne(View v) {

        //Players must select a game piece before selecting a colour.
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

    /**
     * Assigns to playerOne the colour that they selected from the options menu; Will change the
     * drawable to the correct colour using setColourPlayerOne(); Kicks out a toast message alerting
     * the user to their choice; colourCheckPlayerOne() will stop the player from selecting a
     * colour the computer or playerTwo is using.
     * @param item The menu option that was click/selected.
     * @return Returns true if a menu option was selected, false otherwise.
     */

    public boolean onColourMenuItemClickPlayerOne(@NonNull MenuItem item) {

        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerOneImage);
            red.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "RED"));
            colourButton.setText("RED");
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerOneImage);
            blue.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "BLUE"));
            colourButton.setText("BLUE");
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerOneImage);
            green.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "GREEN"));
            colourButton.setText("GREEN");
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerOneImage);
            yellow.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "YELLOW"));
            colourButton.setText("YELLOW");
            return true;

        }

        else if(id == R.id.menu_black) {

            Toast.makeText(this, "You picked black!", Toast.LENGTH_SHORT).show();
            ImageView black = (ImageView) findViewById(R.id.playerOneImage);
            black.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "BLACK"));
            colourButton.setText("BLACK");
            return true;

        }

        else if(id == R.id.menu_white) {

            Toast.makeText(this, "You picked white!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerOneImage);
            noColour.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "WHITE"));
            colourButton.setText("WHITE");
            return true;

        }

        else{

            return false;
        }

    }

    /**
     * Disables from the colour choice menu whichever colour playerOne's opponent has chosen to
     * prevent both players from having the same colour game pieces.
     * @param popup The colour options menu popup.
     * @return The modified colour options menu popup.
     */

    public PopupMenu colourCheckPlayerOne(@NonNull PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour_menu, popup.getMenu());
        Menu menu = popup.getMenu();

        MenuItem red = menu.findItem(R.id.menu_red);
        MenuItem blue = menu.findItem(R.id.menu_blue);
        MenuItem green = menu.findItem(R.id.menu_green);
        MenuItem yellow = menu.findItem(R.id.menu_yellow);
        MenuItem white = menu.findItem(R.id.menu_white);
        MenuItem black = menu.findItem(R.id.menu_black);

        //If playing against another user.
        if(computer.getDifficulty() == 0) {

            if (playerTwo.getPlayerGamePiece() == R.drawable.circle_red |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_red |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_red |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_red) {

                red.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_blue |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_blue |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_blue |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_blue) {

                blue.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_green |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_green |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_green |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_green) {

                green.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_yellow |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_yellow |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_yellow |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_yellow) {

                yellow.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_white |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_white |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_white |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_white) {

                white.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.circle_black |
                    playerTwo.getPlayerGamePiece() == R.drawable.square_black |
                    playerTwo.getPlayerGamePiece() == R.drawable.diamond_black |
                    playerTwo.getPlayerGamePiece() == R.drawable.star_black) {

                black.setVisible(false);
                return popup;

            }

            //If the other user has no colour selected.
            else if (playerTwo.getPlayerGamePiece() == R.drawable.blank) {

                yellow.setVisible(true);
                red.setVisible(true);
                blue.setVisible(true);
                green.setVisible(true);
                white.setVisible(true);
                black.setVisible(true);
            }
        }

        //removes black as an option as that is the default computer colour.
        else if(computer.getDifficulty() == 1 | computer.getDifficulty() == 2){

            black.setVisible(false);
            return popup;

        }

        return popup;

    }

    /**
     * Changes the colour of the game piece playerOne has selected; Checks to see what shape
     * their piece is and returns the same shape coloured in their choice.
     * @param token The game piece that playerOne currently has.
     * @param colour The colour that playerOne wants their game piece to be; must be in ALL CAPS.
     * @return PlayerOne's game piece in the correct colour.
     */

    public int setColourPlayerOne(int token, String colour){

        //if playerOne has a circle they want in a different colour.
        if (token == R.drawable.circle_white | token == R.drawable.circle_red | token == R.drawable.circle_blue
                | token == R.drawable.circle_green | token == R.drawable.circle_yellow | token == R.drawable.circle_black){

            if (colour.equals("RED")){

                ImageView redCircle = (ImageView) findViewById(R.id.playerOneImage);
                redCircle.setImageResource(R.drawable.circle_red);
                playerOne.setPlayerGamePiece(R.drawable.circle_red);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerOneImage);
                blueCircle.setImageResource(R.drawable.circle_blue);
                playerOne.setPlayerGamePiece(R.drawable.circle_blue);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerOneImage);
                greenCircle.setImageResource(R.drawable.circle_green);
                playerOne.setPlayerGamePiece(R.drawable.circle_green);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerOneImage);
                yellowCircle.setImageResource(R.drawable.circle_yellow);
                playerOne.setPlayerGamePiece(R.drawable.circle_yellow);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackCircle = (ImageView) findViewById(R.id.playerOneImage);
                blackCircle.setImageResource(R.drawable.circle_black);
                playerOne.setPlayerGamePiece(R.drawable.circle_black);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteCircle = (ImageView) findViewById(R.id.playerOneImage);
                whiteCircle.setImageResource(R.drawable.circle_white);
                playerOne.setPlayerGamePiece(R.drawable.circle_white);
                playerOne.setPlayerColour(colour);

                return R.drawable.circle_white;

            }

        }

        //if playerOne has a diamond they want in a different colour.
        else if (token == R.drawable.diamond_white | token == R.drawable.diamond_red | token == R.drawable.diamond_blue
                | token == R.drawable.diamond_green | token == R.drawable.diamond_yellow | token == R.drawable.diamond_black){

            if (colour.equals("RED")){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerOneImage);
                redDiamond.setImageResource(R.drawable.diamond_red);
                playerOne.setPlayerGamePiece(R.drawable.diamond_red);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerOneImage);
                blueDiamond.setImageResource(R.drawable.diamond_blue);
                playerOne.setPlayerGamePiece(R.drawable.diamond_blue);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerOneImage);
                greenDiamond.setImageResource(R.drawable.diamond_green);
                playerOne.setPlayerGamePiece(R.drawable.diamond_green);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerOneImage);
                yellowDiamond.setImageResource(R.drawable.diamond_yellow);
                playerOne.setPlayerGamePiece(R.drawable.diamond_yellow);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackDiamond = (ImageView) findViewById(R.id.playerOneImage);
                blackDiamond.setImageResource(R.drawable.diamond_black);
                playerOne.setPlayerGamePiece(R.drawable.diamond_black);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteDiamond = (ImageView) findViewById(R.id.playerOneImage);
                whiteDiamond.setImageResource(R.drawable.diamond_white);
                playerOne.setPlayerGamePiece(R.drawable.diamond_white);
                playerOne.setPlayerColour(colour);

                return R.drawable.diamond_white;

            }

        }

        //if playerOne has a square they want in a different colour.
        else if (token == R.drawable.square_white | token == R.drawable.square_red | token == R.drawable.square_blue
                | token == R.drawable.square_green | token == R.drawable.square_yellow | token == R.drawable.square_black){

            if (colour.equals("RED")){

                ImageView redSquare = (ImageView) findViewById(R.id.playerOneImage);
                redSquare.setImageResource(R.drawable.square_red);
                playerOne.setPlayerGamePiece(R.drawable.square_red);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerOneImage);
                blueSquare.setImageResource(R.drawable.square_blue);
                playerOne.setPlayerGamePiece(R.drawable.square_blue);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerOneImage);
                greenSquare.setImageResource(R.drawable.square_green);
                playerOne.setPlayerGamePiece(R.drawable.square_green);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerOneImage);
                yellowSquare.setImageResource(R.drawable.square_yellow);
                playerOne.setPlayerGamePiece(R.drawable.square_yellow);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackSquare = (ImageView) findViewById(R.id.playerOneImage);
                blackSquare.setImageResource(R.drawable.square_black);
                playerOne.setPlayerGamePiece(R.drawable.square_black);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteSquare = (ImageView) findViewById(R.id.playerOneImage);
                whiteSquare.setImageResource(R.drawable.square_white);
                playerOne.setPlayerGamePiece(R.drawable.square_white);
                playerOne.setPlayerColour(colour);

                return R.drawable.square_white;

            }

        }

        //if playerOne has a star they want in a different colour.
        else if (token == R.drawable.star_white | token == R.drawable.star_red | token == R.drawable.star_blue
                | token == R.drawable.star_green | token == R.drawable.star_yellow | token == R.drawable.star_black){

            if (colour.equals("RED")){

                ImageView redStar = (ImageView) findViewById(R.id.playerOneImage);
                redStar.setImageResource(R.drawable.star_red);
                playerOne.setPlayerGamePiece(R.drawable.star_red);
                playerOne.setPlayerColour(colour);

                return R.drawable.star_red;

            }

            else if (colour.equals("GREEN")){

                ImageView greenStar = (ImageView) findViewById(R.id.playerOneImage);
                greenStar.setImageResource(R.drawable.star_green);
                playerOne.setPlayerGamePiece(R.drawable.star_green);
                playerOne.setPlayerColour(colour);

                return R.drawable.star_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerOneImage);
                yellowStar.setImageResource(R.drawable.star_yellow);
                playerOne.setPlayerGamePiece(R.drawable.star_yellow);
                playerOne.setPlayerColour(colour);

                return R.drawable.star_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackStar = (ImageView) findViewById(R.id.playerOneImage);
                blackStar.setImageResource(R.drawable.star_black);
                playerOne.setPlayerGamePiece(R.drawable.star_black);
                playerOne.setPlayerColour(colour);

                return R.drawable.star_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteStar = (ImageView) findViewById(R.id.playerOneImage);
                whiteStar.setImageResource(R.drawable.star_white);
                playerOne.setPlayerGamePiece(R.drawable.star_white);
                playerOne.setPlayerColour(colour);

                return R.drawable.star_white;

            }

        }

        //Returns zero as a default, however the user will be stopped from selecting a colour if
        //they haven't selected a game piece so this should never be reached.
        return 0;
    }

    /**
     * Preserves colour choice when playerOne selects a new shape for their game piece. The new
     * colour must be assigned to playerOne before calling.
     * @param piece The shape of the game piece playerOne has in ALL CAPS.
     */

    public void playerOnePreserveColourChoice(String piece){

        Button pieceButton = (Button) findViewById(R.id.playerOnePieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerOneColourSelection);
        ImageView display = (ImageView) findViewById(R.id.playerOneImage);

        //If playerOne wants their game piece to be a circle with the same colour as their previous.
        if(piece.equals("CIRCLE")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.circle_red);
                playerOne.setPlayerGamePiece(R.drawable.circle_red);

                pieceButton.setText("CIRCLE");
                colourButton.setText("RED");

            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.circle_blue);
                playerOne.setPlayerGamePiece(R.drawable.circle_blue);

                pieceButton.setText("CIRCLE");
                colourButton.setText("BLUE");

            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.circle_green);
                playerOne.setPlayerGamePiece(R.drawable.circle_green);

                pieceButton.setText("CIRCLE");
                colourButton.setText("GREEN");

            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.circle_yellow);
                playerOne.setPlayerGamePiece(R.drawable.circle_yellow);

                pieceButton.setText("CIRCLE");
                colourButton.setText("YELLOW");

            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.circle_white);
                playerOne.setPlayerGamePiece(R.drawable.circle_white);

                pieceButton.setText("CIRCLE");
                colourButton.setText("WHITE");

            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.circle_black);
                playerOne.setPlayerGamePiece(R.drawable.circle_black);

                pieceButton.setText("CIRCLE");
                colourButton.setText("BLACK");

            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.circle_black);
                    playerOne.setPlayerGamePiece(R.drawable.circle_black);
                    playerOne.setPlayerColour("BLACK");

                    pieceButton.setText("CIRCLE");
                    colourButton.setText("BLACK");
                }

                else{

                    display.setImageResource(R.drawable.circle_white);
                    playerOne.setPlayerGamePiece(R.drawable.circle_white);
                    playerOne.setPlayerColour("WHITE");

                    pieceButton.setText("CIRCLE");
                    colourButton.setText("WHITE");
                }
            }
        }

        //If playerOne wants their game piece to be a diamond with the same colour as their previous.
        else if(piece.equals("DIAMOND")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.diamond_red);
                playerOne.setPlayerGamePiece(R.drawable.diamond_red);

                pieceButton.setText("DIAMOND");
                colourButton.setText("RED");

            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.diamond_blue);
                playerOne.setPlayerGamePiece(R.drawable.diamond_blue);

                pieceButton.setText("DIAMOND");
                colourButton.setText("BLUE");

            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.diamond_green);
                playerOne.setPlayerGamePiece(R.drawable.diamond_green);

                pieceButton.setText("DIAMOND");
                colourButton.setText("GREEN");

            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.diamond_yellow);
                playerOne.setPlayerGamePiece(R.drawable.diamond_yellow);

                pieceButton.setText("DIAMOND");
                colourButton.setText("YELLOW");

            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.diamond_white);
                playerOne.setPlayerGamePiece(R.drawable.diamond_white);

                pieceButton.setText("DIAMOND");
                colourButton.setText("WHITE");

            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.diamond_black);
                playerOne.setPlayerGamePiece(R.drawable.diamond_black);

                pieceButton.setText("DIAMOND");
                colourButton.setText("BLACK");

            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.diamond_black);
                    playerOne.setPlayerGamePiece(R.drawable.diamond_black);
                    playerOne.setPlayerColour("BLACK");

                    pieceButton.setText("DIAMOND");
                    colourButton.setText("BLACK");
                }

                else{

                    display.setImageResource(R.drawable.diamond_white);
                    playerOne.setPlayerGamePiece(R.drawable.diamond_white);
                    playerOne.setPlayerColour("WHITE");

                    pieceButton.setText("DIAMOND");
                    colourButton.setText("WHITE");
                }
            }

        }

        //If playerOne wants their game piece to be a square with the same colour as their previous.
        else if(piece.equals("SQUARE")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.square_red);
                playerOne.setPlayerGamePiece(R.drawable.square_red);

                pieceButton.setText("SQUARE");
                colourButton.setText("RED");

            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.square_blue);
                playerOne.setPlayerGamePiece(R.drawable.square_blue);

                pieceButton.setText("SQUARE");
                colourButton.setText("BLUE");

            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.square_green);
                playerOne.setPlayerGamePiece(R.drawable.square_green);

                pieceButton.setText("SQUARE");
                colourButton.setText("GREEN");

            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.square_yellow);
                playerOne.setPlayerGamePiece(R.drawable.square_yellow);

                pieceButton.setText("SQUARE");
                colourButton.setText("YELLOW");

            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.square_white);
                playerOne.setPlayerGamePiece(R.drawable.square_white);

                pieceButton.setText("SQUARE");
                colourButton.setText("WHITE");

            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.square_black);
                playerOne.setPlayerGamePiece(R.drawable.square_black);

                pieceButton.setText("SQUARE");
                colourButton.setText("BLACK");

            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.square_black);
                    playerOne.setPlayerGamePiece(R.drawable.square_black);
                    playerOne.setPlayerColour("BLACK");

                    pieceButton.setText("SQUARE");
                    colourButton.setText("BLACK");
                }

                else{

                    display.setImageResource(R.drawable.square_white);
                    playerOne.setPlayerGamePiece(R.drawable.square_white);
                    playerOne.setPlayerColour("WHITE");

                    pieceButton.setText("SQUARE");
                    colourButton.setText("WHITE");
                }
            }

        }

        //If playerOne wants their game piece to be a star with the same colour as their previous.
        else if(piece.equals("STAR")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.star_red);
                playerOne.setPlayerGamePiece(R.drawable.star_red);

                pieceButton.setText("STAR");
                colourButton.setText("RED");

            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.star_blue);
                playerOne.setPlayerGamePiece(R.drawable.star_blue);

                pieceButton.setText("STAR");
                colourButton.setText("BLUE");

            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.star_green);
                playerOne.setPlayerGamePiece(R.drawable.star_green);

                pieceButton.setText("STAR");
                colourButton.setText("GREEN");

            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.star_yellow);
                playerOne.setPlayerGamePiece(R.drawable.star_yellow);

                pieceButton.setText("STAR");
                colourButton.setText("YELLOW");

            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.star_white);
                playerOne.setPlayerGamePiece(R.drawable.star_white);

                pieceButton.setText("STAR");
                colourButton.setText("WHITE");

            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.star_black);
                playerOne.setPlayerGamePiece(R.drawable.star_black);

                pieceButton.setText("STAR");
                colourButton.setText("BLACK");

            }
            
            else if(playerOne.getPlayerColour().equals("EMPTY")){
                
                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.star_black);
                    playerOne.setPlayerGamePiece(R.drawable.star_black);
                    playerOne.setPlayerColour("BLACK");

                    pieceButton.setText("STAR");
                    colourButton.setText("BLACK");
                }
                
                else{

                    display.setImageResource(R.drawable.star_white);
                    playerOne.setPlayerGamePiece(R.drawable.star_white);
                    playerOne.setPlayerColour("WHITE");

                    pieceButton.setText("STAR");
                    colourButton.setText("WHITE");
                }
            }

        }
        
    }

    //########################## PLAYER TWO METHODS ################################################

    /**
     * Brings up the options menu for playerTwo's piece selection.
     * @param v The button this is assigned to.
     */

    public void piecePopPlayerTwo(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onPieceMenuItemClickPlayerTwo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

    }

    /**
     * Assigns to playerTwo the game piece that they selected from the options menu; Kicks out a
     * toast message alerting the user to their choice; Uses playerTwoPreserveColourChoice() to
     * preserve the users colour choice.
     * @param item The menu option that was click/selected.
     * @return Returns true if a menu item was selected, false otherwise.
     */

    public boolean onPieceMenuItemClickPlayerTwo(@NonNull MenuItem item) {

        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("CIRCLE");

            return true;

        }

        else if(id == R.id.menu_diamond) {

            Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("DIAMOND");

            return true;

        }

        else if(id == R.id.menu_square) {

            Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("SQUARE");

            return true;

        }

        else if(id == R.id.menu_star) {

            Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();
            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("STAR");

            return true;

        }

        else if(id == R.id.menu_blank) { //Sets the users colour to "EMPTY" and their piece to blank.

            Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerTwoImage);
            blank.setImageResource(R.drawable.blank);
            playerTwo.setPlayerGamePiece(R.drawable.blank);
            playerTwo.setPlayerColour("EMPTY");
            pieceButton.setText("PIECES");
            colourButton.setText("COLOUR");
            return true;

        }

        else{

            return false;
        }

    }

    /**
     * Brings up the options menu for playerTwo's piece selection; Uses colourCheckPlayerTwo to
     * remove from the menu the colour that playerTwo has selected.
     * @param v The button that this is assigned to.
     */

    public void colourPopPlayerTwo(View v) {

        //Players must select a game piece before selecting a colour.
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

    /**
     * Assigns to playerTwo the colour that they selected from the options menu; Will change the
     * drawable to the correct colour using setColourPlayerTwo(); Kicks out a toast message alerting
     * the user to their choice; colourCheckPlayerTwo() will stop the player from selecting a
     * colour the computer or playerOne is using.
     * @param item The menu option that was click/selected.
     * @return Returns true if a menu option was selected, false otherwise.
     */

    public boolean onColourMenuItemClickPlayerTwo(@NonNull MenuItem item) {

        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerTwoImage);
            red.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "RED"));
            colourButton.setText("RED");
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerTwoImage);
            blue.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "BLUE"));
            colourButton.setText("BLUE");
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerTwoImage);
            green.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "GREEN"));
            colourButton.setText("GREEN");
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerTwoImage);
            yellow.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "YELLOW"));
            colourButton.setText("YELLOW");
            return true;

        }

        else if(id == R.id.menu_black) {

            Toast.makeText(this, "You picked black!", Toast.LENGTH_SHORT).show();
            ImageView black = (ImageView) findViewById(R.id.playerTwoImage);
            black.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "BLACK"));
            colourButton.setText("BLACK");
            return true;

        }

        else if(id == R.id.menu_white) {

            Toast.makeText(this, "You picked white!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerTwoImage);
            noColour.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "WHITE"));
            colourButton.setText("WHITE");
            return true;

        }

        else{

            return false;
        }

    }

    /**
     * Disables from the colour choice menu whichever colour playerTwo's opponent has chosen to
     * prevent both players from having the same colour game pieces.
     * @param popup The colour options menu popup.
     * @return The modified colour options menu popup.
     */

    public PopupMenu colourCheckPlayerTwo(@NonNull PopupMenu popup){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour_menu, popup.getMenu());
        Menu menu = popup.getMenu();

        MenuItem red = menu.findItem(R.id.menu_red);
        MenuItem blue = menu.findItem(R.id.menu_blue);
        MenuItem green = menu.findItem(R.id.menu_green);
        MenuItem yellow = menu.findItem(R.id.menu_yellow);
        MenuItem white = menu.findItem(R.id.menu_white);
        MenuItem black = menu.findItem(R.id.menu_black);

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

        else if (playerOne.getPlayerGamePiece() == R.drawable.circle_white |
                playerOne.getPlayerGamePiece() == R.drawable.square_white |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_white |
                playerOne.getPlayerGamePiece() == R.drawable.star_white){

            white.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.circle_black |
                playerOne.getPlayerGamePiece() == R.drawable.square_black |
                playerOne.getPlayerGamePiece() == R.drawable.diamond_black |
                playerOne.getPlayerGamePiece() == R.drawable.star_black){

            black.setVisible(false);
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

    /**
     * Changes the colour of the game piece playerTwo has selected; Checks to see what shape
     * their piece is and returns the same shape coloured in their choice.
     * @param token The game piece that playerTwo currently has.
     * @param colour The colour that playerTwo wants their game piece to be; must be in ALL CAPS.
     * @return PlayerTwo's game piece in the correct colour.
     */

    public int setColourPlayerTwo(int token, String colour){

        //if playerTwo has a circle they want in a different colour.
        if (token == R.drawable.circle_white | token == R.drawable.circle_red | token == R.drawable.circle_blue
                | token == R.drawable.circle_green | token == R.drawable.circle_yellow | token == R.drawable.circle_black){

            if (colour.equals("RED")){

                ImageView redCircle = (ImageView) findViewById(R.id.playerTwoImage);
                redCircle.setImageResource(R.drawable.circle_red);
                playerTwo.setPlayerGamePiece(R.drawable.circle_red);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerTwoImage);
                blueCircle.setImageResource(R.drawable.circle_blue);
                playerTwo.setPlayerGamePiece(R.drawable.circle_blue);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerTwoImage);
                greenCircle.setImageResource(R.drawable.circle_green);
                playerTwo.setPlayerGamePiece(R.drawable.circle_green);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerTwoImage);
                yellowCircle.setImageResource(R.drawable.circle_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.circle_yellow);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackCircle = (ImageView) findViewById(R.id.playerTwoImage);
                blackCircle.setImageResource(R.drawable.circle_black);
                playerTwo.setPlayerGamePiece(R.drawable.circle_black);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteCircle = (ImageView) findViewById(R.id.playerTwoImage);
                whiteCircle.setImageResource(R.drawable.circle_white);
                playerTwo.setPlayerGamePiece(R.drawable.circle_white);
                playerTwo.setPlayerColour(colour);

                return R.drawable.circle_white;

            }

        }

        //if playerTwo has a diamond they want in a different colour.
        else if (token == R.drawable.diamond_white | token == R.drawable.diamond_red | token == R.drawable.diamond_blue
                | token == R.drawable.diamond_green | token == R.drawable.diamond_yellow | token == R.drawable.diamond_black){

            if (colour.equals("RED")){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                redDiamond.setImageResource(R.drawable.diamond_red);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_red);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                blueDiamond.setImageResource(R.drawable.diamond_blue);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_blue);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                greenDiamond.setImageResource(R.drawable.diamond_green);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_green);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                yellowDiamond.setImageResource(R.drawable.diamond_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_yellow);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                blackDiamond.setImageResource(R.drawable.diamond_black);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_black);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                whiteDiamond.setImageResource(R.drawable.diamond_white);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_white);
                playerTwo.setPlayerColour(colour);

                return R.drawable.diamond_white;

            }

        }

        //if playerTwo has a square they want in a different colour.
        else if (token == R.drawable.square_white | token == R.drawable.square_red | token == R.drawable.square_blue
                | token == R.drawable.square_green | token == R.drawable.square_yellow | token == R.drawable.square_black){

            if (colour.equals("RED")){

                ImageView redSquare = (ImageView) findViewById(R.id.playerTwoImage);
                redSquare.setImageResource(R.drawable.square_red);
                playerTwo.setPlayerGamePiece(R.drawable.square_red);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerTwoImage);
                blueSquare.setImageResource(R.drawable.square_blue);
                playerTwo.setPlayerGamePiece(R.drawable.square_blue);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerTwoImage);
                greenSquare.setImageResource(R.drawable.square_green);
                playerTwo.setPlayerGamePiece(R.drawable.square_green);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerTwoImage);
                yellowSquare.setImageResource(R.drawable.square_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.square_yellow);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackSquare = (ImageView) findViewById(R.id.playerTwoImage);
                blackSquare.setImageResource(R.drawable.square_black);
                playerTwo.setPlayerGamePiece(R.drawable.square_black);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteSquare = (ImageView) findViewById(R.id.playerTwoImage);
                whiteSquare.setImageResource(R.drawable.square_white);
                playerTwo.setPlayerGamePiece(R.drawable.square_white);
                playerTwo.setPlayerColour(colour);

                return R.drawable.square_white;

            }

        }

        //if playerTwo has a star they want in a different colour.
        else if (token == R.drawable.star_white | token == R.drawable.star_red | token == R.drawable.star_blue
                | token == R.drawable.star_green | token == R.drawable.star_yellow | token == R.drawable.star_black){

            if (colour.equals("RED")){

                ImageView redStar = (ImageView) findViewById(R.id.playerTwoImage);
                redStar.setImageResource(R.drawable.star_red);
                playerTwo.setPlayerGamePiece(R.drawable.star_red);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_red;

            }

            else if (colour.equals("BLUE")){

                ImageView blueStar = (ImageView) findViewById(R.id.playerTwoImage);
                blueStar.setImageResource(R.drawable.star_blue);
                playerTwo.setPlayerGamePiece(R.drawable.star_blue);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_blue;

            }

            else if (colour.equals("GREEN")){

                ImageView greenStar = (ImageView) findViewById(R.id.playerTwoImage);
                greenStar.setImageResource(R.drawable.star_green);
                playerTwo.setPlayerGamePiece(R.drawable.star_green);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_green;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerTwoImage);
                yellowStar.setImageResource(R.drawable.star_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.star_yellow);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_yellow;

            }

            else if (colour.equals("BLACK")){

                ImageView blackStar = (ImageView) findViewById(R.id.playerTwoImage);
                blackStar.setImageResource(R.drawable.star_black);
                playerTwo.setPlayerGamePiece(R.drawable.star_black);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_black;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteStar = (ImageView) findViewById(R.id.playerTwoImage);
                whiteStar.setImageResource(R.drawable.star_white);
                playerTwo.setPlayerGamePiece(R.drawable.star_white);
                playerTwo.setPlayerColour(colour);

                return R.drawable.star_white;

            }

        }

        //Returns zero as a default, however the user will be stopped from selecting a colour if
        //they haven't selected a game piece so this should never be reached.
        return 0;
    }

    /**
     * Preserves colour choice when playerTwo selects a new shape for their game piece. The new
     * colour must be assigned to playerTwo before calling.
     * @param piece The shape of the game piece playerTwo has in ALL CAPS.
     */

    public void playerTwoPreserveColourChoice(String piece){

        Button pieceButton = (Button) findViewById(R.id.playerTwoPieceSelection);
        Button colourButton = (Button) findViewById(R.id.playerTwoColourSelection);
        ImageView display = (ImageView) findViewById(R.id.playerTwoImage);

        //If playerTwo wants their game piece to be a circle with the same colour as their previous.
        if(piece.equals("CIRCLE")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.circle_red);
                playerTwo.setPlayerGamePiece(R.drawable.circle_red);

                pieceButton.setText("CIRCLE");
                colourButton.setText("RED");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.circle_blue);
                playerTwo.setPlayerGamePiece(R.drawable.circle_blue);

                pieceButton.setText("CIRCLE");
                colourButton.setText("BLUE");

            }
            
            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.circle_green);
                playerTwo.setPlayerGamePiece(R.drawable.circle_green);

                pieceButton.setText("CIRCLE");
                colourButton.setText("GREEN");

            }
            
            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.circle_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.circle_yellow);

                pieceButton.setText("CIRCLE");
                colourButton.setText("YELLOW");

            }
            
            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.circle_white);
                playerTwo.setPlayerGamePiece(R.drawable.circle_white);

                pieceButton.setText("CIRCLE");
                colourButton.setText("WHITE");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.circle_black);
                playerTwo.setPlayerGamePiece(R.drawable.circle_black);

                pieceButton.setText("CIRCLE");
                colourButton.setText("BLACK");

            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.circle_white);
                    playerTwo.setPlayerGamePiece(R.drawable.circle_white);
                    playerTwo.setPlayerColour("WHITE");

                    pieceButton.setText("CIRCLE");
                    colourButton.setText("WHITE");
                }

                else{

                    display.setImageResource(R.drawable.circle_black);
                    playerTwo.setPlayerGamePiece(R.drawable.circle_black);
                    playerTwo.setPlayerColour("BLACK");

                    pieceButton.setText("CIRCLE");
                    colourButton.setText("BLACK");
                }
            }
        }

        //If playerTwo wants their game piece to be a diamond with the same colour as their previous.
        else if(piece.equals("DIAMOND")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.diamond_red);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_red);

                pieceButton.setText("DIAMOND");
                colourButton.setText("RED");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.diamond_blue);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_blue);

                pieceButton.setText("DIAMOND");
                colourButton.setText("BLUE");

            }
            
            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.diamond_green);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_green);

                pieceButton.setText("DIAMOND");
                colourButton.setText("GREEN");

            }
            
            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.diamond_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_yellow);

                pieceButton.setText("DIAMOND");
                colourButton.setText("YELLOW");

            }
            
            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.diamond_white);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_white);

                pieceButton.setText("DIAMOND");
                colourButton.setText("WHITE");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.diamond_black);
                playerTwo.setPlayerGamePiece(R.drawable.diamond_black);

                pieceButton.setText("DIAMOND");
                colourButton.setText("BLACK");

            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.diamond_white);
                    playerTwo.setPlayerGamePiece(R.drawable.diamond_white);
                    playerTwo.setPlayerColour("WHITE");

                    pieceButton.setText("DIAMOND");
                    colourButton.setText("WHITE");
                }

                else{

                    display.setImageResource(R.drawable.diamond_black);
                    playerTwo.setPlayerGamePiece(R.drawable.diamond_black);
                    playerTwo.setPlayerColour("BLACK");

                    pieceButton.setText("DIAMOND");
                    colourButton.setText("BLACK");
                }
            }

        }

        //If playerTwo wants their game piece to be a square with the same colour as their previous.
        else if(piece.equals("SQUARE")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.square_red);
                playerTwo.setPlayerGamePiece(R.drawable.square_red);

                pieceButton.setText("SQUARE");
                colourButton.setText("RED");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.square_blue);
                playerTwo.setPlayerGamePiece(R.drawable.square_blue);

                pieceButton.setText("SQUARE");
                colourButton.setText("BLUE");

            }
            
            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.square_green);
                playerTwo.setPlayerGamePiece(R.drawable.square_green);

                pieceButton.setText("SQUARE");
                colourButton.setText("GREEN");

            }
            
            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.square_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.square_yellow);

                pieceButton.setText("SQUARE");
                colourButton.setText("YELLOW");

            }
            
            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.square_white);
                playerTwo.setPlayerGamePiece(R.drawable.square_white);

                pieceButton.setText("SQUARE");
                colourButton.setText("WHITE");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.square_black);
                playerTwo.setPlayerGamePiece(R.drawable.square_black);

                pieceButton.setText("SQUARE");
                colourButton.setText("BLACK");

            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.square_white);
                    playerTwo.setPlayerGamePiece(R.drawable.square_white);
                    playerTwo.setPlayerColour("WHITE");

                    pieceButton.setText("SQUARE");
                    colourButton.setText("WHITE");
                }

                else{

                    display.setImageResource(R.drawable.square_black);
                    playerTwo.setPlayerGamePiece(R.drawable.square_black);
                    playerTwo.setPlayerColour("BLACK");

                    pieceButton.setText("SQUARE");
                    colourButton.setText("BLACK");
                }
            }

        }

        //If playerTwo wants their game piece to be a star with the same colour as their previous.
        else if(piece.equals("STAR")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.star_red);
                playerTwo.setPlayerGamePiece(R.drawable.star_red);

                pieceButton.setText("STAR");
                colourButton.setText("RED");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.star_blue);
                playerTwo.setPlayerGamePiece(R.drawable.star_blue);

                pieceButton.setText("STAR");
                colourButton.setText("BLUE");

            }
            
            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.star_green);
                playerTwo.setPlayerGamePiece(R.drawable.star_green);

                pieceButton.setText("STAR");
                colourButton.setText("GREEN");

            }
            
            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.star_yellow);
                playerTwo.setPlayerGamePiece(R.drawable.star_yellow);

                pieceButton.setText("STAR");
                colourButton.setText("YELLOW");

            }
            
            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.star_white);
                playerTwo.setPlayerGamePiece(R.drawable.star_white);

                pieceButton.setText("STAR");
                colourButton.setText("WHITE");

            }
            
            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.star_black);
                playerTwo.setPlayerGamePiece(R.drawable.star_black);

                pieceButton.setText("STAR");
                colourButton.setText("BLACK");

            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.star_white);
                    playerTwo.setPlayerGamePiece(R.drawable.star_white);
                    playerTwo.setPlayerColour("WHITE");

                    pieceButton.setText("STAR");
                    colourButton.setText("WHITE");
                }

                else{

                    display.setImageResource(R.drawable.star_black);
                    playerTwo.setPlayerGamePiece(R.drawable.star_black);
                    playerTwo.setPlayerColour("BLACK");

                    pieceButton.setText("STAR");
                    colourButton.setText("BLACK");
                }
            }

        }

    }
    
    //#############################################################################################

}