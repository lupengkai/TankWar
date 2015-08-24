

import java.awt.*;

/**
 * Created by tage on 15-8-5.
 */
public class Blood {
    private int x;
    private int y;
    private int w;
    private int h;

    private int step = 0;

    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private int[][] pos = {
            {350, 300},
            {360, 300},
            {375, 275},
            {400, 200},
            {360, 270},
            {365, 290},
            {340, 280}
    };

    public Blood() {
        this.x = pos[0][0];
        this.y = pos[0][1];
        w = 10;
        h = 10;
    }

    public void draw(Graphics g) {
        if (live) {
            Color c = g.getColor();
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, w, h);
            g.setColor(c);

            move();
        }
    }

    private void move() {
        step++;
        if (step == pos.length)
            step = 0;

        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }


}