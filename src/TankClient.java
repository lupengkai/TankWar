/**
 * Created by tage on 15-7-28.
 */

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * 游戏界面
 */
public class TankClient extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private static Random r = new Random();


    Image offScreenImage = null;
    Tank myTank = new Tank(700, 500, true, this);
    List<Missile> missiles = new ArrayList<Missile>();
    List<Explode> explodes = new ArrayList<>();
    List<Tank> tanks = new ArrayList<Tank>();
    Wall w1 = new Wall(100, 200, 200, 30);
    Wall w2 = new Wall(200, 100, 30, 200);
    Blood b = new Blood();

    public void paint(Graphics g) {
        g.drawString("MissleNum :" + missiles.size(), 60, 60);
        g.drawString("ExplodeNum :" + explodes.size(), 60, 80);
        g.drawString("TankNum :" + tanks.size(), 60, 100);
        g.drawString("TankLife :" + myTank.getLife(), 60, 200);

        w1.draw(g);
        w2.draw(g);

        if (tanks.size() == 0) {
            for (int i = 0; i < 10; i++) {
                tanks.add(new Tank(60 + 40 * i, 60, false, this, Direction.D));
            }
        }
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);

            m.hitMissiles(missiles);
            m.hitTanks(tanks);
            m.hitTank(myTank);
            m.hitWall(w1);
            m.hitWall(w2);

            m.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            t.colidesWithWall(w1);
            t.colidesWithWall(w2);
            t.colidesWithTank(myTank);
            t.colidesWithTanks(tanks);
            t.draw(g);

        }

        b.draw(g);

        myTank.colidesWithWall(w1);
        myTank.colidesWithWall(w2);
        myTank.colidesWithTanks(tanks);
        myTank.hitBlood(b);
        myTank.draw(g);
    }

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color c = goffScreen.getColor();
        goffScreen.setColor(Color.GREEN);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        goffScreen.setColor(c);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);


    }

    public void launchFrame() {
        for (int i = 0; i < 10; i++) {
            tanks.add(new Tank(60 + 40 * i, 60, false, this, Direction.D));
        }
        setLocation(400, 300);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setBackground(Color.BLACK);
        setTitle("TankWar");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });
        addKeyListener(new KeyMonitor());
        setVisible(true);
        setResizable(false);

        new Thread(new MyThread()).start();
    }

    public class MyThread implements Runnable {


        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private class KeyMonitor extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }


    public static void main(String[] args) {
        TankClient tc = new TankClient();
        tc.launchFrame();
    }

}