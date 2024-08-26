package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
import java.awt.Color;

/**
 * CountdownAnimation - an on-screen countdown from 3 to 1, which will show up at the beginning of each turn.
 *  Only after the countdown reaches zero, things will start moving and the game play will start.
 *
 * @author Ayelet Tennenboim
 */
public class CountdownAnimation implements Animation {
    // Number of seconds to display the given gameScreen
    private double numOfSeconds;
    // The number that the count starts from
    private int countFrom;
    // Number to show on the screen
    private int number;
    // The sprites collection of the game
    private SpriteCollection gameScreen;
    // Indication to stop the animation
    private boolean stop;

    /**
     * Constructor - creates a new CountdownAnimation.
     *
     * @param numOfSeconds number of seconds to display the given gameScreen.
     * @param countFrom the number that the count starts from.
     * @param gameScreen the sprites collection of the game.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.number = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
    }
    /**
     * The method displays the given gameScreen for 2 seconds, and on top of the screen it shows a countdown
     *  from 3 back to 1, where each number appears on the screen for 2/3 seconds before it is replaced with the
     *  next one.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        // Create a sleeper
        Sleeper sleeper = new Sleeper();
        // Each number in the animation appears on the screen for 2/3 seconds
        double millisecondsPerNumber = (this.numOfSeconds / this.countFrom) * 1000;
        // Draw all the sprites on the surface
        this.gameScreen.drawAllOn(d);
        // After the countdown reaches zero - stop the CountdownAnimation and start the game play
        if (this.number == 0) {
            this.stop = true;
        } else {
            // Draw the number on the screen
            d.setColor(new Color(230, 230, 230));
            d.fillCircle(400, 25, 25);
            d.setColor(new Color(0, 0, 0));
            d.fillCircle(400, 25, 22);
            d.setColor(new Color(255, 255, 255));
            d.drawText(391, 38, String.valueOf(number), 35);
            // Subtract one from the number
            this.number--;
        }
        // If it is not the first frame of the animation
        if (this.number + 1 != this.countFrom) {
            // Wait 2/3 seconds
            sleeper.sleepFor((long) millisecondsPerNumber);
        }
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the countdown is over and the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
