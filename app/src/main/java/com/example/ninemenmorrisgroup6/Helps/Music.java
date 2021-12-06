package com.example.ninemenmorrisgroup6.Helps;

import android.media.MediaPlayer;

public class Music {

    public static MediaPlayer backgroundMusic;
    public static String muteStatus;
    public static int musicInitialization = 1;

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
