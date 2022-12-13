package Bullets;

import Control.*;

import java.awt.*;


// EnemyType2Bullet is the bullet used by EnemyType2, but it is also used by the Fortress
public class EnemyType2Bullet extends Bullet{
    public int lx, ly;			    // position of the left shot
    public int rx, ry;				// position of the right shot
    public boolean appearl;			// flag if the left shot is already on the display
    public boolean appearr;			// flag if the right shot is already on the display

    /**
     * This is the constructor method for the EnemyType2Bullet
     * @param applet applet the applet instance
     */
    public EnemyType2Bullet(Fort applet) {
        this.app = applet;
        this.appear = false;
        this.appearl = false;
        this.appearr = false;
        this.damage = 15;
        if(app.hostOS.equals ("Windows")){
            this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\bullet2.png");                 // read pictures file in Windows environment
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/bullet2.png");                    // read pictures file in UNIX environment
        }
        this.width = 12;
        this.height = 12;
    }

    /**
     * This method shoots the EnemyType2Bullet
     * @param x X axis for the initial position of the bullet
     * @param y Y axis for the initial position of the bullet
     */
    public void shoot(int x, int y)	{
        if(!appearl && !appear && !appearr){
            this.x = x;
            this.y = y;
            appear = true;
            this.lx = x;
            this.ly = y;
            appearl = true;
            this.rx = x;
            this.ry = y;
            appearr = true;
            if(app.hostOS.equals ("Windows")){
                SoundEffects.Shoot2.play();               // play sound file in Windows environment
            }
            else if(app.hostOS.equals ("UNIXType")){
                SoundEffects.Shoot2UNIX.play();                  // play sound file in UNIX environment
            }
        }
    }

    /**
     * This method controls the movement of the EnemyType2Bullet
     */
    public void move() {
        if (appear) {
            y += 3;                                     //movement of the middle shot
            if (y > app.height)                         //checks if the middle shot gets out of the window
                appear = false;
        }
        if (appearl) {
            lx -= 1;
            ly += 3;
            if (lx < -12 || ly > app.height)             //checks if the left shot gets out of the window
                appearl = false;
        }
        if (appearr) {
            rx += 1;
            ry += 3;
            if (rx > app.width || ry < -12)              //checks if the right shot gets out of the window
                appearr = false;
        }
    }

    /**
     * This method draws the EnemyType2Bullet on the screen
     * @param g  the instance for the JFrame graphics
     */
    @Override
    public void draw(Graphics g) {
        if (appear && app.show)
            g.drawImage(img, x, y, app);
        if (appearl && app.show)
            g.drawImage(img, lx, ly, app);
        if (appearr && app.show)
            g.drawImage(img, rx, ry, app);
    }
}
