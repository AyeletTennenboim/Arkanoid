package animation;

import biuoop.DrawSurface;
import game.Counter;
import java.awt.Color;

/**
 * EndScreen - Once the game is over (either the player run out of lives or managed to clear all the levels), this
 *  EndScreen displays the final score with a suitable message.
 *
 * @author Ayelet Tennenboim
 */
public class EndScreen implements Animation {
    // Indication to stop the animation
    private boolean stop;
    // Number of lives left at the end of the game
    private int lives;
    // The final score
    private int score;

    /**
     * Constructor - creates a new EndScreen.
     *
     * @param lives number of lives left at the end of the game.
     * @param score the final score.
     */
    public EndScreen(Counter lives, Counter score) {
        this.stop = false;
        this.lives = lives.getValue();
        this.score = score.getValue();
    }
    /**
     * This method displays a screen with the final score and a suitable message, until the space key is pressed.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        // Convert the score value to string
        String scoreString = String.valueOf(this.score);
        // If the game ended with the player losing all his lives
        if (this.lives == 0) {
            d.setColor(new Color(110, 105, 105, 255));
            d.fillRectangle(0, 0, 800, 600);
            d.setColor(new Color(140, 135, 135, 255));
            d.fillRectangle(50, 37, 700, 525);
            d.setColor(new Color(165, 160, 160, 255));
            d.fillRectangle(100, 75, 600, 450);
            d.setColor(new Color(180, 175, 175, 255));
            d.fillRectangle(150, 112, 500, 375);
            d.setColor(new Color(205, 200, 200, 255));
            d.fillRectangle(200, 150, 400, 300);
            d.setColor(new Color(194, 0, 22));
            d.drawText(240, 270, "Game Over.", 60);
            d.drawText(235, 380, "Your score is: " + scoreString, 40);
            d.setColor(new Color(255, 255, 255));
            d.drawText(255, 555, "Press space to continue..", 27);
        // If the game ended by clearing all the levels
        } else {
            d.setColor(new Color(214, 163, 158));
            d.fillRectangle(0, 0, 800, 600);
            d.setColor(new Color(230, 230, 230));
            d.fillRectangle(50, 37, 700, 525);
            d.setColor(new Color(221, 146, 146));
            d.fillRectangle(100, 75, 600, 450);
            d.setColor(new Color(230, 230, 230));
            d.fillRectangle(150, 112, 500, 375);
            d.setColor(new Color(214, 112, 112));
            d.fillRectangle(200, 150, 400, 300);
            d.setColor(new Color(255, 255, 255));
            d.drawText(280, 280, "You Win!", 60);
            d.drawText(235, 380, "Your score is: " + scoreString, 40);
            d.drawText(255, 515, "Press space to continue..", 27);
        }
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the space key was pressed and the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
