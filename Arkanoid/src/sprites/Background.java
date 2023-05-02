package sprites;

import biuoop.DrawSurface;
import game.GameLevel;
import levels.ColorsParser;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Background - this class is in charge of the background of the level.
 *
 * @author Ayelet Tennenboim
 */
public class Background implements Sprite {
    private String background;
    private Image image;
    private Color color;

    /**
     * Constructor - creates a new Background.
     *
     * @param background the background of the level.
     */
    public Background(String background) {
        this.background = background;
        this.image = loadImage();
        this.color = getColor();
    }
    /**
     * Draws the sprite to the screen.
     *
     * @param d a surface to draw on it.
     */
    public void drawOn(DrawSurface d) {
        if (this.image != null) {
            // Set the the background image
            d.drawImage(0, 0, this.image);
        } else {
            // Set the the background color
            d.setColor(this.color);
            d.fillRectangle(0, 0, 800, 600);
        }
    }
    /**
     * Loads the image for the background.
     *
     * @return an image for the background.
     */
    public Image loadImage() {
        String[] backgroundSplit = this.background.split("\\(");
        if (backgroundSplit[0].equals("image")) {
            String imageName = backgroundSplit[1].replace(")", "");
            // Load the image data into an java.awt.Image object
            Image img = null;
            Image newImage = null;
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
            try {
                if (is != null) {
                    img = ImageIO.read(is);
                    newImage = img.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
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
            return newImage;
        }
        return null;
    }
    /**
     * Gets a color for the background.
     *
     * @return a color for the background.
     */
    public Color getColor() {
        // Create a colors parser
        ColorsParser colorsParser = new ColorsParser();
        String[] backgroundSplit = this.background.split("\\(");
        if (backgroundSplit[0].equals("color")) {
            return colorsParser.colorFromString(this.background);
        }
        return null;
    }
    /**
     * Notifies the sprite that time has passed.
     */
    public void timePassed() {
        // Do nothing
    }
    /**
     * Adds this background to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
