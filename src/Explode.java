

import java.awt.*;

/**
 * Created by tage on 15-8-1.
 */
public class Explode {
    private int x;
    private int y;
    private boolean live = true;
    private TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    int step = 0;

    int diameter[] = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};

    public void draw(Graphics g) {
        if (!live) {
            tc.explodes.remove(this);
            return;
        }

        if (step == diameter.length) {
            live = false;
            step = 0;
            return;
        }

        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);

        step++;
    }


}