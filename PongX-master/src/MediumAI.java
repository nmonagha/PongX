import java.util.ArrayList;

/**
 * Created by Natha on 10/12/2017.
 */
public class MediumAI {
    ArrayList<Paddles> paddles;
    ArrayList<Ball> balls;
    ArrayList<Upgrades> upgrades;
    Ball currTarget;
    Game g;
    public MediumAI(ArrayList<Paddles> p, ArrayList<Ball> balls, Game g, ArrayList<Upgrades> upgrades ) {
        if (p == null || balls == null || g == null) {
            //some error shit.
            System.out.println("ERROR: AI GIVEN NULL PARAMETERS!");
        }
        this.upgrades = upgrades;
        this.paddles = p;
        this.balls = balls;
        currTarget = balls.get(0);
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
        int temp = 0;
        double loc = 650;
        for(int i = 0; i < upgrades.size(); i++) {
            if (upgrades.get(i).getUpgSpeedY() < 0 && upgrades.get(i).getUpgY() < loc) {
                loc = upgrades.get(i).getUpgY();
                if (upgrades.get(i).getUpgX() < paddles.get(0).getPadX() && currTarget.getBallSpeedY() > 0) {
                    temp = 1;

                }
                if (upgrades.get(i).getUpgX() > paddles.get(0).getPadX() && currTarget.getBallSpeedY() > 0) {
                    temp = 2;
                }

            }


        }

        for (int k = 0; k < paddles.size(); k++) {
            Paddles pad =paddles.get(0);
            if(temp == 2) {
                g.moveTop(paddles.get(k).getSpeed());
            }
            if(temp == 1) {
                g.moveTop(paddles.get(k).getSpeed() * -1);
            }

            if (b.getBallX() < pad.getPadX() && temp == 0) {
                g.moveTop(paddles.get(k).getSpeed() * -1);

            }
            if (b.getBallX() > pad.getPadX() && temp == 0) {
                g.moveTop(paddles.get(k).getSpeed());

            }


        }

    }

    public Boolean hasHit() {
        return true;
    }





}
