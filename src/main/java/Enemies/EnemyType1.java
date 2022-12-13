package Enemies;

import Control.*;
import Bullets.*;

import java.awt.*;


public class EnemyType1 extends Enemy{
    public EnemyType1Bullet bullet;     // the bullet of the EnemyType1
    public int index;                   // index of the enemy in the enemy array defined in the main app

    /**
     * This is the constructor method for the EnemyType1
     * @param applet the applet instance
     * @param index index of the enemy in the enemy array defined in the main app
     */
    public EnemyType1(Fort applet, int index) {
        this.app = applet;
        this.index = index;
        this.awardPoints = 10;
        this.damage = 30;
        this.collideP1 = false;
        this.collideP2 = false;
        this.x = (int)(Math.random() * (app.width - 40));                       // initialize the position
        this.y = -56;
        if(app.hostOS.equals ("Windows")){
            if(Math.random() <= 0.5)                                                                                      // read pictures file in Windows environment
                this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\enemy1blue.png");
            else
                this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\enemy1brown.png");
            this.effect = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\explosion.gif");
        }
        else if(app.hostOS.equals ("UNIXType")){
            if(Math.random() <= 0.5)                                                                                         // read pictures file in UNIX environment
                this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/enemy1blue.png");
            else
                this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/enemy1brown.png");
            this.effect = Toolkit.getDefaultToolkit().createImage("src/main/pictures/explosion.gif");
        }
        this.width = 40;
        this.height = 56;
        this.bullet = new EnemyType1Bullet(app);                               // instantiate Bullets.Bullet object
        this.shootingFrequency = 100;
        this.shootTimer = (int)(shootingFrequency * Math.random());
        this.alreadyHitTimer = 0;
    }

    /**
     * This method controls the movement of the EnemyType1
     */
    public void move() {
        if(index == 2 || index == 3) {
            if (this.shootTimer > 0)
                this.shootTimer--;
            else {
                bullet.shoot(x+16, y+28);
                this.shootTimer = (int)(shootingFrequency * Math.random());
            }
            bullet.move();
        }
        if(app.show){
            if(index == 1 || index == 3 ){
                y+=5;
            }
            else{
                y+=4;  // position is down
            }
        }
        if (y > app.height || !app.show || app.gameStage != Fort.Stage.GAME_PLAY) {     // if it is outside the frame or not gaming
            x = (int)(Math.random() * (app.width - 40));                                // initialize the position for the reborn enemy
            y = -56;
        }
    }

    /**
     * This method calls the collision detection and draws the EnemyType1 and its bullets
     * @param g  the instance for the JFrame graphics
     */
    public void draw(Graphics g) {
        if(index == 2 || index == 3){
            bullet.draw(g);
        }
        if(app.isE1hbP1B(index) || app.isE1hbP2B(index) ||
                collideP1 || collideP2)  {
            g.drawImage(effect, ex, ey, app);
            x = (int)(Math.random() * (app.width - 40));
            y = -56;
            collideP1 = false;
            collideP2 = false;
        }
        else {
            g.drawImage(img, x, y, app);
        }
    }

    /**
     * This method initiates the EnemyType1, using the default shooting frequency
     * @param g the instance for the JFrame graphics
     */
    public  void run(Graphics g) {
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method initiates the EnemyType1, with specified shooting frequency
     * @param g the instance for the JFrame graphics
     * @param f the shooting frequency of the EnemyType1
     */
    public  void run(Graphics g, int f) {
        this.shootingFrequency = f;
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method returns the horizontal positions of the bullet of the EnemyType1
     * @return the X of the bullet
     */
    public int getXbullet(){
        return   bullet.x;
    }

    /**
     * This method returns the vertical positions of the bullet of the EnemyType1
     * @return the Y of the bullet
     */
    public int getYbullet(){
        return bullet.y;
    }
}
