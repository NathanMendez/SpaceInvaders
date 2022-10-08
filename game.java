
import javax.swing.*;

public class game extends JFrame{
    myPanel panel;
    player p;
    public game(){
        panel = new myPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    
    public static void main(String[] args) {
        game g  = new game();
    }
}
