package hitting;

import sprites.ball.Ball;
import sprites.Block;

/**
 * The HitListener interface will be used by objects that want to be notified of hit events.
 *
 * @author Ayelet Tennenboim
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *  The hitter parameter is the ball that's doing the hitting.
     *
     * @param beingHit the hit block.
     * @param hitter the ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
