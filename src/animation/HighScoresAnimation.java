package animation;

import biuoop.DrawSurface;
import score.HighScoresTable;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * HighScoresAnimation - HighScoresAnimation is in charge of the graphical representation of the high-scores table.
 *  It displays the table until a specified key is pressed.
 *
 * @author Ayelet Tennenboim
 */
public class HighScoresAnimation implements Animation {
    // High-scores table
    private HighScoresTable scores;
    // Indication to stop the animation
    private boolean stop;
    // Background
    private Image background;

    /**
     * Constructor - creates a new HighScoresAnimation.
     *
     * @param scores the high-scores table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
        this.background = loadImage();
    }
    /**
     * This method displays a screen with the scores in the high-scores table.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, this.background);
        d.setColor(new Color(0, 0, 0));
        d.drawText(255, 90, "High Scores", 50);
        d.drawText(150, 170, "Name", 40);
        d.drawText(550, 170, "Score", 40);
        d.drawLine(150, 180, 255, 180);
        d.drawLine(550, 180, 655, 180);
        for (int i = 0; i < scores.getHighScores().size(); i++) {
            int height = 230 + (55 * i);
            d.drawText(150, height, this.scores.getHighScores().get(i).getName(), 35);
            d.drawText(550, height, String.valueOf(this.scores.getHighScores().get(i).getScore()), 35);
        }
        d.setColor(new Color(255, 48, 154));
        d.drawText(220, 550, "Press space to continue..", 30);
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the the animation should stop, and false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
    /**
     * Loads the image for the background.
     *
     * @return an image for the background.
     */
    public Image loadImage() {
        // Load the image data into an java.awt.Image object
        Image img = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/triangles.jpg");
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
