package Control;


import Enemies.EnemyFortress;
import Enemies.EnemyType1;
import Enemies.EnemyType2;
import Highscore.HighScore;
import Highscore.Register;
import Players.Player1;
import Players.Player2;

import javax.swing.*;
import java.awt.*;


public class Fort extends JPanel implements Runnable  {
    public Player1 player1;				// first player object
    public Player2 player2;             // second player object
    public EnemyType1[] enemyType1 = new EnemyType1[5];        // enemyType1 object
    public EnemyType2[] enemyType2 = new EnemyType2[5];        // enemyType2 object
    public EnemyFortress fortress;          // fortress object
    private volatile Thread gameThread;	    // game thread
    public int width, height;			    // applet width height
    public Background back;                 // the background object of the applet
    public enum Stage{START, GAME_PLAY, TRY_AGAIN, LEVEL_PASSED, GAME_OVER, YOU_WIN}      // all possible stages during the game
    public Stage gameStage = Stage.START;                                                 // the current game stage
    public int level;                   // game levels, they levels can be from 0 to 9
    public boolean alreadySet;          // used for  level increment
    public boolean show;                // used to make sure that objects are drawn only when needed
    public int[] levelTimerAims = {0,1000,1000,1000,1000,1000,1000,1000,1000,0};          // the time durations of the 8 levels (excluding level 9)
    public int levelTimer;              // the timer for the duration of the levels
    public boolean multiplayerMode = false;                     // the flag which denotes if the game is in Multiplayer mode
    public String hostOS;               // this variable stores the operating system of the host, used for correct file paths
    public boolean gameOver;             // this is a flag which indicates that the game is over
    public static int finalPlayer1Score;                // the final score of Player1 (after the game has finished)
    public static int finalPlayer2Score;                // the final score of Player2 (after the game has finished)
    public static boolean finalMultiplayerMode;         // the final mode of the game (after the game has finished)



    /**
     * This method initializes all the values when the application is started
     */
    public void init() {
        if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){          // check of the type of The host OS
            hostOS = "Windows";                             // user is running Windows OS
        }
        else if(System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0 ||
                System.getProperty("os.name").toLowerCase().indexOf("nix") >= 0 ||
                System.getProperty("os.name").toLowerCase().indexOf("nux") >= 0 ||
                System.getProperty("os.name").toLowerCase().indexOf("sunos") >= 0){
            hostOS = "UNIXType";                            // user is running UNIX type OS
        }
        else {
            System.out.println("Your OS is not supported!!");        // we would assume UNIX type OS in case the OS is not in the listed above
            hostOS = "UNIXType";                                     // however the application may not work if the OS is not from the listed above
        }
        width = 300;                                    //getting the applet size
        height = 400;
        back =  new Background(this);
        addKeyListener(new Key());                      //key listener
        requestFocus();                                 //request focus
        player1 = new Player1(this);                      //player object created
        player2 = new Player2(this);
        fortress = new EnemyFortress(this);
        for(int i=0; i<5; i++ ){
            enemyType1[i] = new EnemyType1(this, i);    //enemyType1 objects created
            enemyType2[i] = new EnemyType2(this, i);
        }

        //initialization of all necessary parameters in use
        gameOver = false;
        show = false;
        alreadySet = false;
        player1.alreadyHitByF = false;
        player1.alreadyHitByFB = false;
        player2.alreadyHitByF = false;
        player2.alreadyHitByFB = false;
        fortress.alreadyHitByP1 = false;
        fortress.alreadyHitByP2 = false;
        for(int i=0; i<5; i++ ){
            player1.alreadyHitByE1[i] = false;
            player1.alreadyHitByE1B[i] = false;
            player1.alreadyHitByE2[i] = false;
            player1.alreadyHitByE2B[i] = false;

            player2.alreadyHitByE1[i] = false;
            player2.alreadyHitByE1B[i] = false;
            player2.alreadyHitByE2[i] = false;
            player2.alreadyHitByE2B[i] = false;
        }
        level = 0;
        gameStage = Stage.START;
        levelTimer = 0;
    }

    /**
     * This method starts the game thread
     */
    public void start() {
        if(gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * This method stops the game thread
     */
    public void stop() {
        gameThread = null;
    }

    /**
     * This method runs the game
     */
    public void run() {
        while (true) {                          //gameThread == Thread.currentThread()
            repaint();                          //re-draw
            try {                               //waining 20 seconds
                Thread.sleep(19);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * This methods draws the components of the game
     * @param g   The <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try{
            back.run(g);              //background start!!!
        }
        catch (NullPointerException e){
            System.out.print(e);
        }

        //title display
        //the commented-out code was used for test purposes to restart the last level after GAME OVER
        if(gameStage == Stage.START){// || (Control.Key.enter && gameStage == Stage.GAME_OVER )){
            show = false;
//            if(gameStage == Stage.GAME_OVER) {
//                gameStage = Stage.START;
//                player1.score = 0;
//                if(multiplayerMode)
//                    player2.score = 0;
//                player1.health = 100;
//                player1.lives = 5;
//                if (multiplayerMode){
//                    player2.health = 100;
//                    player2.lives = 5;
//                }
//            }
            if(Key.two)  {
                multiplayerMode = true;
            }
            if(Key.one)  {
                multiplayerMode = false;
            }

            g.setColor(Color.cyan);                                   //START screen
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Space Shooter", 50, 180);
            g.drawString("Fort!", 120, 220);

            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.setColor(Color.green);
            if(!multiplayerMode)
            g.drawString("Game mode: 1 Player", 75, 340);
            else
                g.drawString("Game mode: 2 Players", 73, 340);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.yellow);
            g.drawString("Press ENTER to start", 55, 370);
        }

        if ( Key.enter && gameStage == Stage.START || gameStage == Stage.GAME_PLAY || (Key.enter && gameStage == Stage.LEVEL_PASSED) || (Key.enter && gameStage == Stage.TRY_AGAIN) )
        {
            if(level == 0)
                level = 1;               //start with level 1

            //make objects visual if gaming
            show = gameStage == Stage.GAME_PLAY;

            //display statistics
            if(!multiplayerMode) {                                                   //statistics in one player mode
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString("Level : " + String.valueOf(level), 10, 20);
                g.drawString("SCORE : " + String.valueOf(player1.score), 10, 40);
                g.drawString("Health : " + String.valueOf(player1.health + "%"), 110, 20);
                g.drawString("Lives left: " + String.valueOf(player1.lives), 230, 20);
                g.drawString("Timer: " + String.valueOf(levelTimer), 230, 40);
            }
            else  {                                                                 //statistics in 2 players mode
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString("Timer: " + String.valueOf(levelTimer), 220, 20);
                g.drawString("Level : " + String.valueOf(level), 10, 20);

                g.drawString("PLAYER 1 ", 10, 40);
                g.drawString("Score: " + String.valueOf(player1.score), 10, 60);
                g.drawString("Health: " + String.valueOf(player1.health + "%"), 10, 80);
                g.drawString("Lives left: " + String.valueOf(player1.lives), 10, 100);

                g.drawString("PLAYER 2 ", 220, 40);
                g.drawString("Score: " + String.valueOf(player2.score), 220, 60);
                g.drawString("Health: " + String.valueOf(player2.health + "%"), 220, 80);
                g.drawString("Lives left: " + String.valueOf(player2.lives), 220, 100);
            }

            levelTimer++;           //every cycle the timer is incremented

            player1.run(g);             //player 1 start!!!!!
            if(multiplayerMode)         // this initialization is only performed in Multiplayer mode
                player2.run(g);         //player 2 start!!!!!

            //enemyType1 objects differ when to be used depending on each level..
            switch (level)  {
                case 1:  //two same enemyType1 comes without any bullets
                    enemyType1[0].run(g,150);          //enemies start!!!
                    enemyType1[1].run(g,150);
                    break;
                case 2:  //two normal enemyType1 one bullet enemyType1
                    enemyType1[0].run(g,130);          //normal enemies start!!!
                    enemyType1[1].run(g,130);
                    enemyType1[2].run(g,130);          //enemyType1 with bullet
                    break;
                case 3:  //one normal enemyType1 and one bullet enemyType1 and one enemyType2
                    enemyType1[0].run(g,100);
                    enemyType1[1].run(g,100);
                    enemyType2[0].run(g,150);
                    break;
                case 4:
                    enemyType1[2].run(g,80);
                    enemyType2[0].run(g,130);
                    enemyType2[1].run(g,130);
                    break;
                case 5:
                    enemyType1[2].run(g,70);
                    enemyType2[0].run(g,110);
                    enemyType2[1].run(g,110);
                    enemyType2[3].run(g,110);
                    break;
                case 6:
                    enemyType1[1].run(g,70);
                    enemyType1[2].run(g,70);
                    enemyType2[0].run(g,110);
                    enemyType2[1].run(g,110);
                    enemyType2[3].run(g,110);
                    break;
                case 7:  //two bullets enemyType1 and 2 enemyType2
                    enemyType1[3].run(g,60);
                    enemyType1[1].run(g,60);
                    enemyType2[0].run(g,90);
                    enemyType2[1].run(g,90);
                    break;
                case 8:  //two bullets enemyType1 and 2 enemyType2
                    enemyType1[1].run(g,40);
                    enemyType2[0].run(g,60);
                    enemyType2[1].run(g,60);
                    break;
                case 9:  //fortress class is debugged
                    fortress.run(g,100);          //fortress start!!!
                    g.drawString("Fortress: ", 148, 400);
                    g.setColor(Color.GREEN);
                    g.fillRect(200,390,fortress.health,10);
                    g.setColor(Color.RED);
                    g.fillRect(200 + fortress.health,390,100 - fortress.health,10);
                    break;
            }
            if(gameStage == Stage.TRY_AGAIN)  {       // when you enter Try Again stage, the last level is reset from the beginning
                levelTimer = 0;
                player1.score = player1.previousScore;
                if(multiplayerMode)
                    player2.score = player2.previousScore;
                player1.health = 100;
                if (multiplayerMode)
                    player2.health = 100;
                player1.isDead = false;
                if (multiplayerMode){
                    player2.isDead = false;
                }
            }
            if(gameStage == Stage.LEVEL_PASSED)   {      // when you pass a level you go to the next level, your health is reset and your score is kept
                if(!alreadySet){
                    alreadySet = true;
                    level++;
                    player1.previousScore = player1.score;
                    if(multiplayerMode)
                        player2.previousScore = player2.score;
                }
                player1.health = 100;
                if (multiplayerMode)
                    player2.health = 100;
                alreadySet = false;
            }
            gameStage = Stage.GAME_PLAY;
        }

        try{
            if(!multiplayerMode){            //when in single player mode, you enter Try Again or Game Over stage if player1 runs out of health
                if(player1.health <= 0 || gameStage == Stage.TRY_AGAIN || gameStage == Stage.GAME_OVER){    //Player dead or game over
                    show =false;
                    player1.start = true;
                    if(gameStage != Stage.GAME_OVER)
                        gameStage = Stage.TRY_AGAIN;
                    if(gameStage == Stage.TRY_AGAIN ){
                        if(player1.health <= 0) {
                            if(!player1.isDead) {
                                if(player1.lives > 0) {
                                    player1.lives--;
                                }
                                else{
                                    gameStage = Stage.GAME_OVER;
                                    finalPlayer1Score = player1.score;
                                    finalMultiplayerMode = multiplayerMode;
                                    new Register();
                                }
                                player1.isDead = true;
                            }
                        }
                    }
                    g.setFont(new Font("Arial", Font.BOLD, 28));            //GAME OVER screen in single player mode
                    if(gameStage == Stage.GAME_OVER) {
                        g.setColor(Color.red);
                        g.drawString("GAME OVER!", 60, 55);
                        g.setColor(Color.orange);
                        g.setFont(new Font("Arial", Font.BOLD, 12));
                        g.drawString("Name    Score                      Date", 20, 100);
                        g.setFont(new Font("Arial", Font.PLAIN, 12));
                        String str2array = HighScore.scorePrint;
                        String[] arr = str2array.split("\n");
                        for(int i=0;i<arr.length;i++){
                            g.drawString(arr[i], 10, 130+(20*i));
                        }
                    }
                    if(gameStage == Stage.TRY_AGAIN){                       //TRY AGAIN screen in single player mode
                        g.setColor(Color.green);
                        g.drawString("TRY AGAIN", 73, 180);
                        g.setFont(new Font("Arial", Font.PLAIN, 20));
                        g.setColor(Color.yellow);
                        g.drawString("Press ENTER to continue...", 35, 370);
                    }
                }
            }
            else{            //when in multiplayer mode, you enter Try Again or Game Over stage if player1 or player1 runs out of health
                if(player1.health <= 0 || player2.health <= 0 || gameStage == Stage.TRY_AGAIN || gameStage == Stage.GAME_OVER){ //Player dead or game over
                    show =false;
                    player1.start = true;
                    player2.start = true;
                    if(gameStage != Stage.GAME_OVER)
                        gameStage = Stage.TRY_AGAIN;
                    if(gameStage == Stage.TRY_AGAIN){
                        if(player1.health <= 0) {
                            if(!player1.isDead) {
                                if(player1.lives > 0) {
                                    player1.lives--;
                                }
                                else{
                                    gameStage = Stage.GAME_OVER;
                                    finalPlayer1Score = player1.score;
                                    finalPlayer2Score = player2.score;
                                    finalMultiplayerMode = multiplayerMode;
                                    new Register();
                                }
                                player1.isDead = true;
                            }
                        }
                    }
                    if(gameStage == Stage.TRY_AGAIN){
                        if(player2.health <= 0) {
                            if(!player2.isDead) {
                                if(player2.lives > 0) {
                                    player2.lives--;
                                }
                                else{
                                    gameStage = Stage.GAME_OVER;
                                    finalPlayer1Score = player1.score;
                                    finalPlayer2Score = player2.score;
                                    finalMultiplayerMode = multiplayerMode;
                                    new Register();
                                }
                                player2.isDead = true;
                            }
                        }
                    }
                    g.setFont(new Font("Arial", Font.BOLD, 28));           //GAME OVER screen in multiplayer mode
                    if(gameStage == Stage.GAME_OVER) {
                        g.setColor(Color.red);
                        g.drawString("GAME OVER!", 60, 55);
                        g.setColor(Color.orange);
                        g.setFont(new Font("Arial", Font.BOLD, 12));
                        g.drawString("Name    Score                      Date", 20, 100);
                        g.setFont(new Font("Arial", Font.PLAIN, 12));
                        String str2array = HighScore.scorePrint;
                        String[] arr = str2array.split("\n");
                        for(int i=0;i<arr.length;i++){
                            g.drawString(arr[i], 10, 130+(20*i));
                        }
                    }
                    if(gameStage == Stage.TRY_AGAIN){                      //TRY AGAIN screen in multiplayer mode
                        g.setColor(Color.green);
                        g.drawString("TRY AGAIN", 73, 180);
                        g.setFont(new Font("Arial", Font.PLAIN, 20));
                        g.setColor(Color.yellow);
                        g.drawString("Press ENTER to continue...", 35, 370);
                    }
                }
            }
        }
        catch (NullPointerException e){          // At the beginning of the game, sometimes a NullPointer exception is thrown, because the gameStage is still Null
            System.out.print(e);
        }
        if((levelTimer >= levelTimerAims[level] && level != 0 && level != 9) || gameStage == Stage.LEVEL_PASSED)  {   //level passed
            gameStage = Stage.LEVEL_PASSED;
            levelTimer = 0;
            g.setColor(Color.blue);                                            //LEVEL PASSED screen
            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Level Passed", 64, 180);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.yellow);
            g.drawString("Press ENTER to continue...", 35, 370);
        }

        try{
            if((fortress.health <= 0 && level == 9 )|| gameStage == Stage.YOU_WIN){//change back to fortress level 6 later
                gameStage = Stage.YOU_WIN;
                if(!gameOver){
                    if(this.hostOS.equals ("Windows")){
                        SoundEffects.Applause.play();               // play sound file in Windows environment
                    }
                    else if(this.hostOS.equals ("UNIXType")){
                        SoundEffects.ApplauseUNIX.play();                  // play sound file in UNIX environment
                    }
                    finalPlayer1Score = player1.score;
                    if(multiplayerMode)
                        finalPlayer2Score = player2.score;
                    finalMultiplayerMode = multiplayerMode;
                    new Register();
                    gameOver = true;
                }
                g.setColor(Color.orange);                                       //YOU WIN screen
                g.setFont(new Font("Arial", Font.BOLD, 28));
                g.drawString("You WIN!!!", 85, 55);
                g.setColor(Color.orange);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString("Name    Score                      Date", 20, 100);
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                String str2array = HighScore.scorePrint;
                String[] arr = str2array.split("\n");
                for(int i=0;i<arr.length;i++){
                    g.drawString(arr[i], 10, 130+(20*i));
                }
            }
        }
        catch (NullPointerException e){
            System.out.print(e);
        }
    }

    /**
     * Calls <code>paint</code>.  Doesn't clear the background but see
     * <code>ComponentUI.update</code>, which is called by
     * <code>paintComponent</code>.
     * @param g   The <code>Graphics</code> context in which to paint
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * This method returns  the highest score achieved during the game.
     * @return The score in points
     */
    public static int TrackScore(){
        if(finalMultiplayerMode){
            if(finalPlayer1Score < finalPlayer2Score)
                return finalPlayer2Score;
        }
        return finalPlayer1Score;
    }

    //COLLISION DETECTION METHODS!!!

    //Player 1----------------------------------------------------------------------------------------------------------
    /**
     * This method checks if<code>Player 1</code> collides with <code>EnemyType1</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>Player 1</code> collides and false if not
     */
    public boolean isP1hbE1(int enemyIndex) {
        if (player1.x > (enemyType1[enemyIndex].x - player1.getWidth()) &&
                player1.x < (enemyType1[enemyIndex].x + enemyType1[enemyIndex].getWidth()) &&
                player1.y > (enemyType1[enemyIndex].y - player1.getHeight()) &&
                player1.y < (enemyType1[enemyIndex].y + enemyType1[enemyIndex].getHeight())) {
            if(!player1.alreadyHitByE1[enemyIndex]) {
                player1.health -= enemyType1[enemyIndex].damage;
                player1.alreadyHitByE1[enemyIndex] = true;
                enemyType1[enemyIndex].collideP1 = true;
                player1.alreadyHitTimer = 0;
                enemyType1[enemyIndex].alreadyHitTimer = 0;
                enemyType1[enemyIndex].ex = enemyType1[enemyIndex].x;
                enemyType1[enemyIndex].ey = enemyType1[enemyIndex].y;
                if(this.hostOS.equals ("Windows")){
                    SoundEffects.Explosion.play();               // play sound file in Windows environment
                }
                else if(this.hostOS.equals ("UNIXType")){
                    SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                }
            }
            return true;
        }
        if(enemyType1[enemyIndex].alreadyHitTimer < 15){
            enemyType1[enemyIndex].alreadyHitTimer++;
            enemyType1[enemyIndex].collideP1 = true;
        }
        if(player1.alreadyHitTimer < 10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByE1[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 1</code> is hit from <code>EnemyType1Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>Player 1</code> is hit and false if not
     */
    public boolean isP1hbE1B(int enemyIndex) {
        if(enemyType1[enemyIndex].bullet.appear)
            if (player1.x>(enemyType1[enemyIndex].getXbullet() - player1.getWidth()) &&
                    player1.x < (enemyType1[enemyIndex].getXbullet() + enemyType1[enemyIndex].bullet.getWidth()) &&
                    player1.y > (enemyType1[enemyIndex].getYbullet() - player1.getHeight()) &&
                    player1.y < (enemyType1[enemyIndex].getYbullet() + enemyType1[enemyIndex].bullet.getHeight())) {
                if(!player1.alreadyHitByE1B[enemyIndex]) {
                    enemyType1[enemyIndex].bullet.appear = false;
                    player1.health -= enemyType1[enemyIndex].bullet.damage;
                    player1.alreadyHitByE1B[enemyIndex] = true;
                    player1.alreadyHitTimer = 0;
                }
                return true;
            }
        if(player1.alreadyHitTimer <10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByE1B[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 1</code> collides with <code>EnemyType2</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>Player 1</code> collides and false if not
     */
    public boolean isP1hbE2(int enemyIndex) {
        if (player1.x > (enemyType2[enemyIndex].x - player1.getWidth()) &&
                player1.x < (enemyType2[enemyIndex].x + enemyType2[enemyIndex].getWidth()) &&
                player1.y > (enemyType2[enemyIndex].y - player1.getHeight()) &&
                player1.y < (enemyType2[enemyIndex].y + enemyType2[enemyIndex].getHeight())) {
            if(!player1.alreadyHitByE2[enemyIndex]) {
                player1.health -= enemyType2[enemyIndex].damage;
                player1.alreadyHitByE2[enemyIndex] = true;
                enemyType2[enemyIndex].collideP1 = true;
                player1.alreadyHitTimer = 0;
                enemyType2[enemyIndex].alreadyHitTimer = 0;
                enemyType2[enemyIndex].ex = enemyType2[enemyIndex].x;
                enemyType2[enemyIndex].ey = enemyType2[enemyIndex].y;
                if(this.hostOS.equals ("Windows")){
                    SoundEffects.Explosion.play();               // play sound file in Windows environment
                }
                else if(this.hostOS.equals ("UNIXType")){
                    SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                }
            }
            return true;
        }
        if(enemyType2[enemyIndex].alreadyHitTimer < 15){
            enemyType2[enemyIndex].alreadyHitTimer++;
            enemyType2[enemyIndex].collideP1 = true;
        }
        if(player1.alreadyHitTimer <10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByE2[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 1</code> is hit from <code>EnemyType2Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>Player 1</code> is hit and false if not
     */
    public boolean isP1hbE2B(int enemyIndex) {
        for(int i=0; i<3; i++){
            if (enemyType2[enemyIndex].isBulletVisible()[i]){
                if (player1.x > (enemyType2[enemyIndex].getXbullet()[i] - player1.getWidth()) &&
                        player1.x < (enemyType2[enemyIndex].getXbullet()[i] + enemyType2[enemyIndex].bullet.getWidth()) &&
                        player1.y > (enemyType2[enemyIndex].getYbullet()[i] - player1.getHeight()) &&
                        player1.y < (enemyType2[enemyIndex].getYbullet()[i] + enemyType2[enemyIndex].bullet.getHeight())) {
                    if(!player1.alreadyHitByE2B[enemyIndex]) {
                        switch (i){
                            case 0:
                                enemyType2[enemyIndex].bullet.appearl = false;
                                break;
                            case 1:
                                enemyType2[enemyIndex].bullet.appear = false;
                                break;
                            case 2:
                                enemyType2[enemyIndex].bullet.appearr = false;
                                break;
                        }
                        player1.health -= enemyType2[enemyIndex].bullet.damage;
                        player1.alreadyHitByE2B[enemyIndex] = true;
                        player1.alreadyHitTimer = 0;
                    }
                    return true;
                }
            }
        }
        if(player1.alreadyHitTimer <10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByE2B[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 1</code> collides with <code>EnemyFortress</code>
     * @return     True if the <code>Player 1</code> collides and false if not
     */
    public boolean isP1hbF() {
        if (player1.x > (fortress.x - player1.getWidth()) &&
                player1.x < (fortress.x + fortress.getWidth()) &&
                player1.y > (fortress.y - player1.getHeight()) &&
                player1.y < (fortress.y + fortress.getHeight())) {
            if(!player1.alreadyHitByF) {
                fortress.health -= player1.damage;
                player1.health -= fortress.damage;
                player1.alreadyHitByF = true;
                fortress.collideP1 = true;
                player1.alreadyHitTimer = 0;
            }
            return true;
        }
        if(player1.alreadyHitTimer <10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByF = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 1</code> is hit from bullet of <code>EnemyFortress</code>
     * @return     True if the <code>Player 1</code> is hit and false if not
     */
    public boolean isP1hbFB() {
        for(int i=0; i<3; i++){
            if (fortress.isBullet1Visible()[i]){
                if (player1.x > (fortress.getXbullet1()[i] - player1.getWidth()) &&
                        player1.x < (fortress.getXbullet1()[i] + fortress.bullet1.getWidth()) &&
                        player1.y > (fortress.getYbullet1()[i] - player1.getHeight()) &&
                        player1.y < (fortress.getYbullet1()[i] + fortress.bullet1.getHeight())) {
                    if(!player1.alreadyHitByFB) {
                        switch (i){
                            case 0:
                                fortress.bullet1.appearl = false;
                                break;
                            case 1:
                                fortress.bullet1.appear = false;
                                break;
                            case 2:
                                fortress.bullet1.appearr = false;
                                break;
                        }
                        player1.health -= fortress.bullet1.damage;
                        player1.alreadyHitByFB = true;
                        player1.alreadyHitTimer = 0;
                    }
                    return true;
                }
            }
        }
        for(int i=0; i<3; i++){
            if (fortress.isBullet2Visible()[i]){
                if (player1.x > (fortress.getXbullet2()[i] - player1.getWidth()) &&
                        player1.x < (fortress.getXbullet2()[i] + fortress.bullet2.getWidth()) &&
                        player1.y > (fortress.getYbullet2()[i] - player1.getHeight()) &&
                        player1.y < (fortress.getYbullet2()[i] + fortress.bullet2.getHeight())) {
                    if(!player1.alreadyHitByFB) {
                        switch (i){
                            case 0:
                                fortress.bullet2.appearl = false;
                                break;
                            case 1:
                                fortress.bullet2.appear = false;
                                break;
                            case 2:
                                fortress.bullet2.appearr = false;
                                break;
                        }
                        player1.health -= fortress.bullet2.damage;
                        player1.alreadyHitByFB = true;
                        player1.alreadyHitTimer = 0;
                    }
                    return true;
                }
            }
        }
        if(fortress.bullet3.appear)
            if (player1.x > (fortress.getXbullet3() - player1.getWidth()) &&
                    player1.x < (fortress.getXbullet3() + fortress.bullet3.getWidth()) &&
                    player1.y > (fortress.getYbullet3() - player1.getHeight()) &&
                    player1.y < (fortress.getYbullet3() + fortress.bullet3.getHeight())) {
                if(!player1.alreadyHitByFB) {
                    fortress.bullet3.appear = false;
                    player1.health -= fortress.bullet3.damage;
                    player1.alreadyHitByFB = true;
                    player1.alreadyHitTimer = 0;
                }
                return true;
            }
        if(player1.alreadyHitTimer <10 ){
            player1.alreadyHitTimer++;
            return true;
        }
        player1.alreadyHitByFB = false;
        return false;
    }

    //Player 2----------------------------------------------------------------------------------------------------------
    /**
     * This method checks if<code>Player 2</code> collides with <code>EnemyType1</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>Player 2</code> collides and false if not
     */
    public boolean isP2hbE1(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if (player2.x > (enemyType1[enemyIndex].x - player2.getWidth()) &&
                    player2.x < (enemyType1[enemyIndex].x + enemyType1[enemyIndex].getWidth()) &&
                    player2.y > (enemyType1[enemyIndex].y - player2.getHeight()) &&
                    player2.y < (enemyType1[enemyIndex].y + enemyType1[enemyIndex].getHeight())) {
                if(!player2.alreadyHitByE1[enemyIndex]) {
                    player2.health -= enemyType1[enemyIndex].damage;
                    player2.alreadyHitByE1[enemyIndex] = true;
                    enemyType1[enemyIndex].collideP2 = true;
                    player2.alreadyHitTimer = 0;
                    enemyType1[enemyIndex].alreadyHitTimer = 0;
                    enemyType1[enemyIndex].ex = enemyType1[enemyIndex].x;
                    enemyType1[enemyIndex].ey = enemyType1[enemyIndex].y;
                    if(this.hostOS.equals ("Windows")){
                        SoundEffects.Explosion.play();               // play sound file in Windows environment
                    }
                    else if(this.hostOS.equals ("UNIXType")){
                        SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                    }
                }
                return true;
            }
        if(enemyType1[enemyIndex].alreadyHitTimer < 15){
            enemyType1[enemyIndex].alreadyHitTimer++;
            enemyType1[enemyIndex].collideP2 = true;
        }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByE1[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 2</code> is hit from <code>EnemyType1Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>Player 2</code> is hit and false if not
     */
    public boolean isP2hbE1B(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if(enemyType1[enemyIndex].bullet.appear)
                if (player2.x > (enemyType1[enemyIndex].getXbullet() - player2.getWidth()) &&
                        player2.x < (enemyType1[enemyIndex].getXbullet() + enemyType1[enemyIndex].bullet.getWidth()) &&
                        player2.y > (enemyType1[enemyIndex].getYbullet() - player2.getHeight()) &&
                        player2.y < (enemyType1[enemyIndex].getYbullet() + enemyType1[enemyIndex].bullet.getHeight())) {
                    if(!player2.alreadyHitByE1B[enemyIndex]) {
                        enemyType1[enemyIndex].bullet.appear = false;
                        player2.health -= enemyType1[enemyIndex].bullet.damage;
                        player2.alreadyHitByE1B[enemyIndex] = true;
                        player2.alreadyHitTimer = 0;
                    }
                    return true;
                }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByE1B[enemyIndex] = true;
        return false;
    }

    /**
     * This method checks if the <code>Player 2</code> collides with <code>EnemyType2</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>Player 2</code> collides and false if not
     */
    public boolean isP2hbE2(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if (player2.x > (enemyType2[enemyIndex].x - player2.getWidth()) &&
                    player2.x < (enemyType2[enemyIndex].x + enemyType2[enemyIndex].getWidth()) &&
                    player2.y > (enemyType2[enemyIndex].y - player2.getHeight()) &&
                    player2.y < (enemyType2[enemyIndex].y + enemyType2[enemyIndex].getHeight())) {
                if(!player2.alreadyHitByE2[enemyIndex]) {
                    player2.health -= enemyType2[enemyIndex].damage;
                    player2.alreadyHitByE2[enemyIndex] = true;
                    enemyType2[enemyIndex].collideP2 = true;
                    player2.alreadyHitTimer = 0;
                    enemyType2[enemyIndex].alreadyHitTimer = 0;
                    enemyType2[enemyIndex].ex = enemyType2[enemyIndex].x;
                    enemyType2[enemyIndex].ey = enemyType2[enemyIndex].y;
                    if(this.hostOS.equals ("Windows")){
                        SoundEffects.Explosion.play();               // play sound file in Windows environment
                    }
                    else if(this.hostOS.equals ("UNIXType")){
                        SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                    }
                }
                return true;
            }
        if(enemyType2[enemyIndex].alreadyHitTimer < 15){
            enemyType2[enemyIndex].alreadyHitTimer++;
            enemyType2[enemyIndex].collideP2 = true;
        }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByE2[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 2</code> is hit from <code>EnemyType2Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>Player 2</code> is hit and false if not
     */
    public boolean isP2hbE2B(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            for(int i=0; i<3; i++){
                if (enemyType2[enemyIndex].isBulletVisible()[i]){
                    if (player2.x > (enemyType2[enemyIndex].getXbullet()[i] - player2.getWidth()) &&
                            player2.x < (enemyType2[enemyIndex].getXbullet()[i] + enemyType2[enemyIndex].bullet.getWidth()) &&
                            player2.y > (enemyType2[enemyIndex].getYbullet()[i] - player2.getHeight()) &&
                            player2.y < (enemyType2[enemyIndex].getYbullet()[i] + enemyType2[enemyIndex].bullet.getHeight())) {
                        if(!player2.alreadyHitByE2B[enemyIndex]) {
                            switch (i){
                                case 0:
                                    enemyType2[enemyIndex].bullet.appearl = false;
                                    break;
                                case 1:
                                    enemyType2[enemyIndex].bullet.appear = false;
                                    break;
                                case 2:
                                    enemyType2[enemyIndex].bullet.appearr = false;
                                    break;
                            }
                            player2.health -= enemyType2[enemyIndex].bullet.damage;
                            player2.alreadyHitByE2B[enemyIndex] = true;
                            player2.alreadyHitTimer = 0;
                        }
                        return true;
                    }
                }
            }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByE2B[enemyIndex] = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 2</code> collides with <code>EnemyFortress</code>
     * @return     True if the <code>Player 2</code> collides and false if not
     */
    public boolean isP2hbF() {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if (player2.x > (fortress.x - player2.getWidth()) &&
                    player2.x < (fortress.x + fortress.getWidth()) &&
                    player2.y > (fortress.y - player2.getHeight()) &&
                    player2.y < (fortress.y + fortress.getHeight())) {
                if(!player2.alreadyHitByF) {
                    fortress.health -= player2.damage;
                    player2.health -= fortress.damage;
                    player2.alreadyHitByF = true;
                    fortress.collideP2 = true;
                    player2.alreadyHitTimer = 0;
                }
                return true;
            }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByF = false;
        return false;
    }

    /**
     * This method checks if the <code>Player 2</code> is hit from bullet of <code>EnemyFortress</code>
     * @return     True if the <code>Player 2</code> is hit and false if not
     */
    public boolean isP2hbFB() {
        if(multiplayerMode){                 // this check is only performed in Multiplayer mode
            for(int i=0; i<3; i++){
                if (fortress.isBullet1Visible()[i]){
                    if (player2.x > (fortress.getXbullet1()[i] - player2.getWidth()) &&
                            player2.x < (fortress.getXbullet1()[i] + fortress.bullet1.getWidth()) &&
                            player2.y > (fortress.getYbullet1()[i] - player2.getHeight()) &&
                            player2.y < (fortress.getYbullet1()[i] + fortress.bullet1.getHeight())) {
                        if(!player2.alreadyHitByFB) {
                            switch (i){
                                case 0:
                                    fortress.bullet1.appearl = false;
                                    break;
                                case 1:
                                    fortress.bullet1.appear = false;
                                    break;
                                case 2:
                                    fortress.bullet1.appearr = false;
                                    break;
                            }
                            player2.health -= fortress.bullet1.damage;
                            player2.alreadyHitByFB = true;
                            player2.alreadyHitTimer = 0;
                        }
                        return true;
                    }
                }
            }
            for(int i=0; i<3; i++){
                if (fortress.isBullet2Visible()[i]){
                    if (player2.x > (fortress.getXbullet2()[i] - player2.getWidth()) &&
                            player2.x < (fortress.getXbullet2()[i] + fortress.bullet2.getWidth()) &&
                            player2.y > (fortress.getYbullet2()[i] - player2.getHeight()) &&
                            player2.y < (fortress.getYbullet2()[i] + fortress.bullet2.getHeight())) {
                        if(!player2.alreadyHitByFB) {
                            switch (i){
                                case 0:
                                    fortress.bullet2.appearl = false;
                                    break;
                                case 1:
                                    fortress.bullet2.appear = false;
                                    break;
                                case 2:
                                    fortress.bullet2.appearr = false;
                                    break;
                            }
                            player2.health -= fortress.bullet2.damage;
                            player2.alreadyHitByFB = true;
                            player2.alreadyHitTimer = 0;
                        }
                        return true;
                    }
                }
            }
            if(fortress.bullet3.appear)
                if (player2.x > (fortress.getXbullet3() - player2.getWidth()) &&
                        player2.x < (fortress.getXbullet3() + fortress.bullet3.getWidth()) &&
                        player2.y > (fortress.getYbullet3() - player2.getHeight()) &&
                        player2.y < (fortress.getYbullet3() + fortress.bullet3.getHeight())) {
                    if(!player2.alreadyHitByFB) {
                        fortress.bullet3.appear = false;
                        player2.health -= fortress.bullet3.damage;
                        player2.alreadyHitByFB = true;
                        player2.alreadyHitTimer = 0;
                    }
                    return true;
                }
        }
        if(player2.alreadyHitTimer <10 ){
            player2.alreadyHitTimer++;
            return true;
        }
        player2.alreadyHitByFB = false;
        return false;
    }

    //EnemyType 1-------------------------------------------------------------------------------------------------------
    /**
     * This method checks if the <code>EnemyType1</code> is hit from <code>PlayerBullet</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>EnemyType1</code> is hit and false if not
     */
    public boolean isE1hbP1B(int enemyIndex) {
        if(player1.bullet.appear)
            if (enemyType1[enemyIndex].x > (player1.getXbullet() - enemyType1[enemyIndex].getWidth()) &&
                    enemyType1[enemyIndex].x < (player1.getXbullet() + player1.bullet.getWidth()) &&
                    enemyType1[enemyIndex].y > (player1.getYbullet() - enemyType1[enemyIndex].getHeight()) &&
                    enemyType1[enemyIndex].y < (player1.getYbullet() + player1.bullet.getHeight())) {
                player1.bullet.appear = false;
                player1.score += enemyType1[enemyIndex].awardPoints;
                enemyType1[enemyIndex].alreadyHitTimer = 0;
                enemyType1[enemyIndex].ex = enemyType1[enemyIndex].x;
                enemyType1[enemyIndex].ey = enemyType1[enemyIndex].y;
                if(this.hostOS.equals ("Windows")){
                    SoundEffects.Explosion.play();               // play sound file in Windows environment
                }
                else if(this.hostOS.equals ("UNIXType")){
                    SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                }
                return true;
            }
        if(enemyType1[enemyIndex].alreadyHitTimer < 15) {
            enemyType1[enemyIndex].alreadyHitTimer++;
            return true;
        }
        return false;
    }

    /**
     * This method checks if the <code>EnemyType1</code> is hit from <code>Player2Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType1</code>
     * @return     True if the <code>EnemyType1</code> is hit and false if not
     */
    public boolean isE1hbP2B(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if(player2.bullet.appear)
                if (enemyType1[enemyIndex].x > (player2.getXbullet() - enemyType1[enemyIndex].getWidth()) &&
                        enemyType1[enemyIndex].x < (player2.getXbullet() + player2.bullet.getWidth()) &&
                        enemyType1[enemyIndex].y > (player2.getYbullet() - enemyType1[enemyIndex].getHeight()) &&
                        enemyType1[enemyIndex].y < (player2.getYbullet() + player2.bullet.getHeight())) {
                    player2.bullet.appear = false;
                    player2.score += enemyType1[enemyIndex].awardPoints;
                    enemyType1[enemyIndex].alreadyHitTimer = 0;
                    enemyType1[enemyIndex].ex = enemyType1[enemyIndex].x;
                    enemyType1[enemyIndex].ey = enemyType1[enemyIndex].y;
                    if(this.hostOS.equals ("Windows")){
                        SoundEffects.Explosion.play();               // play sound file in Windows environment
                    }
                    else if(this.hostOS.equals ("UNIXType")){
                        SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                    }
                    return true;
                }
        if(enemyType1[enemyIndex].alreadyHitTimer < 15) {
            enemyType1[enemyIndex].alreadyHitTimer++;
            return true;
        }
        return false;
    }
    //EnemyType 2-------------------------------------------------------------------------------------------------------
    /**
     * This method checks if the <code>EnemyType2</code> is hit from <code>PlayerBullet</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>EnemyType2</code> is hit and false if not
     */
    public boolean isE2hbP1B(int enemyIndex) {
        if(player1.bullet.appear)
            if (enemyType2[enemyIndex].x > (player1.getXbullet() - enemyType2[enemyIndex].getWidth()) &&
                    enemyType2[enemyIndex].x < (player1.getXbullet() + player1.bullet.getWidth()) &&
                    enemyType2[enemyIndex].y > (player1.getYbullet() - enemyType2[enemyIndex].getHeight()) &&
                    enemyType2[enemyIndex].y < (player1.getYbullet() + player1.bullet.getHeight())) {
                player1.bullet.appear = false;
                player1.score += enemyType2[enemyIndex].awardPoints;
                enemyType2[enemyIndex].alreadyHitTimer = 0;
                enemyType2[enemyIndex].ex = enemyType2[enemyIndex].x;
                enemyType2[enemyIndex].ey = enemyType2[enemyIndex].y;
                if(this.hostOS.equals ("Windows")){
                    SoundEffects.Explosion.play();               // play sound file in Windows environment
                }
                else if(this.hostOS.equals ("UNIXType")){
                    SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                }
                return true;
            }
        if(enemyType2[enemyIndex].alreadyHitTimer < 15) {
            enemyType2[enemyIndex].alreadyHitTimer++;
            return true;
        }
        return false;
    }

    /**
     * This method checks if the <code>EnemyType2</code> is hit from <code>Player2Bullet</code>
     * @param enemyIndex    Index of the <code>EnemyType2</code>
     * @return     True if the <code>EnemyType2</code> is hit and false if not
     */
    public boolean isE2hbP2B(int enemyIndex) {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if(player2.bullet.appear)
                if (enemyType2[enemyIndex].x > (player2.getXbullet() - enemyType2[enemyIndex].getWidth()) &&
                        enemyType2[enemyIndex].x < (player2.getXbullet() + player2.bullet.getWidth()) &&
                        enemyType2[enemyIndex].y > (player2.getYbullet() - enemyType2[enemyIndex].getHeight()) &&
                        enemyType2[enemyIndex].y < (player2.getYbullet() + player2.bullet.getHeight())) {
                    player2.bullet.appear = false;
                    player2.score += enemyType2[enemyIndex].awardPoints;
                    enemyType2[enemyIndex].alreadyHitTimer = 0;
                    enemyType2[enemyIndex].ex = enemyType2[enemyIndex].x;
                    enemyType2[enemyIndex].ey = enemyType2[enemyIndex].y;
                    if(this.hostOS.equals ("Windows")){
                        SoundEffects.Explosion.play();               // play sound file in Windows environment
                    }
                    else if(this.hostOS.equals ("UNIXType")){
                        SoundEffects.ExplosionUNIX.play();                  // play sound file in UNIX environment
                    }
                    return true;
                }
        if(enemyType2[enemyIndex].alreadyHitTimer < 15) {
            enemyType2[enemyIndex].alreadyHitTimer++;
            return true;
        }
        return false;
    }
    //Fortress----------------------------------------------------------------------------------------------------------
    /**
     * This method checks if the <code>EnemyFortress</code> is hit from <code>PlayerBullet</code>
     * @return     True if the <code>EnemyFortress</code> is hit and false if not
     */
    public boolean isFhbP1B() {
        if(player1.bullet.appear)
            if (fortress.x > (player1.getXbullet() - fortress.getWidth()) &&
                    fortress.x < (player1.getXbullet() + player1.bullet.getWidth()) &&
                    fortress.y > (player1.getYbullet() - fortress.getHeight()) &&
                    fortress.y < (player1.getYbullet() + player1.bullet.getHeight())) {
                if(!fortress.alreadyHitByP1) {
                    player1.bullet.appear = false;
                    fortress.health -= player1.bullet.damage;
                    player1.score += fortress.awardPoints;
                    fortress.alreadyHitByP1 = true;
                    fortress.alreadyHitTimer = 0;
                }
                return true;
            }
        if(fortress.alreadyHitTimer <10 ){
            fortress.alreadyHitTimer++;
            return true;
        }
        fortress.alreadyHitByP1 = false;
        return false;
    }

    /**
     * This method checks if the <code>EnemyFortress</code> is hit from <code>Player2Bullet</code>
     * @return     True if the <code>EnemyFortress</code> is hit and false if not
     */
    public boolean isFhbP2B() {
        if(multiplayerMode)                 // this check is only performed in Multiplayer mode
            if(player2.bullet.appear)
                if (fortress.x > (player2.getXbullet() - fortress.getWidth()) &&
                        fortress.x < (player2.getXbullet() + player2.bullet.getWidth()) &&
                        fortress.y > (player2.getYbullet() - fortress.getHeight()) &&
                        fortress.y < (player2.getYbullet() + player2.bullet.getHeight())) {
                    if(!fortress.alreadyHitByP2) {
                        player2.bullet.appear = false;
                        fortress.health -= player2.bullet.damage;
                        player2.score += fortress.awardPoints;
                        fortress.alreadyHitByP2 = true;
                        fortress.alreadyHitTimer = 0;
                    }
                    return true;
                }
        if(fortress.alreadyHitTimer <10 ){
            fortress.alreadyHitTimer++;
            return true;
        }
        fortress.alreadyHitByP2 = false;
        return false;
    }
}