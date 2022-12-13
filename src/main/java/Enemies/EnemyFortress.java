package Enemies;

import Control.*;
import Bullets.*;

import java.awt.*;


public class EnemyFortress extends Enemy{
    public Image imgHit;                // picture when the Fortress is hit
    public EnemyType2Bullet bullet1;    // the first bullet of the Fortress, being shot from the left wing of the Fortress
    public EnemyType2Bullet bullet2;    // the second bullet of the Fortress, being shot from the right wing of the Fortress
    public EnemyType1Bullet bullet3;    // the third bullet of the Fortress, being shot from the nose of the Fortress
    public int directionX;              // the horizontal direction of movement of the Fortress
    public int directionY;              // the vertical direction of movement of the Fortress
    public int counter;                 // used to count the first 175 cycles while the fortress gets out of the corner
    public int health;                  // the health of the fortress
    public int shootTimer1;             // the randomised shooting timer for bullets 1 and 2
    public int shootTimer2;             // the randomised shooting timer for bullet 3
    public boolean alreadyHitByP1;      // flag denoting collision with Bullets from Player 1, used for collision detection
    public boolean alreadyHitByP2;      // flag denoting collision with Bullets from Player 2, used for collision detection

    /**
     * This is the constructor method for the Fortress
     * @param applet the applet instance
     */
    public EnemyFortress(Fort applet) {
        this.app = applet;
        this.awardPoints = 50;
        this.damage = 50;
        this.collideP1 = false;
        this.collideP2 = false;
        this.x = app.width;                    // initialize the position
        this.y = -174;
        if(app.hostOS.equals ("Windows")){
            this.img = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\fortress.png");                 // read pictures file in Windows environment
            this.effect = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\explosion.gif");
            this.imgHit = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\fortresshit.png");
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img = Toolkit.getDefaultToolkit().createImage("src/main/pictures/fortress.png");                    // read pictures file in UNIX environment
            this.effect = Toolkit.getDefaultToolkit().createImage("src/main/pictures/explosion.gif");
            this.imgHit = Toolkit.getDefaultToolkit().createImage("src/main/pictures/fortresshit.png");
        }
        this.width = 100;
        this.height = 174;
        this.bullet1 = new EnemyType2Bullet(app);                               // instantiate Bullets.Bullet object
        this.bullet2 = new EnemyType2Bullet(app);
        this.bullet3 = new EnemyType1Bullet(app);
        this.directionX = -1;    //initialize the move direction X
        this.directionY = 1;     //initialize the move direction Y
        this.counter = 175;
        this.health = 100;
        this.shootingFrequency = 100;
        this.shootTimer1 = (int)(shootingFrequency * Math.random());
        this.shootTimer2 = (int)(shootingFrequency * Math.random() / 2);
        this.alreadyHitTimer = 0;
    }

    /**
     * This method controls the movement of the Fortress
     */
    public void move() {
        if (this.shootTimer1 > 0)
            this.shootTimer1--;
        else {
            bullet1.shoot(x+19, y+120);
            bullet2.shoot(x+69, y+120);
            this.shootTimer1 = (int)(shootingFrequency * Math.random());
        }
        if (this.shootTimer2 > 0)
            this.shootTimer2--;
        else {
            bullet3.shoot(x+46, y+146);
            this.shootTimer2 = (int)(shootingFrequency * Math.random() / 2);
        }
        bullet1.move();
        bullet2.move();
        bullet3.move();
        if(app.show){
            this.x+=directionX;
            this.y+=directionY;
            counter--;
            if(counter<=0) {
                if(x<=0) {                                      //bounces from left wall
                    directionX=1;
                }
                if(x>=app.width - 100) {                        //bounces from right wall
                    directionX=-1;
                }
                if(y<=0) {                                      //bounces from top
                    directionY=1;
                }
                if(y>=app.height - 174) {                       //bounces from bottom
                    directionY=-1;
                }
            }
        }
    }

    /**
     * This method calls the collision detection and draws the Fortress and its bullets
     * @param g  the instance for the JFrame graphics
     */
    public void draw(Graphics g) {
        bullet1.draw(g);
        bullet2.draw(g);
        bullet3.draw(g);
        //second draw boss based on hit or not hit status
        if(app.isFhbP1B() || app.isFhbP2B() ||
                collideP1 || collideP2){
            g.drawImage(imgHit, x, y, app);
            collideP1 = false;
            collideP2 = false;
            if(health <= 0){
                if(app.hostOS.equals ("Windows")){
                    SoundEffects.Explosion.play();               // play sound file in Windows environment
                }
                else if(app.hostOS.equals ("UNIXType")){
                    SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                }
                for(int e = 0; e < 100; e += 10){
                    g.drawImage(effect, x,y, app);
                    g.drawImage(effect, (x+e),y, app);
                    g.drawImage(effect, (x+e),(y+e), app);
                }
            }
        }
        else{
            g.drawImage(img, x, y, app);
        }
    }

    /**
     * This method initiates the Fortress, using the default shooting frequency
     * @param g the instance for the JFrame graphics
     */
    public  void run(Graphics g) {
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method initiates the Fortress, with specified shooting frequency
     * @param g the instance for the JFrame graphics
     * @param f the shooting frequency of the Fortress
     */
    public  void run(Graphics g, int f) {
        this.shootingFrequency = f;
        if(app.show)   {
            this.move();
            this.draw(g);
        }
    }

    /**
     * This method returns a boolean array, which denotes which shots from the first bullet of thr Fortress are still visible
     * @return the boolean array
     */
    public boolean[] isBullet1Visible(){
        return new boolean[]{bullet1.appearl, bullet1.appear, bullet1.appearr};
    }

    /**
     * This method returns a boolean array, which denotes which shots from the second bullet of thr Fortress are still visible
     * @return the boolean array
     */
    public boolean[] isBullet2Visible(){
        return new boolean[]{bullet2.appearl, bullet2.appear, bullet2.appearr};
    }

    /**
     * This method returns the vertical positions of the shots of the first bullet of the Fortress
     * @return the Y of the shots of the first bullet
     */
    public int[] getYbullet1(){
        return new int[]{bullet1.ly, bullet1.y, bullet1.ry};
    }

    /**
     * This method returns the horizontal positions of the shots of the first bullet of the Fortress
     * @return the X of the shots of the first bullet
     */
    public int[] getXbullet1() {
        return new int[]{bullet1.lx, bullet1.x, bullet1.rx};
    }

    /**
     * This method returns the vertical positions of the shots of the second bullet of the Fortress
     * @return the Y of the shots of the second bullet
     */
    public int[] getYbullet2(){
        return new int[]{bullet2.ly, bullet2.y, bullet2.ry};
    }

    /**
     * This method returns the horizontal positions of the shots of the second bullet of the Fortress
     * @return the X of the shots of the second bullet
     */
    public int[] getXbullet2() {
        return new int[]{bullet1.lx, bullet1.x, bullet1.rx};
    }

    /**
     * This method returns the vertical positions of the third bullet of the Fortress
     * @return the Y of the third bullet
     */
    public int getYbullet3(){
        return bullet3.y;
    }

    /**
     * This method returns the horizontal positions of the third bullet of the Fortress
     * @return the X of the third bullet
     */
    public int getXbullet3() {
        return   bullet3.x;
    }
}