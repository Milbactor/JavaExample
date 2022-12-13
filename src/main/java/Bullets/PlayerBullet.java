package Bullets;

import Control.*;

import java.awt.*;


// PlayerBullet is the bullet used by Player/Players
public class PlayerBullet extends Bullet{

    /**
     * This is the constructor method for the PlayerBullet
     * @param applet applet the applet instance
     */
    public PlayerBullet(Fort applet) {
        this.app = applet;
        this.appear = false;
        this.damage = 5;
        if(app.hostOS.equals ("Windows")){
            this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\bullet_up.png");                 // read pictures file in Windows environment
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/bullet_up.png");                    // read pictures file in UNIX environment
        }
        this.width = 8;
        this.height = 28;
    }

    /**
     * This method shoots the PlayerBullet
     * @param x X axis for the initial position of the bullet
     * @param y Y axis for the initial position of the bullet
     */
    public void shoot(int x, int y, int vx, int vy)	{
        if (!appear && app.player1.shootTimer > 30) {
            app.player1.shootTimer = 0;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
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
     * This method controls the movement of the PlayerBullet
     */
    public void move() {
        if (appear) {
            x += vx;
            y += vy;
            if (y<-28 || y>app.height){     //if out of the window
                appear = false;
            }
        }
    }
}
