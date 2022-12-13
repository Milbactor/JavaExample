package Control;


import java.awt.event.*;


//control class
public class Key  extends KeyAdapter {
    public static boolean up, down, left, right, enter, control, A, S, D, W, shift, one, two;

    /**
     * This method is used when a key is pressed
     * @param e the key which is being pressed
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        //player 1 movement keys
            case KeyEvent.VK_UP: up  = true; break;	                //Player 1 move  up
            case KeyEvent.VK_DOWN: down = true; break;	            //Player 1 move  down
            case KeyEvent.VK_LEFT: left = true; break;	            //Player 1 move  left
            case KeyEvent.VK_RIGHT: right = true; break;	        //Player 1 move  right
            case KeyEvent.VK_CONTROL: control = true; break;        //Player 1 SHOOT
        //player 2 movement keys
            case KeyEvent.VK_W: W  = true; break;	            //Player 2 move up
            case KeyEvent.VK_S: S = true; break;	            //Player 2 move  down
            case KeyEvent.VK_A: A = true; break;	            //Player 2 move  left
            case KeyEvent.VK_D: D = true; break;	            //Player 2 move  right
            case KeyEvent.VK_SHIFT: shift = true; break;        //Player 2 SHOOT

            case KeyEvent.VK_1: one = true; break;          //Set single player mode
            case KeyEvent.VK_2: two = true; break;          //Set double player mode (Multiplayer)
            case KeyEvent.VK_ENTER: enter = true; break;	// Enter
        }
    }

    /**
     * his method is used when a key is released
     * @param e @param e the key which is being released
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: up  = false; break;	        //Player 1 move  up
            case KeyEvent.VK_DOWN: down = false; break;	        //Player 1 move  down
            case KeyEvent.VK_LEFT: left = false; break;	        //Player 1 move  left
            case KeyEvent.VK_RIGHT: right = false; break;	    //Player 1 move  right
            case KeyEvent.VK_CONTROL: control = false; break;	//Player 1 SHOOT

            case KeyEvent.VK_W: W  = false; break;	        //Player 2 move up
            case KeyEvent.VK_S: S = false; break;	        //Player 2 move  down
            case KeyEvent.VK_A: A = false; break;	        //Player 2 move  left
            case KeyEvent.VK_D: D = false; break;	        //Player 2 move  right
            case KeyEvent.VK_SHIFT: shift = false; break;	//Player 2 SHOOT

            case KeyEvent.VK_1: one = false; break;         //Set single player mode
            case KeyEvent.VK_2: two = false; break;         //Set double player mode (Multiplayer)
            case KeyEvent.VK_ENTER: enter = false; break;	// Enter
        }
    }
}