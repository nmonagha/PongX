import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JFrame {

    /* Creating rectangles for the Menu */
    public Rectangle onePlayerButton = new Rectangle(640 / 2 - 250, 150, 175, 50);
    public Rectangle twoPlayerButton = new Rectangle(640 / 2 - 250, 225, 175, 50);
    public Rectangle scoreButton = new Rectangle(640 / 2 - 250, 300, 125, 50);
    public Rectangle helpButton = new Rectangle(640 / 2 - 250, 375, 100, 50);
    public Rectangle quitButton = new Rectangle(640 / 2 - 250, 450, 90, 50);

    static boolean pause = false;

    public void render(Graphics g) {

        /* Creating graphics to print and draw text */
        Graphics2D graphics2D = (Graphics2D) g;
        Font font = new Font("impact", Font.BOLD, 75);
        Font font2 = new Font("impact", Font.BOLD, 30);

        /* Creates PongX title */
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString("Pong", 640 / 2 - 85, 100);
        g.setColor(Color.red);
        g.drawString("X", 640 / 2 + 70, 100);

        /* Creates text for rectangles in menu */
        g.setFont(font2);
        g.setColor(Color.black);
        g.drawString("One Player", onePlayerButton.x + 20, onePlayerButton.y + 35);
        g.drawString("Two Player", twoPlayerButton.x + 20, twoPlayerButton.y + 35);
        g.drawString("Help", helpButton.x + 20, helpButton.y + 35);
        g.drawString("Scores", scoreButton.x + 20, scoreButton.y + 35);
        g.drawString("Quit", quitButton.x + 20, quitButton.y + 35);
        graphics2D.draw(onePlayerButton);
        graphics2D.draw(twoPlayerButton);
        graphics2D.draw(quitButton);
        graphics2D.draw(helpButton);
        graphics2D.draw(scoreButton);
    }
}
