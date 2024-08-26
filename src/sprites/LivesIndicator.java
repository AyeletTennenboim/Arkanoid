package sprites;

import game.Counter;
import game.GameLevel;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * LivesIndicator - The LivesIndicator is in charge of displaying the number of lives.
 *
 * @author Ayelet Tennenboim
 */
public class LivesIndicator implements Sprite {
    // A reference to the lives counter
    private Counter lives;

    /**
     * Constructor - creates a new LivesIndicator.
     *
     * @param lives the lives counter.
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
    }
    /**
     * Draws the sprite to the screen.
     *
     * @param surface a surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        // X and Y values for the top left corner
        int xUpperLeft = 220;
        int yUpperLeft = 23;
        // The size of the text
        int fontSize = 20;
        // Lives string
        String livesString = "Lives: " + this.lives.getValue();
        // Draw the number of lives on the rectangle
        surface.setColor(Color.WHITE);
        surface.drawText(xUpperLeft, yUpperLeft, livesString, fontSize);
    }
    /**
     * Notifies the sprite that time has passed.
     */
    public void timePassed() {
        // Do nothing
    }
    /**
     * Adds this LivesIndicator to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
