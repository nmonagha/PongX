import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pause extends JFrame {



    public void render(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        /*Font font = new Font("impact", Font.BOLD, 30);
        g.setFont(font); */
        g.setColor(Color.red);
        g.drawString("Press 'P' to unpause", 640 / 2 - 120, 100);
        //g.setFont(font);

    }

}