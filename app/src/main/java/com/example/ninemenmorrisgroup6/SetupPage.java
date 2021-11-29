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

        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstHardComputer

    public void playAgainstEasyComputer(View myView){

        if(myView == findViewById(R.id.easyComputerButton)) {

            computer.setDifficulty(1);


        }
        else{}

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    public void playAgainstPlayer(View myView){

        if(myView == findViewById(R.id.pvpButton)) {

            computer.setDifficulty(1);


        }
        else{}

        findViewById(R.id.hardComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.easyComputerButton).setBackgroundColor(Color.parseColor("#FF8A8888"));
        findViewById(R.id.pvpButton).setBackgroundColor(Color.parseColor("#BD1717"));

    }//playAgainstEasyComputer

    public void pop(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onMenuItemClick);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();


    }

    public boolean onMenuItemClick(MenuItem item) {

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
            star.setImageResource(R.drawable.clip_star);
            playerOne.setPlayerGamePiece(R.drawable.clip_star);
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

    public void a(View myView){

        ImageView a = (ImageView) findViewById(R.id.playerTwoImage);
        a.setImageResource(playerOne.getPlayerGamePiece());

    }

    public void pop2(View v) {

        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this::onMenuItemClick2);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colour_menu, popup.getMenu());
        popup.show();

    }

    public boolean onMenuItemClick2(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_red) {

            Toast.makeText(this, "You picked red!", Toast.LENGTH_SHORT).show();
            ImageView red = (ImageView) findViewById(R.id.playerOneImage);
            red.setImageResource(setColour(playerOne.getPlayerGamePiece(), "Red"));
            return true;

        }

        else if(id == R.id.menu_blue) {

            Toast.makeText(this, "You picked blue!", Toast.LENGTH_SHORT).show();
            ImageView blue = (ImageView) findViewById(R.id.playerOneImage);
            blue.setImageResource(setColour(playerOne.getPlayerGamePiece(), "Blue"));
            return true;

        }

        else if(id == R.id.menu_green) {

            Toast.makeText(this, "You picked green!", Toast.LENGTH_SHORT).show();
            ImageView green = (ImageView) findViewById(R.id.playerOneImage);
            green.setImageResource(setColour(playerOne.getPlayerGamePiece(), "Green"));
            return true;

        }

        else if(id == R.id.menu_yellow) {

            Toast.makeText(this, "You picked yellow!", Toast.LENGTH_SHORT).show();
            ImageView yellow = (ImageView) findViewById(R.id.playerOneImage);
            yellow.setImageResource(setColour(playerOne.getPlayerGamePiece(), "Yellow"));
            return true;

        }
        else{

            return false;
        }

    }

    public int setColour(int token, String colour){

        if (token == R.drawable.circle){

            if (colour == "Red"){

//                ImageView redCircle = (ImageView) findViewById(R.id.playerOneImage);
//                redCircle.setImageResource(R.drawable.circle_red);
//                playerOne.setPlayerGamePiece(R.drawable.circle_red);

                return R.drawable.circle_red;

            }
            else if (colour == "Blue"){

//                ImageView blueCircle = (ImageView) findViewById(R.id.playerOneImage);
//                blueCircle.setImageResource(R.drawable.circle_blue);
//                playerOne.setPlayerGamePiece(R.drawable.circle_blue);
                return R.drawable.circle_blue;

            }
            else if (colour == "Green"){

//                ImageView greenCircle = (ImageView) findViewById(R.id.playerOneImage);
//                greenCircle.setImageResource(R.drawable.circle_green);
//                playerOne.setPlayerGamePiece(R.drawable.circle_green);
                return R.drawable.circle_green;

            }
            else if (colour == "Yellow"){

//                ImageView yellowCircle = (ImageView) findViewById(R.id.playerOneImage);
//                yellowCircle.setImageResource(R.drawable.circle_yellow);
//                playerOne.setPlayerGamePiece(R.drawable.circle_yellow);
                return R.drawable.circle_yellow;

            }

        }
        else if (token == R.drawable.diamond){

        }
        else if (token == R.drawable.square){

        }
        else if (token == R.drawable.star){

        }

        return 0;
    }
}