package hitting;

import game.Counter;
import game.GameLevel;
import sprites.ball.Ball;
import sprites.Block;

/**
 * ScoreTrackingListener - a ScoreTrackingListener is in charge of updating the score counter when blocks are being
 *  hit and removed.
 *
 * @author Ayelet Tennenboim
 */
public class ScoreTrackingListener implements HitListener {
    // A reference to the game object
    private GameLevel game;
    // Counter for keeping track of scores
    private Counter currentScore;

    /**
     * Constructor - creates a new ScoreTrackingListener.
     *
     * @param game a reference to the game object.
     * @param scoreCounter a counter for keeping track of scores.
     */
    public ScoreTrackingListener(GameLevel game, Counter scoreCounter) {
        this.game = game;
        this.currentScore = scoreCounter;
    }
    /**
     * This method is called whenever the beingHit object is hit. It implements the following scoring rule: hitting a
     *  block is worth 5 points, and destroying a block is worth additional 10 points.
     *
     * @param beingHit the hit block.
     * @param hitter the ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // Add 5 points for hitting the block
        this.currentScore.increase(5);
        // If this hit destroys the block
        if (beingHit.getHitPoints() == 1) {
            // Add 10 more points for destroying a block
            this.currentScore.increase(10);
        }
    }
}