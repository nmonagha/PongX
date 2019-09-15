import java.util.Random;

public class Upgrades {

    private int upgSize = 10;
    private int upgSpeedX = 0;
    private double upgSpeedY;
    private int upgX;
    private double upgY = 320;
    private Powerups type;
    Random r = new Random();

    public int getUpgSize() {
        return upgSize;
    }

    public int getUpgSpeedX() {
        return upgSpeedX;
    }

    public double getUpgSpeedY() {
        return upgSpeedY;
    }

    public int getUpgX() {
        return upgX;
    }

    public double getUpgY() {
        return upgY;
    }

    public void setUpgY(double upgY) {
         this.upgY = upgY;
    }

    public Powerups getType() {
        return type;
    }

    public Upgrades(Powerups type) {
        this.type = type;
        upgSpeedY = (r.nextInt() % 2 == 0) ? 1 : -1;
        upgX = r.nextInt(600 - 40) + 40;
        //upgX = 600;

    }
    //Increases the paddles speed
    void increasePaddleSpeed(Paddles p) {
        p.setSpeed(p.getSpeed() * 2);
    }

    //Decreases the paddles speed
    void decreasePaddleSpeed(Paddles p) {
        p.setSpeed(p.getSpeed() / 2);
    }

    //Increases the balls speed
    void increaseBallSpeed(Ball b) {
        b.setBallSpeedX(b.getBallSpeedX() * 2);
        b.setBallSpeedY(b.getBallSpeedY() * 2);
    }

    //Decreases the balls speed
    void decreaseBallSpeed(Ball b) {
        b.setBallSpeedX(b.getBallSpeedX() / 2);
        b.setBallSpeedY(b.getBallSpeedY() / 2);
    }

    //Increases the paddles size
    void increasePaddleSize(Paddles p) {
        p.setPadWidth(p.getPadWidth() * 2);
    }

    //Decreases the paddles size
    void decreasePaddleSize(Paddles p) {
        p.setPadWidth(p.getPadWidth() / 2);
    }

    //Increases the ball size
    void increaseBallSize(Ball b) {
        b.setBallSize(b.getBallSize() + 5);
    }

    //Decreases the ball size
    void decreaseBallSize(Ball b) {
        b.setBallSize(b.getBallSize() - 5);
    }

    //Adds an additional paddle to the game (done in Game)
    void addPaddle(int pad) {

    }

    //Removes an additional paddle to the game (done in Game)
    void removePaddle() {

    }

    //Adds a ball to the game
    Ball addBall() {
        Ball b = new Ball();
        b.setBallSpeedX((r.nextInt() % 2 == 0) ? 1 : -1);
        b.setBallSpeedY((r.nextInt() % 2 == 0) ? 1 : -1);
        return b;
    }

    //Removes a ball to the game (done in Game)
    void removeBall() {

    }

    //Causes the ball to blink
    void blinkingBall() {

    }

    //Causes the paddle to blink
    void blinkingPaddle() {

    }

    //Causes the ball to stick to the paddle
    void stickyPaddle() {

    }

    //Reverses the way the ball travels
    void reverseBallAngle(Ball b) {
        int angle = r.nextInt(3);
        if (angle == 0) {
            b.setBallX(b.getBallX()*-1);
        } else if (angle == 1) {
            b.setBallY(b.getBallY()*-1);
        } else {
            b.setBallX(b.getBallX()*-1);
            b.setBallY(b.getBallY()*-1);
        }
    }

    //Inverts the opponents paddle movement (done in Game)
    void invertOpponentMovement(Paddles p) {
        p.setSpeed(p.getSpeed()*-1);
    }


}
