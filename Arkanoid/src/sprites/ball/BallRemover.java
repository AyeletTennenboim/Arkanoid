package sprites.ball;

import game.GameLevel;
import hitting.HitListener;
import sprites.Block;
import game.Counter;

/**
 * BallRemover - a BallRemover is in charge of removing balls from the game, and updating an available-balls counter.
 *
 * @author Ayelet Tennenboim
 */
public class BallRemover implements HitListener {
    // A reference to the game object
    private GameLevel game;
    // Count of available balls
    private Counter availableBalls;

    /**
     * Constructor - creates a new BallRemover.
     *
     * @param game a reference to the game object.
     * @param availableBalls count of available balls.
     */
    public BallRemover(GameLevel game, Counter availableBalls) {
        this.game = game;
        this.availableBalls = availableBalls;
    }
    /**
     * This method is called whenever the beingHit object is hit. It removes the ball from the game and updates the
     *  balls counter.
     *
     * @param beingHit the hit block.
     * @param hitter the ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // Remove the ball from the game
        hitter.removeFromGame(this.game);
        // Subtracts 1 from current count
        this.availableBalls.decrease(1);
    }
}
