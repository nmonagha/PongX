import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        EasyAI eAI;
        EasyAI mAI;
        EasyAI hAI;
        Game.STATE finalstate = Game.getFinalState();

        /* Checks the state of the game is MENU */
        if (Game.State == Game.STATE.MENU) {

            /* One Player Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 75) {
                if (my >= 150 && my <= 200) {
                    Game.State = Game.STATE.AI;
                }
            }

            /* Two Player Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 75) {
                if (my >= 225 && my <= 275) {
                    Game.State = Game.STATE.GAME2;
                }
            }

            /* Score Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 125) {
                if (my >= 300 && my <= 350) {
                    Game.State = Game.STATE.SCORE;
                }
            }

            /* Help Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 150) {
                if (my >= 375 && my <= 425) {
                    Game.State = Game.STATE.TUTORIAL;
                }
            }

            /* Quit Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 160) {
                if (my >= 450 && my <= 500) {
                    System.exit(1);
                }
            }

        }
        /* Checks the state of the game is HELP */
        else if (Game.State == Game.STATE.TUTORIAL) {
            /* Menu Button */
            if (mx >= 640 / 2 - 50 && mx <= 640 / 2 + 50) {
                if (my >= 550 && my <= 600) {
                    Game.State = Game.STATE.MENU;
                }
            }
        }
        /* Checks the state of the game is END */
        else if (Game.State == Game.STATE.END) {
            /* Menu Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 50) {
                if (my >= 350 && my <= 400) {
                    Game.State = Game.STATE.MENU;
                }
            }
            /* New Game Button */
            if (mx >= 640 / 2 - 250 && mx <= 640 / 2 - 90) {
                if (my >= 425 && my <= 475) {
                    /* Checks if game was Two-Player or One-Player */
                    if (finalstate == Game.STATE.GAME2) {
                        Game.State = Game.STATE.GAME2;
                    }
                    else if (finalstate == Game.STATE.GAME){
                        Game.State = Game.STATE.GAME;
                    }

                }
            }
        }
        /* Checks the state of the game is SCORE */
        else if (Game.State == Game.STATE.SCORE) {
            /* Menu Button */
            if (mx >= 640 / 2 - 50 && mx <= 640 / 2) {
                if (my >= 550 && my <= 600) {
                    Game.State = Game.STATE.MENU;
                }
            }
        }
        else if (Game.State == Game.STATE.AI) {
            /* Easy button */
            if (mx >= 640 / 2 - 45 && mx <= 640 / 2 + 45) {
                if (my >= 125 && my <= 175) {
                    Game.setAILevel(0);
                    Game.State = Game.STATE.GAME;
                }
            }
            /* Medium button */
            if (mx >= 640 / 2 - 70 && mx <= 640 / 2 + 70) {
                if (my >= 200 && my <= 250) {
                    Game.setAILevel(1);
                    Game.State = Game.STATE.GAME;
                }
            }
            /* Hard button */
            if (mx >= 640 / 2 - 50 && mx <= 640 / 2 + 50) {
                if (my >= 275 && my <= 325) {
                    Game.setAILevel(2);
                    Game.State = Game.STATE.GAME;
                }
            }
            /* Menu Button */
            if (mx >= 640 / 2 - 50 && mx <= 640 / 2 + 50) {
                if (my >= 550 && my <= 600) {
                    Game.State = Game.STATE.MENU;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}
