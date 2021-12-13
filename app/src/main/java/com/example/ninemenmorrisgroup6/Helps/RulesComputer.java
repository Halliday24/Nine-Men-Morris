package com.example.ninemenmorrisgroup6.Helps;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;


/*
 * The game board positions
 *
 * 01           02           03
 *     04       05       06
 *         07   08   09
 * 10  11  12        13  14  15
 *         16   17   18
 *     19       20       21
 * 22           23           24
 *
 */

public class RulesComputer {
    private final String TAG = "Rules";
    private final String PLAYINGFIELD = "PLAYINGFIELD";
    private final String TURN = "TURN";
    private final String WHITE_MARKERS = "WHITE_MARKERS";
    private final String BLACK_MARKERS = "BLACK_MARKERS";

    public int[] playingfield;
    private int turn;
    //Markers not on the playing field
    private int blackMarkers;
    private int whiteMarkers;

    public final static int EMPTY_FIELD = 0;

    public RulesComputer() {
        playingfield = new int[25];
        blackMarkers = 9;
        whiteMarkers = 9;
        turn = Constants.BLACK; //Random who will begin?
    }

    /**
     * Try to move the checker.
     * @param from The position to move from.
     * @param to The position to move to.
     * @return True if the move was successful, else false is returned.
     */
    public boolean validMove(int from, int to) {
        Log.i(TAG, "Trying to move : " + from + " - " + to);
        Log.i(TAG, "White markers in sideboard: " + whiteMarkers);
        Log.i(TAG, "Black markers in sideboard: " + blackMarkers);

        // Put a marker from "hand" to the board
        if(blackMarkers > 0 && turn == Constants.BLACK && playingfield[to] == EMPTY_FIELD) {
            playingfield[to] = Constants.BLACK;
            blackMarkers--;
            turn = Constants.COMPUTER;
            return true;
        }
        if(whiteMarkers > 0 && turn == Constants.COMPUTER  && playingfield[to] == EMPTY_FIELD) {
            playingfield[to] = Constants.COMPUTER;
            whiteMarkers--;
            turn = Constants.BLACK;
            return true;
        }

        //Not the right players turn
        if(playingfield[from] != turn) {
            return false;
        }

        //Not a valid move
        if(!isValidMove(from, to)) {
            return false;
        }

        // Move the marker to it's new position
        playingfield[to] = playingfield[from];
        playingfield[from] = EMPTY_FIELD;

        // Change turn
        if(turn == Constants.COMPUTER) {
            turn = Constants.BLACK;
        } else {
            turn = Constants.COMPUTER;
        }

        return true;
    }

    /**
     * Is it a valid move?
     * @param from The area the checker is at.
     * @param to The area the checker wants to go.
     * @return True if it's a valid move, else false is returned.
     */
    public boolean isValidMove(int from, int to) {
        //The "to"-field needs to be empty
        if(playingfield[to] != EMPTY_FIELD)  {
            return false;
        }

        //If it is from the side board, all moves are valid.
        if(from == 0) {
            return true;
        }

        //If it is flying phase, all moves are valid.
        if(isItFlyingPhase(playingfield[from])) {
            return true;
        }

        //Can only move to it's neighbors.
        switch (to) {
            case 1:
                return (from == 10 || from == 2);
            case 2:
                return (from == 1 || from == 3 || from == 5 );
            case 3:
                return (from == 2 || from == 15);
            case 4:
                return (from == 5 || from == 11);
            case 5:
                return (from == 2 || from == 4 || from == 8 || from == 6);
            case 6:
                return (from == 5 || from == 14);
            case 7:
                return (from == 8 || from == 12);
            case 8:
                return (from == 5 || from == 7 || from == 9);
            case 9:
                return (from == 8 || from == 13);
            case 10:
                return (from == 1 || from == 11 || from == 22);
            case 11:
                return (from == 4 || from == 10 || from == 12 || from == 19);
            case 12:
                return (from == 7 || from == 11 || from == 16);
            case 13:
                return (from == 9 || from == 14 || from == 18);
            case 14:
                return (from == 6 || from == 13 || from == 15 || from == 21);
            case 15:
                return (from == 3 || from == 14 || from == 24);
            case 16:
                return (from == 12 || from == 17);
            case 17:
                return (from == 16 || from == 18 || from == 20);
            case 18:
                return (from == 13 || from == 17);
            case 19:
                return (from == 11 || from == 20);
            case 20:
                return (from == 17 || from == 19 || from == 21 || from == 23);
            case 21:
                return (from == 14 || from == 20);
            case 22:
                return (from == 10 || from == 23);
            case 23:
                return (from == 20 || from == 22 || from == 24);
            case 24:
                return (from == 15 || from == 23);
        }
        return false;
    }

    /**
     * Check if the player is allowed to remove a checker from the other player.
     * @param partOfLine The position of the checker.
     * @return True if the checker is part of a line, else return false.
     */
    public boolean canRemove(int partOfLine) {
        // Check if the argument is part of a line on the board
        if(playingfield[partOfLine] == EMPTY_FIELD) {
            return false;
        }

        //All possible lines.
        if((partOfLine == 1 || partOfLine == 2 || partOfLine == 3) && (playingfield[1] == playingfield[2] && playingfield[2] == playingfield[3])) {
            return true;
        }
        if((partOfLine == 4 || partOfLine == 5 || partOfLine == 6) && (playingfield[4] == playingfield[5] && playingfield[5] == playingfield[6])) {
            return true;
        }
        if((partOfLine == 7 || partOfLine == 8 || partOfLine == 9) && (playingfield[7] == playingfield[8] && playingfield[8] == playingfield[9])) {
            return true;
        }
        if((partOfLine == 10 || partOfLine == 11 || partOfLine == 12) && (playingfield[10] == playingfield[11] && playingfield[11] == playingfield[12])) {
            return true;
        }
        if((partOfLine == 13 || partOfLine == 14 || partOfLine == 15) && (playingfield[13] == playingfield[14] && playingfield[14] == playingfield[15])) {
            return true;
        }
        if((partOfLine == 16 || partOfLine == 17 || partOfLine == 18) && (playingfield[16] == playingfield[17] && playingfield[17] == playingfield[18])) {
            return true;
        }
        if((partOfLine == 19 || partOfLine == 20 || partOfLine == 21) && (playingfield[19] == playingfield[20] && playingfield[20] == playingfield[21])) {
            return true;
        }
        if((partOfLine == 22 || partOfLine == 23 || partOfLine == 24) && (playingfield[22] == playingfield[23] && playingfield[23] == playingfield[24])) {
            return true;
        }
        if((partOfLine == 1 || partOfLine == 10 || partOfLine == 22) && (playingfield[1] == playingfield[10] && playingfield[10] == playingfield[22])) {
            return true;
        }
        if((partOfLine == 4 || partOfLine == 11 || partOfLine == 19) && (playingfield[4] == playingfield[11] && playingfield[11] == playingfield[19])) {
            return true;
        }
        if((partOfLine == 7 || partOfLine == 12 || partOfLine == 16) && (playingfield[7] == playingfield[12] && playingfield[12] == playingfield[16])) {
            return true;
        }
        if((partOfLine == 2 || partOfLine == 5 || partOfLine == 8) && (playingfield[2] == playingfield[5] && playingfield[5] == playingfield[8])) {
            return true;
        }
        if((partOfLine == 17 || partOfLine == 20 || partOfLine == 23) && (playingfield[17] == playingfield[20] && playingfield[20] == playingfield[23])) {
            return true;
        }
        if((partOfLine == 9 || partOfLine == 13 || partOfLine == 18) && (playingfield[9] == playingfield[13] && playingfield[13] == playingfield[18])) {
            return true;
        }
        if((partOfLine == 6 || partOfLine == 14 || partOfLine == 21) && (playingfield[6] == playingfield[14] && playingfield[14] == playingfield[21])) {
            return true;
        }
        if((partOfLine == 3 || partOfLine == 15 || partOfLine == 24) && (playingfield[3] == playingfield[15] && playingfield[15] == playingfield[24])) {
            return true;
        }
        return false;
    }

    public boolean partOfMill(int partOfLine){

        //All possible lines.
        if((partOfLine == 1 || partOfLine == 2 || partOfLine == 3) && (playingfield[1] == playingfield[2] && playingfield[2] == playingfield[3])) {
            return true;
        }
        if((partOfLine == 4 || partOfLine == 5 || partOfLine == 6) && (playingfield[4] == playingfield[5] && playingfield[5] == playingfield[6])) {
            return true;
        }
        if((partOfLine == 7 || partOfLine == 8 || partOfLine == 9) && (playingfield[7] == playingfield[8] && playingfield[8] == playingfield[9])) {
            return true;
        }
        if((partOfLine == 10 || partOfLine == 11 || partOfLine == 12) && (playingfield[10] == playingfield[11] && playingfield[11] == playingfield[12])) {
            return true;
        }
        if((partOfLine == 13 || partOfLine == 14 || partOfLine == 15) && (playingfield[13] == playingfield[14] && playingfield[14] == playingfield[15])) {
            return true;
        }
        if((partOfLine == 16 || partOfLine == 17 || partOfLine == 18) && (playingfield[16] == playingfield[17] && playingfield[17] == playingfield[18])) {
            return true;
        }
        if((partOfLine == 19 || partOfLine == 20 || partOfLine == 21) && (playingfield[19] == playingfield[20] && playingfield[20] == playingfield[21])) {
            return true;
        }
        if((partOfLine == 22 || partOfLine == 23 || partOfLine == 24) && (playingfield[22] == playingfield[23] && playingfield[23] == playingfield[24])) {
            return true;
        }
        if((partOfLine == 1 || partOfLine == 10 || partOfLine == 22) && (playingfield[1] == playingfield[10] && playingfield[10] == playingfield[22])) {
            return true;
        }
        if((partOfLine == 4 || partOfLine == 11 || partOfLine == 19) && (playingfield[4] == playingfield[11] && playingfield[11] == playingfield[19])) {
            return true;
        }
        if((partOfLine == 7 || partOfLine == 12 || partOfLine == 16) && (playingfield[7] == playingfield[12] && playingfield[12] == playingfield[16])) {
            return true;
        }
        if((partOfLine == 2 || partOfLine == 5 || partOfLine == 8) && (playingfield[2] == playingfield[5] && playingfield[5] == playingfield[8])) {
            return true;
        }
        if((partOfLine == 17 || partOfLine == 20 || partOfLine == 23) && (playingfield[17] == playingfield[20] && playingfield[20] == playingfield[23])) {
            return true;
        }
        if((partOfLine == 9 || partOfLine == 13 || partOfLine == 18) && (playingfield[9] == playingfield[13] && playingfield[13] == playingfield[18])) {
            return true;
        }
        if((partOfLine == 6 || partOfLine == 14 || partOfLine == 21) && (playingfield[6] == playingfield[14] && playingfield[14] == playingfield[21])) {
            return true;
        }
        if((partOfLine == 3 || partOfLine == 15 || partOfLine == 24) && (playingfield[3] == playingfield[15] && playingfield[15] == playingfield[24])) {
            return true;
        }
        return false;



    }

    /**
     * Checks to see if the specified player's pieces are all in mills or not.
     * @param colour The player whos pieces are being checked
     * @return True if the number of pieces on the board is the same as the number of pieces in
     * mills, false otherwise.
     */

    public boolean checkOnlyMill(int colour){

        int countOfPieces = 0;
        int countOfPiecesInMill = 0;

        for(int i = 0; i < 24; i++){

            if(playingfield[i+1] == colour){

                countOfPieces = countOfPieces + 1;

            }

        }

        for(int i = 0; i < 24; i++){

            if(playingfield[i+1] == colour) {

                if (partOfMill(i + 1) == true) {

                    countOfPiecesInMill = countOfPiecesInMill + 1;

                }
            }

        }
        Log.i(TAG, "countOfPieces - " + countOfPieces);
        Log.i(TAG, "countOfPiecesInMill - " + countOfPiecesInMill);

        if(countOfPieces == countOfPiecesInMill){

            return true;

        }
        else{

            return false;

        }

    }

    /**
     * Checks to see if a piece can be removed from a mill.
     * @param token The game piece being checked
     * @param colour The colour of the game piece (black or white)
     * @return True if the piece can be removed, false otherwise
     */

    public boolean removeFromMill(int token, int colour) {

        Log.i(TAG, "Token - " + token);
        Log.i(TAG, "Colour - " + colour);

        if (partOfMill(token) == true) {

            Log.i(TAG, "partOfMill = true");

            if (checkOnlyMill(colour) == true) {

                Log.i(TAG, "checkOnlyMill = true");

                return true;

            }
            else {
                Log.i(TAG, "checkOnlyMill = False");

                return false;

            }

        }
        else{
            Log.i(TAG, "partOfMill = false");
            return true;

        }

    }

    /**
     * Remove a marker from the position if it matches the color
     * @param from The checker to be removed.
     * @param color The color the checker should be if the remove is valid.
     * @return True if the removal was successful, else false is returned.
     */
    public boolean remove(int from, int color) {
        if (playingfield[from] == color) {
            playingfield[from] = EMPTY_FIELD;
            return true;
        } else
            return false;
    }

    /**
     * Check if color 'color' has lost
     * @param color The color which may have lost.
     * @return True if color has lost, else false is returned.
     */
    public boolean isItAWin(int color) {
        //A player can't win if it is checker left on the sideboard.
        if(whiteMarkers > 0 || blackMarkers > 0) {
            return false;
        }

        //color lost if there is no valid moves
        if(!hasValidMoves(color)) {
            return true;
        }

        //Does the color have less then 3 checkers left?
        int count = 0;
        for(int i : playingfield) {
            if(i == color) {
                count++;
            }
        }
        return (count < 3);
    }

    private boolean hasValidMoves(int color) {
        for(int i = 0; i < 24; i++) {
            if(playingfield[i+1] == color) {
                Log.i(TAG, "found color: " + color);
                for(int j = 0; j < 24; j++) {
                    if(isValidMove(i+1, j+1)) {
                        Log.i(TAG, "Has valid moves");
                        return true;
                    }
                }
            }
        }
        Log.i(TAG, "Doesn't have valid moves");
        return false;
    }

    /**
     *
     * @param color The color which may be in the flying phase.
     * @return True if it has exactly 3 checkers left, else return false.
     */
    private boolean isItFlyingPhase(int color) {
        int count = 0;
        for(int i : playingfield) {
            if(i == color) {
                count++;
            }
        }
        return (count == 3);
    }

    /**
     *
     * @param field The field to be checked.
     * @return The color the checker on a field is.
     */
    public int fieldColor(int field) {
        return playingfield[field];
    }

    /**
     *
     * @return The player whos turn it is.
     */
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int computerHardLogic(){

        int computerMove;
        int index;

        int player = Constants.BLACK;
        int computer = Constants.COMPUTER;

        //when the computer has 2 pieces in a line with the third spot empty
        ArrayList<Integer> createComputerMillSpots = new ArrayList<Integer>();

        //when the player has 2 pieces in a line with the third spot empty
        ArrayList<Integer> blockPlayerMillSpots = new ArrayList<Integer>();

        //when the computer has one piece in a line with the other 2 empty
        ArrayList<Integer> buildMillSpots = new ArrayList<Integer>();

        //when the computer can't finish off a mill or try to build one off an existing piece
        ArrayList<Integer> randomSpot = new ArrayList<Integer>();

        //looking to create computer mills
        //line 1-2-3
        if((playingfield[1] == computer && playingfield[2] == computer) & (playingfield[3] == EMPTY_FIELD)){
            //3
            createComputerMillSpots.add(3);
        }
        if((playingfield[1] == computer && playingfield[3] == computer) & (playingfield[2] == EMPTY_FIELD)){
            //2
            createComputerMillSpots.add(2);
        }
        if((playingfield[2] == computer && playingfield[3] == computer) & (playingfield[1] == EMPTY_FIELD)){
            //1
            createComputerMillSpots.add(1);
        }
        //line 4-5-6
        if((playingfield[4] == computer && playingfield[5] == computer) & (playingfield[6] == EMPTY_FIELD)){
            //6
            createComputerMillSpots.add(6);
        }
        if((playingfield[4] == computer && playingfield[6] == computer) & (playingfield[5] == EMPTY_FIELD)){
            //5
            createComputerMillSpots.add(5);
        }
        if((playingfield[5] == computer && playingfield[6] == computer) & (playingfield[4] == EMPTY_FIELD)){
            //4
            createComputerMillSpots.add(4);
        }
        //line 7-8-9
        if((playingfield[7] == computer && playingfield[8] == computer) & (playingfield[9] == EMPTY_FIELD)){
            //9
            createComputerMillSpots.add(9);
        }
        if((playingfield[7] == computer && playingfield[9] == computer) & (playingfield[8] == EMPTY_FIELD)){
            //8
            createComputerMillSpots.add(8);
        }
        if((playingfield[8] == computer && playingfield[9] == computer) & (playingfield[7] == EMPTY_FIELD)){
            //7
            createComputerMillSpots.add(7);
        }
        //line 10-11-12
        if((playingfield[10] == computer && playingfield[11] == computer) & (playingfield[12] == EMPTY_FIELD)){
            //12
            createComputerMillSpots.add(12);
        }
        if((playingfield[10] == computer && playingfield[12] == computer) & (playingfield[11] == EMPTY_FIELD)){
            //11
            createComputerMillSpots.add(11);
        }
        if((playingfield[11] == computer && playingfield[12] == computer) & (playingfield[10] == EMPTY_FIELD)){
            //10
            createComputerMillSpots.add(10);
        }
        //line 13-14-15
        if((playingfield[13] == computer && playingfield[14] == computer) & (playingfield[15] == EMPTY_FIELD)){
            //15
            createComputerMillSpots.add(15);
        }
        if((playingfield[13] == computer && playingfield[15] == computer) & (playingfield[14] == EMPTY_FIELD)){
            //14
            createComputerMillSpots.add(14);
        }
        if((playingfield[14] == computer && playingfield[15] == computer) & (playingfield[13] == EMPTY_FIELD)){
            //13
            createComputerMillSpots.add(13);
        }
        //line 16-17-18
        if((playingfield[16] == computer && playingfield[17] == computer) & (playingfield[18] == EMPTY_FIELD)){
            //18
            createComputerMillSpots.add(18);
        }
        if((playingfield[16] == computer && playingfield[18] == computer) & (playingfield[17] == EMPTY_FIELD)){
            //17
            createComputerMillSpots.add(17);
        }
        if((playingfield[17] == computer && playingfield[18] == computer) & (playingfield[16] == EMPTY_FIELD)){
            //16
            createComputerMillSpots.add(16);
        }
        //line 19-20-21
        if((playingfield[19] == computer && playingfield[20] == computer) & (playingfield[21] == EMPTY_FIELD)){
            //21
            createComputerMillSpots.add(21);
        }
        if((playingfield[19] == computer && playingfield[21] == computer) & (playingfield[20] == EMPTY_FIELD)){
            //20
            createComputerMillSpots.add(20);
        }
        if((playingfield[20] == computer && playingfield[21] == computer) & (playingfield[19] == EMPTY_FIELD)){
            //19
            createComputerMillSpots.add(19);
        }
        //line 22-23-24
        if((playingfield[22] == computer && playingfield[23] == computer) & (playingfield[24] == EMPTY_FIELD)){
            //24
            createComputerMillSpots.add(24);
        }
        if((playingfield[22] == computer && playingfield[24] == computer) & (playingfield[23] == EMPTY_FIELD)){
            //23
            createComputerMillSpots.add(23);
        }
        if((playingfield[23] == computer && playingfield[24] == computer) & (playingfield[22] == EMPTY_FIELD)){
            //22
            createComputerMillSpots.add(22);
        }
        //line 1-10-22
        if((playingfield[1] == computer && playingfield[10] == computer) & (playingfield[22] == EMPTY_FIELD)){
            //22
            createComputerMillSpots.add(22);
        }
        if((playingfield[1] == computer && playingfield[22] == computer) & (playingfield[10] == EMPTY_FIELD)){
            //10
            createComputerMillSpots.add(10);
        }
        if((playingfield[10] == computer && playingfield[22] == computer) & (playingfield[1] == EMPTY_FIELD)){
            //1
            createComputerMillSpots.add(1);
        }
        //line 4-11-19
        if((playingfield[4] == computer && playingfield[11] == computer) & (playingfield[19] == EMPTY_FIELD)){
            //19
            createComputerMillSpots.add(19);
        }
        if((playingfield[4] == computer && playingfield[19] == computer) & (playingfield[11] == EMPTY_FIELD)){
            //11
            createComputerMillSpots.add(11);
        }
        if((playingfield[11] == computer && playingfield[19] == computer) & (playingfield[4] == EMPTY_FIELD)){
            //4
            createComputerMillSpots.add(4);
        }
        //line 7-12-16
        if((playingfield[7] == computer && playingfield[12] == computer) & (playingfield[16] == EMPTY_FIELD)){
            //16
            createComputerMillSpots.add(16);
        }
        if((playingfield[7] == computer && playingfield[16] == computer) & (playingfield[12] == EMPTY_FIELD)){
            //12
            createComputerMillSpots.add(12);
        }
        if((playingfield[12] == computer && playingfield[16] == computer) & (playingfield[7] == EMPTY_FIELD)){
            //7
            createComputerMillSpots.add(7);
        }
        //line 2-5-8
        if((playingfield[2] == computer && playingfield[5] == computer) & (playingfield[8] == EMPTY_FIELD)){
            //8
            createComputerMillSpots.add(8);
        }
        if((playingfield[2] == computer && playingfield[8] == computer) & (playingfield[5] == EMPTY_FIELD)){
            //5
            createComputerMillSpots.add(5);
        }
        if((playingfield[5] == computer && playingfield[8] == computer) & (playingfield[2] == EMPTY_FIELD)){
            //2
            createComputerMillSpots.add(2);
        }
        //line 17-20-23
        if((playingfield[17] == computer && playingfield[20] == computer) & (playingfield[23] == EMPTY_FIELD)){
            //23
            createComputerMillSpots.add(23);
        }
        if((playingfield[17] == computer && playingfield[23] == computer) & (playingfield[20] == EMPTY_FIELD)){
            //20
            createComputerMillSpots.add(20);
        }
        if((playingfield[20] == computer && playingfield[23] == computer) & (playingfield[17] == EMPTY_FIELD)){
            //17
            createComputerMillSpots.add(17);
        }
        //line 9-13-18
        if((playingfield[9] == computer && playingfield[13] == computer) & (playingfield[18] == EMPTY_FIELD)){
            //18
            createComputerMillSpots.add(18);
        }
        if((playingfield[9] == computer && playingfield[18] == computer) & (playingfield[13] == EMPTY_FIELD)){
            //13
            createComputerMillSpots.add(13);
        }
        if((playingfield[13] == computer && playingfield[18] == computer) & (playingfield[9] == EMPTY_FIELD)){
            //9
            createComputerMillSpots.add(9);
        }
        //line 6-14-21
        if((playingfield[6] == computer && playingfield[14] == computer) & (playingfield[21] == EMPTY_FIELD)){
            //21
            createComputerMillSpots.add(21);
        }
        if((playingfield[6] == computer && playingfield[21] == computer) & (playingfield[14] == EMPTY_FIELD)){
            //14
            createComputerMillSpots.add(14);
        }
        if((playingfield[14] == computer && playingfield[21] == computer) & (playingfield[6] == EMPTY_FIELD)){
            //6
            createComputerMillSpots.add(6);
        }
        //line 3-15-24
        if((playingfield[3] == computer && playingfield[15] == computer) & (playingfield[24] == EMPTY_FIELD)){
            //24
            createComputerMillSpots.add(24);
        }
        if((playingfield[3] == computer && playingfield[24] == computer) & (playingfield[15] == EMPTY_FIELD)){
            //15
            createComputerMillSpots.add(15);
        }
        if((playingfield[15] == computer && playingfield[24] == computer) & (playingfield[3] == EMPTY_FIELD)){
            //3
            createComputerMillSpots.add(3);
        }

        if(createComputerMillSpots.size() > 0){ //if there were spots to create a computer mill

            index = (int) Math.random() * createComputerMillSpots.size(); //choose a random spot to build a mill
            computerMove = createComputerMillSpots.get(index);
            return computerMove;

        }

        //looking to player block mills
        //line 1-2-3
        if((playingfield[1] == player && playingfield[2] == player) & (playingfield[3] == EMPTY_FIELD)){
            //3
            blockPlayerMillSpots.add(3);
        }
        if((playingfield[1] == player && playingfield[3] == player) & (playingfield[2] == EMPTY_FIELD)){
            //2
            blockPlayerMillSpots.add(2);
        }
        if((playingfield[2] == player && playingfield[3] == player) & (playingfield[1] == EMPTY_FIELD)){
            //1
            blockPlayerMillSpots.add(1);
        }
        //line 4-5-6
        if((playingfield[4] == player && playingfield[5] == player) & (playingfield[6] == EMPTY_FIELD)){
            //6
            blockPlayerMillSpots.add(6);
        }
        if((playingfield[4] == player && playingfield[6] == player) & (playingfield[5] == EMPTY_FIELD)){
            //5
            blockPlayerMillSpots.add(5);
        }
        if((playingfield[5] == player && playingfield[6] == player) & (playingfield[4] == EMPTY_FIELD)){
            //4
            blockPlayerMillSpots.add(4);
        }
        //line 7-8-9
        if((playingfield[7] == player && playingfield[8] == player) & (playingfield[9] == EMPTY_FIELD)){
            //9
            blockPlayerMillSpots.add(9);
        }
        if((playingfield[7] == player && playingfield[9] == player) & (playingfield[8] == EMPTY_FIELD)){
            //8
            blockPlayerMillSpots.add(8);
        }
        if((playingfield[8] == player && playingfield[9] == player) & (playingfield[7] == EMPTY_FIELD)){
            //7
            blockPlayerMillSpots.add(7);
        }
        //line 10-11-12
        if((playingfield[10] == player && playingfield[11] == player) & (playingfield[12] == EMPTY_FIELD)){
            //12
            blockPlayerMillSpots.add(12);
        }
        if((playingfield[10] == player && playingfield[12] == player) & (playingfield[11] == EMPTY_FIELD)){
            //11
            blockPlayerMillSpots.add(11);
        }
        if((playingfield[11] == player && playingfield[12] == player) & (playingfield[10] == EMPTY_FIELD)){
            //10
            blockPlayerMillSpots.add(10);
        }
        //line 13-14-15
        if((playingfield[13] == player && playingfield[14] == player) & (playingfield[15] == EMPTY_FIELD)){
            //15
            blockPlayerMillSpots.add(15);
        }
        if((playingfield[13] == player && playingfield[15] == player) & (playingfield[14] == EMPTY_FIELD)){
            //14
            blockPlayerMillSpots.add(14);
        }
        if((playingfield[14] == player && playingfield[15] == player) & (playingfield[13] == EMPTY_FIELD)){
            //13
            blockPlayerMillSpots.add(13);
        }
        //line 16-17-18
        if((playingfield[16] == player && playingfield[17] == player) & (playingfield[18] == EMPTY_FIELD)){
            //18
            blockPlayerMillSpots.add(18);
        }
        if((playingfield[16] == player && playingfield[18] == player) & (playingfield[17] == EMPTY_FIELD)){
            //17
            blockPlayerMillSpots.add(17);
        }
        if((playingfield[17] == player && playingfield[18] == player) & (playingfield[16] == EMPTY_FIELD)){
            //16
            blockPlayerMillSpots.add(16);
        }
        //line 19-20-21
        if((playingfield[19] == player && playingfield[20] == player) & (playingfield[21] == EMPTY_FIELD)){
            //21
            blockPlayerMillSpots.add(21);
        }
        if((playingfield[19] == player && playingfield[21] == player) & (playingfield[20] == EMPTY_FIELD)){
            //20
            blockPlayerMillSpots.add(20);
        }
        if((playingfield[20] == player && playingfield[21] == player) & (playingfield[19] == EMPTY_FIELD)){
            //19
            blockPlayerMillSpots.add(19);
        }
        //line 22-23-24
        if((playingfield[22] == player && playingfield[23] == player) & (playingfield[24] == EMPTY_FIELD)){
            //24
            blockPlayerMillSpots.add(24);
        }
        if((playingfield[22] == player && playingfield[24] == player) & (playingfield[23] == EMPTY_FIELD)){
            //23
            blockPlayerMillSpots.add(23);
        }
        if((playingfield[23] == player && playingfield[24] == player) & (playingfield[22] == EMPTY_FIELD)){
            //22
            blockPlayerMillSpots.add(22);
        }
        //line 1-10-22
        if((playingfield[1] == player && playingfield[10] == player) & (playingfield[22] == EMPTY_FIELD)){
            //22
            blockPlayerMillSpots.add(22);
        }
        if((playingfield[1] == player && playingfield[22] == player) & (playingfield[10] == EMPTY_FIELD)){
            //10
            blockPlayerMillSpots.add(10);
        }
        if((playingfield[10] == player && playingfield[22] == player) & (playingfield[1] == EMPTY_FIELD)){
            //1
            blockPlayerMillSpots.add(1);
        }
        //line 4-11-19
        if((playingfield[4] == player && playingfield[11] == player) & (playingfield[19] == EMPTY_FIELD)){
            //19
            blockPlayerMillSpots.add(19);
        }
        if((playingfield[4] == player && playingfield[19] == player) & (playingfield[11] == EMPTY_FIELD)){
            //11
            blockPlayerMillSpots.add(11);
        }
        if((playingfield[11] == player && playingfield[19] == player) & (playingfield[4] == EMPTY_FIELD)){
            //4
            blockPlayerMillSpots.add(4);
        }
        //line 7-12-16
        if((playingfield[7] == player && playingfield[12] == player) & (playingfield[16] == EMPTY_FIELD)){
            //16
            blockPlayerMillSpots.add(16);
        }
        if((playingfield[7] == player && playingfield[16] == player) & (playingfield[12] == EMPTY_FIELD)){
            //12
            blockPlayerMillSpots.add(12);
        }
        if((playingfield[12] == player && playingfield[16] == player) & (playingfield[7] == EMPTY_FIELD)){
            //7
            blockPlayerMillSpots.add(7);
        }
        //line 2-5-8
        if((playingfield[2] == player && playingfield[5] == player) & (playingfield[8] == EMPTY_FIELD)){
            //8
            blockPlayerMillSpots.add(8);
        }
        if((playingfield[2] == player && playingfield[8] == player) & (playingfield[5] == EMPTY_FIELD)){
            //5
            blockPlayerMillSpots.add(5);
        }
        if((playingfield[5] == player && playingfield[8] == player) & (playingfield[2] == EMPTY_FIELD)){
            //2
            blockPlayerMillSpots.add(2);
        }
        //line 17-20-23
        if((playingfield[17] == player && playingfield[20] == player) & (playingfield[23] == EMPTY_FIELD)){
            //23
            blockPlayerMillSpots.add(23);
        }
        if((playingfield[17] == player && playingfield[23] == player) & (playingfield[20] == EMPTY_FIELD)){
            //20
            blockPlayerMillSpots.add(20);
        }
        if((playingfield[20] == player && playingfield[23] == player) & (playingfield[17] == EMPTY_FIELD)){
            //17
            blockPlayerMillSpots.add(17);
        }
        //line 9-13-18
        if((playingfield[9] == player && playingfield[13] == player) & (playingfield[18] == EMPTY_FIELD)){
            //18
            blockPlayerMillSpots.add(18);
        }
        if((playingfield[9] == player && playingfield[18] == player) & (playingfield[13] == EMPTY_FIELD)){
            //13
            blockPlayerMillSpots.add(13);
        }
        if((playingfield[13] == player && playingfield[18] == player) & (playingfield[9] == EMPTY_FIELD)){
            //9
            blockPlayerMillSpots.add(9);
        }
        //line 6-14-21
        if((playingfield[6] == player && playingfield[14] == player) & (playingfield[21] == EMPTY_FIELD)){
            //21
            blockPlayerMillSpots.add(21);
        }
        if((playingfield[6] == player && playingfield[21] == player) & (playingfield[14] == EMPTY_FIELD)){
            //14
            blockPlayerMillSpots.add(14);
        }
        if((playingfield[14] == player && playingfield[21] == player) & (playingfield[6] == EMPTY_FIELD)){
            //6
            blockPlayerMillSpots.add(6);
        }
        //line 3-15-24
        if((playingfield[3] == player && playingfield[15] == player) & (playingfield[24] == EMPTY_FIELD)){
            //24
            blockPlayerMillSpots.add(24);
        }
        if((playingfield[3] == player && playingfield[24] == player) & (playingfield[15] == EMPTY_FIELD)){
            //15
            blockPlayerMillSpots.add(15);
        }
        if((playingfield[15] == player && playingfield[24] == player) & (playingfield[3] == EMPTY_FIELD)){
            //3
            blockPlayerMillSpots.add(3);
        }

        if(blockPlayerMillSpots.size() > 0){ //if there were spots to block a player mill from being formed

            index = (int) Math.random() * blockPlayerMillSpots.size(); //choose a random spot to block the mill
            computerMove = blockPlayerMillSpots.get(index);
            return computerMove;

        }

        //Looking to build on existing pieces
        //line 1-2-3
        if((playingfield[1] == computer) & (playingfield[2] == EMPTY_FIELD & playingfield[3] == EMPTY_FIELD)){
            //2-3
            buildMillSpots.add(2);
            buildMillSpots.add(3);
        }
        if((playingfield[2] == computer) & (playingfield[1] == EMPTY_FIELD & playingfield[3] == EMPTY_FIELD)){
            //1-3
            buildMillSpots.add(1);
            buildMillSpots.add(3);
        }
        if((playingfield[3] == computer) & (playingfield[1] == EMPTY_FIELD & playingfield[2] == EMPTY_FIELD)){
            //1-2
            buildMillSpots.add(1);
            buildMillSpots.add(2);
        }
        //line 4-5-6
        if((playingfield[4] == computer) & (playingfield[5] == EMPTY_FIELD & playingfield[6] == EMPTY_FIELD)){
            //5-6
            buildMillSpots.add(5);
            buildMillSpots.add(6);
        }
        if((playingfield[5] == computer) & (playingfield[4] == EMPTY_FIELD & playingfield[6] == EMPTY_FIELD)){
            //4-6
            buildMillSpots.add(4);
            buildMillSpots.add(6);
        }
        if((playingfield[6] == computer) & (playingfield[4] == EMPTY_FIELD & playingfield[5] == EMPTY_FIELD)){
            //4-5
            buildMillSpots.add(4);
            buildMillSpots.add(5);
        }
        //line 7-8-9
        if((playingfield[7] == computer) & (playingfield[8] == EMPTY_FIELD & playingfield[9] == EMPTY_FIELD)){
            //8-9
            buildMillSpots.add(8);
            buildMillSpots.add(9);
        }
        if((playingfield[8] == computer) & (playingfield[7] == EMPTY_FIELD & playingfield[9] == EMPTY_FIELD)){
            //7-9
            buildMillSpots.add(7);
            buildMillSpots.add(9);
        }
        if((playingfield[9] == computer) & (playingfield[7] == EMPTY_FIELD & playingfield[8] == EMPTY_FIELD)){
            //7-8
            buildMillSpots.add(7);
            buildMillSpots.add(8);
        }
        //line 10-11-12
        if((playingfield[10] == computer) & (playingfield[11] == EMPTY_FIELD & playingfield[12] == EMPTY_FIELD)){
            //11-12
            buildMillSpots.add(11);
            buildMillSpots.add(12);
        }
        if((playingfield[11] == computer) & (playingfield[10] == EMPTY_FIELD & playingfield[12] == EMPTY_FIELD)){
            //10-12
            buildMillSpots.add(10);
            buildMillSpots.add(12);
        }
        if((playingfield[12] == computer) & (playingfield[10] == EMPTY_FIELD & playingfield[11] == EMPTY_FIELD)){
            //10-11
            buildMillSpots.add(10);
            buildMillSpots.add(11);
        }
        //line 13-14-15
        if((playingfield[13] == computer) & (playingfield[14] == EMPTY_FIELD & playingfield[15] == EMPTY_FIELD)){
            //14-15
            buildMillSpots.add(14);
            buildMillSpots.add(25);
        }
        if((playingfield[14] == computer) & (playingfield[13] == EMPTY_FIELD & playingfield[15] == EMPTY_FIELD)){
            //13-15
            buildMillSpots.add(13);
            buildMillSpots.add(15);
        }
        if((playingfield[15] == computer) & (playingfield[13] == EMPTY_FIELD & playingfield[14] == EMPTY_FIELD)){
            //13-14
            buildMillSpots.add(13);
            buildMillSpots.add(14);
        }
        //line 16-17-18
        if((playingfield[16] == computer) & (playingfield[17] == EMPTY_FIELD & playingfield[18] == EMPTY_FIELD)){
            //17-18
            buildMillSpots.add(17);
            buildMillSpots.add(18);
        }
        if((playingfield[17] == computer) & (playingfield[16] == EMPTY_FIELD & playingfield[18] == EMPTY_FIELD)){
            //16-18
            buildMillSpots.add(16);
            buildMillSpots.add(18);
        }
        if((playingfield[18] == computer) & (playingfield[16] == EMPTY_FIELD & playingfield[17] == EMPTY_FIELD)){
            //16-17
            buildMillSpots.add(16);
            buildMillSpots.add(17);
        }
        //line 19-20-21
        if((playingfield[19] == computer) & (playingfield[20] == EMPTY_FIELD & playingfield[21] == EMPTY_FIELD)){
            //20-21
            buildMillSpots.add(20);
            buildMillSpots.add(21);
        }
        if((playingfield[20] == computer) & (playingfield[19] == EMPTY_FIELD & playingfield[21] == EMPTY_FIELD)){
            //19-21
            buildMillSpots.add(19);
            buildMillSpots.add(21);
        }
        if((playingfield[21] == computer) & (playingfield[19] == EMPTY_FIELD & playingfield[20] == EMPTY_FIELD)){
            //19-20
            buildMillSpots.add(19);
            buildMillSpots.add(20);
        }
        //line 22-23-24
        if((playingfield[22] == computer) & (playingfield[23] == EMPTY_FIELD & playingfield[24] == EMPTY_FIELD)){
            //23-24
            buildMillSpots.add(23);
            buildMillSpots.add(24);
        }
        if((playingfield[23] == computer) & (playingfield[22] == EMPTY_FIELD & playingfield[24] == EMPTY_FIELD)){
            //22-24
            buildMillSpots.add(22);
            buildMillSpots.add(24);
        }
        if((playingfield[24] == computer) & (playingfield[22] == EMPTY_FIELD & playingfield[23] == EMPTY_FIELD)){
            //22-23
            buildMillSpots.add(22);
            buildMillSpots.add(23);
        }
        //line 1-10-22
        if((playingfield[1] == computer) & (playingfield[10] == EMPTY_FIELD & playingfield[22] == EMPTY_FIELD)){
            //10-22
            buildMillSpots.add(10);
            buildMillSpots.add(22);
        }
        if((playingfield[10] == computer) & (playingfield[1] == EMPTY_FIELD & playingfield[22] == EMPTY_FIELD)){
            //1-22
            buildMillSpots.add(1);
            buildMillSpots.add(22);
        }
        if((playingfield[22] == computer) & (playingfield[1] == EMPTY_FIELD & playingfield[10] == EMPTY_FIELD)){
            //1-10
            buildMillSpots.add(1);
            buildMillSpots.add(10);
        }
        //line 4-11-19
        if((playingfield[4] == computer) & (playingfield[11] == EMPTY_FIELD & playingfield[19] == EMPTY_FIELD)){
            //11-19
            buildMillSpots.add(11);
            buildMillSpots.add(19);
        }
        if((playingfield[11] == computer) & (playingfield[4] == EMPTY_FIELD & playingfield[19] == EMPTY_FIELD)){
            //4-19
            buildMillSpots.add(4);
            buildMillSpots.add(19);
        }
        if((playingfield[19] == computer) & (playingfield[4] == EMPTY_FIELD & playingfield[11] == EMPTY_FIELD)){
            //4-11
            buildMillSpots.add(4);
            buildMillSpots.add(11);
        }
        //line 7-12-16
        if((playingfield[7] == computer) & (playingfield[12] == EMPTY_FIELD & playingfield[16] == EMPTY_FIELD)){
            //12-16
            buildMillSpots.add(12);
            buildMillSpots.add(16);
        }
        if((playingfield[12] == computer) & (playingfield[7] == EMPTY_FIELD & playingfield[16] == EMPTY_FIELD)){
            //7-16
            buildMillSpots.add(7);
            buildMillSpots.add(16);
        }
        if((playingfield[16] == computer) & (playingfield[7] == EMPTY_FIELD & playingfield[12] == EMPTY_FIELD)){
            //7-12
            buildMillSpots.add(7);
            buildMillSpots.add(12);
        }
        //line 2-5-8
        if((playingfield[2] == computer) & (playingfield[5] == EMPTY_FIELD & playingfield[8] == EMPTY_FIELD)){
            //5-8
            buildMillSpots.add(5);
            buildMillSpots.add(8);
        }
        if((playingfield[5] == computer) & (playingfield[2] == EMPTY_FIELD & playingfield[8] == EMPTY_FIELD)){
            //2-8
            buildMillSpots.add(2);
            buildMillSpots.add(8);
        }
        if((playingfield[8] == computer) & (playingfield[2] == EMPTY_FIELD & playingfield[5] == EMPTY_FIELD)){
            //2-5
            buildMillSpots.add(2);
            buildMillSpots.add(5);
        }
        //line 17-20-23
        if((playingfield[17] == computer) & (playingfield[20] == EMPTY_FIELD & playingfield[23] == EMPTY_FIELD)){
            //20-23
            buildMillSpots.add(20);
            buildMillSpots.add(23);
        }
        if((playingfield[20] == computer) & (playingfield[17] == EMPTY_FIELD & playingfield[23] == EMPTY_FIELD)){
            //17-23
            buildMillSpots.add(17);
            buildMillSpots.add(23);
        }
        if((playingfield[23] == computer) & (playingfield[17] == EMPTY_FIELD & playingfield[20] == EMPTY_FIELD)){
            //17-20
            buildMillSpots.add(17);
            buildMillSpots.add(20);
        }
        //line 9-13-18
        if((playingfield[9] == computer) & (playingfield[13] == EMPTY_FIELD & playingfield[18] == EMPTY_FIELD)){
            //13-18
            buildMillSpots.add(13);
            buildMillSpots.add(18);
        }
        if((playingfield[13] == computer) & (playingfield[9] == EMPTY_FIELD & playingfield[18] == EMPTY_FIELD)){
            //9-18
            buildMillSpots.add(9);
            buildMillSpots.add(18);
        }
        if((playingfield[18] == computer) & (playingfield[9] == EMPTY_FIELD & playingfield[13] == EMPTY_FIELD)){
            //9-13
            buildMillSpots.add(9);
            buildMillSpots.add(13);
        }
        //line 6-14-21
        if((playingfield[6] == computer) & (playingfield[14] == EMPTY_FIELD & playingfield[21] == EMPTY_FIELD)){
            //14-21
            buildMillSpots.add(14);
            buildMillSpots.add(21);
        }
        if((playingfield[14] == computer) & (playingfield[6] == EMPTY_FIELD & playingfield[21] == EMPTY_FIELD)){
            //6-21
            buildMillSpots.add(6);
            buildMillSpots.add(21);
        }
        if((playingfield[21] == computer) & (playingfield[6] == EMPTY_FIELD & playingfield[14] == EMPTY_FIELD)){
            //6-14
            buildMillSpots.add(6);
            buildMillSpots.add(14);
        }
        //line 3-15-24
        if((playingfield[3] == computer) & (playingfield[15] == EMPTY_FIELD & playingfield[24] == EMPTY_FIELD)){
            //15-24
            buildMillSpots.add(15);
            buildMillSpots.add(24);
        }
        if((playingfield[15] == computer) & (playingfield[3] == EMPTY_FIELD & playingfield[24] == EMPTY_FIELD)){
            //3-24
            buildMillSpots.add(3);
            buildMillSpots.add(24);
        }
        if((playingfield[24] == computer) & (playingfield[3] == EMPTY_FIELD & playingfield[25] == EMPTY_FIELD)){
            //3-15
            buildMillSpots.add(3);
            buildMillSpots.add(15);
        }

        if(buildMillSpots.size() > 0){ //if there is a computer piece that it can continue to build on

            index = (int) Math.random() * buildMillSpots.size(); //choose a random spot keep building
            computerMove = buildMillSpots.get(index);
            return computerMove;

        }

        if((createComputerMillSpots.size() == 0) & (blockPlayerMillSpots.size() == 0) & (buildMillSpots.size() ==0)){

            for(int i =0; i<24; i++){

                if(playingfield[i+1] == EMPTY_FIELD){

                    randomSpot.add(i+1);

                }
            }
        }

        index = (int) Math.random() * randomSpot.size();
        computerMove = randomSpot.get(index);
        return computerMove;

    }

    public int computerEasyLogic(){

        int computerMove;
        int index;

        //All the empty spots on the board
        ArrayList<Integer> randomSpot = new ArrayList<Integer>();


        for(int i =0; i<24; i++){

            if(playingfield[i+1] == EMPTY_FIELD){

                randomSpot.add(i+1);

            }
        }

        index = (int) Math.random() * randomSpot.size();
        computerMove = randomSpot.get(index);
        return computerMove;

    }

}