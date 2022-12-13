package Players;
/**
 * Created with IntelliJ IDEA.
 * User: Hristo Dimitrov
 * Date: 10/15/12
 * Time: 2:15 AM
 * University Java Project - Fort!
 * Group members: Hristo Dimitrov, Nana Okamoto, Paul Clydesdale, Danne Connolly;
 */
import Control.*;
import Bullets.*;

import java.awt.*;


public class Player1 {
    public Fort app;				// applet
    public Image img;				// picture
    public Image imgHit;            // picture when the player is hit
    public int x, y;		        // position x,y
    public PlayerBullet bullet;	// the bullet of the player
    public boolean start;           // marks the initiation of the player
    public int width;               // the width of the player picture
    public int height;              // the height of the player picture
    public int health;              // the health of the player
    public int lives;               // the remaining lives of the player
    public int score;               // the current score
    public int previousScore;       // the score from previous level
    public int damage;              // the damage that the player causes when it collides with the Fortress
    public boolean isDead;          // dead flag used between the levels
    public boolean[] alreadyHitByE1 = new boolean[5];       // array of flags denoting collision with EnemyType1, used for collision detection
    public boolean[] alreadyHitByE1B = new boolean[5];      // array of flags denoting collision with EnemyType1Bullet, used for collision detection
    public boolean[] alreadyHitByE2 = new boolean[5];       // array of flags denoting collision with EnemyType2, used for collision detection
    public boolean[] alreadyHitByE2B = new boolean[5];      // array of flags denoting collision with EnemyType2Bullet, used for collision detection
    public boolean alreadyHitByF;   // flag denoting collision with EnemyFortress, used for collision detection
    public boolean alreadyHitByFB;  // flag denoting collision with Bullets from EnemyFortress, used for collision detection
    public int shootTimer;          // timer used for freezing shooting ability of the player, right after a bullet has been shot
    public int alreadyHitTimer;     // timer used for showing imgHit instead of img, after a collision has happened

    /**
     * This is the constructor method for the first player
     * @param applet the applet instance
     */
    public Player1(Fort applet) {
        this.app = applet;
        this.start = true;
        this.isDead = false;
        this.health = 100;
        this.lives = 15;
        this.damage = 5;
        this.score = 0;
        this.previousScore = 0;
        this.x = app.width / 2 - 16;                    // initialize the position for 1 player mode
        this.y = app.height - 42;
        if(app.hostOS.equals ("Windows")){
            this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\player.png");                 // read pictures file in Windows environment
            this.imgHit = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\playerhit.png");
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/player.png");                    // read pictures file in UNIX environment
            this.imgHit = Toolkit.getDefaultToolkit().createImage("src/main/pictures/playerhit.png");
        }
        this.width = 32;
        this.height = 46;
        this.bullet = new PlayerBullet(app);             // instantiate Bullets.Bullet object
        this.shootTimer = 0;
        this.alreadyHitTimer = 0;
    }

    /**
     * This method controls the movement of the first player
     */
    public void move() {
        bullet.move();                 // Bullets.Bullet movement
        if (Key.left) {                // in left direction
            x -= 4;
            if (x < 0) x = 0;
        }
        if (Key.right) {               // right direction
            x += 4;
            if (x > app.width - 32) x = app.width - 32;
        }
        if (Key.up) {                  // in up direction
            y -= 4;
            if (y < 0) y = 0;
        }
        if (Key.down) {                // down direction
            y += 4;
            if (y > app.height - 46 ) y = app.height - 46 ;
        }
        if (Key.control){
            bullet.shoot(x + 12, y, 0, -8);          // shoot the Bullets.Bullet
        }
        shootTimer++;
    }

    /**
     * this method calls the collision detection and draws the player and its bullets
     * @param g  the instance for the JFrame graphics
     */
    public void draw(Graphics g) {
        bullet.draw(g);         //bullet is secretly create here
        if(app.isP1hbE1(0) || app.isP1hbE1B(0) ||
                app.isP1hbE1(1) || app.isP1hbE1B(1) ||
                app.isP1hbE1(2) || app.isP1hbE1B(2) ||
                app.isP1hbE1(3) || app.isP1hbE1B(3) ||
                app.isP1hbE1(4) || app.isP1hbE1B(4) ||
                app.isP1hbE2(0) || app.isP1hbE2B(0) ||
                app.isP1hbE2(1) || app.isP1hbE2B(1) ||
                app.isP1hbE2(2) || app.isP1hbE2B(2) ||
                app.isP1hbE2(3) || app.isP1hbE2B(3) ||
                app.isP1hbE2(4) || app.isP1hbE2B(4) ||
                app.isP1hbF() || app.isP1hbFB())  {
            g.drawImage(imgHit, x, y, app);
        }
        else{
            g.drawImage(img, x, y, app);
        }
    }

    /**
     * this method initiates the first player
     * @param g the instance for the JFrame graphics
     */
    public void run(Graphics g){
        if(app.show) {                                        //if it is not game-over
            if(start) {                                       // det the correct position for the player to start, based on the game mode selected
                if(!app.multiplayerMode)  {
                    x = app.width / 2 - 16;                    // initialize the position for 1 player mode
                    y = app.height - 42;
                }
                else   {
                    x = app.width / 4 - 16;                    // initialize the position for 2 players mode
                    y = app.height - 42;
                }
                start = false;
            }
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method returns the horizontal position of the bullet of the first player
     * @return the X of the bullet
     */
    public int getXbullet(){
        return   bullet.x;
    }

    /**
     * This method returns the vertical position of the bullet of the first player
     * @return the Y of the bullet
     */
    public int getYbullet(){
        return bullet.y;
    }

    /**
     * This method returns the width of the first player picture
     * @return the width of the first player picture
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the first player
     * @return the height of the first player picture
     */
    public int getHeight() {
        return height;
    }
}
