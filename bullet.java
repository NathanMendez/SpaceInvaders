
import java.awt.*;
public class bullet {
    public int xPos;
    public int yPos;
    public int yVel = 25;

    public bullet(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(xPos, yPos, 25, 25);
    }
}
