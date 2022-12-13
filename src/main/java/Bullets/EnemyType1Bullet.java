package Bullets;

import Control.*;

import java.awt.*;


// EnemyType1Bullet is the bullet used by EnemyType1, but it is also used by the Fortress
public class EnemyType1Bullet extends Bullet{

    /**
     * This is the constructor method for the EnemyType1Bullet
     * @param applet applet the applet instance
     */
    public EnemyType1Bullet(Fort applet) {
        this.app = applet;
        this.appear = false;
        this.damage = 20;
        if(app.hostOS.equals ("Windows")){
            this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\bullet_down.png");                 // read pictures file in Windows environment
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/bullet_down.png");                    // read pictures file in UNIX environment
        }
        this.width = 8;
        this.height = 28;
    }

    /**
     * This method shoots the EnemyType1Bullet
     * @param x X axis for the initial position of the bullet
     * @param y Y axis for the initial position of the bullet
     */
    public void shoot(int x, int y)	{
        if (!appear) {
            this.x = x;
            this.y = y;
            appear = true;
            if(app.hostOS.equals ("Windows")){
                SoundEffects.Shoot1.play();               // play sound file in Windows environment
            }
            else if(app.hostOS.equals ("UNIXType")){
                SoundEffects.Shoot1UNIX.play();                  // play sound file in UNIX environment
            }
        }
    }

    /**
     * This method controls the movement of the EnemyType1Bullet
     */
    public void move() {
        if (appear) {
            y += 8;
            if (y > app.height)              //if the bullet goes out of the window
                appear = false;
        }
    }
}
