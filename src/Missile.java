


import java.awt.*;
import java.util.List;


/**
 * Created by tage on 15-7-28.
 */
public class Missile {
    public static final int XSPEED = 10;
    public static final int YSPEED = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private boolean live = true;

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    private int x;
    private int y;
    private Direction dir;
    private TankClient tc;
    private boolean good;

    public Missile(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, dir);
        this.good = good;
        this.tc = tc;
    }

    private void move() {
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


        }

        if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
            setLive(false);
            tc.missiles.remove(this);
        }
    }

    public void draw(Graphics g) {
        if (!live) {
            //tc.missiles.remove(this);
            move();
            return;
        }

        Color c = g.getColor();
        if (good) g.setColor(Color.BLACK);
        else g.setColor(Color.ORANGE);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);
        move();
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);

    }

    public boolean hitTank(Tank t) {
        if (this.live && this.getRect().intersects(t.getRect()) && t.isbLive() && t.isGood() != this.good) {

            if (t.isGood()) {
                t.setLife(t.getLife() - 20);
                if (t.getLife() <= 0)
                    t.setbLive(false);
            } else
                t.setbLive(false);
            this.setLive(false);

            Explode e = new Explode(x, y, this.tc);
            tc.explodes.add(e);
            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            hitTank(tanks.get(i));

        }
        return false;
    }

    public boolean hitWall(Wall w) {
        if (live && this.getRect().intersects(w.getRect())) {
            live = false;
            return true;
        }
        return false;
    }

    public boolean hitMissile(Missile m) {
        if (live && m.live && m.good != this.good && this.getRect().intersects(m.getRect())) {
            live = false;
            m.live = false;
            return true;
        }
        return false;
    }

    public boolean hitMissiles(List<Missile> missiles) {
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            if (this != m)
                this.hitMissile(m);
        }
        return false;
    }


}