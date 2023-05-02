package score;

/**
 * ScoreInfo - this class holds the score's information (score and player name).
 *
 * @author Ayelet Tennenboim
 */
public class ScoreInfo {
    // The player name
    private String name;
    // The player's score
    private int score;

    /**
     * Constructor - creates a new ScoreInfo.
     *
     * @param name the player name.
     * @param score the player's score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * Returns the player name.
     *
     * @return the player name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the player's score.
     *
     * @return the player's score.
     */
    public int getScore() {
        return this.score;
    }
}
