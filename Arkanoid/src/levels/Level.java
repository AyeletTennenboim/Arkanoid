package levels;

import sprites.Background;
import sprites.Block;
import sprites.Sprite;
import sprites.ball.Velocity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Level - this class specifies the information required to the level.
 *
 * @author Ayelet Tennenboim
 */
public class Level implements LevelInformation {
    // HashMap with fields and their values
    private HashMap<String, String> fieldsHashMap;
    // List with the block Layout
    private List<String> blockLayout;

    /**
     * Constructor - creates a new Level.
     *
     * @param fieldsHashMap HashMap with fields and their values.
     * @param blockLayout List with the block Layout.
     */
    public Level(HashMap<String, String> fieldsHashMap, List<String> blockLayout) {
        this.fieldsHashMap = fieldsHashMap;
        this.blockLayout = blockLayout;
    }
    /**
     * Returns the number of balls at each turn.
     *
     * @return the number of balls at each turn.
     */
    public int numberOfBalls() {
        return this.fieldsHashMap.get("ball_velocities").split(" ").length;
    }
    /**
     * Returns a list with the initial velocity of each ball.
     *
     * @return a list with the initial velocity of each ball.
     */
    public List<Velocity> initialBallVelocities() {
        // Create an ArrayList of velocities
        List<Velocity> velocities = new ArrayList<Velocity>();
        // Create an array of angles and speeds
        String[] angleAndSpeed = this.fieldsHashMap.get("ball_velocities").split(" ");
        for (String s : angleAndSpeed) {
            String[] angelAndSpeedSplit = s.split(",");
            int angle = Integer.parseInt(angelAndSpeedSplit[0]);
            int speed = Integer.parseInt(angelAndSpeedSplit[1]);
            // Create a new velocity and add it to the velocities list
            velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
        }
        // Return the velocities list
        return velocities;
    }
    /**
     * Returns the paddle speed.
     *
     * @return the paddle speed.
     */
    public int paddleSpeed() {
        return Integer.parseInt(this.fieldsHashMap.get("paddle_speed"));
    }
    /**
     * Returns the paddle width.
     *
     * @return the paddle width.
     */
    public int paddleWidth() {
        return Integer.parseInt(this.fieldsHashMap.get("paddle_width"));
    }
    /**
     * Returns a string of the level name. The level name will be displayed at the top of the screen.
     *
     * @return a string of the level name.
     */
    public String levelName() {
        return this.fieldsHashMap.get("level_name");
    }
    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level.
     */
    public Sprite getBackground() {
        return new Background(this.fieldsHashMap.get("background"));
    }
    /**
     * Returns a list of blocks that make up the level. Each block contains its size, color and location.
     *
     * @return a list of blocks that make up the level.
     */
    public List<Block> blocks() {
        // Create a new list of blocks
        List<Block> blocksList = new ArrayList<>();
        // Get x and y of the layout starting point
        int xPos = Integer.parseInt(this.fieldsHashMap.get("blocks_start_x"));
        int yPos = Integer.parseInt(this.fieldsHashMap.get("blocks_start_y"));
        BlocksFromSymbolsFactory blockFactory = null;
        try {
            // Create the block factory from the file
            blockFactory = BlockDefinitionsReader.fromReader(new BufferedReader(new InputStreamReader(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(fieldsHashMap.get("block_definitions")))));
        } catch (Exception e) {
            System.err.println("Missing file.");
            System.exit(0);
        }
        // Go over the rows of blocks from the blocks layout
        for (int i = 0; i < this.blockLayout.size(); i++) {
            // Split the row to chars
            char[] row = this.blockLayout.get(i).toCharArray();
            for (int j = 0; j < row.length; j++) {
                // If the char is a valid block symbol
                if (blockFactory.isBlockSymbol(String.valueOf(row[j]))) {
                    Block block = blockFactory.getBlock(String.valueOf(row[j]), xPos, yPos);
                    // Add the block to the list
                    blocksList.add(block);
                    xPos += block.getCollisionRectangle().getWidth();
                // If the char is a valid spacer symbol
                } else if (blockFactory.isSpaceSymbol(String.valueOf(row[j]))) {
                    xPos += blockFactory.getSpaceWidth(String.valueOf(row[j]));
                }
            }
            xPos = Integer.parseInt(this.fieldsHashMap.get("blocks_start_x"));
            yPos += Integer.parseInt(this.fieldsHashMap.get("row_height"));
        }
        // Return the blocks list
        return blocksList;
    }
    /**
     * Returns number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return number of blocks that should be removed before the level is considered to be "cleared".
     */
    public int numberOfBlocksToRemove() {
        return Integer.parseInt(this.fieldsHashMap.get("num_blocks"));
    }
}