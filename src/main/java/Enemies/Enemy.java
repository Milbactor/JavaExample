package Enemies;

import Control.*;

import java.awt.*;

public class Enemy {
    public Fort app;				    // applet
    public Image img;					// image
    public Image effect;                // explosion animation
    public int x, y;					// position
    public int ex, ey;                  // position of explosion effect
    public int shootingFrequency;       // the shooting frequency of the Enemy, used to control the limits of the randomized time intervals between the shooting
    public int shootTimer;              // the randomised shooting timer for the bullet
    public int width;                   // the width of the Enemy picture
    public int height;                  // the height of the Enemy picture
    public int damage;                  // the damage that the Enemy causes when it collides with a Player
    public int awardPoints;             // the points that the Player gets when he shoots the Enemy
    public boolean collideP1;           // a flag, which denotes if Player 1 has collided with the Enemy, used for collision detection
    public boolean collideP2;           // a flag, which denotes if Player 2 has collided with the Enemy, used for collision detection
    public int alreadyHitTimer;         // timer used for showing effect/imgHit instead of img, after a collision has happened

    /**
     * This is the default constructor for enemies
     */
    public Enemy() {}

    /**
     * This method returns the width of the Enemy Picture
     * @return the width of the Enemy Picture
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the Enemy Picture
     * @return the height of the Enemy Picture
     */
    public int getHeight() {
        return height;
    }
}
