package Highscore;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame{
    public  JTextField displayBox;
    private final JPanel buttons;
    private final JPanel display;
    private final JLabel label1;
    private final JButton b0;
    public static String str;

    public Register(){
        final JFrame window = new JFrame("Welcome to the scoring list");
        window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.PAGE_AXIS));
        window.add(Box.createVerticalGlue());
        window.add(Box.createHorizontalGlue());

        label1 = new JLabel("Type your name: ");
        add(label1);
        displayBox = new JTextField(20);
        display = new JPanel();

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3));

        b0 = new JButton("Submit");
        b0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( e.getSource() == b0 ) {
                    str = displayBox.getText();
                    window.setVisible(false);
                    window.dispose();
                    new HighScore();

                }
            }
        });

        window.getContentPane().add(display);
        display.add(label1);
        display.add(displayBox);

        window.getContentPane().add(buttons);
        buttons.add(b0);

        window.pack();
        window.setSize(300, 150);
        window.setVisible(true);

    }

}