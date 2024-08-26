package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation - this decorator-class wraps an existing animation and adds a "waiting-for-key"
 *  behavior to it.
 *
 * @author Ayelet Tennenboim
 */
public class KeyPressStoppableAnimation implements Animation {
    // A keyboard to read the key presses
    private KeyboardSensor sensor;
    // The key that stops the animation
    private String key;
    // Animation to display on the screen
    private Animation animation;
    // Indication to stop the animation
    private boolean stop;
    // Indication if the key is already pressed
    private boolean isAlreadyPressed;

    /**
     * Constructor - creates a new KeyPressStoppableAnimation.
     *
     * @param sensor a keyboard to read the key presses.
     * @param key the key that stops the animation.
     * @param animation animation to display on the screen.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
    this.sensor = sensor;
    this.key = key;
    this.animation = animation;
    this.stop = false;
    this.isAlreadyPressed = true;
    }
    /**
     * This method does one frame of the animation and gives an indication to stop running it.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        // Call animation.doOneFrame() method
        animation.doOneFrame(d);
        // If the key is pressed and was not pressed before the animation started
        if (this.sensor.isPressed(this.key) && !this.isAlreadyPressed) {
            // Give an indication to stop the animation
            this.stop = true;
        }
        // If the key is not pressed
        if (!this.sensor.isPressed(this.key)) {
            // Change isAlreadyPressed to be false
            this.isAlreadyPressed = false;
        }
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}