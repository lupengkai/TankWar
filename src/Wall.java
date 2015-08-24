
import java.awt.*;

/**
 * Created by tage on 15-8-4.
 */
public class Wall {
    private int x;
    private int y;
    private int width;
    private int height;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}