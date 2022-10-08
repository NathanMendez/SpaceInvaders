import java.awt.*;


class player{
    public int xPos;
    public int yPos;
    public  int xVel = 25;

    public player(int xPos,int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(this.xPos, this.yPos, 25, 25);
    }



}