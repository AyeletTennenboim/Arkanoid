package sprites;

import biuoop.DrawSurface;
import game.GameLevel;

/**
 * The Sprite interface will be used by things that can be drawn on the screen, and can be notified that time has
 *  passed.
 *
 * @author Ayelet Tennenboim
 */
public interface Sprite {
    /**
     * Draws the sprite to the screen.
     *
     * @param d a surface to draw on it.
     */
    void drawOn(DrawSurface d);
    /**
     * Notifies the sprite that time has passed.
     */
    void timePassed();
    /**
     * Adds this sprite to the game.
     *
     * @param g the game.
     */
    void addToGame(GameLevel g);
}