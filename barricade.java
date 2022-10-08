import java.awt.*;
public class barricade {
    public int xPos;
    public int yPos;
    public int health = 30;
    public barricade(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Graphics g){
        if(health >5){
            g.setColor(Color.WHITE);
        }else if(health >4){
            g.setColor(Color.lightGray);
        }else{
            g.setColor(Color.gray);
        }g.fillRect(xPos, yPos, 25, 25);
    }
}
