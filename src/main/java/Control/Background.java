package Control;

import java.awt.*;


public class Background {
    public Fort app;				                    // jpanel
    public Image[] img = new Image[10];					// background pictures
    public int x, y;                                    // position x,y

    /**
     * This is teh constructor method for the Control.Background object
     * @param applet     The applet instance
     */
    public Background(Fort applet){
        this.app = applet;
        this.x=y=0;        //initialize the position
        if(app.hostOS.equals ("Windows")){
            this.img[0] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background0.png");          // read pictures file in Windows environment
            this.img[1] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background1.png");
            this.img[2] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background2.png");
            this.img[3] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background3.png");
            this.img[4] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background4.png");
            this.img[5] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background5.png");
            this.img[6] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background6.png");
            this.img[7] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background7.png");
            this.img[8] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background8.png");
            this.img[9] = Toolkit.getDefaultToolkit().createImage("src\\main\\pictures\\background9.png");
        }
        else if(app.hostOS.equals ("UNIXType")){
            this.img[0] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background0.png");           // read pictures file in UNIX environment
            this.img[1] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background1.png");
            this.img[2] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background2.png");
            this.img[3] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background3.png");
            this.img[4] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background4.png");
            this.img[5] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background5.png");
            this.img[6] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background6.png");
            this.img[7] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background7.png");
            this.img[8] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background8.png");
            this.img[9] = Toolkit.getDefaultToolkit().createImage("src/main/pictures/background9.png");
        }
    }

    /**
     * This method draws the background
     * @param g    The <code>Graphics</code> context in which to paint
     */
    public void draw(Graphics g) {
        g.drawImage(img[app.level], x, y, app);
        g.drawImage(img[app.level], x, y-400, app);
        if(y==400)  y=0;                     //if scrolled to the bottom bring it back to the original location
    }

    /**
     * This method scrolls the background
     */
    public void move(){
        y+=2;
    }

    /**
     * This method runs the Control.Background
     * @param g    The <code>Graphics</code> context in which to paint
     */
    public  void run(Graphics g){
        this.draw(g);
        if(app.level != 0) this.move();
    }
}
