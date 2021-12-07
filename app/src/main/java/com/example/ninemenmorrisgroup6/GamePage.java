package com.example.ninemenmorrisgroup6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.ninemenmorrisgroup6.Helps.Constants;
import com.example.ninemenmorrisgroup6.Helps.Music;
import com.example.ninemenmorrisgroup6.Helps.Rules;

public class GamePage extends AppCompatActivity {

    public static Player playerOne = new HumanPlayer();
    public static Player playerTwo = new HumanPlayer();
    public static Player computer = new ComputerPlayer();

    private final String TAG = "Main activity";

    private final String PLAYER_ONE_INDEXES = "PLAYER_ONE_INDEXES";
    private final String PLAYER_TWO_INDEXES = "PLAYER_TWO_INDEXES";
    private final String PLAYER_ONE_INDEXES_SIZE = "PLAYER_ONE_INDEXES_SIZE";
    private final String PLAYER_TWO_INDEXES_SIZE = "PLAYER_TWO_INDEXES_SIZE";
    private final String REMOVE_CHECKER = "REMOVE_CHECKER";
    private final String IS_WIN = "IS_WIN";
    private final String NEW_GAME = "NEW_GAME";

    private Rules rules = new Rules();

    private TextView playerTurn;
    private ArrayList<ImageView> playerOneCheckers;
    private ArrayList<ImageView> playerTwoCheckers;
    private ArrayList<FrameLayout> higBoxAreas;
    private ImageView selectedChecker;
    private FrameLayout areaToMoveTo;
    private HashMap<ImageView, Integer> checkerPositions;

    private boolean hasSelectedChecker = false;
    private boolean removeNextChecker = false;
    private boolean isWin = false;
    private boolean newGame = true;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;

    private ArrayList<String> playerOneIndexes = new ArrayList<String>();
    private ArrayList<String> playerTwoIndexes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.i(TAG, "Creating activity");
            setContentView(R.layout.activity_game_page);

            playerOne = (Player) getIntent().getSerializableExtra("playerOne");
            playerTwo = (Player) getIntent().getSerializableExtra("playerTwo");
            computer = (Player) getIntent().getSerializableExtra("computer");


            initializeGamePieces();

            pref = this.getSharedPreferences("errorlabs.in", Context.MODE_PRIVATE);
            edit = pref.edit();

            selectedChecker = null;
            areaToMoveTo = null;
            checkerPositions = new HashMap<ImageView, Integer>();
            playerTurn = (TextView) findViewById(R.id.TurnText);

            //Save all white checkers in a list
            playerOneCheckers = new ArrayList<ImageView>();
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker1));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker2));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker3));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker4));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker5));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker6));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker7));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker8));
            playerOneCheckers.add((ImageView) findViewById(R.id.whiteChecker9));

            //Save all black checkers in a list
            playerTwoCheckers = new ArrayList<ImageView>();
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker1));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker2));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker3));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker4));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker5));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker6));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker7));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker8));
            playerTwoCheckers.add((ImageView) findViewById(R.id.blackChecker9));

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

            //Add a onClickListener to the white checkers
            for (ImageView v : playerOneCheckers) {
                checkerPositions.put(v, 0);
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (rules.getTurn() == Constants.WHITE && !isWin) {
                            selectChecker(v);
                        }
                    }
                });
            }

            //Add a onClickListener to the black checkers
            for (ImageView v : playerTwoCheckers) {
                checkerPositions.put(v, 0);
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (rules.getTurn() == Constants.BLACK && !isWin) {
                            selectChecker(v);
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

                                //Did the move create a row of 3?
                                removeNextChecker = rules.canRemove(to);

                                //Update the turn text
                                if (removeNextChecker) {
                                    if (currentTurn == Constants.BLACK) {
                                        playerTurn.setText("Remove Player one");
                                    } else {
                                        playerTurn.setText("Remove Player two");
                                    }
                                } else {
                                    if (currentTurn == Constants.BLACK) {
                                        playerTurn.setText("Player one turn");
                                    } else {
                                        playerTurn.setText("Player two turn");
                                    }
                                }
                                //Did someone win?
                                isWin = rules.isItAWin(rules.getTurn());
                                if (isWin) {
                                    if (rules.getTurn() == Constants.BLACK) {
                                        playerTurn.setText("Player one wins!");
                                    } else {
                                        playerTurn.setText("Player two wins!");
                                    }

                                }
                            }
                        }
                    }
                });
            }

            //Start music
            musicCheckGame();

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.i(TAG, "---------------------pause----------------");
//        rules.savePref(edit);
//        edit.putInt(PLAYER_ONE_INDEXES_SIZE, playerOneIndexes.size());
//        edit.putInt(PLAYER_TWO_INDEXES_SIZE, playerTwoIndexes.size());
//
//        for (int i = 0; i < playerOneIndexes.size(); i++) {
//            edit.putString(PLAYER_ONE_INDEXES + i, playerOneIndexes.get(i));
//        }
//        for (int i = 0; i < playerTwoIndexes.size(); i++) {
//            edit.putString(PLAYER_TWO_INDEXES + i, playerTwoIndexes.get(i));
//        }
//        edit.putBoolean(IS_WIN, isWin);
//        edit.putBoolean(REMOVE_CHECKER, removeNextChecker);
//        edit.commit();
//
//        super.onPause();
//        //backgroundMusic.pause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.i(TAG, "---------------------resume----------------");
//        newGame = pref.getBoolean(NEW_GAME, false);
//        edit.putBoolean(NEW_GAME, false);
//        if (!newGame) {
//            rules.restorePref(pref);
//            int whiteSize = pref.getInt(PLAYER_ONE_INDEXES_SIZE, 0);
//            int blackSize = pref.getInt(PLAYER_TWO_INDEXES_SIZE, 0);
//            for (int i = 0; i < whiteSize; i++) {
//                playerOneIndexes.add(pref.getString(PLAYER_ONE_INDEXES + i, ""));
//            }
//            for (int i = 0; i < blackSize; i++) {
//                playerTwoIndexes.add(pref.getString(PLAYER_TWO_INDEXES + i, ""));
//            }
//
//            isWin = pref.getBoolean(IS_WIN, false);
//            removeNextChecker = pref.getBoolean(REMOVE_CHECKER, false);
//            //restore();
//        }
//
//        //super.onResume();
//        //backgroundMusic.start();
//    }

    /*private void restore() {
        int white = 0;
        int black = 0;
        for (int i = 1; i < 25; i++) {
            int color = rules.fieldColor(i);
            int index = 0;
            ViewGroup parent = null;
            if (color == Constants.WHITE) {
                index = Integer.parseInt(playerOneIndexes.get(white));
                white++;
                parent = ((ViewGroup) findViewById(R.id.whiteCheckerArea));
            } else if (color == Constants.BLACK) {
                index = Integer.parseInt(playerTwoIndexes.get(black));
                black++;
                parent = ((ViewGroup) findViewById(R.id.blackCheckerArea));
            }
            if (parent != null) {
                ImageView checker = setPlaceHolder(index, parent);
                ((ViewGroup) findViewById(R.id.board)).addView(checker);
                int areaId = getResources().getIdentifier("area" + i, "id", this.getPackageName());
                checker.setLayoutParams(findViewById(areaId).getLayoutParams());
                checkerPositions.put(checker, i);
            }
            if (removeNextChecker) {
                if (rules.getTurn() == Constants.WHITE) {
                    playerTurn.setText("Remove Player one");
                } else {
                    playerTurn.setText("Remove Player two");
                }
            } else {
                if (rules.getTurn() == Constants.WHITE) {
                    playerTurn.setText("Player one turn");
                } else {
                    playerTurn.setText("Player two turn");
                }
            }
            if (isWin) {
                if (rules.getTurn() == Constants.BLACK) {
                    playerTurn.setText("Player one wins!");
                } else {
                    playerTurn.setText("Player two wins!");
                }
            }
        }
        while (white < playerOneIndexes.size()) {
            Log.i(TAG, "Integer.parseInt(playerOneIndexes.get(white)) - " + Integer.parseInt(playerOneIndexes.get(white)));
            Log.i(TAG, "white - " + playerOneIndexes.size());
            Log.i(TAG, "white - " + white);
            setPlaceHolder(Integer.parseInt(playerOneIndexes.get(white)), ((ViewGroup) findViewById(R.id.whiteCheckerArea)));
            white++;
        }
        while (black < playerTwoIndexes.size()) {
            Log.i(TAG, "BLACK INDEX - " + Integer.parseInt(playerTwoIndexes.get(black)));
            Log.i(TAG, "BLACK - " + black);
            setPlaceHolder(Integer.parseInt(playerTwoIndexes.get(black)), ((ViewGroup) findViewById(R.id.blackCheckerArea)));
            black++;
        }
    }*/

    private ImageView setPlaceHolder(int index, ViewGroup parent) {
        Log.i(TAG, "Index - " + Integer.toString(index));
        ImageView checker = (ImageView) parent.getChildAt(index);
        parent.removeViewAt(index);
        FrameLayout placeholder = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_placeholder, parent, false);
        parent.addView(placeholder, index);
        return checker;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Standard menu creating
//        switch (item.getItemId()) {
//            //Start a new game
//            case R.id.newgame:
//                //Start a new game
//                finish();
//                startActivity(getIntent());
//                edit.putBoolean(NEW_GAME, true);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    /**
     * Move the checker from the current position to a new position
     *
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

        ViewGroup parent = ((ViewGroup) selectedChecker.getParent());
        final int index = parent.indexOfChild(selectedChecker);

        //Create a ghost checker which will be animated while the real one just moves.
        if (turn == Constants.WHITE) {
            playerOneIndexes.add(index + "");
            animChecker = (ImageView) getLayoutInflater().inflate(R.layout.anim_white_checker, parent, false);
        } else {
            playerTwoIndexes.add(index + "");
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
                ViewGroup parent = ((ViewGroup) tmpAnimChecker.getParent());
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

    /**
     * Lets the player select a checker to remove or move
     *
     * @param v The checker which was clicked on.
     */
    private void selectChecker(View v) {
        //Is it a remove click=
        if (removeNextChecker) {
            //Is it a valid remove click?
            if (rules.getTurn() == Constants.BLACK && rules.remove(checkerPositions.get(v), Constants.BLACK)) {
                //Unamrk all options and remove the selected checker

                unMarkAllFields();
                playerTwoCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup) v.getParent());
                Log.i(TAG,"White Child Before - " + parent.getChildCount());
                parent.removeView(v);
                Log.i(TAG,"White Child After - " + parent.getChildCount());
                playerTurn.setText("Player Two's turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.BLACK);
                if (isWin) {
                    playerTurn.setText("Player One wins!");
                }
            } else if (rules.getTurn() == Constants.WHITE && rules.remove(checkerPositions.get(v), Constants.WHITE)) {
                //Unmark all options and remove the selected checker
                unMarkAllFields();
                playerOneCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup) v.getParent());
                Log.i(TAG,"Black Child Before - " + parent.getChildCount());
                parent.removeView(v);
                Log.i(TAG,"Black Child After - " + parent.getChildCount());
                playerTurn.setText("Player One's turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.WHITE);
                if (isWin) {
                    playerTurn.setText("Player Two wins!");
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

            if (selectedChecker == v) {
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
     *
     * @param from The position of the checker which wants to move
     */
    private void markAvailableMoveFields(int from) {
        for (int i = 0; i < 24; i++) {
            if (rules.isValidMove(from, i + 1)) {
                higBoxAreas.get(i).setBackgroundResource(R.drawable.valid_move);
            }
        }
    }

    /**
     * Unmark all fields.
     */
    private void unMarkAllFields() {
        for (FrameLayout f : higBoxAreas) {
            f.setBackgroundResource(0);
        }
    }

    /**
     * 后退键，销毁主界面
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void initializeGamePieces() {

        ImageView playerOne1 = (ImageView) findViewById(R.id.blackChecker1);
        ImageView playerOne2 = (ImageView) findViewById(R.id.blackChecker2);
        ImageView playerOne3 = (ImageView) findViewById(R.id.blackChecker3);
        ImageView playerOne4 = (ImageView) findViewById(R.id.blackChecker4);
        ImageView playerOne5 = (ImageView) findViewById(R.id.blackChecker5);
        ImageView playerOne6 = (ImageView) findViewById(R.id.blackChecker6);
        ImageView playerOne7 = (ImageView) findViewById(R.id.blackChecker7);
        ImageView playerOne8 = (ImageView) findViewById(R.id.blackChecker8);
        ImageView playerOne9 = (ImageView) findViewById(R.id.blackChecker9);

        ImageView playerTwo1 = (ImageView) findViewById(R.id.whiteChecker1);
        ImageView playerTwo2 = (ImageView) findViewById(R.id.whiteChecker2);
        ImageView playerTwo3 = (ImageView) findViewById(R.id.whiteChecker3);
        ImageView playerTwo4 = (ImageView) findViewById(R.id.whiteChecker4);
        ImageView playerTwo5 = (ImageView) findViewById(R.id.whiteChecker5);
        ImageView playerTwo6 = (ImageView) findViewById(R.id.whiteChecker6);
        ImageView playerTwo7 = (ImageView) findViewById(R.id.whiteChecker7);
        ImageView playerTwo8 = (ImageView) findViewById(R.id.whiteChecker8);
        ImageView playerTwo9 = (ImageView) findViewById(R.id.whiteChecker9);

        if (computer.getDifficulty() == 0) {

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo2.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo3.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo4.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo5.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo6.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo7.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo8.setImageResource(playerTwo.getPlayerGamePiece());
            playerTwo9.setImageResource(playerTwo.getPlayerGamePiece());

        } else if (computer.getDifficulty() == 1) {

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());

        } else if (computer.getDifficulty() == 2) {

            playerOne1.setImageResource(playerOne.getPlayerGamePiece());
            playerOne2.setImageResource(playerOne.getPlayerGamePiece());
            playerOne3.setImageResource(playerOne.getPlayerGamePiece());
            playerOne4.setImageResource(playerOne.getPlayerGamePiece());
            playerOne5.setImageResource(playerOne.getPlayerGamePiece());
            playerOne6.setImageResource(playerOne.getPlayerGamePiece());
            playerOne7.setImageResource(playerOne.getPlayerGamePiece());
            playerOne8.setImageResource(playerOne.getPlayerGamePiece());
            playerOne9.setImageResource(playerOne.getPlayerGamePiece());

            playerTwo1.setImageResource(computer.getPlayerGamePiece());
            playerTwo2.setImageResource(computer.getPlayerGamePiece());
            playerTwo3.setImageResource(computer.getPlayerGamePiece());
            playerTwo4.setImageResource(computer.getPlayerGamePiece());
            playerTwo5.setImageResource(computer.getPlayerGamePiece());
            playerTwo6.setImageResource(computer.getPlayerGamePiece());
            playerTwo7.setImageResource(computer.getPlayerGamePiece());
            playerTwo8.setImageResource(computer.getPlayerGamePiece());
            playerTwo9.setImageResource(computer.getPlayerGamePiece());

        }
    }

    public void muteSoundGame(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonGame);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonGame);

        Music.backgroundMusic.pause();
        Music.setMuteStatus("MUTED");

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }

    public void unmuteSoundGame(View myView) {

        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButtonGame);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButtonGame);

        Music.backgroundMusic.start();
        Music.setMuteStatus("MUTED");

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }

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

    public void reset(View myView){

        //doesn't seem to resest the game board. Put the pieces back but stops you from placing
        //pieces where they were before the game was reset
        Intent start = new Intent (this, GamePage.class);

        start.putExtra("playerOne", playerOne);
        start.putExtra("playerTwo", playerTwo);
        start.putExtra("computer", computer);

        startActivity(start);

    }

    public void exit(View myView){

        Intent home = new Intent (this, MainActivity.class);
        Music.musicInitialization = 2;
        startActivity(home);

    }

}
