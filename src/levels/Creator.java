package levels;

import geometricshapes.Point;
import geometricshapes.Rectangle;
import sprites.Block;
import java.awt.Color;
import java.util.HashMap;

/**
 * Creator - an object of this class is a factory-object that is used for creating blocks.
 *
 * @author Ayelet Tennenboim
 */
public class Creator implements BlockCreator {
    // Width
    private double width;
    // Height
    private double height;
    // Fill
    private String fill;
    // Stroke color
    private Color strokeColor;
    // HashMap of fills
    private HashMap<Integer, String> fillHashMap;
    // Number of hits that left to reach 0
    private int hitPoints;

    /**
     * Constructor - creates a new blockCreator.
     *
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param fill the fill of the block (color or image).
     * @param strokeColor the stroke color.
     * @param fillHashMap hashMap of fills.
     * @param hitPoints number of hits that left to reach 0.
     */
    public Creator(double width, double height, String fill, Color strokeColor, HashMap<Integer, String> fillHashMap,
                   int hitPoints) {
        this.width = width;
        this.height = height;
        this.fill = fill;
        this.strokeColor = strokeColor;
        this.fillHashMap = fillHashMap;
        this.hitPoints = hitPoints;
    }
    /**
     * Creates a block at the specified location.
     *
     * @param xpos the x position of the block.
     * @param ypos the y position of the block.
     * @return a block at the specified location.
     */
    public Block create(int xpos, int ypos) {
        Point upperLeft = new Point(xpos, ypos);
        Block block = new Block(new Rectangle(upperLeft, this.width, this.height), this.fill, this.strokeColor,
                this.fillHashMap, this.hitPoints);
        return block;
    }
}
