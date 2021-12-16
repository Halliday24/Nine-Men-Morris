package com.example.ninemenmorrisgroup6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
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

        //Change status bar to black to better suit theme
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        //disables the colour and piece selection buttons right off the bat, they will be
        //enabled in the correct manner when a game mode is chosen.

        ImageButton playerOnePieceSelection = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageButton playerOneColourSelection = (ImageButton) findViewById(R.id.playerOneColourSelection);
        ImageButton playerTwoPieceSelection = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageButton playerTwoColourSelection = (ImageButton) findViewById(R.id.playerTwoColourSelection);

        EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
        EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

        playerOneName.setEnabled(false);
        playerOneName.setFocusable(false);
        playerTwoName.setEnabled(false);
        playerTwoName.setFocusable(false);


        computer.setDifficulty(3);
        musicCheckSetup();


    }
    //displays popup when the page is opened
    @Override
    protected void onStart() {
        super.onStart();
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_setup_info);
        dialog.setTitle("Dialog box");

        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
        dialog.getWindow().setLayout(700, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(SetupPage.this,SetupPage.class));
        Music.backgroundMusic.pause();// pause music
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(SetupPage.this,SetupPage.class));
        Music.backgroundMusic.start();// start music
    }

    /**
     * Sets the intent and sends the user back to the home page.
     * @param myView The button that this is assigned to.
     */

    public void goToHomePage(View myView){

        Intent homePage = new Intent (this, MainActivity.class);
        Music.musicInitialization = 2;
        startActivity(homePage);
    }//goToHomePage

    /**
     * Sets the intent and sends the user to the game page. Will check to see that the correct
     * settings have been selected first by using checkForInitialization();.
     * @param myView The button that this is assigned to.
     */

    public void startGame(View myView){

        if(checkForInitialization() == true){

            if(computer.getDifficulty() == 0) {

                Intent start = new Intent(getApplicationContext(), GamePage.class);

                EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
                EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

                playerOne.setPlayerName(playerOneName.getText().toString());
                playerTwo.setPlayerName(playerTwoName.getText().toString());

                start.putExtra("playerOne", playerOne);
                start.putExtra("playerTwo", playerTwo);
                start.putExtra("computer", computer);

                startActivity(start);
            }

            else if(computer.getDifficulty() == 2){

                Intent start = new Intent(getApplicationContext(), Hard_ComputerActivity.class);

                EditText playerOneName = (EditText) findViewById(R.id.playerOneName);

                playerOne.setPlayerName(playerOneName.getText().toString());
                computer.setPlayerName("Computer");

                start.putExtra("playerOne", playerOne);
                start.putExtra("playerTwo", playerTwo);
                start.putExtra("computer", computer);

                startActivity(start);

            }
            else if(computer.getDifficulty() == 1){

                Intent start = new Intent(getApplicationContext(), Easy_ComputerActivity.class);

                EditText playerOneName = (EditText) findViewById(R.id.playerOneName);

                playerOne.setPlayerName(playerOneName.getText().toString());
                computer.setPlayerName("Computer");

                start.putExtra("playerOne", playerOne);
                start.putExtra("playerTwo", playerTwo);
                start.putExtra("computer", computer);

                startActivity(start);

            }

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
        ImageButton pvpButton = (ImageButton) findViewById(R.id.pvpButton);
        ImageButton pvpButtonGrey = (ImageButton) findViewById(R.id.pvpButtonGrey);
        ImageButton easyComputerButton = (ImageButton) findViewById(R.id.easyComputerButton);
        ImageButton easyComputerButtonGrey = (ImageButton) findViewById(R.id.easyComputerButtonGrey);
        ImageButton hardComputerButton = (ImageButton) findViewById(R.id.hardComputerButton);
        ImageButton hardComputerButtonGrey = (ImageButton) findViewById(R.id.hardComputerButtonGrey);
        EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
        EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

        computer.setDifficulty(2);
        computer.setPlayerGamePiece(R.drawable.black_circle);

        singlePlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

        playerOneName.setEnabled(true);
        playerOneName.setFocusableInTouchMode(true);
        playerTwoName.setEnabled(false);
        playerTwoName.setFocusable(false);
        playerTwoName.setText("-----------");


        //Dims the easyComputerButton and pvpButton.
        //findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

        easyComputerButton.setVisibility(View.GONE);
        pvpButton.setVisibility(View.GONE);
        easyComputerButtonGrey.setVisibility(View.VISIBLE);
        pvpButtonGrey.setVisibility(View.VISIBLE);

        hardComputerButton.setVisibility(View.VISIBLE);
        hardComputerButtonGrey.setVisibility(View.GONE);
    }//playAgainstHardComputer

    /**
     * Sets the computer difficulty initializer to the correct value, initializes the colour and
     * piece choices appropriately and dims the Hard computer and pvp buttons.
     * @param myView The button this is assigned to.
     */

    public void playAgainstEasyComputer(View myView){
        ImageButton pvpButton = (ImageButton) findViewById(R.id.pvpButton);
        ImageButton pvpButtonGrey = (ImageButton) findViewById(R.id.pvpButtonGrey);
        ImageButton easyComputerButton = (ImageButton) findViewById(R.id.easyComputerButton);
        ImageButton easyComputerButtonGrey = (ImageButton) findViewById(R.id.easyComputerButtonGrey);
        ImageButton hardComputerButton = (ImageButton) findViewById(R.id.hardComputerButton);
        ImageButton hardComputerButtonGrey = (ImageButton) findViewById(R.id.hardComputerButtonGrey);
        EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
        EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

        computer.setDifficulty(1);
        computer.setPlayerGamePiece(R.drawable.black_circle);

        singlePlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToEmpty();

        playerOneName.setEnabled(true);
        playerOneName.setFocusableInTouchMode(true);
        playerTwoName.setEnabled(false);
        playerTwoName.setFocusable(false);
        playerTwoName.setText("-----------");

        //Dims the hardComputerButton and the pvpButton.
        //findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

        pvpButton.setVisibility(View.GONE);
        hardComputerButton.setVisibility(View.GONE);
        pvpButtonGrey.setVisibility(View.VISIBLE);
        hardComputerButtonGrey.setVisibility(View.VISIBLE);

        easyComputerButton.setVisibility(View.VISIBLE);
        easyComputerButtonGrey.setVisibility(View.GONE);

    }//playAgainstEasyComputer

    /**
     * Sets the computer difficulty initializer to the correct value, initializes the colour and
     * piece choices appropriately and dims the Hard computer and Easy computer buttons.
     * @param myView The button this is assigned to.
     */

    public void playAgainstPlayer(View myView){
        ImageButton pvpButton = (ImageButton) findViewById(R.id.pvpButton);
        ImageButton pvpButtonGrey = (ImageButton) findViewById(R.id.pvpButtonGrey);
        ImageButton easyComputerButton = (ImageButton) findViewById(R.id.easyComputerButton);
        ImageButton easyComputerButtonGrey = (ImageButton) findViewById(R.id.easyComputerButtonGrey);
        ImageButton hardComputerButton = (ImageButton) findViewById(R.id.hardComputerButton);
        ImageButton hardComputerButtonGrey = (ImageButton) findViewById(R.id.hardComputerButtonGrey);
        EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
        EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

        computer.setDifficulty(0);
        computer.setPlayerGamePiece(R.drawable.blank);

        multiPlayerButtonSetup();
        setPlayerOneToDefault();
        setPlayerTwoToDefault();

        playerOneName.setEnabled(true);
        playerOneName.setFocusableInTouchMode(true);
        playerTwoName.setEnabled(true);
        playerTwoName.setFocusableInTouchMode(true);
        playerTwoName.getText().clear();
        playerTwoName.setHint("Enter Your Name");

        //findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        //findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#BD1717"));

        easyComputerButton.setVisibility(View.GONE);
        hardComputerButton.setVisibility(View.GONE);
        easyComputerButtonGrey.setVisibility(View.VISIBLE);
        hardComputerButtonGrey.setVisibility(View.VISIBLE);

        pvpButton.setVisibility(View.VISIBLE);
        pvpButtonGrey.setVisibility(View.GONE);

    }//playAgainstEasyComputer

    /**
     * Sets playerOne to a white circle game piece as its default game piece, and changes the
     * playerOne colour and piece selection buttons text to reflect that.
     */

    public void setPlayerOneToDefault(){

        ImageButton colourButton = (ImageButton) findViewById(R.id.playerOneColourSelection);
        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageView playerOneImage = (ImageView) findViewById(R.id.playerOneImage);

        playerOneImage.setImageResource(R.drawable.white_circle);

        playerOne.setPlayerGamePiece(R.drawable.white_circle);
        playerOne.setPlayerColour("WHITE");

    }

    /**
     * Sets playerTwo to a black circle game piece as its default game piece, and changes the
     * playerTwo colour and piece selection buttons text to reflect that.
     */

    public void setPlayerTwoToDefault(){

        ImageButton colourButton = (ImageButton) findViewById(R.id.playerTwoColourSelection);
        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.black_circle);

        playerTwo.setPlayerGamePiece(R.drawable.black_circle);
        playerTwo.setPlayerColour("BLACK");

    }

    /**
     * Sets playerTwo's piece selection to blank and it's colour to "EMPTY". Used when playerOne
     * is going to play against the computer.
     */

    public void setPlayerTwoToEmpty(){

        ImageButton colourButton = (ImageButton) findViewById(R.id.playerTwoColourSelection);
        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageView playerTwoImage = (ImageView) findViewById(R.id.playerTwoImage);

        playerTwoImage.setImageResource(R.drawable.blank);

        playerTwo.setPlayerColour("EMPTY");
        playerTwo.setPlayerGamePiece(R.drawable.blank);

    }

    /**
     * Disables the colour and piece selection buttons for playerTwo and enables them for playerOne
     * for when playerOne is going to play against the computer.
     */

    public void singlePlayerButtonSetup(){

        ImageButton playerOnePieceSelection = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageButton playerOneColourSelection = (ImageButton) findViewById(R.id.playerOneColourSelection);
        ImageButton playerTwoPieceSelection = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageButton playerTwoColourSelection = (ImageButton) findViewById(R.id.playerTwoColourSelection);
        ImageButton playerOnePieceSelectionGrey = (ImageButton) findViewById(R.id.playerOnePieceSelectionGrey);
        ImageButton playerOneColourSelectionGrey = (ImageButton) findViewById(R.id.playerOneColourSelectionGrey);
        ImageButton playerTwoPieceSelectionGrey = (ImageButton) findViewById(R.id.playerTwoPieceSelectionGrey);
        ImageButton playerTwoColourSelectionGrey = (ImageButton) findViewById(R.id.playerTwoColourSelectionGrey);

        //playerOnePieceSelection.setEnabled(true);
        //playerOneColourSelection.setEnabled(true);
        //playerTwoPieceSelection.setEnabled(false);
        //playerTwoColourSelection.setEnabled(false);

        playerOnePieceSelectionGrey.setVisibility(View.GONE);
        playerOneColourSelectionGrey.setVisibility(View.GONE);
        playerOnePieceSelection.setVisibility(View.VISIBLE);
        playerOneColourSelection.setVisibility(View.VISIBLE);

        playerTwoPieceSelection.setVisibility(View.GONE);
        playerTwoColourSelection.setVisibility(View.GONE);
        playerTwoPieceSelectionGrey.setVisibility(View.VISIBLE);
        playerTwoColourSelectionGrey.setVisibility(View.VISIBLE);

    }

    /**
     * Enables the colour and piece selection buttons for playerOne and playerTwo when they are
     * going to play against each other.
     */

    public void multiPlayerButtonSetup(){

        ImageButton playerOnePieceSelection = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageButton playerOneColourSelection = (ImageButton) findViewById(R.id.playerOneColourSelection);
        ImageButton playerTwoPieceSelection = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageButton playerTwoColourSelection = (ImageButton) findViewById(R.id.playerTwoColourSelection);
        ImageButton playerOnePieceSelectionGrey = (ImageButton) findViewById(R.id.playerOnePieceSelectionGrey);
        ImageButton playerOneColourSelectionGrey = (ImageButton) findViewById(R.id.playerOneColourSelectionGrey);
        ImageButton playerTwoPieceSelectionGrey = (ImageButton) findViewById(R.id.playerTwoPieceSelectionGrey);
        ImageButton playerTwoColourSelectionGrey = (ImageButton) findViewById(R.id.playerTwoColourSelectionGrey);


        //playerOnePieceSelection.setEnabled(true);
        //playerOneColourSelection.setEnabled(true);
        //playerTwoPieceSelection.setEnabled(true);
        //playerTwoColourSelection.setEnabled(true);

        playerTwoColourSelectionGrey.setVisibility(View.GONE);
        playerTwoPieceSelectionGrey.setVisibility(View.GONE);
        playerOneColourSelectionGrey.setVisibility(View.GONE);
        playerOnePieceSelectionGrey.setVisibility(View.GONE);

        playerTwoColourSelection.setVisibility(View.VISIBLE);
        playerTwoPieceSelection.setVisibility(View.VISIBLE);
        playerOneColourSelection.setVisibility(View.VISIBLE);
        playerOnePieceSelection.setVisibility(View.VISIBLE);

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

        EditText playerOneName = (EditText) findViewById(R.id.playerOneName);
        EditText playerTwoName = (EditText) findViewById(R.id.playerTwoName);

        //Player vs player mode
        if(computer.getDifficulty() == 0){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() != R.drawable.blank){

                if(TextUtils.isEmpty(playerOneName.getText().toString())){

                    customToast("Player One needs a name!");
                    //Toast.makeText(this, "Player One needs a name!", Toast.LENGTH_SHORT).show();
                    return false;

                }

                else if(TextUtils.isEmpty(playerTwoName.getText().toString())){

                    customToast("Player Two needs a name!");
                    //Toast.makeText(this, "Player Two needs a name!", Toast.LENGTH_SHORT).show();
                    return false;

                }

                else{

                    return true;

                }

            }

            else{

                customToast("Player one and Two need game pieces!");
                //Toast.makeText(this, "Player One and Two need game pieces!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        //Player vs easy computer mode
        else if(computer.getDifficulty() == 1){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() == R.drawable.blank){

                if(TextUtils.isEmpty(playerOneName.getText().toString())){

                    customToast("Player One needs a name!");
                    //Toast.makeText(this, "Player One needs a name!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{

                    return true;

                }

            }

            else{

                customToast("Player One needs a game pieces!");
                //Toast.makeText(this, "Player One needs game pieces!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        //Player vs hard computer mode
        else if(computer.getDifficulty() == 2){

            if(playerOne.getPlayerGamePiece() != R.drawable.blank && playerTwo.getPlayerGamePiece() == R.drawable.blank){

                if(TextUtils.isEmpty(playerOneName.getText().toString())){

                    customToast("Player One needs a name!");
                    //Toast.makeText(this, "Player One needs a name!", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else{

                    return true;

                }



            }

            else{

                customToast("Player One needs game pieces!");
                //Toast.makeText(this, "Player One needs game pieces!", Toast.LENGTH_SHORT).show();
                return false;

            }

        }

        else if(computer.getDifficulty() == 3){

            customToast("Select a game mode first!");
            //Toast.makeText(this, "Select a game mode first!", Toast.LENGTH_SHORT).show();
            return false;

        }

        customToast("Something went wrong, please try again!");
        //Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Mutes the sound on the SetupPage activity.
     * @param myView The ImageButton this is assigned to.
     */

    public void muteSoundSetup(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonSetup);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonSetup);

        //Music.backgroundMusic.pause();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Unmutes the sound on the SetupPage activity.
     * @param myView The ImageButton this is assigned to.
     */

    public void unmuteSoundSetup(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonSetup);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonSetup);

        // Music.backgroundMusic.start();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,false);
        Music.setMuteStatus("UNMUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Checks to see if the mute or unmute ImageButton should be displayed.
     */

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

    public void disabledButtonToast(View v){

        if(computer.getDifficulty() == 3){

            customToast("Select a Game mode first!");
            //Toast.makeText(this, "Select a Game mode first", Toast.LENGTH_SHORT).show();

        }

        if (computer.getDifficulty() == 1 | computer.getDifficulty() == 2){

            customToast("You are playing against the computer!");
            //Toast.makeText(this, "You are playing against the computer!", Toast.LENGTH_SHORT).show();

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

        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageButton colourButton = (ImageButton) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            customToast("You picked a Circle!");
            //Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("CIRCLE");

            return true;

        }

        else if(id == R.id.menu_diamond) {

            customToast("You picked a Diamond!");
            //Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerOnePreserveColourChoice("DIAMOND");

            return true;

        }

        else if(id == R.id.menu_square) {

            customToast("You picked a Square!");
            //Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();

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

            customToast("You have made no choice!");
            //Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerOneImage);
            blank.setImageResource(R.drawable.blank);
            playerOne.setPlayerGamePiece(R.drawable.blank);
            playerOne.setPlayerColour("EMPTY");
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

            customToast("Pick a game piece first");
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

        ImageButton colourButton = (ImageButton) findViewById(R.id.playerOneColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            customToast("You picked red!");
            //Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerOneImage);
            red.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "RED"));
            return true;

        }

        else if(id == R.id.menu_blue) {

            customToast("You picked blue!");
            //Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerOneImage);
            blue.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "BLUE"));
            return true;

        }

        else if(id == R.id.menu_green) {

            customToast("You picked green!");
            //Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerOneImage);
            green.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "GREEN"));
            return true;

        }

        else if(id == R.id.menu_yellow) {

            customToast("You picked yellow!");
            //Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerOneImage);
            yellow.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "YELLOW"));
            return true;

        }

        else if(id == R.id.menu_black) {

            customToast("You picked black!");
            //Toast.makeText(this, "You picked black!", Toast.LENGTH_SHORT).show();
            ImageView black = (ImageView) findViewById(R.id.playerOneImage);
            black.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "BLACK"));
            return true;

        }

        else if(id == R.id.menu_white) {

            customToast("You picked white!");
            //Toast.makeText(this, "You picked white!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerOneImage);
            noColour.setImageResource(setColourPlayerOne(playerOne.getPlayerGamePiece(), "WHITE"));
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

            if (playerTwo.getPlayerGamePiece() == R.drawable.red_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.red_square |
                    playerTwo.getPlayerGamePiece() == R.drawable.red_diamon |
                    playerTwo.getPlayerGamePiece() == R.drawable.red_star) {

                red.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.blue_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.blue_square |
                    playerTwo.getPlayerGamePiece() == R.drawable.blue_diamond |
                    playerTwo.getPlayerGamePiece() == R.drawable.blue_star) {

                blue.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.green_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.green_squa |
                    playerTwo.getPlayerGamePiece() == R.drawable.green_diamond |
                    playerTwo.getPlayerGamePiece() == R.drawable.green_star) {

                green.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.yellow_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.yellow_square |
                    playerTwo.getPlayerGamePiece() == R.drawable.yellow_diamond |
                    playerTwo.getPlayerGamePiece() == R.drawable.yellow_star) {

                yellow.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.white_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.white_square |
                    playerTwo.getPlayerGamePiece() == R.drawable.white_diamond |
                    playerTwo.getPlayerGamePiece() == R.drawable.white_star) {

                white.setVisible(false);
                return popup;

            }

            else if (playerTwo.getPlayerGamePiece() == R.drawable.black_circle |
                    playerTwo.getPlayerGamePiece() == R.drawable.black_square |
                    playerTwo.getPlayerGamePiece() == R.drawable.black_diamond |
                    playerTwo.getPlayerGamePiece() == R.drawable.black_star) {

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
        if (token == R.drawable.white_circle | token == R.drawable.red_circle | token == R.drawable.blue_circle
                | token == R.drawable.green_circle | token == R.drawable.yellow_circle | token == R.drawable.black_circle){

            if (colour.equals("RED")){

                ImageView redCircle = (ImageView) findViewById(R.id.playerOneImage);
                redCircle.setImageResource(R.drawable.red_circle);
                playerOne.setPlayerGamePiece(R.drawable.red_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.red_circle;

            }

            else if (colour.equals("BLUE")){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerOneImage);
                blueCircle.setImageResource(R.drawable.blue_circle);
                playerOne.setPlayerGamePiece(R.drawable.blue_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.blue_circle;

            }

            else if (colour.equals("GREEN")){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerOneImage);
                greenCircle.setImageResource(R.drawable.green_circle);
                playerOne.setPlayerGamePiece(R.drawable.green_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.green_circle;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerOneImage);
                yellowCircle.setImageResource(R.drawable.yellow_circle);
                playerOne.setPlayerGamePiece(R.drawable.yellow_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.yellow_circle;

            }

            else if (colour.equals("BLACK")){

                ImageView blackCircle = (ImageView) findViewById(R.id.playerOneImage);
                blackCircle.setImageResource(R.drawable.black_circle);
                playerOne.setPlayerGamePiece(R.drawable.black_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.black_circle;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteCircle = (ImageView) findViewById(R.id.playerOneImage);
                whiteCircle.setImageResource(R.drawable.white_circle);
                playerOne.setPlayerGamePiece(R.drawable.white_circle);
                playerOne.setPlayerColour(colour);

                return R.drawable.white_circle;

            }

        }

        //if playerOne has a diamond they want in a different colour.
        else if (token == R.drawable.white_diamond | token == R.drawable.red_diamon | token == R.drawable.blue_diamond
                | token == R.drawable.green_diamond | token == R.drawable.yellow_diamond | token == R.drawable.black_diamond){

            if (colour.equals("RED")){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerOneImage);
                redDiamond.setImageResource(R.drawable.red_diamon);
                playerOne.setPlayerGamePiece(R.drawable.red_diamon);
                playerOne.setPlayerColour(colour);

                return R.drawable.red_diamon;

            }

            else if (colour.equals("BLUE")){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerOneImage);
                blueDiamond.setImageResource(R.drawable.blue_diamond);
                playerOne.setPlayerGamePiece(R.drawable.blue_diamond);
                playerOne.setPlayerColour(colour);

                return R.drawable.blue_diamond;

            }

            else if (colour.equals("GREEN")){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerOneImage);
                greenDiamond.setImageResource(R.drawable.green_diamond);
                playerOne.setPlayerGamePiece(R.drawable.green_diamond);
                playerOne.setPlayerColour(colour);

                return R.drawable.green_diamond;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerOneImage);
                yellowDiamond.setImageResource(R.drawable.yellow_diamond);
                playerOne.setPlayerGamePiece(R.drawable.yellow_diamond);
                playerOne.setPlayerColour(colour);

                return R.drawable.yellow_diamond;

            }

            else if (colour.equals("BLACK")){

                ImageView blackDiamond = (ImageView) findViewById(R.id.playerOneImage);
                blackDiamond.setImageResource(R.drawable.black_diamond);
                playerOne.setPlayerGamePiece(R.drawable.black_diamond);
                playerOne.setPlayerColour(colour);

                return R.drawable.black_diamond;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteDiamond = (ImageView) findViewById(R.id.playerOneImage);
                whiteDiamond.setImageResource(R.drawable.white_diamond);
                playerOne.setPlayerGamePiece(R.drawable.white_diamond);
                playerOne.setPlayerColour(colour);

                return R.drawable.white_diamond;

            }

        }

        //if playerOne has a square they want in a different colour.
        else if (token == R.drawable.white_square | token == R.drawable.red_square | token == R.drawable.blue_square
                | token == R.drawable.green_squa | token == R.drawable.yellow_square | token == R.drawable.black_square){

            if (colour.equals("RED")){

                ImageView redSquare = (ImageView) findViewById(R.id.playerOneImage);
                redSquare.setImageResource(R.drawable.red_square);
                playerOne.setPlayerGamePiece(R.drawable.red_square);
                playerOne.setPlayerColour(colour);

                return R.drawable.red_square;

            }

            else if (colour.equals("BLUE")){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerOneImage);
                blueSquare.setImageResource(R.drawable.blue_square);
                playerOne.setPlayerGamePiece(R.drawable.blue_square);
                playerOne.setPlayerColour(colour);

                return R.drawable.blue_square;

            }

            else if (colour.equals("GREEN")){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerOneImage);
                greenSquare.setImageResource(R.drawable.green_squa);
                playerOne.setPlayerGamePiece(R.drawable.green_squa);
                playerOne.setPlayerColour(colour);

                return R.drawable.green_squa;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerOneImage);
                yellowSquare.setImageResource(R.drawable.yellow_square);
                playerOne.setPlayerGamePiece(R.drawable.yellow_square);
                playerOne.setPlayerColour(colour);

                return R.drawable.yellow_square;

            }

            else if (colour.equals("BLACK")){

                ImageView blackSquare = (ImageView) findViewById(R.id.playerOneImage);
                blackSquare.setImageResource(R.drawable.black_square);
                playerOne.setPlayerGamePiece(R.drawable.black_square);
                playerOne.setPlayerColour(colour);

                return R.drawable.black_square;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteSquare = (ImageView) findViewById(R.id.playerOneImage);
                whiteSquare.setImageResource(R.drawable.white_square);
                playerOne.setPlayerGamePiece(R.drawable.white_square);
                playerOne.setPlayerColour(colour);

                return R.drawable.white_square;

            }

        }

        //if playerOne has a star they want in a different colour.
        else if (token == R.drawable.white_star | token == R.drawable.red_star | token == R.drawable.blue_star
                | token == R.drawable.green_star | token == R.drawable.yellow_star | token == R.drawable.black_star){

            if (colour.equals("RED")){

                ImageView redStar = (ImageView) findViewById(R.id.playerOneImage);
                redStar.setImageResource(R.drawable.red_star);
                playerOne.setPlayerGamePiece(R.drawable.red_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.red_star;

            }

            else if (colour.equals("GREEN")){

                ImageView greenStar = (ImageView) findViewById(R.id.playerOneImage);
                greenStar.setImageResource(R.drawable.green_star);
                playerOne.setPlayerGamePiece(R.drawable.green_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.green_star;

            }

            else if (colour.equals("BLUE")){

                ImageView greenStar = (ImageView) findViewById(R.id.playerOneImage);
                greenStar.setImageResource(R.drawable.blue_star);
                playerOne.setPlayerGamePiece(R.drawable.blue_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.blue_star;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerOneImage);
                yellowStar.setImageResource(R.drawable.yellow_star);
                playerOne.setPlayerGamePiece(R.drawable.yellow_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.yellow_star;

            }

            else if (colour.equals("BLACK")){

                ImageView blackStar = (ImageView) findViewById(R.id.playerOneImage);
                blackStar.setImageResource(R.drawable.black_star);
                playerOne.setPlayerGamePiece(R.drawable.black_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.black_star;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteStar = (ImageView) findViewById(R.id.playerOneImage);
                whiteStar.setImageResource(R.drawable.white_star);
                playerOne.setPlayerGamePiece(R.drawable.white_star);
                playerOne.setPlayerColour(colour);

                return R.drawable.white_star;

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

        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerOnePieceSelection);
        ImageButton colourButton = (ImageButton) findViewById(R.id.playerOneColourSelection);
        ImageView display = (ImageView) findViewById(R.id.playerOneImage);

        //If playerOne wants their game piece to be a circle with the same colour as their previous.
        if(piece.equals("CIRCLE")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_circle);
                playerOne.setPlayerGamePiece(R.drawable.red_circle);

            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_circle);
                playerOne.setPlayerGamePiece(R.drawable.blue_circle);

            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_circle);
                playerOne.setPlayerGamePiece(R.drawable.green_circle);

            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_circle);
                playerOne.setPlayerGamePiece(R.drawable.yellow_circle);

            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_circle);
                playerOne.setPlayerGamePiece(R.drawable.white_circle);

            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_circle);
                playerOne.setPlayerGamePiece(R.drawable.black_circle);

            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.black_circle);
                    playerOne.setPlayerGamePiece(R.drawable.black_circle);
                    playerOne.setPlayerColour("BLACK");

                }

                else{

                    display.setImageResource(R.drawable.white_circle);
                    playerOne.setPlayerGamePiece(R.drawable.white_circle);
                    playerOne.setPlayerColour("WHITE");

                }
            }
        }

        //If playerOne wants their game piece to be a diamond with the same colour as their previous.
        else if(piece.equals("DIAMOND")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_diamon);
                playerOne.setPlayerGamePiece(R.drawable.red_diamon);


            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_diamond);
                playerOne.setPlayerGamePiece(R.drawable.blue_diamond);


            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_diamond);
                playerOne.setPlayerGamePiece(R.drawable.green_diamond);


            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_diamond);
                playerOne.setPlayerGamePiece(R.drawable.yellow_diamond);


            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_diamond);
                playerOne.setPlayerGamePiece(R.drawable.white_diamond);


            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_diamond);
                playerOne.setPlayerGamePiece(R.drawable.black_diamond);


            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.black_diamond);
                    playerOne.setPlayerGamePiece(R.drawable.black_diamond);
                    playerOne.setPlayerColour("BLACK");

                }

                else{

                    display.setImageResource(R.drawable.white_diamond);
                    playerOne.setPlayerGamePiece(R.drawable.white_diamond);
                    playerOne.setPlayerColour("WHITE");

                }
            }

        }

        //If playerOne wants their game piece to be a square with the same colour as their previous.
        else if(piece.equals("SQUARE")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_square);
                playerOne.setPlayerGamePiece(R.drawable.red_square);


            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_square);
                playerOne.setPlayerGamePiece(R.drawable.blue_square);


            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_squa);
                playerOne.setPlayerGamePiece(R.drawable.green_squa);


            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_square);
                playerOne.setPlayerGamePiece(R.drawable.yellow_square);


            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_square);
                playerOne.setPlayerGamePiece(R.drawable.white_square);


            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_square);
                playerOne.setPlayerGamePiece(R.drawable.black_square);


            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.black_square);
                    playerOne.setPlayerGamePiece(R.drawable.black_square);
                    playerOne.setPlayerColour("BLACK");

                }

                else{

                    display.setImageResource(R.drawable.white_square);
                    playerOne.setPlayerGamePiece(R.drawable.white_square);
                    playerOne.setPlayerColour("WHITE");

                }
            }

        }

        //If playerOne wants their game piece to be a star with the same colour as their previous.
        else if(piece.equals("STAR")){

            if(playerOne.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_star);
                playerOne.setPlayerGamePiece(R.drawable.red_star);


            }

            else if(playerOne.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_star);
                playerOne.setPlayerGamePiece(R.drawable.blue_star);


            }

            else if(playerOne.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_star);
                playerOne.setPlayerGamePiece(R.drawable.green_star);


            }

            else if(playerOne.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_star);
                playerOne.setPlayerGamePiece(R.drawable.yellow_star);


            }

            else if(playerOne.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_star);
                playerOne.setPlayerGamePiece(R.drawable.white_star);


            }

            else if(playerOne.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_star);
                playerOne.setPlayerGamePiece(R.drawable.black_star);


            }

            else if(playerOne.getPlayerColour().equals("EMPTY")){

                if(playerTwo.getPlayerColour().equals("WHITE")){

                    display.setImageResource(R.drawable.black_star);
                    playerOne.setPlayerGamePiece(R.drawable.black_star);
                    playerOne.setPlayerColour("BLACK");

                }

                else{

                    display.setImageResource(R.drawable.white_star);
                    playerOne.setPlayerGamePiece(R.drawable.white_star);
                    playerOne.setPlayerColour("WHITE");

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

        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageButton colourButton = (ImageButton) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_circle) {

            customToast("You picked a Circle!");
            //Toast.makeText(this, "You picked a Circle!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("CIRCLE");

            return true;

        }

        else if(id == R.id.menu_diamond) {

            customToast("You picked a Diamond!");
            //Toast.makeText(this, "You picked a Diamond!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("DIAMOND");

            return true;

        }

        else if(id == R.id.menu_square) {

            customToast("You picked a square!");
            //Toast.makeText(this, "You picked a Square!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("SQUARE");

            return true;

        }

        else if(id == R.id.menu_star) {

            customToast("You picked a Star!");
            //Toast.makeText(this, "You picked a Star!", Toast.LENGTH_SHORT).show();

            //Gives the user the piece they selected with the same colour as before.
            playerTwoPreserveColourChoice("STAR");

            return true;

        }

        else if(id == R.id.menu_blank) { //Sets the users colour to "EMPTY" and their piece to blank.

            customToast("You have made no choice!");
            //Toast.makeText(this, "You have made no choice!", Toast.LENGTH_SHORT).show();
            ImageView blank = (ImageView) findViewById(R.id.playerTwoImage);
            blank.setImageResource(R.drawable.blank);
            playerTwo.setPlayerGamePiece(R.drawable.blank);
            playerTwo.setPlayerColour("EMPTY");
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

            customToast("Pick a game piece first!");
            //Toast.makeText(this, "Pick a game piece before selecting a colour!", Toast.LENGTH_SHORT).show();

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

        ImageButton colourButton = (ImageButton) findViewById(R.id.playerTwoColourSelection);

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            customToast("You picked red!");
            //Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerTwoImage);
            red.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "RED"));
            return true;

        }

        else if(id == R.id.menu_blue) {

            customToast("You picked blue!");
            //Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerTwoImage);
            blue.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "BLUE"));
            return true;

        }

        else if(id == R.id.menu_green) {

            customToast("You picked green!");
            //Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerTwoImage);
            green.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "GREEN"));
            return true;

        }

        else if(id == R.id.menu_yellow) {

            customToast("You picked yellow!");
            //Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerTwoImage);
            yellow.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "YELLOW"));
            return true;

        }

        else if(id == R.id.menu_black) {

            customToast("You picked black!");
            //Toast.makeText(this, "You picked black!", Toast.LENGTH_SHORT).show();
            ImageView black = (ImageView) findViewById(R.id.playerTwoImage);
            black.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "BLACK"));
            return true;

        }

        else if(id == R.id.menu_white) {

            customToast("You picked white!");
            //Toast.makeText(this, "You picked white!", Toast.LENGTH_SHORT).show();
            ImageView noColour = (ImageView) findViewById(R.id.playerTwoImage);
            noColour.setImageResource(setColourPlayerTwo(playerTwo.getPlayerGamePiece(), "WHITE"));
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

        if (playerOne.getPlayerGamePiece() == R.drawable.red_circle |
                playerOne.getPlayerGamePiece() == R.drawable.red_square |
                playerOne.getPlayerGamePiece() == R.drawable.red_diamon |
                playerOne.getPlayerGamePiece() == R.drawable.red_star){

            red.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.blue_circle |
                playerOne.getPlayerGamePiece() == R.drawable.blue_square |
                playerOne.getPlayerGamePiece() == R.drawable.blue_diamond |
                playerOne.getPlayerGamePiece() == R.drawable.blue_star){

            blue.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.green_circle |
                playerOne.getPlayerGamePiece() == R.drawable.green_squa |
                playerOne.getPlayerGamePiece() == R.drawable.green_diamond|
                playerOne.getPlayerGamePiece() == R.drawable.green_star){

            green.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.yellow_circle |
                playerOne.getPlayerGamePiece() == R.drawable.yellow_square |
                playerOne.getPlayerGamePiece() == R.drawable.yellow_diamond |
                playerOne.getPlayerGamePiece() == R.drawable.yellow_star){

            yellow.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.white_circle |
                playerOne.getPlayerGamePiece() == R.drawable.white_square |
                playerOne.getPlayerGamePiece() == R.drawable.white_diamond |
                playerOne.getPlayerGamePiece() == R.drawable.white_star){

            white.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.black_circle |
                playerOne.getPlayerGamePiece() == R.drawable.black_square |
                playerOne.getPlayerGamePiece() == R.drawable.black_diamond |
                playerOne.getPlayerGamePiece() == R.drawable.black_star){

            black.setVisible(false);
            return popup;

        }

        else if (playerOne.getPlayerGamePiece() == R.drawable.blank) {

            yellow.setVisible(true);
            red.setVisible(true);
            blue.setVisible(true);
            green.setVisible(true);
            black.setVisible(true);
            white.setVisible(true);
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
        if (token == R.drawable.white_circle | token == R.drawable.red_circle | token == R.drawable.blue_circle
                | token == R.drawable.green_circle | token == R.drawable.yellow_circle | token == R.drawable.black_circle){

            if (colour.equals("RED")){

                ImageView redCircle = (ImageView) findViewById(R.id.playerTwoImage);
                redCircle.setImageResource(R.drawable.red_circle);
                playerTwo.setPlayerGamePiece(R.drawable.red_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.red_circle;

            }

            else if (colour.equals("BLUE")){

                ImageView blueCircle = (ImageView) findViewById(R.id.playerTwoImage);
                blueCircle.setImageResource(R.drawable.blue_circle);
                playerTwo.setPlayerGamePiece(R.drawable.blue_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.blue_circle;

            }

            else if (colour.equals("GREEN")){

                ImageView greenCircle = (ImageView) findViewById(R.id.playerTwoImage);
                greenCircle.setImageResource(R.drawable.green_circle);
                playerTwo.setPlayerGamePiece(R.drawable.green_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.green_circle;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowCircle = (ImageView) findViewById(R.id.playerTwoImage);
                yellowCircle.setImageResource(R.drawable.yellow_circle);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.yellow_circle;

            }

            else if (colour.equals("BLACK")){

                ImageView blackCircle = (ImageView) findViewById(R.id.playerTwoImage);
                blackCircle.setImageResource(R.drawable.black_circle);
                playerTwo.setPlayerGamePiece(R.drawable.black_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.black_circle;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteCircle = (ImageView) findViewById(R.id.playerTwoImage);
                whiteCircle.setImageResource(R.drawable.white_circle);
                playerTwo.setPlayerGamePiece(R.drawable.white_circle);
                playerTwo.setPlayerColour(colour);

                return R.drawable.white_circle;

            }

        }

        //if playerTwo has a diamond they want in a different colour.
        else if (token == R.drawable.white_diamond | token == R.drawable.red_diamon | token == R.drawable.blue_diamond
                | token == R.drawable.green_diamond | token == R.drawable.yellow_diamond | token == R.drawable.black_diamond){

            if (colour.equals("RED")){

                ImageView redDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                redDiamond.setImageResource(R.drawable.red_diamon);
                playerTwo.setPlayerGamePiece(R.drawable.red_diamon);
                playerTwo.setPlayerColour(colour);

                return R.drawable.red_diamon;

            }

            else if (colour.equals("BLUE")){

                ImageView blueDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                blueDiamond.setImageResource(R.drawable.blue_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.blue_diamond);
                playerTwo.setPlayerColour(colour);

                return R.drawable.blue_diamond;

            }

            else if (colour.equals("GREEN")){

                ImageView greenDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                greenDiamond.setImageResource(R.drawable.green_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.green_diamond);
                playerTwo.setPlayerColour(colour);

                return R.drawable.green_diamond;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                yellowDiamond.setImageResource(R.drawable.yellow_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_diamond);
                playerTwo.setPlayerColour(colour);

                return R.drawable.yellow_diamond;

            }

            else if (colour.equals("BLACK")){

                ImageView blackDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                blackDiamond.setImageResource(R.drawable.black_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.black_diamond);
                playerTwo.setPlayerColour(colour);

                return R.drawable.black_diamond;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteDiamond = (ImageView) findViewById(R.id.playerTwoImage);
                whiteDiamond.setImageResource(R.drawable.white_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.white_diamond);
                playerTwo.setPlayerColour(colour);

                return R.drawable.white_diamond;

            }

        }

        //if playerTwo has a square they want in a different colour.
        else if (token == R.drawable.white_square | token == R.drawable.red_square | token == R.drawable.blue_square
                | token == R.drawable.green_squa | token == R.drawable.yellow_square | token == R.drawable.black_square){

            if (colour.equals("RED")){

                ImageView redSquare = (ImageView) findViewById(R.id.playerTwoImage);
                redSquare.setImageResource(R.drawable.red_square);
                playerTwo.setPlayerGamePiece(R.drawable.red_square);
                playerTwo.setPlayerColour(colour);

                return R.drawable.red_square;

            }

            else if (colour.equals("BLUE")){

                ImageView blueSquare = (ImageView) findViewById(R.id.playerTwoImage);
                blueSquare.setImageResource(R.drawable.blue_square);
                playerTwo.setPlayerGamePiece(R.drawable.blue_square);
                playerTwo.setPlayerColour(colour);

                return R.drawable.blue_square;

            }

            else if (colour.equals("GREEN")){

                ImageView greenSquare = (ImageView) findViewById(R.id.playerTwoImage);
                greenSquare.setImageResource(R.drawable.green_squa);
                playerTwo.setPlayerGamePiece(R.drawable.green_squa);
                playerTwo.setPlayerColour(colour);

                return R.drawable.green_squa;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowSquare = (ImageView) findViewById(R.id.playerTwoImage);
                yellowSquare.setImageResource(R.drawable.yellow_square);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_square);
                playerTwo.setPlayerColour(colour);

                return R.drawable.yellow_square;

            }

            else if (colour.equals("BLACK")){

                ImageView blackSquare = (ImageView) findViewById(R.id.playerTwoImage);
                blackSquare.setImageResource(R.drawable.black_square);
                playerTwo.setPlayerGamePiece(R.drawable.black_square);
                playerTwo.setPlayerColour(colour);

                return R.drawable.black_square;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteSquare = (ImageView) findViewById(R.id.playerTwoImage);
                whiteSquare.setImageResource(R.drawable.white_square);
                playerTwo.setPlayerGamePiece(R.drawable.white_square);
                playerTwo.setPlayerColour(colour);

                return R.drawable.white_square;

            }

        }

        //if playerTwo has a star they want in a different colour.
        else if (token == R.drawable.white_star | token == R.drawable.red_star | token == R.drawable.blue_star
                | token == R.drawable.green_star | token == R.drawable.yellow_star | token == R.drawable.black_star){

            if (colour.equals("RED")){

                ImageView redStar = (ImageView) findViewById(R.id.playerTwoImage);
                redStar.setImageResource(R.drawable.red_star);
                playerTwo.setPlayerGamePiece(R.drawable.red_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.red_star;

            }

            else if (colour.equals("BLUE")){

                ImageView blueStar = (ImageView) findViewById(R.id.playerTwoImage);
                blueStar.setImageResource(R.drawable.blue_star);
                playerTwo.setPlayerGamePiece(R.drawable.blue_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.blue_star;

            }

            else if (colour.equals("GREEN")){

                ImageView greenStar = (ImageView) findViewById(R.id.playerTwoImage);
                greenStar.setImageResource(R.drawable.green_star);
                playerTwo.setPlayerGamePiece(R.drawable.green_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.green_star;

            }

            else if (colour.equals("YELLOW")){

                ImageView yellowStar = (ImageView) findViewById(R.id.playerTwoImage);
                yellowStar.setImageResource(R.drawable.yellow_star);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.yellow_star;

            }

            else if (colour.equals("BLACK")){

                ImageView blackStar = (ImageView) findViewById(R.id.playerTwoImage);
                blackStar.setImageResource(R.drawable.black_star);
                playerTwo.setPlayerGamePiece(R.drawable.black_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.black_star;

            }

            else if (colour.equals("WHITE")){

                ImageView whiteStar = (ImageView) findViewById(R.id.playerTwoImage);
                whiteStar.setImageResource(R.drawable.white_star);
                playerTwo.setPlayerGamePiece(R.drawable.white_star);
                playerTwo.setPlayerColour(colour);

                return R.drawable.white_star;

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

        ImageButton pieceButton = (ImageButton) findViewById(R.id.playerTwoPieceSelection);
        ImageButton colourButton = (ImageButton) findViewById(R.id.playerTwoColourSelection);
        ImageView display = (ImageView) findViewById(R.id.playerTwoImage);

        //If playerTwo wants their game piece to be a circle with the same colour as their previous.
        if(piece.equals("CIRCLE")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_circle);
                playerTwo.setPlayerGamePiece(R.drawable.red_circle);


            }

            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_circle);
                playerTwo.setPlayerGamePiece(R.drawable.blue_circle);


            }

            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_circle);
                playerTwo.setPlayerGamePiece(R.drawable.green_circle);


            }

            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_circle);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_circle);


            }

            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_circle);
                playerTwo.setPlayerGamePiece(R.drawable.white_circle);


            }

            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_circle);
                playerTwo.setPlayerGamePiece(R.drawable.black_circle);


            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.white_circle);
                    playerTwo.setPlayerGamePiece(R.drawable.white_circle);
                    playerTwo.setPlayerColour("WHITE");

                }

                else{

                    display.setImageResource(R.drawable.black_circle);
                    playerTwo.setPlayerGamePiece(R.drawable.black_circle);
                    playerTwo.setPlayerColour("BLACK");

                }
            }
        }

        //If playerTwo wants their game piece to be a diamond with the same colour as their previous.
        else if(piece.equals("DIAMOND")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_diamon);
                playerTwo.setPlayerGamePiece(R.drawable.red_diamon);


            }

            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.blue_diamond);


            }

            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.green_diamond);


            }

            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_diamond);


            }

            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.white_diamond);


            }

            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_diamond);
                playerTwo.setPlayerGamePiece(R.drawable.black_diamond);


            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.white_diamond);
                    playerTwo.setPlayerGamePiece(R.drawable.white_diamond);
                    playerTwo.setPlayerColour("WHITE");

                }

                else{

                    display.setImageResource(R.drawable.black_diamond);
                    playerTwo.setPlayerGamePiece(R.drawable.black_diamond);
                    playerTwo.setPlayerColour("BLACK");

                }
            }

        }

        //If playerTwo wants their game piece to be a square with the same colour as their previous.
        else if(piece.equals("SQUARE")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_square);
                playerTwo.setPlayerGamePiece(R.drawable.red_square);


            }

            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_square);
                playerTwo.setPlayerGamePiece(R.drawable.blue_square);


            }

            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_squa);
                playerTwo.setPlayerGamePiece(R.drawable.green_squa);


            }

            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_square);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_square);


            }

            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_square);
                playerTwo.setPlayerGamePiece(R.drawable.white_square);


            }

            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_square);
                playerTwo.setPlayerGamePiece(R.drawable.black_square);


            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.white_square);
                    playerTwo.setPlayerGamePiece(R.drawable.white_square);
                    playerTwo.setPlayerColour("WHITE");

                }

                else{

                    display.setImageResource(R.drawable.black_square);
                    playerTwo.setPlayerGamePiece(R.drawable.black_square);
                    playerTwo.setPlayerColour("BLACK");

                }
            }

        }

        //If playerTwo wants their game piece to be a star with the same colour as their previous.
        else if(piece.equals("STAR")){

            if(playerTwo.getPlayerColour().equals("RED")){

                display.setImageResource(R.drawable.red_star);
                playerTwo.setPlayerGamePiece(R.drawable.red_star);

            }

            else if(playerTwo.getPlayerColour().equals("BLUE")){

                display.setImageResource(R.drawable.blue_star);
                playerTwo.setPlayerGamePiece(R.drawable.blue_star);

            }

            else if(playerTwo.getPlayerColour().equals("GREEN")){

                display.setImageResource(R.drawable.green_star);
                playerTwo.setPlayerGamePiece(R.drawable.green_star);


            }

            else if(playerTwo.getPlayerColour().equals("YELLOW")){

                display.setImageResource(R.drawable.yellow_star);
                playerTwo.setPlayerGamePiece(R.drawable.yellow_star);

            }

            else if(playerTwo.getPlayerColour().equals("WHITE")){

                display.setImageResource(R.drawable.white_star);
                playerTwo.setPlayerGamePiece(R.drawable.white_star);

            }

            else if(playerTwo.getPlayerColour().equals("BLACK")){

                display.setImageResource(R.drawable.black_star);
                playerTwo.setPlayerGamePiece(R.drawable.black_star);

            }

            else if(playerTwo.getPlayerColour().equals("EMPTY")){

                if(playerOne.getPlayerColour().equals("BLACK")){

                    display.setImageResource(R.drawable.white_star);
                    playerTwo.setPlayerGamePiece(R.drawable.white_star);
                    playerTwo.setPlayerColour("WHITE");
                }

                else{

                    display.setImageResource(R.drawable.black_star);
                    playerTwo.setPlayerGamePiece(R.drawable.black_star);
                    playerTwo.setPlayerColour("BLACK");
                }
            }

        }

    }

    /**
     * Opens the sma epopup that is first displayed on the setup page when the help button is pressed
     * @param view
     */

    public void helpButton(View view) {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_setup_info);
        dialog.setTitle("Dialog box");

        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
        dialog.getWindow().setLayout(700, 1000);
    }

    //#############################################################################################

}