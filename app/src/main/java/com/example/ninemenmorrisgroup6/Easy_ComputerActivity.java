package com.example.ninemenmorrisgroup6;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.ninemenmorrisgroup6.Helps.RulesComputer.playingfield;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ninemenmorrisgroup6.Helps.Constants;
import com.example.ninemenmorrisgroup6.Helps.Music;
import com.example.ninemenmorrisgroup6.Helps.Rules;
import com.example.ninemenmorrisgroup6.Helps.RulesComputer;
import com.example.ninemenmorrisgroup6.Helps.Utils;

import java.util.ArrayList;
import java.util.HashMap;


public class Easy_ComputerActivity extends AppCompatActivity {
    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();

    private final String TAG = "Main activity";

    private final String WHITE_INDEXES = "WHITE_INDEXES";
    private final String BLACK_INDEXES = "BLACK_INDEXES";
    private final String WHITE_INDEXES_SIZE = "WHITE_INDEXES_SIZE";
    private final String BLACK_INDEXES_SIZE = "BLACK_INDEXES_SIZE";
    private final String REMOVE_CHECKER = "REMOVE_CHECKER";
    private final String IS_WIN = "IS_WIN";
    private final String NEW_GAME = "NEW_GAME";

    RulesComputer rules = new RulesComputer();

    private TextView playerTurn;
    private ArrayList<ImageView> whiteCheckers;
    private ArrayList<ImageView> blackCheckers;
    private ArrayList<FrameLayout> higBoxAreas;
    private ImageView selectedChecker;
    private FrameLayout areaToMoveTo;
    private HashMap<ImageView, Integer> checkerPositions;
    private ImageView playerOnePieceHighlight;
    private ImageView playerTwoPieceHighlight;

    private boolean hasSelectedChecker = false;
    private boolean removeNextChecker = false;
    private boolean isWin = false;
    private boolean newGame = true;

    private int mode;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private ArrayList<String> whiteIndexes = new ArrayList<String>();
    private ArrayList<String> blackIndexes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Creating activity");
        setContentView(R.layout.activity_game_page);

        pref = this.getSharedPreferences("", Context.MODE_PRIVATE);
        edit = pref.edit();

        playerOne = (Player) getIntent().getSerializableExtra("playerOne");
        playerTwo = (Player) getIntent().getSerializableExtra("playerTwo");
        computer = (Player) getIntent().getSerializableExtra("computer");

        initializeGamePieces();
        setHowManyPiecesRemaining();

        TextView playerOneHeader = (TextView) findViewById(R.id.playerOneHeader);
        TextView playerTwoHeader = (TextView) findViewById(R.id.playerTwoHeader);
        TextView header = (TextView) findViewById(R.id.TurnText);

        ImageView playerOnePieceHighlight = (ImageView) findViewById(R.id.playerOnePieceHighlight);
        ImageView playerTwoPieceHighlight = (ImageView) findViewById(R.id.playerTwoPieceHighlight);

        playerTwoPieceHighlight.setVisibility(View.GONE);
        playerOnePieceHighlight.setVisibility(View.VISIBLE);

        header.setText("It is " + playerOne.getPlayerName() + "'s turn");
        playerOneHeader.setText(playerOne.getPlayerName() + "'s Pieces");
        playerTwoHeader.setText(computer.getPlayerName() + "'s Pieces");

        selectedChecker = null;
        areaToMoveTo = null;
        checkerPositions = new HashMap<ImageView, Integer>();
        playerTurn = (TextView) findViewById(R.id.TurnText);
        mode =  pref.getInt(Utils.GAME_MODE, Utils.MODE_HUMAN);
        if (mode == Utils.Easy_MODE_COMPUTER) {
            playerTurn.setText("Computer turn");
        } else {
            playerTurn.setText(playerOne.getPlayerName() + "'s turn");
        }


        musicCheckGame();

        //Save all white checkers in a list
        whiteCheckers = new ArrayList<ImageView>();
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker1));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker2));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker3));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker4));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker5));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker6));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker7));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker8));
        whiteCheckers.add((ImageView) findViewById(R.id.whiteChecker9));

        //Save all black checkers in a list
        blackCheckers = new ArrayList<ImageView>();
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker1));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker2));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker3));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker4));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker5));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker6));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker7));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker8));
        blackCheckers.add((ImageView) findViewById(R.id.blackChecker9));

        //Save all areas the checkers can move to in a list
        higBoxAreas = new ArrayList<FrameLayout>();
        higBoxAreas.add((FrameLayout) findViewById(R.id.area1));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area2));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area3));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area4));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area5));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area6));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area7));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area8));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area9));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area10));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area11));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area12));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area13));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area14));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area15));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area16));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area17));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area18));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area19));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area20));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area21));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area22));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area23));
        higBoxAreas.add((FrameLayout) findViewById(R.id.area24));



        //Add a onClickListener to the white checkers,
        // don't need it any more, computer doesn't need this action
 /*       for (ImageView v : whiteCheckers) {
            checkerPositions.put(v, 0);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (rules.getTurn() == Constants.WHITE && !isWin) {
                        selectChecker(v);
                    }
                }
            });
        }*/


        //Add a onClickListener to the black checkers (real human)
        for (ImageView v : blackCheckers) {
            checkerPositions.put(v, 0);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (rules.getTurn() == Constants.BLACK && !isWin) {
                        Log.i(TAG, "190");
                        selectChecker(v);
                        Log.i(TAG, "192");
                    }
                }
            });
        }

        //Add a clickListener to all the hit box areas
        for (FrameLayout v : higBoxAreas) {
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //If we have a selected checker, try to move it
                    if (hasSelectedChecker) {
                        Log.i(TAG, "Area clicked");
                        int currentTurn = rules.getTurn();
                        areaToMoveTo = (FrameLayout) v;
                        setHowManyPiecesRemaining();
                        //What areas are we moving from and to?
                        int to = Integer.parseInt((String) areaToMoveTo.getContentDescription());
                        int from = checkerPositions.get(selectedChecker);
                        //Try to move the checker
                        if (rules.validMove(from, to)) { // This line will change turn
                            Log.i(TAG, "Valid move");
                            //Update the UI
                            unMarkAllFields();
                            moveChecker(currentTurn);

                            checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                            //Did the row create a row of 3?
                            removeNextChecker = rules.canRemove(to);

                            selectedChecker.setAlpha(1.0f);

                            //The selected checker is not selected anymore
                            hasSelectedChecker = false;
                            selectedChecker = null;
                            checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));
                            //checkerPositions.get(R.id.whiteChecker1);

                            //Did the move create a row of 3?
                            //removeNextChecker = rules.canRemove(to);

                            //Update the turn text
                            if (removeNextChecker) {
                                if (currentTurn == Constants.BLACK) {
                                    Log.i(TAG,"Line 238");
                                    playerTurn.setText(playerOne.getPlayerName() + " can remove one of " + computer.getPlayerName() + "'s pieces");

                                } else {
                                    playerTurn.setText(computer.getPlayerName() + " can remove one of " + playerOne.getPlayerName() + "'s pieces");
                                }
                            } else {
                                if (currentTurn == Constants.BLACK) {
                                    playerTurn.setText("It is " + computer.getPlayerName() + "'s turn");
                                } else {
                                    playerTurn.setText("It is " + playerOne.getPlayerName() + "'s turn");
                                }
                            }
                            //Did someone win?
                            isWin = rules.isItAWin(rules.getTurn());
                            if (isWin) {
                                if (rules.getTurn() == Constants.BLACK) {
                                    playerTurn.setText(playerOne.getPlayerName() + " WINS!");
                                    winnerPopupGame();
                                } else {
                                    playerTurn.setText(playerTwo.getPlayerName() + " WINS!");
                                    winnerPopupGame();
                                }

                            }
                            Log.i(TAG,"Before Comp Check rules.getTurn() - " + rules.getTurn());
                            // computer go on after human place piece
                            if (rules.getTurn() == Constants.COMPUTER && !isWin && !removeNextChecker) {
                                Log.i("","computerSelectChecker");
                                computerSelectChecker();
                                Log.i(TAG,"Line 265");
                                setComputerWhere();
                            }
                            else{

                                rules.setTurn(1);

                            }
                        }
                    }
                    Log.i(TAG,"View - " + checkerPositions.get(v));
                    Log.i(TAG,"After comp check rules.getTurn() - " + rules.getTurn());
                    Log.i(TAG,"removeNextChecker - " + removeNextChecker);
                    Log.i(TAG,"leaving onClick");
                }
            });
        }

    }

    /**
     * Pauses the music when the app is minimized.
     */

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(Easy_ComputerActivity.this, Easy_ComputerActivity.class));
        Music.backgroundMusic.pause();// pause music
    }

    /**
     * Restarts the music when the app is opened back up.
     */

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(Easy_ComputerActivity.this, Easy_ComputerActivity.class));
        Music.backgroundMusic.start();
    }

    public FrameLayout convertIntegerToFrameLayout(int chosenSpot){

        FrameLayout temp = higBoxAreas.get(1);

        for(FrameLayout v : higBoxAreas){

            if(v.getContentDescription().toString().equals(Integer.toString(chosenSpot))){

                return v;

            }

        }

        return temp;

    }

    private void setComputerWhere() {
        Log.i(TAG, "SetComputerWhere entered");

        int to = 0;

            Log.i(TAG, "FrameLayout for loop entered");
            to = RulesComputer.computerEasyLogic();
            if (playingfield[to] == RulesComputer.EMPTY_FIELD /*need more logic on hard_mode */){ //computer move to the empty field
                Log.i(TAG, "to1 - " + playingfield[to]);
                areaToMoveTo = convertIntegerToFrameLayout(to);
                //Log.i(TAG, "v - " + Integer.parseInt((String) v.getContentDescription()));

            }

        if (hasSelectedChecker) {
            Log.i(TAG, "Area clicked");
            int currentTurn = rules.getTurn();
            //areaToMoveTo = (FrameLayout) v;

            //What areas are we moving from and to?
            int from = checkerPositions.get(selectedChecker);
            //Try to move the checker
            if (rules.validMove(from, to)) { // This line will change turn
                Log.i(TAG, "Valid move");
                //Update the UI
                unMarkAllFields();
                moveChecker(currentTurn);

                checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                //Did the row create a row of 3?
                removeNextChecker = rules.canRemove(to);

                selectedChecker.setAlpha(1.0f);

                //The selected checker is not selected anymore
                hasSelectedChecker = false;
                selectedChecker = null;
                checkerPositions.put((ImageView) selectedChecker, Integer.parseInt((String) areaToMoveTo.getContentDescription()));

                //Did the move create a  row of 3?
                removeNextChecker = rules.canRemove(to);

                //Update the turn text
                if (removeNextChecker) {
                    if (currentTurn == Constants.BLACK) {
                        playerTurn.setText("Remove Computer piece");
                    } else {
                        playerTurn.setText(playerOne.getPlayerName() + " can remove one of " + playerTwo.getPlayerName() + "'s pieces");
                        // hitbox has some checker and random remove
                        computerSelectChecker(); // Still need to fix, human has to help Ai remove their pieces
                        RulesComputer.computerEasyRemovalLogic();
                    }
                } else {
                    if (currentTurn == Constants.BLACK) {
                        playerTurn.setText("Computer turn");
                    } else {
                        playerTurn.setText("It is " + playerOne.getPlayerName() + "'s turn");
                    }
                }
                //Did someone win?
                isWin = rules.isItAWin(rules.getTurn());
                if(isWin) {
                    if (rules.getTurn() == Constants.BLACK) {
                        playerTurn.setText("Computer wins!");
                    } else {
                        playerTurn.setText(playerTwo.getPlayerName() + " WINS!");
                        winnerPopupGame();
                    }

                }
            }
        }
        //showHashmap();
    }

    /**
     * Move the checker from the current position to a new position
     * @param turn Constant.WHITE or Constant.BLACK according to whos turn it is
     */

    private void moveChecker(int turn) {

        ImageView animChecker = null;
        //Get the position of the checker that will move and the area it will move to
        final int[] locationChecker = {0, 0};
        final int[] locationArea = {0, 0};
        selectedChecker.getLocationOnScreen(locationChecker);
        areaToMoveTo.getLocationOnScreen(locationArea);
        Log.i(TAG, "move from x: " + locationChecker[0] + " y: " + locationChecker[1]);
        Log.i(TAG, "move to x: " + locationArea[0] + " y: " + locationArea[1]);

        ViewGroup parent = ((ViewGroup)selectedChecker.getParent());
        final int index = parent.indexOfChild(selectedChecker);

        //Create a ghost checker which will be animated while the real one just moves.
        if(turn == Constants.COMPUTER) {
            whiteIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.anim_white_checker, parent, false);
        } else {
            blackIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.anim_black_checker, parent, false);
        }


        //If the checker is in the side board, we need to update the side board as well
        if (parent != findViewById(R.id.board)) {
            //Remove the real checker and add the ghost where the real one was
            parent.removeView(selectedChecker);
            parent.addView(animChecker, index);
            //Move the real one to the side board

            ((ViewGroup) findViewById(R.id.board)).addView(selectedChecker);

        } else {

            //Add the ghost checker at the real ones position
            parent.addView(animChecker);
            animChecker.setLayoutParams(selectedChecker.getLayoutParams());
        }

        //Make the real checker invisible and move it
        selectedChecker.setLayoutParams(areaToMoveTo.getLayoutParams());
        selectedChecker.setVisibility(View.INVISIBLE);

        //final copies to be used in the animation thread

        final ImageView tmpAnimChecker = animChecker;
        final ImageView tmpSelectedChecker = selectedChecker;

        //Prepare animation with x and y movement
        TranslateAnimation tAnimation = new TranslateAnimation(0, locationArea[0] - locationChecker[0], 0, locationArea[1] - locationChecker[1]);
        tAnimation.setFillEnabled(true);
        tAnimation.setFillAfter(true);
        tAnimation.setDuration(750);

        tAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parent = ((ViewGroup)tmpAnimChecker.getParent());
                //Fix the side board so its children stays in position
                if (tmpAnimChecker.getParent() != findViewById(R.id.board)) {
                    //Add a placeholder frame layout to stop the other checkers from jmping towards the middle.
                    FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
                    parent.addView(placeholder, index);
                }

                //Remove the ghost and make the real checker visible again.
                parent.removeView(tmpAnimChecker);
                tmpSelectedChecker.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        tmpAnimChecker.startAnimation(tAnimation);
    }

    int index = 0;
    /**
     * computer set a checker to remove or move, easy mode computer place pieces in order
     */

    public void computerSelectChecker() {
        int id = setComputerChecker(index);
        Log.i("","computerSelectChecker index =" + index + "Turn = "+ rules.getTurn() +"id = "+ id);
        if (rules.getTurn() == Constants.COMPUTER && !isWin) {
            ImageView tempView = whiteCheckers.get(index);
            Log.i("","tempView" + tempView.toString());
            checkerPositions.put(tempView, 0);
            selectChecker(tempView);
            //double a = Math.floor(Math.random()*24); still consider easy mode place in order  is work, or need to place randomly.
            index++;
        }
    }

    private int setComputerChecker(int id) {
        switch (id) {
            case 1:
                return R.id.whiteChecker1;
            case 2:
                return R.id.whiteChecker2;
            case 3:
                return R.id.whiteChecker3;
            case 4:
                return R.id.whiteChecker4;
            case 5:
                return R.id.whiteChecker5;
            case 6:
                return R.id.whiteChecker6;
            case 7:
                return R.id.whiteChecker7;
            case 8:
                return R.id.whiteChecker8;
            case 9:
                return R.id.whiteChecker9;
            default:
                return -1;
        }
    }

    /**
     * Iterates through the hashmaps and shows what pieces are at each hitbox in the logcat
     */

    public void showHashmap(){

        for(int i = 0; i < 24; i++){

            Log.i(TAG, "HashMap spot " + (i+1) + " is - " + playingfield[i+1]);

        }

    }

    /**
     * Lets the player select a checker to remove or move
     * @param v The checker which was clicked on.
     */

    public void selectChecker(View v) {

        FrameLayout removePlayerFL;
        Log.i(TAG, "selectChecker Entered");
        //Is it a remove click=
        Log.i(TAG,"selectChecker removeNextChecker = " + removeNextChecker +" " +  checkerPositions.get(v)+" "+ playingfield[checkerPositions.get(v)]);
        showHashmap();
        Log.i(TAG,"view - " + checkerPositions.get(v));

        if (removeNextChecker) {
            //Is it a valid remove click?
            if(rules.getTurn() == Constants.BLACK && rules.remove(checkerPositions.get(v), Constants.BLACK)) {
                Log.i(TAG,"rules.getTurn() == Constants.Black == true");
            //if(rules.getTurn() == Constants.BLACK && !rules.canRemove(checkerPositions.get(v))) {
                //Unmark all options and remove the selected checker

                unMarkAllFields();
                blackCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("Computer turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.BLACK);
                if (isWin) {
                    playerTurn.setText("Computer wins!");
                }
                rules.setTurn(Constants.COMPUTER);
                if (rules.getTurn() == Constants.COMPUTER && !isWin) {
                    Log.i("","computerSelectChecker");
                    computerSelectChecker();
                    Log.i(TAG,"line 720");
                    setComputerWhere();
                }
            }
            else if(rules.getTurn() == Constants.COMPUTER && rules.remove(1, Constants.COMPUTER)) {
                //Unmark all options and remove the selected checker
                Log.i("","else turn == computer");
                int removePlayer = 0;
                removePlayer = RulesComputer.computerEasyRemovalLogic();
                removePlayerFL = convertIntegerToFrameLayout(removePlayer);
                unMarkAllFields();
                whiteCheckers.remove(removePlayerFL);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup)v.getParent());
                parent.removeView(v);
                playerTurn.setText("Computer turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.COMPUTER);
                if (isWin) {
                    playerTurn.setText("Black wins!");
                }
            }

        }
        //Try to select the checker for a move
        else if (!(checkerPositions.get(v) != 0 && checkerPositions.containsValue(0)) || (checkerPositions.get(v) == 0)) {
            //If a checker is already selected, unselect it
            if (selectedChecker != null) {
                selectedChecker.setAlpha(1.0f);
            }
            //If its the selected checker which is clicked, no checker is selected.

            if(selectedChecker == v) {
                hasSelectedChecker = false;
                selectedChecker = null;
                unMarkAllFields();
                return;
            }
            //Select a checker and mark available moves.
            markAvailableMoveFields(checkerPositions.get(v));
            hasSelectedChecker = true;
            selectedChecker = (ImageView) v;
            selectedChecker.setAlpha(0.5f);
        }
    }

    /**
     * Mark all available moves that can be done.
     * @param from The position of the checker which wants to move
     */

    private void markAvailableMoveFields(int from) {
        for(int i = 0; i < 24; i++) {
            if(rules.isValidMove(from, i+1)) {
                higBoxAreas.get(i).setBackgroundResource(R.drawable.valid_move);
            }
        }
    }

    /**
     * Unmark all fields.
     */

    private void unMarkAllFields() {
        for(FrameLayout f : higBoxAreas) {
            f.setBackgroundResource(0);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    /**
     * Opens a popup containing who won the game.
     */

    public void winnerPopupGame() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.winner_popup, null);
        View view = findViewById(R.id.rulesButtonGame);
        // create the popup window
        int width = 1000;
        int height = 1500;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        LinearLayout dim_layout = (LinearLayout) findViewById(R.id.dim_layout_game);
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        dim_layout.setVisibility(View.VISIBLE);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(null, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                dim_layout.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }

    /**
     * Opens a popup containing the rules for nine men morris.
     * @param view
     */

    public void rulesPopupGame(View view) {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.activity_rules_popup);
        dialog.setTitle("Dialog box");

        dialog.setCanceledOnTouchOutside(true);


        dialog.show();
        dialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
    }

    /**
     * Sets the correct animation for the game pieces that the users chose.
     * @param layout The layout that will be set to the correct animation.
     * @param player The Player whose animation is being set.
     * @return An int representative of the correct animation.
     */

    public int setAnimation(int layout, Player player){

        if (player.getPlayerGamePiece() == R.drawable.black_circle){

            layout = R.layout.anim_black_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.black_diamond){

            layout = R.layout.anim_black_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.black_square){

            layout = R.layout.anim_black_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.black_star){

            layout = R.layout.anim_black_star;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.blue_circle){

            layout = R.layout.anim_blue_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.blue_diamond){

            layout = R.layout.anim_blue_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.blue_square){

            layout = R.layout.anim_blue_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.blue_star){

            layout = R.layout.anim_blue_star;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.green_circle){

            layout = R.layout.anim_green_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.green_diamond){

            layout = R.layout.anim_green_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.green_squa){

            layout = R.layout.anim_green_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.green_star){

            layout = R.layout.anim_green_star;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.red_circle){

            layout = R.layout.anim_red_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.red_diamon){

            layout = R.layout.anim_red_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.red_square){

            layout = R.layout.anim_red_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.red_star){

            layout = R.layout.anim_red_star;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.white_circle){

            layout = R.layout.anim_white_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.white_diamond){

            layout = R.layout.anim_white_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.white_square){

            layout = R.layout.anim_white_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.white_star){

            layout = R.layout.anim_white_star;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.yellow_circle){

            layout = R.layout.anim_yellow_circle;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.yellow_diamond){

            layout = R.layout.anim_yellow_diamond;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.yellow_square){

            layout = R.layout.anim_yellow_square;
            return layout;

        }

        else if (player.getPlayerGamePiece() == R.drawable.yellow_star){

            layout = R.layout.anim_yellow_star;
            return layout;

        }

        return layout = R.layout.anim_white_checker;

    }

    /**
     * Takes the playerOne, playerTwo, and computer game pieces and replaces the imageview of the
     * game pieces with the appropriate choices.
     */

    public void initializeGamePieces() {

        Log.i(TAG, "initializaGamePieces called");

        ImageView playerTwo1 = (ImageView) findViewById(R.id.whiteChecker1);
        ImageView playerTwo2 = (ImageView) findViewById(R.id.whiteChecker2);
        ImageView playerTwo3 = (ImageView) findViewById(R.id.whiteChecker3);
        ImageView playerTwo4 = (ImageView) findViewById(R.id.whiteChecker4);
        ImageView playerTwo5 = (ImageView) findViewById(R.id.whiteChecker5);
        ImageView playerTwo6 = (ImageView) findViewById(R.id.whiteChecker6);
        ImageView playerTwo7 = (ImageView) findViewById(R.id.whiteChecker7);
        ImageView playerTwo8 = (ImageView) findViewById(R.id.whiteChecker8);
        ImageView playerTwo9 = (ImageView) findViewById(R.id.whiteChecker9);
        ImageView playerTwo10 = (ImageView) findViewById(R.id.whiteChecker10);

        ImageView playerOne1 = (ImageView) findViewById(R.id.blackChecker1);
        ImageView playerOne2 = (ImageView) findViewById(R.id.blackChecker2);
        ImageView playerOne3 = (ImageView) findViewById(R.id.blackChecker3);
        ImageView playerOne4 = (ImageView) findViewById(R.id.blackChecker4);
        ImageView playerOne5 = (ImageView) findViewById(R.id.blackChecker5);
        ImageView playerOne6 = (ImageView) findViewById(R.id.blackChecker6);
        ImageView playerOne7 = (ImageView) findViewById(R.id.blackChecker7);
        ImageView playerOne8 = (ImageView) findViewById(R.id.blackChecker8);
        ImageView playerOne9 = (ImageView) findViewById(R.id.blackChecker9);
        ImageView playerOne10 = (ImageView) findViewById(R.id.blackChecker10);

        if (computer.getDifficulty() == 0) {

            Log.i(TAG, "PVP");

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());
            playerOne10.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo2.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo3.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo4.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo5.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo6.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo7.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo8.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo9.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo10.setImageResource(playerTwo.getPlayerGamePiece());

        }
        else if (computer.getDifficulty() == 1) {

            Log.i(TAG, "Easy Computer");

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());
            playerOne10.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());
            playerTwo10.setImageResource(computer.getPlayerGamePiece());

        }
        else if (computer.getDifficulty() == 2) {

            Log.i(TAG, "Hard Computer");

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());
            playerOne10.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());
            playerTwo10.setImageResource(computer.getPlayerGamePiece());

        }
    }

    /**
     * Updates the textViews on the gamePage with how many pieces each player has to place still.
     */

    public void setHowManyPiecesRemaining(){

        TextView playerOnePieces = (TextView) findViewById(R.id.playerOnePiecesRemaining);
        TextView playerTwoPieces = (TextView) findViewById(R.id.playerTwoPiecesRemaining);

        playerOnePieces.setText(Integer.toString(Rules.whiteMarkers));
        playerTwoPieces.setText(Integer.toString(Rules.blackMarkers));

    }

    /**
     * Resets the game with the current settings.
     * @param myView The button that this is assigned to.
     */

    public void reset(View myView){

        Intent start = new Intent (this, Easy_ComputerActivity.class);

        start.putExtra("playerOne", playerOne);
        start.putExtra("playerTwo", playerTwo);
        start.putExtra("computer", computer);

        startActivity(start);

    }

    /**
     * Sends the user back to the home page.
     * @param myView The button that this is assigned to.
     */

    public void exit(View myView){

        Intent home = new Intent (this, MainActivity.class);
        Music.musicInitialization = 2;
        startActivity(home);

    }

    /**
     * Checks to see if music is already playing when entering the game page.
     */

    public void musicCheckGame(){

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonGame);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonGame);

        if(Music.getMuteStatus().equals("MUTED")){

            unmuteBtn.setVisibility(View.VISIBLE);
            muteBtn.setVisibility(View.INVISIBLE);

        }
        else if(Music.getMuteStatus().equals("UNMUTED")){

            unmuteBtn.setVisibility(View.INVISIBLE);
            muteBtn.setVisibility(View.VISIBLE);

        }

    }

    /**
     * Mutes the sound when on the game page.
     * @param myView The button that this is assigned to.
     */

    public void muteSoundGame(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonGame);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonGame);

       // Music.backgroundMusic.pause();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }

    /**
     * Unmutes the sound when on the game page.
     * @param myView The button that this is assigned to.
     */

    public void unmuteSoundGame(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonGame);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonGame);

        //Music.backgroundMusic.start();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC,false);
        Music.setMuteStatus("UNMUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }
}



