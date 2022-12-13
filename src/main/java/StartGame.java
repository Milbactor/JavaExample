/**
 * Created with IntelliJ IDEA.
 * User: Nana Okamoto
 * Date: 10/15/12
 * Time: 2:42 AM
 * University Java Project - Fort!
 * Group members: Hristo Dimitrov, Nana Okamoto, Paul Clydesdale, Danne Connolly;
 */
import Control.*;

import javax.swing.*;


public class StartGame {
    /**
     * This is the main method of the game. You start the game from here.
     * @param args the arguments for the main method
     */
    public static void main(final String[] args) {
        JFrame frame = new JFrame("Fort!");
        Fort test = new Fort();
        test.setSize(300, 400);
        frame.getContentPane().add(test);
        frame.setBounds(0 , 0 , 316 , 438);
        frame.setVisible(true);
        test.init();
        test.start();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
