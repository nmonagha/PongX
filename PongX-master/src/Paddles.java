/**
 * Created by Ashwin Prasad on 9/17/2017.
 */
public class Paddles {
    private int padHeight = 10;
    private int padWidth = 60;
    private int padX;
    private int padY;
    private int speed;
    private boolean stuck;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getStuck() {
        return stuck;
    }

    public void setStuck(boolean stuck) {
        this.stuck = stuck;
    }

    public Paddles(int loc, int X) {
        if (loc == 1) {
            padX = X;
            padY = 10;
        } else {
            padX = X;
            padY = 590;
        }
        speed = 2;
        stuck = false;

    }

    public int getPadHeight() {
        return padHeight;
    }

    public int getPadWidth() {
        return padWidth;
    }

    public void setPadWidth(int padWidth) {
        if(padWidth <= 320)
            this.padWidth = padWidth;
    }

    public int getPadX() {
        return padX;
    }

    public void setPadX(int padX) {
            this.padX = padX;
    }

    public int getPadY() {
        return padY;
    }
}
