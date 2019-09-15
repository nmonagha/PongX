import java.util.ArrayList;

public class Scores {

    /* Array list we will use to work with the scores inside the class */
    public ArrayList<Integer> scoresTop = new ArrayList<Integer>();
    public ArrayList<Integer> scoresBottom = new ArrayList<Integer>();
    public ArrayList<Integer> gameCount = new ArrayList<Integer>();

    public ArrayList<Integer> getScoresTop() {
        return scoresTop;
    }

    public ArrayList<Integer> getScoresBottom() {
        return scoresBottom;
    }

    public ArrayList<Integer> getGameCount() {
        return gameCount;
    }

    public void removeScoresTop(int scoreTop) {
        this.scoresTop.remove(scoreTop);
    }

    public void removeScoresBot(int scoreBot) {
        this.scoresBottom.remove(scoreBot);
    }
    public void removeGameCount(int gameCount) {
        this.gameCount.remove(gameCount);
    }

    public void addScoresTop(int scoresTop) {
        this.scoresTop.add(scoresTop);
    }

    public void addScoresBottom(int scoresBottom) {
        this.scoresBottom.add(scoresBottom);
    }

    public void addGameCount(int gameCount) {
        this.gameCount.add(gameCount);
    }

    public int getSizeTop() {
        return scoresTop.size();
    }

    public int getSizeBot() {
        return scoresBottom.size();
    }

    public int getSizeGameCount() {
        return gameCount.size();
    }

}
