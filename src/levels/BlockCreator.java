package levels;

import sprites.Block;

/**
 * BlockCreator - BlockCreator is an interface of a factory-object that is used for creating blocks.
 *
 * @author Ayelet Tennenboim
 */
public interface BlockCreator {
    /**
     * Creates a block at the specified location.
     *
     * @param xpos the x position of the block.
     * @param ypos the y position of the block.
     * @return a block at the specified location.
     */
    Block create(int xpos, int ypos);
}
