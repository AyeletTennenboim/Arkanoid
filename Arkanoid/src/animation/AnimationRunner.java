package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * AnimationRunner - this class is the animation looping code. It takes an Animation object and runs it.
 *
 * @author Ayelet Tennenboim
 */
public class AnimationRunner {
    // The window of the game
    private GUI gui;
    // Number of frames the animation displays in a second
    private int framesPerSecond;

    /**
     * Constructor - creates a new AnimationRunner.
     *
     * @param gui the window of the game.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
    }
    /**
     * Runs the current animation loop of one turn of the game.
     *
     * This method is in charge of the timing of the animation and displaying the drawing on the screen. It runs the
     *  animation loop, and calls animation.doOneFrame() method that does one frame of the animation.
     *
     * @param animation an animation object to run.
     */
    public void run(Animation animation) {
        // Create a sleeper
        Sleeper sleeper = new Sleeper();
        // Each frame in the animation can last 1000 / framesPerSecond milliseconds
        int millisecondsPerFrame = 1000 / framesPerSecond;
        // While the animation should not stop
        while (!animation.shouldStop()) {
            // Timing
            long startTime = System.currentTimeMillis();
            // Get a drawSurface to draw on it
            DrawSurface d = this.gui.getDrawSurface();
            // GameLevel-specific logic
            animation.doOneFrame(d);
            // Display the drawing on the screen
            this.gui.show(d);
            // Timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}