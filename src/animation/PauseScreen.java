package animation;

import biuoop.DrawSurface;
import java.awt.Color;

/**
 * PauseScreen - PauseScreen is in charge of pausing the game. It is a simple animation, that displays a screen
 *  with the message "paused -- press space to continue" until the space key is pressed.
 *
 * @author Ayelet Tennenboim
 */
public class PauseScreen implements Animation {
    // Indication to stop the animation
    private boolean stop;

    /**
     * Constructor - creates a new PauseScreen.
     */
    public PauseScreen() {
        this.stop = false;
    }
    /**
     * This method displays a screen with the message "paused -- press space to continue" until the space key is
     *  pressed.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(191, 166, 203, 255));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(0, 0, 0));
        d.drawText(100, d.getHeight() / 2, "paused -- press space to continue", 40);
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the space key was pressed and the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
