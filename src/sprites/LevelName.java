package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import java.awt.Color;

/**
 * LevelName - this class is in charge of drawing the name of the level.
 *
 * @author Ayelet Tennenboim
 */
public class LevelName implements Sprite {
    private String levelName;

    /**
     * Constructor - creates a new Background.
     *
     * @param levelName the name of the level.
     */
    public LevelName(String levelName) {
        this.levelName = levelName;
    }
    /**
     * Draws the level name a the top of the screen.
     *
     * @param d a surface to draw on it.
     */
    public void drawOn(DrawSurface d) {
        // X and Y values for the top left corner
        int xUpperLeft = 465;
        int yUpperLeft = 23;
        // The size of the text
        int fontSize = 20;
        // Level name string
        String levelNameS = "Level Name: " + this.levelName;
        // Draw the level name on the screen
        d.setColor(Color.WHITE);
        d.drawText(xUpperLeft, yUpperLeft, levelNameS, fontSize);
    }
    /**
     * Notifies the sprite that time has passed.
     */
    public void timePassed() {
        // Do nothing
    }
    /**
     * Adds this LevelName to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
