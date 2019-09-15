import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        final Frame frame = new Frame();
        final Game g = new Game();
        frame.setContentPane(g);
        frame.setVisible(true);
    }
}
