package hitting;

import game.GameLevel;
import sprites.ball.Ball;
import sprites.Block;
import game.Counter;

/**
 * BlockRemover - a BlockRemover is in charge of removing blocks from the game, as well as keeping count of the number
 *  of blocks that remain.
 *
 * @author Ayelet Tennenboim
 */
public class BlockRemover implements HitListener {
    // A reference to the game object
    private GameLevel game;
    // Count of remaining blocks
    private Counter remainingBlocks;

    /**
     * Constructor - creates a new BlockRemover.
     *
     * @param game a reference to the game object.
     * @param remainingBlocks count of remaining blocks.
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }
    /**
     * This method is called whenever the beingHit object is hit. It removes blocks that are hit and reach 0 hit-points
     *  from the game and updates the count of the number of blocks that remain.
     *
     * @param beingHit the hit block.
     * @param hitter the ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // If the block is hit and reaches 0 hit-points
        if (beingHit.getHitPoints() == 1) {
            // Remove the block from the game
            beingHit.removeFromGame(this.game);
            // Remove this listener from the block
            beingHit.removeHitListener(this);
            // Subtracts 1 from current count
            this.remainingBlocks.decrease(1);
        }
    }
}