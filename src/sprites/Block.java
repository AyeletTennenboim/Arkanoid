package sprites;

import collisiondetection.Collidable;
import game.GameLevel;
import geometricshapes.Point;
import hitting.HitListener;
import hitting.HitNotifier;
import levels.ColorsParser;
import sprites.ball.Ball;
import sprites.ball.Velocity;
import geometricshapes.Rectangle;
import biuoop.DrawSurface;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Block - block is a rectangle we collide into.
 *
 * @author Ayelet Tennenboim
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // The location and size of the block (specified using a Rectangle)
    private Rectangle shape;
    // Number of hits that left to reach 0
    private int hitPoints;
    // Fill (color or image)
    private String fill;
    // Stroke color
    private Color strokeColor;
    // HashMap of fills
    private HashMap<Integer, String> fillHashMap;
    // HashMap of images to fill
    private HashMap<Integer, Image> imageHashMap;
    // Default Image to fill
    private Image defaultImage;
    // A list of listeners to notify them about a hit event
    private List<HitListener> hitListeners;

    /**
     * Constructor - creates a new block.
     *
     * @param rec the rectangle that defines the block.
     * @param fill the fill of the block (color or image).
     * @param strokeColor the stroke color.
     * @param fillHashMap HashMap of fills.
     * @param hitPoints number of hits that left to reach 0.
     */
    public Block(Rectangle rec, String fill, Color strokeColor, HashMap<Integer, String> fillHashMap, int hitPoints) {
        this.shape = rec;
        this.hitPoints = hitPoints;
        this.fill = fill;
        this.strokeColor = strokeColor;
        this.fillHashMap = fillHashMap;
        this.hitListeners = new ArrayList<>();
        this.imageHashMap = loadImages();
        this.defaultImage = loadDefaultImage();
    }
    /**
     * Returns the "collision shape" of the object.
     *
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }
    /**
     * Returns the number of hits that left to reach 0.
     *
     * @return the number of hits that left to reach 0.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }
    /**
     * Draws the block on the given DrawSurface.
     *
     * @param surface a surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        // Create a colors parser
        ColorsParser colorsParser = new ColorsParser();
        // Fill the block
        if ((fillHashMap != null) && (fillHashMap.containsKey(this.hitPoints))) {
            String[] fillSplit = fillHashMap.get(this.hitPoints).split("\\(");
            if (fillSplit[0].equals("color")) {
                surface.setColor(colorsParser.colorFromString(fillHashMap.get(this.hitPoints)));
                surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                        (int) this.shape.getWidth(), (int) this.shape.getHeight());
            } else {
                Image img = this.imageHashMap.get(this.hitPoints);
                // Draw the image on a DrawSurface
                surface.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(), img);
            }
        } else {
            String[] fillSplit = fill.split("\\(");
            if (fillSplit[0].equals("color")) {
                surface.setColor(colorsParser.colorFromString(fill));
                surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                        (int) this.shape.getWidth(), (int) this.shape.getHeight());
            } else {
                Image img = this.defaultImage;
                // Draw the image on a DrawSurface
                surface.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(), img);
            }
        }
        // Draw the block
        if (this.strokeColor != null) {
            surface.setColor(this.strokeColor);
            surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());
        }
    }
    /**
     * Loads the images for this block.
     *
     * @return HashMap of the images.
     */
    public HashMap<Integer, Image> loadImages() {
        HashMap<Integer, Image> imagesMap = new HashMap<>();
         for (int i = 1; i <= this.hitPoints; i++) {
            if (this.fillHashMap.containsKey(i)) {
                String[] fillSplit = this.fillHashMap.get(i).split("\\(");
                if (fillSplit[0].equals("image")) {
                    String imageName = fillSplit[1].replace(")", "");
                    // Load the image data into an java.awt.Image object
                    Image img = null;
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
                    try {
                        if (is != null) {
                            img = ImageIO.read(is);
                        } else {
                            System.err.println("Image file not found.");
                        }
                    } catch (IOException e) {
                        System.err.println("Failed loading block image.");
                    } finally {
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                            System.err.println("Failed closing Image file.");
                        }
                    }
                    imagesMap.put(i, img);
                }
            }
         }
         return imagesMap;
    }
    /**
     * Loads the default image.
     *
     * @return the default image.
     */
    public Image loadDefaultImage() {
        if (this.fill != null) {
            String[] fillSplit = this.fill.split("\\(");
            if (fillSplit[0].equals("image")) {
                String imageName = fillSplit[1].replace(")", "");
                // Load the image data into an java.awt.Image object
                Image img = null;
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
                try {
                    if (is != null) {
                        img = ImageIO.read(is);
                    } else {
                        System.err.println("Image file not found.");
                    }
                } catch (IOException e) {
                    System.err.println("Failed loading block image.");
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
        return null;
    }
    /**
     * Draws the number of hit-points on the block.
     *
     * @param surface a surface to draw on it.
     */
    private void drawHitPoints(DrawSurface surface) {
        // X and Y values for the top left corner
        int xUpperLeft = (int) (this.shape.getUpperLeft().getX() + (this.shape.getWidth() / 2) - 4);
        int yUpperLeft = (int) (this.shape.getUpperLeft().getY() + (this.shape.getHeight() / 2) + 5);
        // The size of the text
        int fontSize = 16;
        // number of hit-points
        String hitPoint;
        if (this.hitPoints == 0) {
            hitPoint = "X";
        } else {
            hitPoint = String.valueOf(this.hitPoints);
        }
        // Draw the number of hit-points on the block
        surface.setColor(Color.WHITE);
        surface.drawText(xUpperLeft, yUpperLeft, hitPoint, fontSize);
    }
    /**
     * Updates the number of hit-points of the block after a hitting.
     *
     * When a ball hits the block, the number of hit-points decreases. When the number of points is 0, it stays 0.
     */
    private void updateHitPoints() {
        if (this.hitPoints > 0) {
            this.hitPoints -= 1;
        }
    }
    /**
     * Returns a new velocity expected after the hit.
     *
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *  The return is the new velocity expected after the hit (based on the force the object inflicted on us):
     *  if the hit is at the left or right of the rectangle - change the dx, and if the hit is at the top or bottom
     *  of the rectangle - change the dy.
     *
     * @param hitter the ball that's doing the hitting.
     * @param collisionPoint the point we collided with the object.
     * @param currentVelocity the velocity before the hit.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        // If the hit is at the left or right of the rectangle
        if (GameLevel.compareDoubles(collisionPoint.getX(), this.shape.getUpperLeft().getX())
            || GameLevel.compareDoubles(collisionPoint.getX(),
                this.shape.getUpperLeft().getX() + this.shape.getWidth())) {
            // Change the dx
            newDx = (-currentVelocity.getDx());
        }
        // if the hit is at the top or bottom of the rectangle
        if (GameLevel.compareDoubles(collisionPoint.getY(), this.shape.getUpperLeft().getY())
            || GameLevel.compareDoubles(collisionPoint.getY(),
                this.shape.getUpperLeft().getY() + this.shape.getHeight())) {
            // Change the dy
            newDy = (-currentVelocity.getDy());
        }
        // Notify all of the registered HitListener objects that a hit occurs
        this.notifyHit(hitter);
        // Update the number of hit-points of the block
        this.updateHitPoints();
        // Return the new velocity
        return new Velocity(newDx, newDy);
    }
    /**
     * This method will be called whenever a hit() occurs, and notifies all of the registered HitListener objects by
     *  calling their hitEvent method.
     *
     * @param hitter the ball that's doing the hitting.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Adds hl as a listener to hit events.
     *
     * @param hl a HitListener.
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }
    /**
     * Removes hl from the list of listeners to hit events.
     *
     * @param hl a HitListener.
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
    /**
     * Notifies the sprite that time has passed.
     */
    public void timePassed() {
        // Do nothing
    }
    /**
     * Adds this block to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * Removes this block from the game.
     *
     * @param game the game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }
}
