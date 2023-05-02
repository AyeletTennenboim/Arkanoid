package animation.menu;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;

/**
 * This task runs the highScoresAnimation.
 *
 * @author Ayelet Tennenboim
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private Animation highScoresAnimation;

    /**
     * Constructor - creates a new ShowHiScoresTask.
     *
     * @param runner a runner to run the animation object.
     * @param keyboard a keyboard to read the key presses.
     * @param highScoresAnimation graphical representation of the high-scores table.
     */
    public ShowHiScoresTask(AnimationRunner runner, KeyboardSensor keyboard, Animation highScoresAnimation) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.highScoresAnimation = highScoresAnimation;
    }
    /**
     * Runs the highScoresAnimation.
     *
     * @return null.
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                this.highScoresAnimation));
        return null;
    }
}
