package animation.menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class runs the menu animation.
 *
 * @param <T> a task to run.
 * @author Ayelet Tennenboim
 */
public class MenuAnimation<T> implements Menu<T> {
    // Menu title
    private String title;
    // Runner to run the animation object
    private AnimationRunner animationRunner;
    // Keyboard to read the key presses
    private KeyboardSensor keyboardSensor;
    // List of optional keys to choose
    private List<String> keys;
    // List of messages
    private List<String> messages;
    // HashMap to match a key to a task
    private HashMap<String, T> hashMap;
    // HashMap for the sub-menu
    private HashMap<String, Menu<T>> subHashMap;
    // Background
    private Image background;

    /**
     * Constructor - creates a new MenuAnimation.
     *
     * @param title the menu title.
     * @param ar a runner to run the animation object.
     * @param ks a keyboard to read the key presses.
     */
    public MenuAnimation(String title, AnimationRunner ar, KeyboardSensor ks) {
        this.title = title;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        this.hashMap = new HashMap<String, T>();
        this.subHashMap = new HashMap<String, Menu<T>>();
        this.background = loadImage();
    }
    /**
     * This method displays a screen with the menu, until a key is pressed.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, this.background);
        d.drawText(220, 120, this.title, 50);
        d.drawText(100, 230, "Choose one of the following options:", 30);
        for (int i = 0; i < keys.size(); i++) {
            d.drawText(100, 300 + (60 * i), '(' + keys.get(i) + ")  " + messages.get(i), 30);
        }
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if a key was pressed and the animation should stop, and false otherwise.
     */
    public boolean shouldStop() {
        // Go over the keys list
        for (String key : keys) {
            // If the key is pressed
            if (this.keyboardSensor.isPressed(key)) {
                return true;
            }
        }
        // If none of the keys is pressed
        return false;
    }
    /**
     * Adds a selection to the menu.
     *
     * @param key a key to wait for.
     * @param message line to print.
     * @param returnVal a task to return.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.hashMap.put(key, returnVal);
    }
    /**
     * Returns the task to run.
     *
     * @return the task to run.
     */
    public T getStatus() {
        // Go over the keys list
        for (String key : keys) {
            // If the key is pressed
            if (this.keyboardSensor.isPressed(key)) {
                if (this.hashMap.containsKey(key)) {
                    return hashMap.get(key);
                } else if (this.subHashMap.containsKey(key)) {
                    // Run the sub-menu
                    this.animationRunner.run(this.subHashMap.get(key));
                    return this.subHashMap.get(key).getStatus();
                }
            }
        }
        return null;
    }
    /**
     * Adds a sub-menu to this menu.
     *
     * @param key a key to wait for.
     * @param message line to print.
     * @param subMenu a sub-menu to add to this menu.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.subHashMap.put(key, subMenu);
    }
    /**
     * Loads the image for the background.
     *
     * @return an image for the background.
     */
    public Image loadImage() {
        // Load the image data into an java.awt.Image object
        Image img = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/flowers.jpg");
        try {
            if (is != null) {
                img = ImageIO.read(is);
            } else {
                System.err.println("Image file not found.");
            }
        } catch (IOException e) {
            System.err.println("Failed loading background image.");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing Image file.");
            }
        }
        return img;
    }
}