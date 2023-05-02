package sprites;

import game.Counter;
import game.GameLevel;
import geometricshapes.Point;
import geometricshapes.Rectangle;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * ScoreIndicator - The ScoreIndicator is in charge of displaying the current score.
 *
 * @author Ayelet Tennenboim
 */
public class ScoreIndicator implements Sprite {
    // The location and size of the ScoreIndicator (specified using a Rectangle)
    private Rectangle rec;
    // Color
    private Color color;
    // A reference to the scores counter
    private Counter score;

    /**
     * Constructor - creates a new ScoreIndicator.
     *
     * @param score the scores counter.
     */
    public ScoreIndicator(Counter score) {
        this.rec = new Rectangle(new Point(0, 0), 800, 30);
        this.color = new Color(119, 119, 120, 255);;
        this.score = score;
    }
    /**
     * Draws the sprite to the screen.
     *
     * @param surface a surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        // Fill the rectangle
        surface.setColor(this.color);
        surface.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
        // Draw the score on the rectangle
        this.drawScore(surface);
    }
    /**
     * Draws the score on the rectangle.
     *
     * @param surface a surface to draw on it.
     */
    private void drawScore(DrawSurface surface) {
        // X and Y values for the top left corner
        int xUpperLeft = 80;
        int yUpperLeft = 23;
        // The size of the text
        int fontSize = 20;
        // Score string
        String scoreString = "Score: " + this.score.getValue();
        // Draw the score on the rectangle
        surface.setColor(Color.WHITE);
        surface.drawText(xUpperLeft, yUpperLeft, scoreString, fontSize);
    }
    /**
     * Notifies the sprite that time has passed.
     */
    public void timePassed() {
        // Do nothing
    }
    /**
     * Adds this ScoreIndicator to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
