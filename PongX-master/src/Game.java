import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.io.*;
import java.util.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.event.*;

import javax.swing.*;

import static java.awt.event.KeyEvent.VK_A;

public class Game extends JPanel implements KeyListener, ActionListener {
    private int height, width;
    private Timer t = new Timer(5, this);
    private boolean start;
    private HashSet<String> keys = new HashSet<String>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Paddles> topPaddles = new ArrayList<>();
    private ArrayList<Paddles> botPaddles = new ArrayList<>();
    private ArrayList<Upgrades> upgrades = new ArrayList<>();
    private int topScore, botScore;
    private Graphics2D g2d;
    private Menu menu;
    private Pause pause;
    private Random r = new Random();
    private int scoreTopfinal;
    private int scoreBotfinal;
    private int botUpgs;
    private int topUpgs;
    private boolean firstRun = true; //when game object is created, it'll be the first run so set to true
    private static STATE finalState;
    private int gameCount = 2;
    private Scores scoreTop = new Scores();
    private Scores scoreBot = new Scores();
    private Scores gameNum = new Scores();
    private static final int focus = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private boolean p1Left, p1Right, p2Left, p2Right = false;
    private int[] upgTimerB = new int[15];
    private int[] upgTimerT = new int[15];
    private boolean p1stick, p2stick;
    public static STATE getFinalState() {
        return finalState;
    }

    public static int AILEVEL;

    public static int getAILEVEL() {
        return AILEVEL;
    }

    public static void setAILevel(int i) {
        AILEVEL = i;
    }


    public static enum STATE {
        MENU,
        GAME,
        GAME2,
        TUTORIAL,
        END,
        SCORE,
        PAUSE,
        AI
    }
//    private class MoveAction extends AbstractAction {
//        int direction; //-1 for left, 1 for right
//        int player;
//        MoveAction(int direction, int player) {
//            this.direction = direction;
//            this.player = player;
//
//
//        }
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            //idk
//
//            if (player == 0) {
//                //bottom paddles
//                //moveBot(direction * botPaddles.get(0).getSpeed());
//                //System.out.println("PLAYER 1");
//                if (direction == -1) {
//                    keys.add("LEFT");
////                    System.out.println("LEFT");
////                    System.out.println(keys.size());
//                    //moveBot(-1 * botPaddles.get(0).getSpeed());
//                    //System.out.println(botPaddles.get(0).getPadX());
//                }
//                else {
//                    keys.add("RIGHT");
////                    System.out.println("RIGHT");
////                    System.out.println(keys.size());
//                    //moveBot(botPaddles.get(0).getSpeed());
//                }
//            }
//            else {
//                //top paddles
//                //moveTop(direction * topPaddles.get(0).getSpeed());
//                if (direction == -1) {
//                    keys.add("LEFTA");
//                    //moveTop(-1 * topPaddles.get(0).getSpeed());
//                }
//                else {
//                    keys.add("RIGHTD");
//                    //moveTop(topPaddles.get(0).getSpeed());
//                }
//            }
////            if (keys.contains("LEFT")) {
////                System.out.println("sup");
////                keys.remove("LEFT");
////            } else if (keys.contains("RIGHT")) {
////                moveTop(botPaddles.get(0).getSpeed());
////                keys.remove("RIGHT");
////            }
//        }
//    }


    ;

    public void init() {
        System.out.println("Applet initializing");

    }
    public void start() {
        System.out.println("Applet starting");
    }
    public void stop() {
        System.out.println("Applet stopping");
    }
    public void destroy() {
        System.out.println("Applet destroyed");
    }

    public static STATE State = STATE.MENU; //start/state seem to have the same functionality

    public Game() {
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(37, 0), "player 1 left");
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(39, 0), "player 1 right");
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("A"), "player 2 left");
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("D"), "player 2 right");
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("P"), "pause");
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("M"), "menu");
        this.getActionMap().put("menu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botScore = 0;
                topScore = 0;
                resetPower();
                firstRun = true;
                if (topPaddles.size() != 0) {
                    for (int i = 0; i < topPaddles.size(); i++) {
                        topPaddles.remove(i);
                    }
                }
                if (botPaddles.size() != 0) {
                    for (int i = 0; i < botPaddles.size(); i++) {
                        botPaddles.remove(i);
                    }
                }
                if (balls.size() != 0) {
                    for (int i = 0; i < balls.size(); i++) {
                        balls.remove(i);
                    }
                }
                if (upgrades.size() != 0) {
                    for (int i = 0; i < upgrades.size(); i++) {
                        upgrades.remove(i);
                    }
                }
                State = STATE.MENU;
                repaint();
            }
        });
        this.getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (State == STATE.GAME || State == STATE.GAME2)
                    State = STATE.PAUSE;
                else if (State == STATE.PAUSE)
                    State = STATE.GAME;
            }
        });
        this.getActionMap().put("player 1 left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Left = true;
                p1Right = false;
            }
        });
        this.getActionMap().put("player 1 right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Right = true;
                p1Left = false;
            }
        });
        this.getActionMap().put("player 2 left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2Left = true;
                p2Right = false;
            }
        });
        this.getActionMap().put("player 2 right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2Right = true;
                p2Left = false;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("L"), "player 1 unstick");
        this.getActionMap().put("player 1 unstick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1stick = true;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke("E"), "player 2 unstick");
        this.getActionMap().put("player 2 unstick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2stick = true;
            }
        });
        //key releases
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(37, 0, true), "p1leftup");
        this.getActionMap().put("p1leftup", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Left = false;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(39, 0, true), "p1rightup");
        this.getActionMap().put("p1rightup", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Right = false;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(VK_A, 0, true), "p2leftup");
        this.getActionMap().put("p2leftup", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2Left = false;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "p2rightup");
        this.getActionMap().put("p2rightup", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2Right = false;
            }
        });
        this.getActionMap().put("p1unstick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1stick = false;
            }
        });
        this.getInputMap(focus).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0, true), "p2unstick");
        this.getActionMap().put("p2unstick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2stick = false;
            }
        });


        this.addKeyListener(this);
        this.addMouseListener(new MouseInput());
        setFocusable(true);
        //setFocusTraversalKeysEnabled(true);
        setVisible(true);
        start = false; //set true when one or two player clicked
        t.setInitialDelay(100);
        t.start();
    }

    public void drawPaddle(Paddles p) {
        Rectangle2D r = new Rectangle(p.getPadX(), p.getPadY(), p.getPadWidth(), p.getPadHeight());
        if (botPaddles.contains(p) && upgTimerB[13] > 0) {
            if ((upgTimerB[13] / 20 ) % 2 == 0) {
                g2d.setColor(Color.black);
            } else {
                g2d.setColor(new Color(238, 238, 238));
            }
        } else if (topPaddles.contains(p) && upgTimerT[13] > 0) {
            if ((upgTimerT[13] / 20 ) % 2 == 0) {
                g2d.setColor(Color.black);
            } else {
                g2d.setColor(new Color(238, 238, 238));
            }
        } else {
            g2d.setColor(Color.black);
        }
        g2d.fill(r);
    }

    public void drawBall(Ball b) {
        Ellipse2D e = new Ellipse2D.Double(b.getBallX(), b.getBallY(), b.getBallSize(), b.getBallSize());
        if (upgTimerB[12] > 0 || upgTimerT[12] > 0) {
            if ((upgTimerB[12] / 20 ) % 2 == 0) {
                g2d.setColor(Color.black);
            } else {
                g2d.setColor(Color.white);
            }
        } else {
            g2d.setColor(Color.black);
        }
        g2d.fill(e);
    }

    public void drawUpgrade(Upgrades u) {
        Ellipse2D e = new Ellipse2D.Double(u.getUpgX(), u.getUpgY(), u.getUpgSize(), u.getUpgSize());
        if (u.getType() == Powerups.INCBALLSIZE) {
            g2d.setColor(new Color(16, 255, 0));
        } else if (u.getType() == Powerups.DECBALLSIZE) {
            g2d.setColor(new Color(9, 122, 1));
        } else if (u.getType() == Powerups.INCBALLSPEED) {
            g2d.setColor(new Color(255, 21, 0));
        } else if (u.getType() == Powerups.DECBALLSPEED) {
            g2d.setColor(new Color(175, 14, 0));
        } else if (u.getType() == Powerups.INCPADDLESIZE) {
            g2d.setColor(new Color(242, 255, 0));
        } else if (u.getType() == Powerups.DECPADDLESIZE) {
            g2d.setColor(new Color(188, 198, 0));
        } else if (u.getType() == Powerups.INCPADDLESPEED) {
            g2d.setColor(new Color(0, 38, 255));
        } else if (u.getType() == Powerups.DECPADDLESPEED) {
            g2d.setColor(new Color(0, 22, 150));
        } else if (u.getType() == Powerups.ADDPADDLE) {
            g2d.setColor(new Color(255, 0, 246));
        } else if (u.getType() == Powerups.REMPADDLE) {
            g2d.setColor(new Color(140, 0, 135));
        } else if (u.getType() == Powerups.ADDBALL) {
            g2d.setColor(new Color(2, 229, 255));
        } else if (u.getType() == Powerups.REMBALL) {
            g2d.setColor(new Color(0, 134, 150));
        } else if (u.getType() == Powerups.REVBALLS) {
            g2d.setColor(new Color(255, 170, 0));
        } else if (u.getType() == Powerups.REVPADDLES) {
            g2d.setColor(new Color(177, 107, 0));
        } else if (u.getType() == Powerups.BLINKBALL) {
            g2d.setColor(new Color(105, 0, 194));
        } else if (u.getType() == Powerups.BLINKPADD) {
            g2d.setColor(new Color(225, 130, 132));
        } else if (u.getType() == Powerups.STICKPADD) {
            g2d.setColor(new Color(197, 0, 80));
        }
        g2d.fill(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        int code = e.getKeyCode();
//        switch (code) {
//            case KeyEvent.VK_LEFT:
//                keys.add("LEFT");
//                break;
//            case KeyEvent.VK_RIGHT:
//                keys.add("RIGHT");
//                break;
//            case KeyEvent.VK_A:
//                keys.add("LEFTA");
//                break;
//            case KeyEvent.VK_D:
//                keys.add("RIGHTD");
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        int code = e.getKeyCode();
//        switch (code) {
//            case KeyEvent.VK_LEFT:
//                keys.remove("LEFT");
//                break;
//            case KeyEvent.VK_RIGHT:
//                keys.remove("RIGHT");
//                break;
//            case KeyEvent.VK_A:
//                keys.remove("LEFTA");
//                break;
//            case KeyEvent.VK_D:
//                keys.remove("RIGHTD");
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        //this.isFocusable();
        super.paintComponent(g);
        g2d = (Graphics2D) g;

        height = 640;
        width = 640;
        //for beginning of game or after a score ?
        for (int i = 0; i < topPaddles.size(); i++) {
            drawPaddle(topPaddles.get(i));
        }
        for (int i = 0; i < botPaddles.size(); i++) {
            drawPaddle(botPaddles.get(i));
        }
        for (int i = 0; i < balls.size(); i++) {
            drawBall(balls.get(i));
        }
        if (State == STATE.AI) {
            Font font = new Font("impact", Font.BOLD, 30);
            g.setFont(font);
            g.setColor(Color.red);
            g2d.drawString("Please Select Difficulty", 640 / 2 - 125, 50);
            g.setColor(Color.black);
            Rectangle easy = new Rectangle(640 / 2 - 45, 125, 90, 50);
            Rectangle medium = new Rectangle(640 / 2 - 70, 200, 140, 50);
            Rectangle hard = new Rectangle(640 / 2 - 50, 275, 100, 50);
            g.drawString("Easy", easy.x + 20, easy.y + 35);
            g.drawString("Medium", medium.x + 20, medium.y + 35);
            g.drawString("Hard", hard.x + 20, hard.y + 35);
            Rectangle back = new Rectangle(640 / 2 - 50, 550, 100, 50);
            g.drawString("Back", back.x + 20, back.y + 35);
            ((Graphics2D) g).draw(back);
            ((Graphics2D) g).draw(easy);
            ((Graphics2D) g).draw(medium);
            ((Graphics2D) g).draw(hard);

        }
        if (State == STATE.GAME) { //or if start
            //if it's the first run of the game set up the initial state of the game

            EasyAI eAI;
            MediumAI mAI;
            HardAI hAI;
            if (firstRun) {
                Paddles p = new Paddles(0, 290);
                Paddles q = new Paddles(1, 290);
                Ball b = new Ball();

                botPaddles.add(p);
                topPaddles.add(q);
                balls.add(b);
                start = false;
                //eAI = new EasyAI(topPaddles, balls, this);
                //AI = true;
                //eAI.trackBalls();
                String scoreB = "Bottom: " + new Integer(botScore).toString();
                String scoreT = "Top: " + new Integer(topScore).toString();
                g2d.drawString(scoreB, 10, height / 2 - 15);
                g2d.drawString(scoreT, width - 50, height / 2 - 15);
                firstRun = false;
            } else {
                if (getAILEVEL() == 0) {
                    eAI = new EasyAI(topPaddles, balls, this);
                    eAI.trackBalls();
                }
                else if (getAILEVEL() == 1) {
                    mAI = new MediumAI(topPaddles, balls, this, upgrades);
                    mAI.trackBalls();
                }
                else if (getAILEVEL() == 2) {
                    hAI = new HardAI(topPaddles, balls, this, upgrades);
                    hAI.trackBalls();
                }
                //not sure what to update yet as game is running yet
                String scoreB = "Bottom: " + new Integer(botScore).toString();
                String scoreT = "Top: " + new Integer(topScore).toString();
                g2d.drawString(scoreB, 10, height / 2 - 15);
                g2d.drawString(scoreT, width - 50, height / 2 - 15);
            }
            for (int i = 0; i < topPaddles.size(); i++) {
                drawPaddle(topPaddles.get(i));
            }
            for (int i = 0; i < botPaddles.size(); i++) {
                drawPaddle(botPaddles.get(i));
            }
            for (int i = 0; i < balls.size(); i++) {
                drawBall(balls.get(i));
            }
            //change getRandomUpgrade to a specific one for testing

            if (r.nextInt(1000) == 50) {
                Upgrades u = new Upgrades(Powerups.getRandomUpgrade());
                upgrades.add(u);
            }
            for (int i = 0; i < upgrades.size(); i++) {
                drawUpgrade(upgrades.get(i));
            }
            for (int i = 0; i < upgTimerB.length; i++) {
                if (upgTimerB[i] > 0) {
                    if (upgTimerB[i] == 1) {
                        if (i == 0) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() - 5);
                        } else if (i == 1) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() + 5);
                        } else if (i == 2) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() / 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() / 2);
                            }
                        } else if (i == 3) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() * 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() * 2);
                            }
                        } else if (i == 4) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() / 2);
                        } else if (i == 5) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() * 2);
                        } else if (i == 6) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() / 2);
                        } else if (i == 7) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() * 2);
                        } else if (i == 8) {
                            if (botPaddles.size() > 1) {
                                botPaddles.remove(botPaddles.size() - 1);
                            }
                        } else if (i == 9) {
                            if (balls.size() > 1) {
                                balls.remove(balls.size() - 1);
                            }
                        } else if (i == 10) {
                            //don't need to anything for reverse ball angles
//                            for (int k = 0; k < balls.size(); k++) {
//                                upgrades.get(i).reverseBallAngle(balls.get(k));
//                            }
                        } else if (i == 11) {
                            for (int k = 0; k < topPaddles.size(); k++) {
                                topPaddles.get(i).setSpeed(topPaddles.get(i).getSpeed() * -1);
                            }
                        } else if (i == 12) {
                            //don't need to fix anything for blinking ball
                        } else if (i == 13) {
                            //don't need to fix anything for blinking paddle
                        } else if (i == 14) {
                            for(int k = 0; k < balls.size(); k++) {
                                if (k < balls.size() && k >= 0) {
                                    if (balls.get(k).getStuck1()) {
                                        balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedXStuck());
                                        balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedYStuck());
                                        balls.get(k).setBallSpeedXStuck(0);
                                        balls.get(k).setBallSpeedYStuck(0);
                                        balls.get(k).setStuck1(false);
                                        //System.out.println(balls.get(k).getBallSpeedX() + " " + balls.get(k).getBallSpeedY() + " ");
                                    }
                                }
                            }
                            for(int k = 0; k < botPaddles.size(); k++) {
                                if (k < botPaddles.size() && k >= 0) {
                                    botPaddles.get(k).setStuck(false);
                                }
                            }
                        }
                        botUpgs--;
                    }
                    upgTimerB[i]--;
                }
            }
        } else if (State == STATE.GAME2) {
            //or if start
            //if it's the first run of the game set up the initial state of the game
            if (firstRun) {
                Paddles p = new Paddles(0, 290);
                Paddles q = new Paddles(1, 290);
                Ball b = new Ball();

                botPaddles.add(p);
                topPaddles.add(q);
                balls.add(b);
                start = false;
                //eAI = new EasyAI(topPaddles, balls, this);
                String scoreB = "Bottom: " + new Integer(botScore).toString();
                String scoreT = "Top: " + new Integer(topScore).toString();
                g2d.drawString(scoreB, 10, height / 2);
                g2d.drawString(scoreT, width - 50, height / 2);
                firstRun = false;
            } else {
                //not sure what to update yet as game is running yet
                String scoreB = "Bottom: " + new Integer(botScore).toString();
                String scoreT = "Top: " + new Integer(topScore).toString();
                g2d.drawString(scoreB, 10, height / 2);
                g2d.drawString(scoreT, width - 50, height / 2);
            }
            for (int i = 0; i < topPaddles.size(); i++) {
                drawPaddle(topPaddles.get(i));
            }
            for (int i = 0; i < botPaddles.size(); i++) {
                drawPaddle(botPaddles.get(i));
            }
            for (int i = 0; i < balls.size(); i++) {
                drawBall(balls.get(i));
            }
            if (r.nextInt(1000) == 50) {
                Upgrades u = new Upgrades(Powerups.getRandomUpgrade());
                upgrades.add(u);
            }
            for (int i = 0; i < upgrades.size(); i++) {
                drawUpgrade(upgrades.get(i));
            }
            //t.start();
            for (int i = 0; i < upgTimerB.length; i++) {
                if (upgTimerB[i] > 0) {
                    if (upgTimerB[i] == 1) {
                        if (i == 0) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() - 5);
                        } else if (i == 1) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() + 5);
                        } else if (i == 2) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() / 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() / 2);
                            }
                        } else if (i == 3) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() * 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() * 2);
                            }
                        } else if (i == 4) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() / 2);
                        } else if (i == 5) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() * 2);
                        } else if (i == 6) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() / 2);
                        } else if (i == 7) {
                            for (int k = 0; k < botPaddles.size(); k++)
                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() * 2);
                        } else if (i == 8) {
                            if (botPaddles.size() > 1) {
                                botPaddles.remove(botPaddles.size() - 1);
                            }
                        } else if (i == 9) {
                            if (balls.size() > 1) {
                                balls.remove(balls.size() - 1);
                            }
                        } else if (i == 10) {
                            //don't need to anything for reverse ball angles
//                            for (int k = 0; k < balls.size(); k++) {
//                                upgrades.get(i).reverseBallAngle(balls.get(k));
//                            }
                        } else if (i == 11) {
                            for (int k = 0; k < topPaddles.size(); k++) {
                                topPaddles.get(i).setSpeed(topPaddles.get(i).getSpeed() * -1);
                            }
                        } else if (i == 12) {
                            //don't need to fix anything for blinking ball
                        } else if (i == 13) {
                            //don't need to fix anything for blinking paddle
                        } else if (i == 14) {
                            //sticky paddle
                            for(int k = 0; k < balls.size(); k++) {
                                if (k < balls.size() && k >= 0) {
                                    if (balls.get(k).getStuck1()) {
                                        balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedXStuck());
                                        balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedYStuck());
                                        balls.get(k).setBallSpeedXStuck(0);
                                        balls.get(k).setBallSpeedYStuck(0);
                                        balls.get(k).setStuck1(false);
                                        //System.out.println("hey");
                                    }
                                }
                            }
                            for(int k = 0; k < botPaddles.size(); k++) {
                                if (k < botPaddles.size() && k >= 0) {
                                    botPaddles.get(k).setStuck(false);
                                }
                            }
                        }
                        botUpgs--;
                    }
                    upgTimerB[i]--;
                }
            }
            for (int i = 0; i < upgTimerT.length; i++) {
                if (upgTimerT[i] > 0) {
                    if (upgTimerT[i] == 1) {
                        if (i == 0) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() - 5);
                        } else if (i == 1) {
                            for (int k = 0; k < balls.size(); k++)
                                balls.get(k).setBallSize(balls.get(k).getBallSize() + 5);
                        } else if (i == 2) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() / 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() / 2);
                            }
                        } else if (i == 3) {
                            for (int k = 0; k < balls.size(); k++) {
                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() * 2);
                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() * 2);
                            }
                        } else if (i == 4) {
                            for (int k = 0; k < topPaddles.size(); k++)
                                topPaddles.get(k).setPadWidth(topPaddles.get(k).getPadWidth() / 2);
                        } else if (i == 5) {
                            for (int k = 0; k < topPaddles.size(); k++)
                                topPaddles.get(k).setPadWidth(topPaddles.get(k).getPadWidth() * 2);
                        } else if (i == 6) {
                            for (int k = 0; k < topPaddles.size(); k++)
                                topPaddles.get(k).setSpeed(topPaddles.get(k).getSpeed() / 2);
                        } else if (i == 7) {
                            for (int k = 0; k < topPaddles.size(); k++)
                                topPaddles.get(k).setSpeed(topPaddles.get(k).getSpeed() * 2);
                        } else if (i == 8) {
                            if (topPaddles.size() > 1) {
                                topPaddles.remove(topPaddles.size() - 1);
                            }
                        } else if (i == 9) {
                            if (balls.size() > 1) {
                                balls.remove(balls.size() - 1);
                            }
                        } else if (i == 10) {
                            //don't need to anything for reverse ball angles
//                            for (int k = 0; k < balls.size(); k++) {
//                                upgrades.get(i).reverseBallAngle(balls.get(k));
//                            }
                        } else if (i == 11) {
                            for (int k = 0; k < botPaddles.size(); k++) {
                                botPaddles.get(i).setSpeed(botPaddles.get(i).getSpeed() * -1);
                            }
                        } else if (i == 12) {
                            //don't need to fix anything for blinking ball
                        } else if (i == 13) {
                            //don't need to fix anything for blinking paddle
                        } else if (i == 14) {
                            //sticky paddle
                            for(int k = 0; k < balls.size(); k++) {
                                if (k < balls.size() && k >= 0) {
                                    if (balls.get(k).getStuck2()) {
                                        balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedXStuck());
                                        balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedYStuck());
                                        balls.get(k).setBallSpeedXStuck(0);
                                        balls.get(k).setBallSpeedYStuck(0);
                                        balls.get(k).setStuck2(false);
                                        //System.out.println("hey");
                                    }
                                }
                            }
                            for(int k = 0; k < topPaddles.size(); k++) {
                                if (k < topPaddles.size() && k >= 0) {
                                    topPaddles.get(k).setStuck(false);
                                }
                            }
                        }
                        topUpgs--;
                    }
                    upgTimerT[i]--;
                }
            }
        } else if (State == STATE.MENU) {
            menu = new Menu();
            menu.render(g);
        } else if (State == STATE.PAUSE) {
            Font font = new Font("impact", Font.BOLD, 30);
            g.setFont(font);
            pause = new Pause();
            pause.render(g);

        } else if (State == STATE.END) {
            Font font = new Font("impact", Font.BOLD, 30);
            g.setFont(font);

            if (botScore > topScore) {
                g.setColor(Color.red);
                g2d.drawString("Bottom Player Wins!", 640 / 2 - 125, 100);
                g.setColor(Color.black);
                g2d.drawString("Score", 640 / 2 - 40, 150);
                g2d.drawString("Top: " + scoreTopfinal + " Bottom: " + scoreBotfinal, 640 / 2 - 95, 200);
                //scoreBot.addScoresBottom(scoreBotfinal);
                //scoreTop.addScoresTop(scoreTopfinal);
                botScore = 0;
                topScore = 0;
                Rectangle back = new Rectangle(640 / 2 - 250, 350, 200, 50);
                Rectangle newGame = new Rectangle(640 / 2 - 250, 425, 160, 50);
                g.drawString("Back to Menu", back.x + 20, back.y + 35);
                g.drawString("New Game", newGame.x + 20, newGame.y + 35);
                ((Graphics2D) g).draw(back);
                ((Graphics2D) g).draw(newGame);
                if (topPaddles.size() != 0) {
                    for (int i = 0; i < topPaddles.size(); i++) {
                        drawPaddle(topPaddles.remove(i));
                    }
                }
                if (botPaddles.size() != 0) {
                    for (int i = 0; i < botPaddles.size(); i++) {
                        drawPaddle(botPaddles.remove(i));
                    }
                }
                if (balls.size() != 0) {
                    for (int i = 0; i < balls.size(); i++) {
                        drawBall(balls.remove(i));
                    }
                }
                if (upgrades.size() != 0) {
                    for (int i = 0; i < upgrades.size(); i++) {
                        drawUpgrade(upgrades.remove(i));
                    }
                }
                firstRun = true;
            } else {
                g.setColor(Color.red);
                g2d.drawString("Top Player Wins!", 640 / 2 - 100, 100);
                g.setColor(Color.black);
                g2d.drawString("Score", 640 / 2 - 40, 150);
                g2d.drawString("Top: " + scoreTopfinal + " Bottom: " + scoreBotfinal, 640 / 2 - 95, 200);
                //scoreBot.addScoresBottom(scoreTopfinal);
                //scoreTop.addScoresTop(scoreBotfinal);
                botScore = 0;
                topScore = 0;
                Rectangle back = new Rectangle(640 / 2 - 250, 350, 200, 50);
                Rectangle newGame = new Rectangle(640 / 2 - 250, 425, 160, 50);
                g.drawString("Back to Menu", back.x + 20, back.y + 35);
                g.drawString("New Game", newGame.x + 20, newGame.y + 35);
                ((Graphics2D) g).draw(back);
                ((Graphics2D) g).draw(newGame);
                if (topPaddles.size() != 0) {
                    for (int i = 0; i < topPaddles.size(); i++) {
                        drawPaddle(topPaddles.remove(i));
                    }
                }
                if (botPaddles.size() != 0) {
                    for (int i = 0; i < botPaddles.size(); i++) {
                        drawPaddle(botPaddles.remove(i));
                    }
                }
                if (balls.size() != 0) {
                    for (int i = 0; i < balls.size(); i++) {
                        drawBall(balls.remove(i));
                    }
                }
                if (upgrades.size() != 0) {
                    for (int i = 0; i < upgrades.size(); i++) {
                        drawUpgrade(upgrades.remove(i));
                    }
                }
                firstRun = true;
            }
        } else if (State == STATE.SCORE) {
            Font font = new Font("impact", Font.BOLD, 20);
            Font font2 = new Font("impact", Font.BOLD, 30);

            g.setFont(font2);
            g2d.drawString("Previous Scores", 640 / 2 - 85, 50);
            Rectangle back = new Rectangle(640 / 2 - 50, 550, 100, 50);
            g.drawString("Back", back.x + 20, back.y + 35);
            ((Graphics2D) g).draw(back);

            g.setFont(font);
            g.setColor(Color.black);
            if (scoreBot.getScoresBottom().size() != 0) {
                g2d.drawString("Top Score:", 640 / 2 - 90, 100);
                g.setColor(Color.red);
                if (scoreBot.getSizeBot() > 20) {
                   // while (scoreBot.getSizeBot() > 20) {
                    //    scoreBot.removeScoresBot(0);
                   // }
                }
                for (int i = 0; i < scoreBot.getSizeBot(); i++) {
                    g2d.drawString(scoreBot.getScoresBottom().get(i).toString(), 640 / 2 + 10, 260 / 2 + 20 * (i));
                }
            }
            g.setColor(Color.black);
            if (scoreTop.getScoresTop().size() != 0) {
                g2d.drawString("Bottom Score:", 640 / 2 + 160, 100);
                g.setColor(Color.red);
                if (scoreTop.getSizeTop() > 20) {
                    //while (scoreTop.getSizeTop() > 20) {
                      //  scoreTop.removeScoresTop(0);
                    //}
                }
                for (int i = 0; i < scoreTop.getSizeTop(); i++) {
                    g2d.drawString(scoreTop.getScoresTop().get(i).toString(), 640 / 2 + 230, 260 / 2 + 20 * (i));
                }
            }
            g.setColor(Color.black);
            if (scoreTop.getScoresTop().size() != 0) {
                g2d.drawString("Game: ", 640 / 2 - 280, 100);
                g.setColor(Color.red);
                if (gameNum.getSizeGameCount() > 20) {
                    while (gameNum.getSizeGameCount() > 20) {
                        gameNum.removeGameCount(0);
                    }
                }
                for (int i = 0; i < gameNum.getSizeGameCount(); i++) {
                    g2d.drawString(gameNum.getGameCount().get(i).toString(), 640 / 2 - 240, 260 / 2 + 20 * (i));
                }
            }
        } else if (State == STATE.TUTORIAL){
            Font font = new Font("impact", Font.BOLD, 20);
            Font font2 = new Font("impact", Font.BOLD, 30);
            Font font3 = new Font("impact", Font.BOLD, 10);

            g.setFont(font2);
            g.setColor(Color.black);
            g2d.drawString("Help", 640 / 2 - 30, 50);

            g.setFont(font2);
            g.setColor(Color.red);
            g2d.drawString("Controls", 640 / 2 - 300, 100);

            g.setFont(font);
            g.setColor(Color.black);
            g2d.drawString("Player 1 (Bottom): Use left and right arrow-keys", 640 / 2 - 300, 130);
            g2d.drawString("Player 2 (Top): Use 'A' and 'D' keys", 640 / 2 - 300, 160);
            g2d.drawString("Press 'P' key to pause/unpause", 640 / 2 - 300, 190);
            g2d.drawString("Press 'M' to return to menu", 640 / 2 - 300, 220);

            g.setFont(font2);
            g.setColor(Color.red);
            g2d.drawString("Mechanics", 640 / 2 - 300, 250);

            g.setFont(font);
            g.setColor(Color.black);
            g2d.drawString("Use paddle to obtain temporary power-ups", 640 / 2 - 300, 280);
            //g2d.drawString("");
            g.setFont(font3);
            /*if (u.getType() == Powerups.INCBALLSIZE) {
            g2d.setColor(new Color(16, 255, 0));
        } else if (u.getType() == Powerups.DECBALLSIZE) {
            g2d.setColor(new Color(9, 122, 1));
        } else if (u.getType() == Powerups.INCBALLSPEED) {
            g2d.setColor(new Color(255, 21, 0));
        } else if (u.getType() == Powerups.DECBALLSPEED) {
            g2d.setColor(new Color(175, 14, 0);
        } else if (u.getType() == Powerups.INCPADDLESIZE) {
            g2d.setColor(new Color(242, 255, 0));
        } else if (u.getType() == Powerups.DECPADDLESIZE) {
            g2d.setColor(new Color(188, 198, 0));
        } else if (u.getType() == Powerups.INCPADDLESPEED) {
            g2d.setColor(new Color(0, 38, 255));
        } else if (u.getType() == Powerups.DECPADDLESPEED) {
            g2d.setColor(new Color(0, 22, 150));
        } else if (u.getType() == Powerups.ADDPADDLE) {
            g2d.setColor(new Color(255, 0, 246));
        } else if (u.getType() == Powerups.REMPADDLE) {
            g2d.setColor(new Color(140, 0, 135));
        } else if (u.getType() == Powerups.ADDBALL) {
            g2d.setColor(new Color(2, 229, 255));
        } else if (u.getType() == Powerups.REMBALL) {
            g2d.setColor(new Color(0, 134, 150));
        } else if (u.getType() == Powerups.REVBALLS) {
            g2d.setColor(new Color(255, 170, 0));
        } else if (u.getType() == Powerups.REVPADDLES) {
            g2d.setColor(new Color(177, 107, 0));
        } else if (u.getType() == Powerups.BLINKBALL) {
            g2d.setColor(new Color(105, 0, 194));
        } else if (u.getType() == Powerups.BLINKPADD) {
            g2d.setColor(new Color(225, 130, 132));
        } else if (u.getType() == Powerups.STICKPADD) {
            g2d.setColor(new Color(197, 0, 80));*/
            g.setColor(new Color(16, 255, 0));
            g2d.drawString("Increase ball size", 640 / 2 - 300, 295);

            g.setFont(font3);
            g.setColor(new Color(9, 122, 1));
            g2d.drawString("Decrease ball size", 640 / 2 - 300, 310);

            g.setFont(font3);
            g.setColor(new Color(255, 21, 0));
            g2d.drawString("Increase ball speed", 640 / 2 - 300, 325);

            g.setFont(font3);
            g.setColor(new Color(175, 14, 0));
            g2d.drawString("Decrease ball speed", 640 / 2 - 300, 340);

            g.setFont(font3);
            g.setColor(new Color(242, 255, 0));
            g2d.drawString("Increase paddle size", 640 / 2 - 300, 355);

            g.setFont(font3);
            g.setColor(new Color(188, 198, 0));
            g2d.drawString("Decrease paddle size", 640 / 2 - 300, 370);

            g.setFont(font3);
            g.setColor(new Color(0, 38, 255));
            g2d.drawString("Increase paddle speed", 640 / 2 - 300, 385);

            g.setFont(font3);
            g.setColor(new Color(0, 22, 150));
            g2d.drawString("Decrease paddle speed", 640 / 2 - 300, 400);

            g.setFont(font3);
            g.setColor(new Color(255, 0, 246));
            g2d.drawString("Add a paddle", 640 / 2 - 300, 415);

            g.setFont(font3);
            g.setColor(new Color(140, 0, 135));
            g2d.drawString("Remove a paddle", 640 / 2 - 300, 430);

            g.setFont(font3);
            g.setColor(new Color(2, 229, 255));
            g2d.drawString("Add a ball", 640 / 2 - 300, 445);

            g.setFont(font3);
            g.setColor(new Color(0, 134, 150));
            g2d.drawString("Remove a ball", 640 / 2 - 300, 460);

            g.setFont(font3);
            g2d.setColor(new Color(255, 170, 0));
            g2d.drawString("Reverse ball direction", 640 / 2 - 300, 475);

            g.setFont(font3);
            g2d.setColor(new Color(177, 107, 0));
            g2d.drawString("Invert opponent paddle movement", 640 / 2 - 300, 490);

            g.setFont(font3);
            g2d.setColor(new Color(105, 0, 194));
            g2d.drawString("Balls will blink", 640 / 2 - 300, 505);

            g.setFont(font3);
            g2d.setColor(new Color(225, 130, 132));
            g2d.drawString("Paddles will blink", 640 / 2 - 300, 520);

            g.setFont(font3);
            g2d.setColor(new Color(197, 0, 80));
            g2d.drawString("Your paddle will catch and release ball on keypress of L (P1) or E (P2)", 640 / 2 - 300, 535);

            g.setFont(font2);
            g.setColor(Color.black);
            Rectangle back = new Rectangle(640 / 2 - 50, 550, 100, 50);
            g.drawString("Back", back.x + 20, back.y + 35);
            ((Graphics2D) g).draw(back);
        }


    }

    //resets to base conditions
    public void resetPower() {
        if (botPaddles.size() > 1) {
            for (int i = 1; i < botPaddles.size(); i++) {
                botPaddles.remove(i);
            }
        }
        //add check for multiple balls index  and size 0
        botPaddles.get(0).setSpeed(2);
        botPaddles.get(0).setPadX(290);
        botPaddles.get(0).setPadWidth(60);
        botPaddles.get(0).setStuck(false);
        if (topPaddles.size() > 1) {
            for (int i = 1; i < topPaddles.size(); i++) {
                topPaddles.remove(i);
            }
        }
        topPaddles.get(0).setSpeed(2);
        topPaddles.get(0).setPadX(290);
        topPaddles.get(0).setPadWidth(60);
        if (balls.size() > 1) {
            for (int i = 1; i < balls.size(); i++) {
                balls.remove(i);
            }
        }
        for (int i = 0; i < upgTimerB.length; i++) {
            upgTimerB[i] = 0;
        }
        for (int i = 0; i < upgTimerT.length; i++) {
            upgTimerT[i] = 0;
        }
        balls.get(0).setBallX(315);
        balls.get(0).setBallY(315);
        balls.get(0).setBallSize(10);
        balls.get(0).setStuck1(false);
        balls.get(0).setBallSpeedX((r.nextInt() % 2 == 0) ? 1 : -1);
        balls.get(0).setBallSpeedY((r.nextInt() % 2 == 0) ? 1 : -1);
        balls.get(0).setBallSpeedXStuck(0);
        balls.get(0).setBallSpeedYStuck(0);
        botUpgs = 0;
        topUpgs = 0;
        //t.setDelay(100); //might not work as i think it will lol
    }

    //sup
    @Override
    public void actionPerformed(ActionEvent e) {
        //this.setFocusable(true);
        //if (State != STATE.END && State != STATE.PAUSE && State != STATE.MENU) {
        if (State == STATE.GAME || State == STATE.GAME2) {
            for (int i = 0; i < balls.size(); i++) {
                if (i < balls.size() && i >= 0) {
                    //eAI.trackBalls();
                    //if it hits a side
                    if (balls.get(i).getBallX() < 0 || balls.get(i).getBallX() > width - balls.get(i).getBallSize()) {
                        balls.get(i).setBallSpeedX(-1 * balls.get(i).getBallSpeedX());
                    }
                    //if it hits the top or bottom
                    else if (balls.get(i).getBallY() < 0) {
                        botScore++;
                        resetPower();
                    } else if (balls.get(i).getBallY() + balls.get(i).getBallSize() > height) {
                        topScore++;
                        resetPower();
                    }

                    //check if a ball hit a pad
                    for (int j = 0; j < botPaddles.size(); j++) {
                        if (i < balls.size() && i >= 0 && j < botPaddles.size() && j >= 0) {
                            if (600 - (balls.get(i).getBallY() + botPaddles.get(j).getPadHeight() + balls.get(i).getBallSize()) == 0) {
                                if (balls.get(i).getBallX() + balls.get(i).getBallSize() >= botPaddles.get(j).getPadX() &&
                                        balls.get(i).getBallX() <= botPaddles.get(j).getPadX() + botPaddles.get(j).getPadWidth()) {
                                    balls.get(i).setBallSpeedY(balls.get(i).getBallSpeedY() * -1);
                                    if (botPaddles.get(j).getStuck()) {
                                        balls.get(i).setBallSpeedXStuck(balls.get(i).getBallSpeedX());
                                        balls.get(i).setBallSpeedYStuck(balls.get(i).getBallSpeedY());
                                        balls.get(i).setBallSpeedX(0);
                                        balls.get(i).setBallSpeedY(0);
                                        balls.get(i).setStuck1(true);
                                    }
                                }
                            }
                            // pressed keys
                            // pressed keys
                            //if (keys.size() == 1) {
//                    if (keys.contains("LEFTA")) {
//                        moveTop(-1 * topPaddles.get(j).getSpeed());
//                        keys.remove("LEFTA");
//                    } else if (keys.contains("RIGHTD")) {
//                        moveTop(topPaddles.get(j).getSpeed());
//                        keys.remove("RIGHTD");
//                    }

                            //}
                        }
                    }
                    //top pad
                    for (int j = 0; j < topPaddles.size(); j++) {
                        if (i < balls.size() && i >= 0 && j < topPaddles.size() && j >= 0) {
                            if (balls.get(i).getBallY() - topPaddles.get(j).getPadHeight() - balls.get(i).getBallSize() == 0) {
                                if (balls.get(i).getBallX() + balls.get(i).getBallSize() >= topPaddles.get(j).getPadX() &&
                                        balls.get(i).getBallX() <= topPaddles.get(j).getPadX() + topPaddles.get(j).getPadWidth()) {
                                    balls.get(i).setBallSpeedY(balls.get(i).getBallSpeedY() * -1);
                                    if (topPaddles.get(j).getStuck()) {
                                        balls.get(i).setBallSpeedXStuck(balls.get(i).getBallSpeedX());
                                        balls.get(i).setBallSpeedYStuck(balls.get(i).getBallSpeedY());
                                        balls.get(i).setBallSpeedX(0);
                                        balls.get(i).setBallSpeedY(0);
                                        balls.get(i).setStuck2(true);
                                    }
                                }
                            }
                            //if (keys.size() == 1) {
                        }
                    }
                    if (p1Right) {
                        moveBot(botPaddles.get(0).getSpeed());
                        //p1Right = false;
                    }
                    if (p1Left) {
                        moveBot(-1 * botPaddles.get(0).getSpeed());
                        //p1Left = false;
                    }
                    if (p1stick) {
                        if (i < balls.size() && i >= 0) {
                            if (balls.get(i).getStuck1()) {
                                balls.get(i).setBallSpeedX(balls.get(i).getBallSpeedXStuck());
                                balls.get(i).setBallSpeedY(balls.get(i).getBallSpeedYStuck());
                                //System.out.println(balls.get(i).getBallSpeedX() + " " + balls.get(i).getBallSpeedY() + " ");
                                balls.get(i).setBallSpeedXStuck(0);
                                balls.get(i).setBallSpeedYStuck(0);
                                balls.get(i).setStuck1(false);
                            }
                        }
                    }
                    if (p2stick) {
                        if (i < balls.size() && i >= 0) {
                            if (balls.get(i).getStuck2()) {
                                balls.get(i).setBallSpeedX(balls.get(i).getBallSpeedXStuck());
                                balls.get(i).setBallSpeedY(balls.get(i).getBallSpeedYStuck());
                                //System.out.println(balls.get(i).getBallSpeedX() + " " + balls.get(i).getBallSpeedY() + " ");
                                balls.get(i).setBallSpeedXStuck(0);
                                balls.get(i).setBallSpeedYStuck(0);
                                balls.get(i).setStuck2(false);
                            }
                        }
                    }
                    if (State == STATE.GAME2) {
                        if (p2Left) {
                            moveTop(-1 * topPaddles.get(0).getSpeed());
                            //p2Left = false;
                        }
                        if (p2Right) {
                            moveTop(topPaddles.get(0).getSpeed());
                            //p2Right = false;
                        }
                    }

//                if (keys.contains("LEFT")) {
//                    moveBot(-1 * botPaddles.get(0).getSpeed());
//                    keys.remove("LEFT");
//                } else if (keys.contains("RIGHT")) {
//                    moveBot(botPaddles.get(0).getSpeed());
//                    keys.remove("RIGHT");
//                }
                    if (i < balls.size() && i >= 0) {
                        balls.get(i).setBallX(balls.get(i).getBallX() + balls.get(i).getBallSpeedX());
                        balls.get(i).setBallY(balls.get(i).getBallY() + balls.get(i).getBallSpeedY());
                    }
                }

            }

            //check if upgrade hits bot paddle
            for (int i = 0; i < upgrades.size(); i++) {
                for (int j = 0; j < botPaddles.size(); j++) {
                    //only three powerups enabled
                    if (i < upgrades.size() && j < botPaddles.size() && i >= 0 && j >= 0) {
                        upgrades.get(i).setUpgY(upgrades.get(i).getUpgY() + upgrades.get(i).getUpgSpeedY());

                        if (upgrades.get(i).getUpgY() < -5 || upgrades.get(i).getUpgY() > 645) {
                            upgrades.remove(i);
                        //hits bottom paddle
                        } else if (botUpgs < 3 && upgrades.get(i).getUpgY() > 500) {
                            //only three powerups enabled
                            if (upgrades.get(i).getUpgY() + upgrades.get(i).getUpgSize() <= height - botPaddles.get(j).getPadHeight() - 20 - upgrades.get(i).getUpgSize() && upgrades.get(i).getUpgY() + upgrades.get(i).getUpgSize() >= height - botPaddles.get(j).getPadHeight() - 25 - upgrades.get(i).getUpgSize()) {
                                if (upgrades.get(i).getUpgX() + upgrades.get(i).getUpgSize() >= botPaddles.get(j).getPadX() &&
                                        upgrades.get(i).getUpgX() <= botPaddles.get(j).getPadX() + botPaddles.get(j).getPadWidth()) {
                                    //powerup
                                    if (upgrades.get(i).getType() == Powerups.INCBALLSIZE) {
                                        if (upgTimerB[0] > 0) {
                                            botUpgs--;
                                        }else if (upgTimerB[1] > 0) {
                                            upgTimerB[1] = 0;
                                            for (int k = 0; k < balls.size(); k++)
                                                balls.get(k).setBallSize(balls.get(k).getBallSize() + 5);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[0] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).increaseBallSize(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECBALLSIZE) {
                                        if (upgTimerB[1] > 0) {
                                            botUpgs--;
                                        } else if (upgTimerB[0] > 0) {
                                            upgTimerB[0] = 0;
                                            for (int k = 0; k < balls.size(); k++)
                                                balls.get(k).setBallSize(balls.get(k).getBallSize() - 5);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[1] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).decreaseBallSize(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCBALLSPEED) {
                                        if (upgTimerB[2] > 0) {
                                            botUpgs--;
                                        } else if (upgTimerB[3] > 0) {
                                            upgTimerB[3] = 0;
                                            for (int k = 0; k < balls.size(); k++) {
                                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() * 2);
                                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() * 2);
                                            }
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[2] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).increaseBallSpeed(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECBALLSPEED) {
                                        if (upgTimerB[3] > 0) {
                                            botUpgs--;
                                        } else if (balls.get(0).getBallSpeedX() == 1 || balls.get(0).getBallSpeedY() == 1) {
                                            botUpgs--;
                                        }else if (upgTimerB[2] > 0) {
                                            upgTimerB[2] = 0;
                                            for (int k = 0; k < balls.size(); k++) {
                                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() / 2);
                                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() / 2);
                                            }
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[3] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).decreaseBallSpeed(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCPADDLESIZE) {
                                        if (upgTimerB[4] > 0) {
                                            botUpgs--;
                                        } else if (upgTimerB[5] > 0) {
                                            upgTimerB[5] = 0;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() * 2);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[4] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++) {
                                                upgrades.get(i).increasePaddleSize(botPaddles.get(j));
                                                if (botPaddles.get(j).getPadX() + botPaddles.get(j).getPadWidth() > 640) {
                                                    botPaddles.get(j).setPadX(botPaddles.get(j).getPadX() - (60));
                                                }

                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECPADDLESIZE) {
                                        if (upgTimerB[5] > 0) {
                                            botUpgs--;
                                        } else if (upgTimerB[4] > 0) {
                                            upgTimerB[4] = 0;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                botPaddles.get(k).setPadWidth(botPaddles.get(k).getPadWidth() / 2);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[5] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                upgrades.get(i).decreasePaddleSize(botPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCPADDLESPEED) {
                                        if (upgTimerB[6] > 0) {
                                            botUpgs--;
                                        } else if (upgTimerB[7] > 0) {
                                            upgTimerB[7] = 0;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() * 2);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[6] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                upgrades.get(i).increasePaddleSpeed(botPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECPADDLESPEED) {
                                        if (upgTimerB[7] > 0) {
                                            botUpgs--;
                                        } else if (botPaddles.get(0).getSpeed() == 1) {
                                            botUpgs--;
                                        } else if (upgTimerB[6] > 0) {
                                            upgTimerB[7] = 0;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                botPaddles.get(k).setSpeed(botPaddles.get(k).getSpeed() * 2);
                                            botUpgs -= 2;
                                        } else {
                                            upgTimerB[7] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                upgrades.get(i).decreasePaddleSpeed(botPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.ADDPADDLE) {
                                        if (upgTimerB[8] > 0) {
                                            botUpgs--;
                                        } else {
                                            upgTimerB[8] = 1000;
                                            if (botPaddles.get(0).getPadX() < 50 + botPaddles.get(0).getPadWidth()) {
                                                Paddles p = new Paddles(0, botPaddles.get(0).getPadX() + 50 + botPaddles.get(0).getPadWidth());
                                                botPaddles.add(p);
                                            } else {
                                                Paddles p = new Paddles(0, botPaddles.get(0).getPadX() - 50 - botPaddles.get(0).getPadWidth());
                                                botPaddles.add(p);
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REMPADDLE) {
                                        upgTimerB[8] = 0;
                                        if (botPaddles.size() > 1) {
                                            botPaddles.remove(botPaddles.size() - 1);
                                            botUpgs -= 2;
                                        } else {
                                            botUpgs--;
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.ADDBALL) {
                                        if (upgTimerB[9] > 0) {
                                            botUpgs--;
                                        } else {
                                            upgTimerB[9] = 1000;
                                            Ball b = upgrades.get(i).addBall();
                                            balls.add(b);
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REMBALL) {
                                        upgTimerB[9] = 0;
                                        if (balls.size() > 1) {
                                            balls.remove(balls.size() - 1);
                                            botUpgs -= 2;
                                        } else {
                                            botUpgs--;
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REVBALLS) {
                                        upgTimerB[10] = 0;
                                        for (int k = 0; k < balls.size(); k++) {
                                            int angle = r.nextInt(3);
                                            if (angle == 0) {
                                                balls.get(k).setBallX(balls.get(k).getBallX()*-1);
                                            } else if (angle == 1) {
                                                balls.get(k).setBallY(balls.get(k).getBallY()*-1);
                                            } else {
                                                balls.get(k).setBallX(balls.get(k).getBallX()*-1);
                                                balls.get(k).setBallY(balls.get(k).getBallY()*-1);
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REVPADDLES) {
                                        if (upgTimerB[11] > 0) {
                                            botUpgs-= 2;
                                            upgTimerB[11] = 0;
                                            for (int k = 0; k < topPaddles.size(); k++) {
                                                upgrades.get(i).invertOpponentMovement(topPaddles.get(k));
                                            }
                                        } else {
                                            upgTimerB[11] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++) {
                                                upgrades.get(i).invertOpponentMovement(topPaddles.get(k));
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.BLINKBALL) {
                                        upgTimerB[12] += 1000;
                                    } else if (upgrades.get(i).getType() == Powerups.BLINKPADD) {
                                        upgTimerB[13] += 1000;
                                    } else if (upgrades.get(i).getType() == Powerups.STICKPADD) {
                                        if (upgTimerB[14] == 0) {
                                            upgTimerB[14] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++)
                                                botPaddles.get(k).setStuck(true);
                                        } else {
                                            botUpgs--;
                                        }
                                    }
                                    botUpgs++;
                                    upgrades.remove(i);
                                }
                            }
                        //hits top paddle (2P only)
                        }
                    }
                }
            }
            for (int i = 0; i < upgrades.size(); i++) {
                for (int j = 0; j < topPaddles.size(); j++) {
                    //only three powerups enabled
                    if (i < upgrades.size() && j < topPaddles.size() && i >= 0 && j >= 0) {
                        upgrades.get(i).setUpgY(upgrades.get(i).getUpgY() + upgrades.get(i).getUpgSpeedY());

                        if (upgrades.get(i).getUpgY() < -5 || upgrades.get(i).getUpgY() > 645) {
                            upgrades.remove(i);
                            //hits bottom paddle
                        } else if (topUpgs < 3 && upgrades.get(i).getUpgY() < 40 && State == STATE.GAME2) {
                            if (upgrades.get(i).getUpgY() >  0 && upgrades.get(i).getUpgY() <=  topPaddles.get(j).getPadWidth()) {
                                if (upgrades.get(i).getUpgX() + upgrades.get(i).getUpgSize() >= topPaddles.get(j).getPadX() &&
                                        upgrades.get(i).getUpgX() <= topPaddles.get(j).getPadX() + topPaddles.get(j).getPadWidth()) {
                                    if (upgrades.get(i).getType() == Powerups.INCBALLSIZE) {
                                        if (upgTimerT[0] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[1] > 0) {
                                            upgTimerT[1] = 0;
                                            for (int k = 0; k < balls.size(); k++)
                                                balls.get(k).setBallSize(balls.get(k).getBallSize() + 5);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[0] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).increaseBallSize(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECBALLSIZE) {
                                        if (upgTimerT[1] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[0] > 0) {
                                            upgTimerT[0] = 0;
                                            for (int k = 0; k < balls.size(); k++)
                                                balls.get(k).setBallSize(balls.get(k).getBallSize() - 5);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[1] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).decreaseBallSize(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCBALLSPEED) {
                                        if (upgTimerT[2] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[3] > 0) {
                                            upgTimerT[3] = 0;
                                            for (int k = 0; k < balls.size(); k++) {
                                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() * 2);
                                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() * 2);
                                            }
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[2] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).increaseBallSpeed(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECBALLSPEED) {
                                        if (upgTimerT[3] > 0) {
                                            topUpgs--;
                                        } else if (balls.get(0).getBallSpeedX() == 1 || balls.get(0).getBallSpeedY() == 1) {
                                            topUpgs--;
                                        } else if (upgTimerT[2] > 0) {
                                            upgTimerT[2] = 0;
                                            for (int k = 0; k < balls.size(); k++) {
                                                balls.get(k).setBallSpeedX(balls.get(k).getBallSpeedX() / 2);
                                                balls.get(k).setBallSpeedY(balls.get(k).getBallSpeedY() / 2);
                                            }
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[3] = 1000;
                                            for (int k = 0; k < balls.size(); k++)
                                                upgrades.get(i).decreaseBallSpeed(balls.get(k));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCPADDLESIZE) {
                                        if (upgTimerT[4] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[5] > 0) {
                                            upgTimerT[5] = 0;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                topPaddles.get(k).setPadWidth(topPaddles.get(k).getPadWidth() * 2);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[4] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++) {
                                                upgrades.get(i).increasePaddleSize(topPaddles.get(j));
                                                if (topPaddles.get(j).getPadX() + topPaddles.get(j).getPadWidth() > 640) {
                                                    topPaddles.get(j).setPadX(topPaddles.get(j).getPadX() - (60));
                                                }

                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECPADDLESIZE) {
                                        if (upgTimerT[5] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[4] > 0) {
                                            upgTimerT[4] = 0;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                topPaddles.get(k).setPadWidth(topPaddles.get(k).getPadWidth() / 2);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[5] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                upgrades.get(i).decreasePaddleSize(topPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.INCPADDLESPEED) {
                                        if (upgTimerT[6] > 0) {
                                            topUpgs--;
                                        } else if (upgTimerT[7] > 0) {
                                            upgTimerT[7] = 0;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                topPaddles.get(k).setSpeed(topPaddles.get(k).getSpeed() * 2);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[6] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                upgrades.get(i).increasePaddleSpeed(topPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.DECPADDLESPEED) {
                                        if (upgTimerT[7] > 0) {
                                            topUpgs--;
                                        } else if (topPaddles.get(0).getSpeed() == 1) {
                                            topUpgs--;
                                        } else if (upgTimerT[6] > 0) {
                                            upgTimerT[7] = 0;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                topPaddles.get(k).setSpeed(topPaddles.get(k).getSpeed() * 2);
                                            topUpgs -= 2;
                                        } else {
                                            upgTimerT[7] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                upgrades.get(i).decreasePaddleSpeed(topPaddles.get(j));
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.ADDPADDLE) {
                                        if (upgTimerT[8] > 0) {
                                            topUpgs--;
                                        } else {
                                            upgTimerT[8] = 1000;
                                            if (topPaddles.get(0).getPadX() < 50 + topPaddles.get(0).getPadWidth()) {
                                                Paddles p = new Paddles(1, topPaddles.get(0).getPadX() + 50 + topPaddles.get(0).getPadWidth());
                                                topPaddles.add(p);
                                            } else {
                                                Paddles p = new Paddles(1, topPaddles.get(0).getPadX() - 50 - topPaddles.get(0).getPadWidth());
                                                topPaddles.add(p);
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REMPADDLE) {
                                        upgTimerT[8] = 0;
                                        if (topPaddles.size() > 1) {
                                            topPaddles.remove(topPaddles.size() - 1);
                                            topUpgs -= 2;
                                        } else {
                                            topUpgs--;
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.ADDBALL) {
                                        if (upgTimerT[9] > 0) {
                                            topUpgs--;
                                        } else {
                                            upgTimerT[9] = 1000;
                                            Ball b = upgrades.get(i).addBall();
                                            balls.add(b);
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REMBALL) {
                                        upgTimerT[9] = 0;
                                        if (balls.size() > 1) {
                                            balls.remove(balls.size() - 1);
                                            topUpgs -= 2;
                                        } else {
                                            topUpgs--;
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REVBALLS) {
                                        upgTimerT[10] = 0;
                                        for (int k = 0; k < balls.size(); k++) {
                                            int angle = r.nextInt(3);
                                            if (angle == 0) {
                                                balls.get(k).setBallX(balls.get(k).getBallX() * -1);
                                            } else if (angle == 1) {
                                                balls.get(k).setBallY(balls.get(k).getBallY() * -1);
                                            } else {
                                                balls.get(k).setBallX(balls.get(k).getBallX() * -1);
                                                balls.get(k).setBallY(balls.get(k).getBallY() * -1);
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.REVPADDLES) {
                                        if (upgTimerT[11] > 0) {
                                            topUpgs -= 2;
                                            upgTimerT[11] = 0;
                                            for (int k = 0; k < botPaddles.size(); k++) {
                                                upgrades.get(i).invertOpponentMovement(botPaddles.get(k));
                                            }
                                        } else {
                                            upgTimerT[11] = 1000;
                                            for (int k = 0; k < botPaddles.size(); k++) {
                                                upgrades.get(i).invertOpponentMovement(botPaddles.get(k));
                                            }
                                        }
                                    } else if (upgrades.get(i).getType() == Powerups.BLINKBALL) {
                                        upgTimerT[12] += 1000;
                                    } else if (upgrades.get(i).getType() == Powerups.BLINKPADD) {
                                        upgTimerT[13] += 1000;
                                    } else if (upgrades.get(i).getType() == Powerups.STICKPADD) {
                                        if (upgTimerT[14] == 0) {
                                            upgTimerT[14] = 1000;
                                            for (int k = 0; k < topPaddles.size(); k++)
                                                topPaddles.get(k).setStuck(true);
                                        } else {
                                            topUpgs--;
                                        }
                                    }
                                    topUpgs++;
                                    upgrades.remove(i);
                                }
                            }
                        }
                    }
                }
            }
            if (topScore == 7 || botScore == 6) {
                finalState = this.State;
                scoreBotfinal = botScore;
                scoreTopfinal = topScore;
                scoreBot.addScoresBottom(scoreBotfinal);
                scoreTop.addScoresTop(scoreTopfinal);
                gameNum.addGameCount(gameCount++);
                State = STATE.END;
            }
            repaint();
        } else {
            //if (State != STATE.END)
            repaint();
        }
    }

    public void moveTop(int dist) {
        int mover = 0;
        for (int i = 0; i < topPaddles.size(); i++) {
            if (topPaddles.get(i).getPadX() + dist < (0) || topPaddles.get(i).getPadX() + dist > (640-topPaddles.get(i).getPadWidth()))
                mover = 1;
        }
        if (mover == 0) {
            for (int i = 0; i < topPaddles.size(); i++) {
                topPaddles.get(i).setPadX(topPaddles.get(i).getPadX() + dist);
                if (topPaddles.get(i).getStuck()) {
                    for (int j = 0; j < balls.size(); j++) {
                        if (balls.get(j).getStuck2())
                            balls.get(j).setBallX(balls.get(j).getBallX() + dist);
                    }
                }
            }
        }
    }

    public void moveBot(int dist) {
        int mover = 0;
        for (int i = 0; i < botPaddles.size(); i++) {
            if (botPaddles.get(i).getPadX() + dist < (0) || botPaddles.get(i).getPadX() + dist > (640-botPaddles.get(i).getPadWidth()))
                mover = 1;
        }
        if (mover == 0) {
            for (int i = 0; i < botPaddles.size(); i++) {
                botPaddles.get(i).setPadX(botPaddles.get(i).getPadX() + dist);
                if (botPaddles.get(i).getStuck()) {
                    for (int j = 0; j < balls.size(); j++) {
                        if (balls.get(j).getStuck1())
                            balls.get(j).setBallX(balls.get(j).getBallX() + dist);
                    }
                }
            }
        }
    }
}