/**
 * Created by micha_000 on 9/17/2017.
 */
public class Ball {
    private int ballSize;
    private double ballSpeedX;
    private double ballSpeedY;
    private double ballX;
    private double ballY;
    private boolean stuck1;
    private boolean stuck2;
    private double ballSpeedXStuck;
    private double ballSpeedYStuck;

    public boolean getStuck2() {
        return stuck2;
    }

    public void setStuck2(boolean stuck2) {
        this.stuck2 = stuck2;
    }

    public boolean getStuck1() {
        return stuck1;
    }

    public void setStuck1(boolean stuck) {
        this.stuck1 = stuck;
    }

    public Ball() {
        ballSize = 10;
        ballSpeedX = 1;
        ballSpeedY = 1;
        ballSpeedXStuck = 0;
        ballSpeedYStuck = 0;
        ballX = 315;
        ballY = 315;
        stuck1 = false;
        stuck2 = false;
    }

    public int getBallSize() {
        return ballSize;
    }

    public void setBallSize(int ballSize) {
        this.ballSize = ballSize;
    }

    public double getBallSpeedX() {
        return ballSpeedX;
    }

    public void setBallSpeedX(double ballSpeedX) {
        this.ballSpeedX = ballSpeedX;
    }

    public double getBallSpeedY() {
        return ballSpeedY;
    }

    public void setBallSpeedY(double ballSpeedY) {
        this.ballSpeedY = ballSpeedY;
    }

    public double getBallX() {
        return ballX;
    }

    public void setBallX(double ballX) {
        this.ballX = ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public void setBallY(double ballY) {
        this.ballY = ballY;
    }

    public double getBallSpeedXStuck() {
        return ballSpeedXStuck;
    }

    public void setBallSpeedXStuck(double ballSpeedXStuck) {
        this.ballSpeedXStuck = ballSpeedXStuck;
    }

    public double getBallSpeedYStuck() {
        return ballSpeedYStuck;
    }

    public void setBallSpeedYStuck(double ballSpeedYStuck) {
        this.ballSpeedYStuck = ballSpeedYStuck;
    }
}
