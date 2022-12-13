package Highscore;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class Tracking {
    private ArrayList<Score> ScoreList;


    private static final String FileName = "scores.txt";


    ObjectOutputStream OutputStream = null;
    ObjectInputStream InputStream = null;

    public Tracking() {

        ScoreList = new ArrayList<Score>();
    }
    private void sort() {
        ScoreComparator num = new ScoreComparator();
        Collections.sort(ScoreList, num);
    }

    public ArrayList<Score> getScores() {
        LoadScore();
        sort();
        return ScoreList;
    }
    public void LoadScore() {
        try {
            InputStream = new ObjectInputStream(new FileInputStream(FileName));
            ScoreList = (ArrayList<Score>) InputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (OutputStream != null) {
                    OutputStream.flush();
                    OutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    public void UpdateScore() {
        try {
            OutputStream = new ObjectOutputStream(new FileOutputStream(FileName));
            OutputStream.writeObject(ScoreList);
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "restarting the program");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (OutputStream != null) {
                    OutputStream.flush();
                    OutputStream.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    public String getScoring() {
        String HighScore= "";
        int max = 10;

        ArrayList<Score> ScoreList1;
        ScoreList1 = getScores();

        int i = 0;
        int x = ScoreList1.size();
        if (x > max) {
            x = max;
        }
        System.out.println("Position\t\tName\t\tScore\t\tDate\n");
        while (i < x) {
            HighScore += (i + 1) + ". " + ScoreList1.get(i).getName() +
                    " - " + ScoreList1.get(i).getScore() +  "   ("+ ScoreList1.get(i).getTime() + ")\n";
            i++;
        }
        return HighScore;
    }
    public void addScore(String name, String time, int score) {
        LoadScore();
        ScoreList.add(new Score(name, time, score));
        UpdateScore();
    }
}
