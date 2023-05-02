package levels;

import sprites.Block;
import java.util.HashMap;
import java.util.Map;

/**
 * BlocksFromSymbolsFactory - this class gets a symbol and create the desired block.
 *
 * @author Ayelet Tennenboim
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor - creates a new BlocksFromSymbolsFactory.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new HashMap<>();
        this.blockCreators = new HashMap<>();
    }
    /**
     * Adds a block creator to the blockCreators map.
     *
     * @param s a symbol.
     * @param creator a block creator.
     */
    public void addBlockCreator(String s, BlockCreator creator) {
        blockCreators.put(s, creator);
    }
    /**
     * Adds a spacer to the spacerWidths map.
     *
     * @param s a spacer-symbol.
     * @param width the width of the spacing element in pixels.
     */
    public void addSpacer(String s, int width) {
        spacerWidths.put(s, width);
    }
    /**
     * Returns true if the given string is a valid space symbol.
     *
     * @param s a symbol.
     * @return true if the given string is a valid space symbol, and false if not.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }
    /**
     * Returns true if the given string is a valid block symbol.
     *
     * @param s a symbol.
     * @return true if the given string is a valid block symbol, and false if not.
     */
    public boolean isBlockSymbol(String s) {
        return blockCreators.containsKey(s);
    }
    /**
     * Returns a block according to the definitions associated with the given symbol.
     *  The block will be located at position (xpos, ypos).
     *
     * @param s a symbol.
     * @param xpos the x position of the block.
     * @param ypos the y position of the block.
     * @return a block according to the definitions associated with the given symbol.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }
    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s a spacer-symbol.
     * @return the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}