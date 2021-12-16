package com.example.ninemenmorrisgroup6.Helps;

import android.media.MediaPlayer;

public class Music {

    public static MediaPlayer backgroundMusic;

    //Whether the music is muted or unmuted.
    public static String muteStatus;
    public static int musicInitialization = 1;

    /**
     * Initializes playing the background music. Sets muteStatus to UNMUTED as the music will
     * be playing.
     */

    public static void startMusic(){


        backgroundMusic.setLooping(true);
        setMuteStatus("UNMUTED");
        backgroundMusic.start();


    }

    public static void setMuteStatus(String status){

        muteStatus = status;

    }

    public static String getMuteStatus(){

        return muteStatus;

    }


}
