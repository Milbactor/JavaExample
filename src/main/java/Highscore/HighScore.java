package Highscore;

import Control.*;

import javax.swing.*;
import java.util.Date;

public class HighScore extends JFrame{
    private int points;
    private String timer;
    private String name;
    public static String scorePrint;
    String str2array;

//    public Fort player1;

    public HighScore(){
        //Add the score
        Date date = new Date();
        Tracking hm = new Tracking();
        name = Register.str;
        points = Fort.TrackScore();
        timer = date.toString();
        hm.addScore(name, timer, points);

        scorePrint = hm.getScoring();
        str2array = scorePrint;
        String[] arr=str2array.split("\n");
        System.out.println("Array :"+arr.length);
        for(int i=0;i<arr.length;i++){
            System.out.println("array"+i+"  :"+arr[i]);
        }
    }
}

