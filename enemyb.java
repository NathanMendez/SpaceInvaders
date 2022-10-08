
import java.awt.*;
public class enemyb {
    int yPos;
    int xPos;
    int yVel = 25;
    boolean off  = false;
    public enemyb(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public void draw(Graphics g){
        g.setColor(Color.magenta);
        g.fillRect(xPos, yPos, 25, 25);
    }
}
