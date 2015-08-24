/**
 * Created by tage on 15-7-28.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Tank {

    private int x;
    private int y;
    private int oldX;
    private int oldY;
    private TankClient tc;
    private int life = 100;

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;


    public static final int XSPEED = 5;
    public static final int YSPEED = 5;

    private boolean LEFT = false;
    private boolean RIGHT = false;
    private boolean UP = false;
    private boolean DOWN = false;


    private static Direction[] dirs = Direction.values();


    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;

    public boolean isGood() {
        return good;
    }

    private boolean good;
    private boolean bLive = true;

    private BloodBar bb = new BloodBar();

    private static Random r = new Random();
    private int step = r.nextInt(12) + 3;

    public boolean isbLive() {
        return bLive;
    }

    public void setbLive(boolean bLive) {
        this.bLive = bLive;
    }

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
    }

    public Tank(int x, int y, boolean good, TankClient tc) {
        this(x, y, good);
        this.tc = tc;

    }

    public Tank(int x, int y, boolean good, TankClient tc, Direction dir) {
        this(x, y, good, tc);
        this.dir = dir;

    }


    private void move() {
        oldX = x;
        oldY = y;
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case STOP:
                break;

        }
        if (dir != Direction.STOP)
            ptDir = dir;

        if (x < 0 || x == 0) x = 0;
        if (y < 30 || y == 30) y = 30;
        if (x + Tank.WIDTH > TankClient.GAME_WIDTH || x + Tank.WIDTH == TankClient.GAME_WIDTH)
            x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT || y + Tank.HEIGHT == TankClient.GAME_HEIGHT)
            y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

        if (!good) {


            if (step == 0) {
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
            }

            step--;
            if (r.nextInt(40) > 38)
                fire();

        }
    }

    public void draw(Graphics g) {

        if (!bLive) {
            if (good == false)
                tc.tanks.remove(this);
            return;
        }

        Color c = g.getColor();

        if (good)
            g.setColor(Color.RED);
        else g.setColor(Color.BLUE);

        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);


        switch (ptDir) {
            case L:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT / 2);
                break;
            case R:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT / 2);
                break;
            case LU:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y);
                break;
            case RU:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y);
                break;
            case LD:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT);
                break;
            case RD:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT);
                break;
            case U:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y);
                break;
            case D:
                g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y + HEIGHT);
                break;

        }
        g.setColor(c);

        if (good)
            bb.draw(g);
        move();


    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                superFire();
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_LEFT:
                LEFT = false;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = false;
                break;
            case KeyEvent.VK_UP:
                UP = false;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = false;
                break;
        }
        locateDirection();
    }

    public Missile fire() {
        if (!bLive) {
            return null;
        }
        int x = this.x + WIDTH / 2 - Missile.WIDTH;
        int y = this.y + HEIGHT / 2 - Missile.HEIGHT;
        Missile m = new Missile(x, y, this.good, ptDir, this.tc);
        tc.missiles.add(m);
        return m;
    }

    public Missile fire(Direction dir) {
        if (!bLive) {
            return null;
        }
        int x = this.x + WIDTH / 2 - Missile.WIDTH;
        int y = this.y + HEIGHT / 2 - Missile.HEIGHT;
        Missile m = new Missile(x, y, this.good, dir, this.tc);
        tc.missiles.add(m);
        return m;

    }

    public void superFire() {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < dirs.length - 1; i++) {
            fire(dirs[i]);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_F2:
                if (!bLive) {
                    this.life = 100;
                    this.bLive = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                LEFT = true;
                break;
            case KeyEvent.VK_RIGHT:
                RIGHT = true;
                break;
            case KeyEvent.VK_UP:
                UP = true;
                break;
            case KeyEvent.VK_DOWN:
                DOWN = true;
                break;
        }
        locateDirection();
    }


    void locateDirection() {
        if (LEFT && !RIGHT && !UP && !DOWN) dir = Direction.L;
        if (!LEFT && RIGHT && !UP && !DOWN) dir = Direction.R;
        if (!LEFT && !RIGHT && UP && !DOWN) dir = Direction.U;
        if (!LEFT && !RIGHT && !UP && DOWN) dir = Direction.D;
        if (LEFT && !RIGHT && UP && !DOWN) dir = Direction.LU;
        if (LEFT && !RIGHT && !UP && DOWN) dir = Direction.LD;
        if (!LEFT && RIGHT && UP && !DOWN) dir = Direction.RU;
        if (!LEFT && RIGHT && !UP && DOWN) dir = Direction.RD;
        if (!LEFT && !RIGHT && !UP && !DOWN) dir = Direction.STOP;


    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void stay() {
        x = oldX;
        y = oldY;
    }

    /**
     * 撞墙
     *
     * @param w 墙
     * @return 撞上返回true, 否则false
     */
    public boolean colidesWithWall(Wall w) {
        if (bLive && getRect().intersects(w.getRect())) {
            stay();
            return true;
        }
        return false;
    }

    public boolean colidesWithTank(Tank t) {
        if (bLive && t.bLive && getRect().intersects(t.getRect())) {
            stay();
            return true;
        }
        return false;

    }

    public boolean colidesWithTanks(java.util.List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                this.colidesWithTank(t);
            }

        }
        return false;
    }

    private class BloodBar {


        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x, y - 10, WIDTH, 10);
            int m = WIDTH * life / 100;
            g.fillRect(x, y - 10, m, 10);
            g.setColor(c);
        }
    }

    public boolean hitBlood(Blood b) {
        if (bLive && b.isLive() && this.getRect().intersects(b.getRect())) {
            life = 100;
            b.setLive(false);
            return true;
        }
        return false;


    }


}