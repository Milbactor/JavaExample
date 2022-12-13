package Enemies;

import Control.*;
import Bullets.*;

import java.awt.*;


public class EnemyType2 extends Enemy{
    public EnemyType2Bullet bullet;         // the bullet of the EnemyType2
    public int index;                       // index of the enemy in the enemy array defined in the main app
    public enum Direction {LEFT, RIGHT}     // the possible horizontal directions of EnemyType2
    public Direction enemyDirection;        // the horizontal direction of movement of the EnemyType2

    /**
     * This is the constructor method for the EnemyType2
     * @param applet the applet instance
     * @param index index of the enemy in the enemy array defined in the main app
     */
    public EnemyType2(Fort applet, int index) {
        this.app = applet;
        this.index = index;
        this.awardPoints = 20;
        this.damage = 40;
        this.collideP1 = false;
        this.collideP2 = false;
        if(Math.random()<=0.5)
            enemyDirection = Direction.RIGHT;
        else
            enemyDirection = Direction.LEFT;
        this.x = (int)(Math.random() * (app.width - 50));                       // initialize the position
        this.y = -62;
        double colorRandomizer = Math.random();
        if(app.hostOS.equals ("Windows")){
            if(colorRandomizer <= 0.33)                                                                                  // read pictures file in Windows environment
                this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\enemy2blue.png");
            else if(colorRandomizer <= 0.66)
                this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\enemy2green.png");
            else
                this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\enemy2purple.png");
            this.effect = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\explosion.gif");
        }
        else if(app.hostOS.equals ("UNIXType")){
            if(colorRandomizer <= 0.33)                                                                                    // read pictures file in UNIX environment
                this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/enemy2blue.png");
            else if(colorRandomizer <= 0.66)
                this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/enemy2green.png");
            else
                this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/enemy2purple.png");
            this.effect = Toolkit.getDefaultToolkit().createImage("src/main/pictures/explosion.gif");
        }
        this.width = 50;
        this.height = 62;
        this.bullet = new EnemyType2Bullet(app);                               // instantiate Bullets.Bullet object
        this.shootingFrequency = 100;
        this.shootTimer = (int)(shootingFrequency * Math.random());
        this.alreadyHitTimer = 0;
    }

    /**
     * This method controls the movement of the EnemyType2
     */
    public void move() {
        if (this.shootTimer > 0)
            this.shootTimer--;
        else {
            bullet.shoot(x+19, y+50);
            this.shootTimer = (int)(shootingFrequency * Math.random());
        }
        bullet.move();

        if(app.show){
            if (enemyDirection == Direction.LEFT)
                x-=1;
            else
                x+=1;
            if (x <= 0)
                enemyDirection = Direction.RIGHT;
            if (x >= app.width - 50)
                enemyDirection = Direction.LEFT;
            if(index == 1 || index == 3 ){
                y+=2;
            }
            else{
                y+=1;  // position is down
            }
        }
        if (y > app.height || !app.show || app.gameStage != Fort.Stage.GAME_PLAY) {     // if it is outside the frame or not gaming
            this.x = (int)(Math.random() * (app.width - 50));                           // initialize the position for the reborn enemy
            this.y = -62;
        }
    }

    /**
     * This method calls the collision detection and draws the EnemyType2 and its bullets
     * @param g  the instance for the JFrame graphics
     */
    public void draw(Graphics g) {
        bullet.draw(g);
        if(app.isE2hbP1B(index) || app.isE2hbP2B(index) ||
                collideP1 || collideP2)  {
            g.drawImage(effect, ex, ey, app);
            x = (int)(Math.random() * (app.width - 50));
            y = -62;
            collideP1 = false;
            collideP2 = false;
        }
        else {
            g.drawImage(img, x, y, app);
        }
    }

    /**
     * This method initiates the EnemyType2, using the default shooting frequency
     * @param g the instance for the JFrame graphics
     */
    public  void run(Graphics g) {
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method initiates the EnemyType2, with specified shooting frequency
     * @param g the instance for the JFrame graphics
     * @param f the shooting frequency of the EnemyType2
     */
    public  void run(Graphics g, int f) {
        this.shootingFrequency = f;
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method returns a boolean array, which denotes which shots from the bullet of thr EnemyType2 are still visible
     * @return the boolean array
     */
    public boolean[] isBulletVisible(){
        return new boolean[]{bullet.appearl, bullet.appear, bullet.appearr};
    }

    /**
     * This method returns the horizontal positions of the shots of the bullet of the EnemyType2
     * @return the X of the shots of the bullet
     */
    public int[] getXbullet(){
        return new int[]{bullet.lx, bullet.x, bullet.rx};
    }

    /**
     * This method returns the vertical positions of the shots of the bullet of the EnemyType2
     * @return the Y of the shots of the bullet
     */
    public int[] getYbullet(){
        return new int[]{bullet.ly, bullet.y, bullet.ry};
    }
}

