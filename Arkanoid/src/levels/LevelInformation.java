package levels;

import sprites.Block;
import sprites.Sprite;
import sprites.ball.Velocity;
import java.util.List;

/**
 * The LevelInformation interface specifies the information required to fully describe a level.
 *
 * @author Ayelet Tennenboim
 */
public interface LevelInformation {
    /**
     * Returns the number of balls at each turn.
     *
     * @return the number of balls at each turn.
     */
    int numberOfBalls();
    /**
     * Returns a list with the initial velocity of each ball.
     *
     * @return a list with the initial velocity of each ball.
     */
    List<Velocity> initialBallVelocities();
    /**
     * Returns the paddle speed.
     *
     * @return the paddle speed.
     */
    int paddleSpeed();
    /**
     * Returns the paddle width.
     *
     * @return the paddle width.
     */
    int paddleWidth();
    /**
     * Returns a string of the level name. The level name will be displayed at the top of the screen.
     *
     * @return a string of the level name.
     */
    String levelName();
    /**
     * Returns a sprite with the background of the level.
     *
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();
    /**
     * Returns a list of blocks that make up the level. Each block contains its size, color and location.
     *
     * @return a list of blocks that make up the level.
     */
    List<Block> blocks();
    /**
     * Returns number of blocks that should be removed before the level is considered to be "cleared".
     *
     * @return number of blocks that should be removed before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}
