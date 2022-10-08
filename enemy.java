
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class enemy {
    int xPos;
    int yPos;
    int yVel = 25;
    int xVel = 25;
    boolean dead = false;
    int firecooldow = ThreadLocalRandom.current().nextInt(10,20);
    public enemy(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(xPos, yPos, 25, 25);
    }
}
