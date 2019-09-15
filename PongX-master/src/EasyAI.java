import java.util.ArrayList;

/**
 * Created by Natha on 9/17/2017.
 */
public class EasyAI {
    ArrayList<Paddles> paddles;
    ArrayList<Ball> balls;
    Ball currTarget;
    Game g;
    public EasyAI(ArrayList<Paddles> p, ArrayList<Ball> balls, Game g) {
        if (p == null || balls == null || g == null) {
            //some error shit.
            System.out.println("ERROR: AI GIVEN NULL PARAMETERS!");
        }
        this.paddles = p;
        this.balls = balls;
        if (balls.size() != 0) {
            currTarget = balls.get(0);
        }
        this.g = g;


    }


    public void trackBalls() {
        Ball b = currTarget;

                for (int j = 0; j < balls.size(); j++) {
                        if (b.getBallY() > balls.get(j).getBallY()) {
                            b = balls.get(j);
                            currTarget = b;
                        }

                }




        for (int k = 0; k < paddles.size(); k++) {
            //  System.out.println(b.getBallY() + " " + b.getBallX());
            Paddles pad =paddles.get(0);
         //   System.out.println("Ball: " + b.getBallY());
           // System.out.println("Pad: " + pad.getPadX());
            if (b.getBallX() < pad.getPadX()) {
                g.moveTop(pad.getSpeed() * -1);
               // pad.setSpeed(pad.getSpeed() * -1);
            }
            if (b.getBallX() > pad.getPadX()) {
                g.moveTop(pad.getSpeed());
               // pad.setSpeed(pad.getSpeed() * -1);
            }


        }
        }

    public Boolean hasHit() {
        return true;
    }


}
