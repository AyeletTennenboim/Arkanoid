package animation;

import biuoop.DrawSurface;

/**
 * The Animation interface will be used by animations that should run during the game.
 *
 * @author Ayelet Tennenboim
 */
public interface Animation {
    /**
     * This method does one frame of the animation.
     *
     * @param d a surface to draw on it.
     */
    void doOneFrame(DrawSurface d);
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the animation should stop, and false otherwise.
     */
    boolean shouldStop();
}
