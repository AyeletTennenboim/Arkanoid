package sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * SpriteCollection - a collection of sprites. It will first draw all of the sprites, then notify all of them that
 *  time has passed.
 *
 * @author Ayelet Tennenboim
 */
public class SpriteCollection {
    // An ArrayList of sprites
    private List<Sprite> sprites;

    /**
     * Constructor - creates a new sprites.Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**
     * Adds the given sprite to the collection.
     *
     * @param s the given sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }
    /**
     * Removes the given sprite from the collection.
     *
     * @param s the given sprite.
     */
    public void removeSprite(Sprite s) { this.sprites.remove(s); }
    /**
     * Calls timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the sprites before iterating over them
        List<Sprite> spritesList = new ArrayList<Sprite>(this.sprites);
        // Call timePassed() on all sprites
        for (Sprite s : spritesList) {
            s.timePassed();
        }
    }
    /**
     * Calls drawOn(d) on all sprites.
     *
     * @param d a surface to draw on it.
     */
    public void drawAllOn(DrawSurface d) {
        // Make a copy of the sprites before iterating over them
        List<Sprite> spritesList = new ArrayList<Sprite>(this.sprites);
        // Call drawOn(d) on all sprites
        for (Sprite s : spritesList) {
            s.drawOn(d);
        }
    }
}