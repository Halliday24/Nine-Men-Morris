package com.example.ninemenmorrisgroup6;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.ninemenmorrisgroup6.Helps.Rules;

public class GamePage extends AppCompatActivity {
    MediaPlayer backgroundMusic;


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

    private Rules rules = new Rules();

    private TextView playerTurn;
    private ArrayList<ImageView> whiteCheckers;
    private ArrayList<ImageView> blackCheckers;
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

    private ArrayList<String> whiteIndexes = new ArrayList<String>();
    private ArrayList<String> blackIndexes = new ArrayList<String>();

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

            //Add a onClickListener to the white checkers
            for (ImageView v : whiteCheckers) {
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
            for (ImageView v : blackCheckers) {
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
                                        playerTurn.setText("Remove White");
                                    } else {
                                        playerTurn.setText("Remove Black");
                                    }
                                } else {
                                    if (currentTurn == Constants.BLACK) {
                                        playerTurn.setText("White turn");
                                    } else {
                                        playerTurn.setText("Black turn");
                                    }
                                }
                                //Did someone win?
                                isWin = rules.isItAWin(rules.getTurn());
                                if (isWin) {
                                    if (rules.getTurn() == Constants.BLACK) {
                                        playerTurn.setText("White wins!");
                                    } else {
                                        playerTurn.setText("Black wins!");
                                    }

                                }
                            }
                        }
                    }
                });
            }

            //Start music
            backgroundMusic = MediaPlayer.create(GamePage.this, R.raw.cali);
            backgroundMusic.setLooping(true);
            backgroundMusic.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "---------------------pause----------------");
        rules.savePref(edit);
        edit.putInt(WHITE_INDEXES_SIZE, whiteIndexes.size());
        edit.putInt(BLACK_INDEXES_SIZE, blackIndexes.size());

        for (int i = 0; i < whiteIndexes.size(); i++) {
            edit.putString(WHITE_INDEXES + i, whiteIndexes.get(i));
        }
        for (int i = 0; i < blackIndexes.size(); i++) {
            edit.putString(BLACK_INDEXES + i, blackIndexes.get(i));
        }
        edit.putBoolean(IS_WIN, isWin);
        edit.putBoolean(REMOVE_CHECKER, removeNextChecker);
        edit.commit();

        super.onPause();
        backgroundMusic.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "---------------------resume----------------");
        newGame = pref.getBoolean(NEW_GAME, false);
        edit.putBoolean(NEW_GAME, false);
        if (!newGame) {
            rules.restorePref(pref);
            int whiteSize = pref.getInt(WHITE_INDEXES_SIZE, 0);
            int blackSize = pref.getInt(BLACK_INDEXES_SIZE, 0);
            for (int i = 0; i < whiteSize; i++) {
                whiteIndexes.add(pref.getString(WHITE_INDEXES + i, ""));
            }
            for (int i = 0; i < blackSize; i++) {
                blackIndexes.add(pref.getString(BLACK_INDEXES + i, ""));
            }

            isWin = pref.getBoolean(IS_WIN, false);
            removeNextChecker = pref.getBoolean(REMOVE_CHECKER, false);
            restore();
        }

        super.onResume();
        backgroundMusic.start();
    }

    private void restore() {
        int white = 0;
        int black = 0;
        for (int i = 1; i < 25; i++) {
            int color = rules.fieldColor(i);
            int index = 0;
            ViewGroup parent = null;
            if (color == Constants.WHITE) {
                index = Integer.parseInt(whiteIndexes.get(white));
                white++;
                parent = ((ViewGroup) findViewById(R.id.whiteCheckerArea));
            } else if (color == Constants.BLACK) {
                index = Integer.parseInt(blackIndexes.get(black));
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
                    playerTurn.setText("Remove White");
                } else {
                    playerTurn.setText("Remove Black");
                }
            } else {
                if (rules.getTurn() == Constants.WHITE) {
                    playerTurn.setText("White turn");
                } else {
                    playerTurn.setText("Black turn");
                }
            }
            if (isWin) {
                if (rules.getTurn() == Constants.BLACK) {
                    playerTurn.setText("White wins!");
                } else {
                    playerTurn.setText("Black wins!");
                }
            }
        }
        while (white < whiteIndexes.size()) {
            setPlaceHolder(Integer.parseInt(whiteIndexes.get(white)), ((ViewGroup) findViewById(R.id.whiteCheckerArea)));
            white++;
        }
        while (black < blackIndexes.size()) {
            setPlaceHolder(Integer.parseInt(blackIndexes.get(black)), ((ViewGroup) findViewById(R.id.blackCheckerArea)));
            black++;
        }
    }

    private ImageView setPlaceHolder(int index, ViewGroup parent) {
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
        tAnimation.setDuration(1500);

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
                blackCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup) v.getParent());
                parent.removeView(v);
                playerTurn.setText("Black turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.BLACK);
                if (isWin) {
                    playerTurn.setText("White wins!");
                }
            } else if (rules.getTurn() == Constants.WHITE && rules.remove(checkerPositions.get(v), Constants.WHITE)) {
                //Unmark all options and remove the selected checker
                unMarkAllFields();
                whiteCheckers.remove(v);
                removeNextChecker = false;
                ViewGroup parent = ((ViewGroup) v.getParent());
                parent.removeView(v);
                playerTurn.setText("White turn");

                //Did someone win?
                isWin = rules.isItAWin(Constants.WHITE);
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


    public void muteSound(View myView) {
        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButton);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButton);

        backgroundMusic.pause();

        muteBtn.setVisibility(View.INVISIBLE);
        unmuteBtn.setVisibility(View.VISIBLE);
    }


    public void unmuteSound(View myView) {
        ImageButton unmuteBtn = (ImageButton) findViewById(R.id.unmuteButton);
        ImageButton muteBtn = (ImageButton) findViewById(R.id.muteButton);

        backgroundMusic.start();

        unmuteBtn.setVisibility(View.INVISIBLE);
        muteBtn.setVisibility(View.VISIBLE);
    }

}


//package com.example.ninemenmorrisgroup6;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Button;
//import android.widget.TextView;
//
//public class GamePage extends AppCompatActivity {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_game_page);
//
//        playerOne = (Player) getIntent().getSerializableExtra("playerOne");
//        playerTwo = (Player) getIntent().getSerializableExtra("playerTwo");
//        computer = (Player) getIntent().getSerializableExtra("computer");
//
//        initializeGamePieces();
//
//    }
//
//    /**
//     * Doesn't do anything yet but it will open a pop-up window where the players can change their
//     * pieces
//     * @param view
//     */
//    public void ChangePieces(View view) {
//
//    }
//
//    /**
//     * Links the Exit game button to the home page.
//     * @param view
//     */
//    public void ExitGame(View view) {
//        Intent intent = new Intent(this, SetupPage.class);
//        startActivity(intent);
//    }
//
//    /**
//     * Doesn't do anything yet but it will give action to the reset button
//     * @param view
//     */
//    public void ResetGame(View view) {
//    }
//

//
////    public void checkPlayer(int a, int b){
////        TextView diffCheck = (TextView) findViewById(R.id.textView123);
////        ImageView playerCheck = (ImageView) findViewById(R.id.viewCheck);
////
////        diffCheck.setText(Integer.toString(b));
////        playerCheck.setImageResource(a);
////
////    }
//
//
//}