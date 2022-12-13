package Bullets;

import Control.*;

import java.awt.*;


public class Bullet {
    public Fort app;				// applet
    public Image img;				// picture of the bullet
    public int x, y;				// position of the bullet
    public int vx, vy;				// speed of the bullet
    public boolean appear;			// flag if the bullet is to be displayed
    public int width;               // the width of the bullet picture
    public int height;              // the height of the bullet picture
    public int damage;              // the damage that the bullet causes when it collides with a Player or an Enemy

    /**
     * This is the default constructor for bullets
     */
    public Bullet() {}

    /**
     * This method shoots the bullet
     * @param x  X axis for the initial position of the bullet
     * @param y  Y axis for the initial position of the bullet
     * @param vx  horizontal speed of the bullet
     * @param vy  vertical speed of the bullet
     */
    public void shoot(int x, int y, int vx, int vy)	{
        if (!appear) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            appear = true;
        }
    }

    /**
     * This method draws the bullet on the screen
     * @param g  the instance for the JFrame graphics
     */
    public void draw(Graphics g) {
        if (appear && app.show) g.drawImage(img, x, y, app);
    }

    /**
     * This method returns the width of the bullet picture
     * @return the width of the bullet picture
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the bullet picture
     * @return the height of the bullet picture
     */
    public int getHeight() {
        return height;
    }
}
