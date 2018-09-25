package snakegame;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author Kurei Kageno
 */

public class MainGame extends JApplet implements KeyListener{

    private GamePanel sPanel=new GamePanel();

    public void init() {
        Container cpane=getContentPane();
        cpane.add(sPanel);
        addKeyListener(this);
    }

    public static void main(String[] args) {
        MainGame newGame=new MainGame();
        JFrame snakeFrame=new JFrame("Snake v1.0");
        snakeFrame.setSize(650,557);
        snakeFrame.getContentPane().add(newGame);
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeFrame.addKeyListener(newGame);
        newGame.init();
        newGame.start();
        newGame.setVisible(true);
        snakeFrame.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode())>=37&&(e.getKeyCode()<=40)) sPanel.setDir(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
    }
}
