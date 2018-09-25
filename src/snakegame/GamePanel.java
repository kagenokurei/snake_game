package snakegame;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 *
 * @author Kurei Kageno
 */

/*
 * Meat of the game
 */

public class GamePanel extends JPanel {
    private snakeThread gThread=new snakeThread();
    private snakeGrid sGrid=new snakeGrid();
    private snake sChar=new snake();
    private int score=0;
    private int dir=4; //direction
    private boolean food=false;
    private boolean sfood=false; //superfood indicator
    private boolean walled=false; //walled game indicator
    private int game=0; // game identifier; also used to identify snake death type
    private int foodx=0;
    private int foody=0;
    private int speed=100;
    private int sfoodx=0;
    private int sfoody=0;
    private int sCount=5; // counter for superfood generation
    private int sFv=0; // counter for superfood validity
    private int level=1;
    private int sLd=200; // counter for score and levelup delta (similar to experience counter)
    private final int LEFT = 37;
    private final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40;

    public GamePanel() {
        gThread.start();
    }

    public void paintComponent(Graphics g) { //main engine
        super.paintComponent(g);
        g.clearRect(0, 0, 520, 520);
        //g.drawString(""+dir+"", 20, 20); //debug use
        moveSnake();
        game=gameChecks();
        sGrid.clearGrid();
        drawSnake();
        drawFood();
        levelUp();
        drawDetails(g);
        drawGrid(g);
    }

    public void levelUp(){ //function for leveling up
        sLd=(level*200)-score;
        if (sLd<=0){
            level++;
            if((speed*0.8)<1)speed=1;
            else speed=(int)(speed*0.8);
        }
    }

    public void drawDetails(Graphics g){ //function for displaying game information
        g.setFont(Font.decode("Arial-BOLD-16"));
        g.drawString("Snake v1.0", 530, 20);
        if(walled) g.drawString("Walled Game", 530, 40);
        if(!walled) g.drawString("No Walls", 530, 40);
        g.drawString("Score: "+score, 530, 80);
        g.drawString("Level: "+level, 530, 60);
        if(sfood){
            g.drawString("SuperFood!!", 530, 100);
            g.drawString("Value: "+sFv, 530, 120);
        }
    }

    public void wrapSnake(){ // function for "wrapping" snake in case of unwalled game
        while(sChar.headx<1)sChar.headx+=50;
        while(sChar.headx>50)sChar.headx-=50;
        while(sChar.heady<1)sChar.heady+=50;
        while(sChar.heady>50)sChar.heady-=50;
    }

    public void drawFood(){ //function for plotting food on the snake grid
        if(!sfood&&(sCount==0)){ //super food generator
            while(sfoodx<1||sfoody<1||sfoodx>50||sfoody>50||sGrid.getGrid(sfoodx, sfoody)==2||sGrid.getGrid(sfoodx, sfoody)==4||sGrid.getGrid(sfoodx, sfoody)==3){
                sfoodx=(int)(Math.random()*49+1);
                sfoody=(int)(Math.random()*49+1);
            }
            sfood=true;
            sCount=5;
            sFv=100;
        }
        if(sfood&&(sFv!=0)){ // function for super food expiry
            sGrid.setGrid(sfoodx, sfoody, 5);
            sFv--;
            if(sFv==0) sfood=false;
        }
        if(!food){ // standard food generator
            while(foodx<1||foody<1||foodx>50||foody>50||sGrid.getGrid(foodx, foody)==2||sGrid.getGrid(foodx, foody)==4||sGrid.getGrid(foodx, foody)==5){
                foodx=(int)(Math.random()*49+1);
                foody=(int)(Math.random()*49+1);
            }
            food=true;
        }
        sGrid.setGrid(foodx, foody, 3);
    }

    public int gameChecks(){ // checks snake death type
        if(sChar.headx<1||sChar.headx>50||sChar.heady<1||sChar.heady>50) return 1;
        if(sGrid.getGrid(sChar.headx, sChar.heady)==2) return 2;
        return 0;
    }

    public void moveSnake(){ // it does what it says: moving the snake
        sChar.newBend();
        switch(dir) {
            case 1 : sChar.heady--;
            break;
            case 2 : sChar.heady++;
            break;
            case 3 : sChar.headx--;
            break;
            case 4 : sChar.headx++;
            break;
        }
        sChar.dir=dir;
        if(!walled) wrapSnake();
        if(sChar.headx==foodx&&sChar.heady==foody){ //check for food hit
            sChar.length++;
            if(!sfood) sCount--;
            score+=20;
            food=false;
        }
        if(sChar.headx==sfoodx&&sChar.heady==sfoody){ //check for superfood hit
            //sChar.length++; //enable if snake should grow even with superfood
            score+=sFv;
            sfood=false;
        }
    }

    public void drawGrid(Graphics g){ //function for drawing the grid(game world) on the screen
        int x,y,d;
        for(x=0;x<52;x++){
            for(y=0;y<52;y++){
                d=sGrid.getGrid(x, y);
                switch(d){
                    case 1 : { //draws border
                        g.setColor(Color.black);
                        g.fillRect(x*10, y*10, 10, 10);
                    }
                    break;
                    case 2: { //draws snake body
                        g.setColor(Color.black);
                        g.fillRect(x*10, y*10, 10, 10);
                        g.setColor(Color.pink);
                        g.drawLine(x*10, y*10, x*10+9, y*10+9);
                        g.drawLine(x*10+9, y*10, x*10, y*10+9);
                    }
                    break;
                    case 3:{ //draws food
                        g.setColor(Color.red);
                        g.fillOval(x*10-1, y*10-1, 11, 11);
                    }
                    break;
                    case 4:{ //draws snake head
                        g.setColor(Color.black);
                        g.fillOval(x*10-1, y*10-1, 11, 11);
                        if(dir==1) g.fillRect(x*10, y*10+5, 10, 5);
                        if(dir==2) g.fillRect(x*10, y*10, 10, 5);
                        if(dir==3) g.fillRect(x*10+5, y*10, 5, 10);
                        if(dir==4) g.fillRect(x*10, y*10, 5, 10);
                    }
                    break;
                    case 5:{ //draws super food
                        g.setColor(Color.black);
                        g.fillOval(x*10-1, y*10-1, 11, 11);
                    }
                    break;
                    default : break;
                }
            }
        }
    }

    public int wrapC(int c){ // function for "wrapping" plotting coordinates in case of unwalled game
        while(c<1)c+=50;
        while(c>50)c-=50;
        return c;
    }

    public void drawSnake(){ //function for plotting snake onto grid
        sGrid.setGrid(sChar.headx, sChar.heady, 4);
        int i=0, x=sChar.headx, y=sChar.heady, d=sChar.dir, l=sChar.length-1;
        while(l>0){
            switch(d){
                case 1 :{
                    while(y!=sChar.bendy[i]&&l>0){
                        y++;
                        y=wrapC(y);
                        l--;
                        sGrid.setGrid(x, y, 2);
                    }
                }
                break;
                case 2 :{
                    while(y!=sChar.bendy[i]&&l>0){
                        y--;
                        y=wrapC(y);
                        l--;
                        sGrid.setGrid(x, y, 2);
                    }
                }
                break;
                case 3 :{
                    while(x!=sChar.bendx[i]&&l>0){
                        x++;
                        x=wrapC(x);
                        l--;
                        sGrid.setGrid(x, y, 2);
                    }
                }
                break;
                case 4 :{
                    while(x!=sChar.bendx[i]&&l>0){
                        x--;
                        x=wrapC(x);
                        l--;
                        sGrid.setGrid(x, y, 2);
                    }
                }
                break;
            }
            d=sChar.bend[i];
            i++;
        }
    }

    public void restartGame(){ //does what it says: restarting the game
        sGrid=new snakeGrid();
        sChar=new snake();
        dir=4;
        score=0;
        speed=100;
        foodx=0;
        foody=0;
        sfoodx=0;
        sfoody=0;
        game=0;
        sCount=5;
        food=false;
        sfood=false;
        sFv=0;
        level=1;
        sLd=200;
    }

    class snakeThread extends Thread { //main thread runner
        public void run() {
            int c=JOptionPane.showConfirmDialog(null, "Would you like to start with a Walled Game?", "New Game", JOptionPane.YES_NO_OPTION);
            if(c==JOptionPane.YES_OPTION) walled=true;
            while(game==0){
                while(game==0){
                    repaint();
                    try{ Thread.sleep(speed); }
                    catch (InterruptedException e){}
                }
                if(game==1) c=JOptionPane.showConfirmDialog(null, "You hit the wall! Game Over.\nWould you like to start a new Game?", "Game Over", JOptionPane.YES_NO_OPTION);
                if(game==2) c=JOptionPane.showConfirmDialog(null, "You hit yourself! Game Over.\nWould you like to start a new Game?", "Game Over", JOptionPane.YES_NO_OPTION);
                if(c==JOptionPane.YES_OPTION) restartGame();
            }
            //
            interrupt();
        }
    }

    public void setDir(int n){ //sets direction
        switch(n){
            case UP : {
                if(dir==2)dir=2;
                else dir=1;
            }
            break;
            case DOWN : {
                if(dir==1)dir=1;
                else dir=2;
            }
            break;
            case LEFT : {
                if(dir==4)dir=4;
                else dir=3;
            }
            break;
            case RIGHT : {
                if(dir==3)dir=3;
                else dir=4;
            }
            break;
            default : dir=n;
            break;
        }
    }
}