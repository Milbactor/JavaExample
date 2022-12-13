package Highscore;
import java.io.Serializable;

public class Score implements Serializable{
    private static final long serialVersionUID = 1L;
    private int score;
    private String name;
    private String time;

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    public String getTime(){
        return time;
    }
    public Score(String name, String time, int score) {
        this.score = score;
        this.time = time;
        this.name = name;
    }
}
