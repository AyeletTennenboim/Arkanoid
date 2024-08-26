package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.KeyboardSensor;
import collisiondetection.Collidable;
import geometricshapes.Point;
import hitting.BlockRemover;
import hitting.ScoreTrackingListener;
import levels.LevelInformation;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;
import sprites.LevelName;
import sprites.Block;
import sprites.Sprite;
import sprites.Paddle;
import sprites.SpriteCollection;
import sprites.ball.BallRemover;
import geometricshapes.Rectangle;
import sprites.ball.Ball;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * GameLevel - the GameLevel class holds the sprites and the collidables of the game, and it is in charge of the
 *  animation.
 *
 * @author Ayelet Tennenboim
 */
public class GameLevel implements Animation {
    // Level information
    private LevelInformation levelInformation;
    // Sprites collection
    private SpriteCollection sprites;
    // Paddle
    private Paddle paddle;
    // Collidables collection
    private GameEnvironment environment;
    // Count of remaining blocks
    private Counter remainingBlocks;
    // Count of available balls
    private Counter availableBalls;
    // Counter for keeping track of scores
    private Counter score;
    // Count of number of lives
    private Counter lives;
    // A keyboard to read the key presses
    private KeyboardSensor keyboard;
    // AnimationRunner runs the animation object
    private AnimationRunner runner;
    // Indication whether the animation is running
    private boolean running;

    /**
     * Constructor - creates a new GameLevel.
     *
     * @param information the Level information.
     * @param ar a runner to run the animation object.
     * @param ks a keyboard to read the key presses.
     * @param score counter for keeping track of scores.
     * @param lives Count of number of lives.
     */
    public GameLevel(LevelInformation information, AnimationRunner ar, KeyboardSensor ks, Counter score,
                     Counter lives) {
        this.levelInformation = information;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        // Create a counter to count the remaining blocks
        this.remainingBlocks = new Counter(this.levelInformation.numberOfBlocksToRemove());
        // Create a counter to count the available balls
        this.availableBalls = new Counter(this.levelInformation.numberOfBalls());
        this.score = score;
        this.lives = lives;
        this.keyboard = ks;
        this.runner = ar;
        this.running = true;
    }
    /**
     * Adds the given collidable to the environment.
     *
     * @param c the given collidable.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }
    /**
     * Removes the given collidable from the environment.
     *
     * @param c the given collidable.
     */
    public void removeCollidable(Collidable c) { this.environment.removeCollidable(c); }
    /**
     * Adds the given sprite to the collection.
     *
     * @param s the given sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    /**
     * Removes the given sprite from the collection.
     *
     * @param s the given sprite.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
    /**
     * Returns the number of remaining blocks.
     *
     * @return the number of remaining blocks.
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }
    /**
     * Initializes a new level: creates blocks and indicators, and adds them to the game.
     */
    public void initialize() {
        // Create blocks for the game
        List<Block> blocks = levelInformation.blocks();
        // Create blocks at the borders of the screen
        List<Block> bordersBlocks = createBordersBlocks();
        // Create a death-region below the bottom of the screen
        Block deathRegion = new Block(new Rectangle(new Point(0, 600), 800, 30), "color(lightGray)", null, null, 0);
        // Create a BlockRemover
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        // Create a BallRemover
        BallRemover ballRemover = new BallRemover(this, this.availableBalls);
        // Create a ScoreTrackingListener
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this, this.score);
        // Create a LevelName
        LevelName levelName = new LevelName(levelInformation.levelName());
        // Add the background to the game
        this.levelInformation.getBackground().addToGame(this);
        // Add the BallRemover to the death-region
        deathRegion.addHitListener(ballRemover);
        // Add the death-region to the game
        deathRegion.addToGame(this);
        // Go over the bordersBlocks list
        for (Block b : bordersBlocks) {
            // Add the block to the game
            b.addToGame(this);
        }
        // Create a ScoreIndicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        // Add the ScoreIndicator to the game
        scoreIndicator.addToGame(this);
        // Create a LivesIndicator
        LivesIndicator livesIndicator = new LivesIndicator(this.lives);
        // Add the LivesIndicator to the game
        livesIndicator.addToGame(this);
        // Go over the blocks list
        for (Block b : blocks) {
            // Add the ScoreTrackingListener to the block
            b.addHitListener(scoreTracking);
            // Add the BlockRemover to the block
            b.addHitListener(blockRemover);
            // Add the block to the game
            b.addToGame(this);
        }
        // Add the levelName to the game
        levelName.addToGame(this);
    }
    /**
     * This method is in charge of stopping condition.
     *
     * @return true if the animation is not running and should stop, and false otherwise.
     */
    public boolean shouldStop() { return !this.running; }
    /**
     * This method goes over all the sprites, and call drawOn and timePassed on each of them. The method gives an
     *  indication when there are either no more blocks or no more balls.
     *
     * @param d a surface to draw on it.
     */
    public void doOneFrame(DrawSurface d) {
        // If no more blocks are available
        if (this.remainingBlocks.getValue() == 0) {
            // Add 100 points for clearing an entire level (destroying all blocks)
            this.score.increase(100);
            // Remove the paddle from the game
            this.paddle.removeFromGame(this);
            // Indicate that this turn is over
            this.running = false;
        }
        // If no more balls are available
        if (this.availableBalls.getValue() == 0) {
            // Remove the paddle from the game
            this.paddle.removeFromGame(this);
            // Indicate that this turn is over
            this.running = false;
        }
        // Draw all the sprites on the surface
        this.sprites.drawAllOn(d);
        // Call timePassed() on all sprites
        this.sprites.notifyAllTimePassed();
        // Pause the game when pressing the 'p' key
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                    new PauseScreen()));
        }
    }
    /**
     * Runs the current animation loop of one turn of the game.
     *
     * This method runs the animation loop, that goes over all the sprites, and call drawOn and timePassed on each of
     *  them. The method starts by creating balls and a paddle in the middle of the bottom of the screen, and
     *  indicates when there are either no more blocks or no more balls.
     */
    public void playOneTurn() {
        // Create new balls and add them to the game
        createBalls(this.levelInformation.numberOfBalls());
        // Create a new paddle
        this.paddle = this.createPaddle();
        // Add the paddle to the game
        paddle.addToGame(this);
        // Countdown before turn starts
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        // Run the current animation (one turn of the game)
        this.runner.run(this);
        // If there are no more available balls
        if (this.availableBalls.getValue() == 0) {
            // Renew the number of available balls
            this.availableBalls.increase(this.levelInformation.numberOfBalls());
            // Update the number of lives
             this.lives.decrease(1);
        }
    }
    /**
     * Creates an array of balls with center point, size, color and velocity, and adds them to the game.
     *
     * @param num number of ball to add to the game.
     */
    public void createBalls(int num) {
        // Create an array of balls
        Ball[] balls = new Ball[num];
        // Calculate the initial distance between the balls
        int distanceBetweenBalls = this.levelInformation.paddleWidth() / this.levelInformation.numberOfBalls();
        // X and Y values of the center of the ball
        int valueOfX;
        int valueOfY = 566;
        // If the number of balls is odd
        if (num % 2 != 0) {
            // Calculate the X value of the first ball in the array
            valueOfX = 400 - (((num - 1) / 2) * distanceBetweenBalls);
        // If the number of balls is even
        } else {
            // Calculate the X value of the first ball in the array
            valueOfX = 400 - (distanceBetweenBalls / 2) - (((num / 2) - 1) * distanceBetweenBalls);
        }
        // Create the balls array
        for (int i = 0; i < num; i++) {
            // Create a ball
            balls[i] = new Ball(valueOfX, valueOfY, 7, new Color(250, 250, 250));
            // Set the velocity of the ball
            balls[i].setVelocity(this.levelInformation.initialBallVelocities().get(i));
            // Set the ball's game environment
            balls[i].setGameEnvironment(this.environment);
            // Add the ball to the game
            balls[i].addToGame(this);
            // Calculate the value of x for the next ball
            valueOfX += distanceBetweenBalls;
        }
    }
    /**
     * Creates an ArrayList of blocks that are at the borders of the screen.
     *
     * The method creates three big blocks at the borders of the screen, adds all the blocks to an ArrayList and
     *  returns the list.
     *
     * @return an ArrayList of bordersBlocks.
     */
    public List<Block> createBordersBlocks() {
        // Create an ArrayList of blocks
        List<Block> bordersBlocks = new ArrayList<Block>();
        // Create blocks at the borders of the screen
        bordersBlocks.add(new Block(new Rectangle(new Point(0, 0), 800, 50), "color(lightGray)", null, null, 0));
        bordersBlocks.add(new Block(new Rectangle(new Point(0, 30), 20, 570), "color(lightGray)", null, null, 0));
        bordersBlocks.add(new Block(new Rectangle(new Point(780, 30), 20, 570), "color(lightGray)", null, null, 0));
        // Return the bordersBlocks list
        return bordersBlocks;
    }
    /**
     * Creates a paddle.
     *
     * @return a new paddle.
     */
    public Paddle createPaddle() {
        int upperLeftX = 400 - (this.levelInformation.paddleWidth() / 2);
        int upperLeftY = 575;
        // The upper-left point of the paddle
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        // Width
        double width = this.levelInformation.paddleWidth();
        // Height
        double height = 20;
        // Create a rectangle for the paddle
        Rectangle rec = new Rectangle(upperLeft, width, height);
        // The change in position on the x axis by pressing the right key
        double dx = this.levelInformation.paddleSpeed();
        // Color
        Color color = new Color(255, 192, 182);
        // Create a new paddle
        Paddle newPaddle = new Paddle(this.keyboard, rec, dx, color);
        // Return the new paddle
        return newPaddle;
    }
    /**
     * Compares two doubles.
     *
     * Subtracts one number from the other number and returns true if the difference between the numbers is under
     *  0.0000001, and false otherwise.
     *
     * @param num1 the first double to compare.
     * @param num2 the second double to compare.
     * @return true if the difference between the numbers is under 0.0000001, and false otherwise.
     */
    public static boolean compareDoubles(double num1, double num2) {
        return (Math.abs(num1 - num2) < 0.0000001);
    }
}