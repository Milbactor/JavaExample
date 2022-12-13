package Highscore;
import java.util.Comparator;


public class ScoreComparator implements Comparator<Score> {


    @Override
    public int compare(Score o1, Score o2) {
        return ((Integer)(o2.getScore())).compareTo(o1.getScore());
    }
}
