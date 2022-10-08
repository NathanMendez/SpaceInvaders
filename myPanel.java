import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;
import java.awt.*;

public class myPanel  extends JPanel implements ActionListener,KeyListener{
    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGHT = 600;
    int moveColLim = 1;
    final int enemyNum = 40;
    boolean shoot = false;
    player p;
    bullet b;
    enemy[] enemies;
    enemyb[] enemybs;
    barricade[] barcades;
    Timer t;
    int loopCount = 0;
    int cycleReset = 0;
    int moveCol = 0;
    int remaining;
    int score = 0;
    myPanel(){
        enemies = new enemy[enemyNum];
        enemybs = new enemyb[10];
        barcades = new barricade[20];
        int colCnst = 50;
        int rowCnst = 50;
        remaining = enemies.length;
        for(int i = 0;i<enemyNum;i++){
            enemies[i] = new enemy(colCnst,rowCnst);
            colCnst = colCnst + 50;
            if(colCnst == SCREEN_WIDTH-50){
                colCnst = 50;
                rowCnst = rowCnst+50;
            }
        }

        for(int i = 0;i<10;i++){
            enemybs[i] = new enemyb(enemies[enemyNum-i-1].xPos,enemies[enemyNum-i-1].yPos);
            enemybs[i].off = true;
        }
        colCnst = 50;
        for(int i = 0;i<20;i++){
            barcades[i] = new barricade(colCnst, 450);
            colCnst = colCnst + 50;
        }

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        t = new Timer(60, this);
        this.addKeyListener(this);
        setFocusable(true);
        p = new player(250,550);
        b = new bullet(p.xPos, p.yPos);
        t.start();

    }

    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_A){
            if(p.xPos > 0){
                if(shoot == false){
                    b.yPos = p.yPos;
                    b.xPos = p.xPos;
                }
                p.xPos = p.xPos - p.xVel; 

            }
        }else if(keyCode == KeyEvent.VK_D){
            if(p.xPos < 550){
                if(shoot == false){
                    b.yPos = p.yPos;
                    b.xPos = p.xPos;
                }
                p.xPos = p.xPos + p.xVel; 

            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_SPACE){
            shoot = true;
        }
        
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.fillRect(0, 500, 600, 10);

        g2D.setColor(Color.white);
        g2D.drawString("Score: "+score, 20, 15);

        p.draw(g);
        if(shoot == true){
            b.draw(g);
        }
        g2D.setColor(Color.white);
        for(enemyb b:enemybs){
            if(b.off == false){
                b.draw(g);
            }
        }
        for(enemy e:enemies){
            if(e.dead == false){
                e.draw(g);
            }
        }

        for(barricade b:barcades){
            if(b.health>0){
                b.draw(g);
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        loopCount ++;
        if(score == enemyNum){
            System.exit(0);

        }

        repaint();
        if(b.yPos < 0){
            shoot=false;
        }
        if(shoot == true){
            b.yPos = b.yPos - b.yVel;
        }else{
            b.yPos = p.yPos;
            b.xPos = p.xPos;
        }

        for(enemy en:enemies){
            if(en.xPos == b.xPos && en.yPos == b.yPos){
                en.xPos = -100;
                en.yPos = -100;
                shoot = false;
                en.dead = true;
                remaining--;
                score++;
            }
            if(loopCount == 50){
                en.xPos = en.xPos + en.xVel;

            }
        }
        if(loopCount == 50){
            loopCount = 0;
            cycleReset++;
        }

        for(enemy en:enemies){
            if(cycleReset == 2){
                en.xVel = -1*en.xVel;
            }if(moveCol == moveColLim){
                en.yPos = en.yPos + en.yVel;
            }
        }

 

        if(moveCol == moveColLim){
            moveCol = 0;
        }
        if(cycleReset == 2){
            cycleReset = 0;
            moveCol ++;
        }

        ArrayList<enemy> shoots = getShootable();
        for(int i=0;i<shoots.size();i++){
            if(enemybs[i].off==true&&shoots.get(i).firecooldow<=0){
                enemybs[i].xPos = shoots.get(i).xPos;
                enemybs[i].yPos = shoots.get(i).yPos;
                enemybs[i].off = false;
                shoots.get(i).firecooldow = ThreadLocalRandom.current().nextInt(5,40);
            }else{
                shoots.get(i).firecooldow--;
            }
        }

        for(int i=0;i<shoots.size();i++){
            if(enemybs[i].off == false){
                enemybs[i].yPos = enemybs[i].yPos+enemybs[i].yVel;
                if(enemybs[i].yPos == p.yPos && enemies[i].xPos == p.xPos){
                    enemybs[i].off=true;
                }
                if(enemybs[i].yPos >=600){
                    enemybs[i].off=true;
                }for(barricade b:barcades){
                    if(b.xPos == enemybs[i].xPos && b.yPos == enemybs[i].yPos){
                        b.health --;
                        if(b.health==0){
                            b.xPos = -100;
                            b.yPos = -100;
                        }enemybs[i].off=true;

                    }
                }
            }
        }
       



        
    }
    public ArrayList<enemy> getShootable(){
        ArrayList<enemy> e = new ArrayList<enemy>();
        boolean cantShoot = false;
        for(int i = 0;i<this.enemies.length;i++){
            if(this.enemies[i].dead == true){
                continue;
            }
            for(int j = i+1;j<this.enemies.length;j++){
                if(this.enemies[i].xPos == this.enemies[j].xPos){
                    if(this.enemies[j].dead == true){
                        continue;
                    }
                    cantShoot = true;
                    break;
                }
            }
            if(cantShoot == false){
                e.add(this.enemies[i]);
            }else{
                cantShoot = false;
            }
        }
        return e;

    }
}
