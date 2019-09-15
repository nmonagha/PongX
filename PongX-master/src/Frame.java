import javax.swing.*;

public class Frame extends JFrame {

    /* Used to set the size of the game */
    private int WIDTH = 640, HEIGHT = 640;

    public Frame() {
        setSize(WIDTH, HEIGHT);
        setTitle("PongX");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
