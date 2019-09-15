import java.util.Random;

/**
 * Created by Ashwin Prasad on 9/23/2017.
 */
public  enum Powerups{
    INCPADDLESPEED, DECPADDLESPEED, INCPADDLESIZE, DECPADDLESIZE, INCBALLSPEED, DECBALLSPEED, INCBALLSIZE, DECBALLSIZE, ADDPADDLE, REMPADDLE, ADDBALL, REMBALL, REVBALLS, REVPADDLES, BLINKBALL, BLINKPADD, STICKPADD;
    public static Powerups getRandomUpgrade() {
        Random r = new Random();
        return values()[r.nextInt(values().length)];
    }
};
