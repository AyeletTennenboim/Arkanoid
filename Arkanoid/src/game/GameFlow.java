package game;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import score.HighScoresTable;
import score.ScoreInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * GameFlow - this class is in charge of creating the different levels of the game, and moving from one level to
 *  the next.
 *
 * @author Ayelet Tennenboim
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private DialogManager dialog;
    // High-scores table
    private HighScoresTable table;
    // High-scores file
    private File scoresFile;
    // Counter for keeping track of scores
    private Counter score;
    // Count of number of lives
    private Counter lives;

    /**
     * Constructor - creates a new GameFlow.
     *
     * @param ar a runner to run the animation object.
     * @param ks a keyboard to read the key presses.
     * @param dialog a DialogManager.
     * @param table the high-scores table.
     * @param scoresFile the high-scores file.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, DialogManager dialog, HighScoresTable table,
                    File scoresFile) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.dialog = dialog;
        this.table = table;
        this.scoresFile = scoresFile;
        // Create a counter for keeping track of scores
        this.score = new Counter(0);
        // Create a counter to count number of lives
        this.lives = new Counter(7);
    }
    /**
     * Runs the game - starts the animation loop.
     *
     * While there are lives left, the method calls playOneTurn() method in each level of the game. When all the balls
     *  fall out of the screen: one life is lost, new balls and paddle are created, and the level continues. When there
     *  are no more blocks, the game moves to the next level. When the player has 0 lives, the game ends.
     *
     * @param levels LevelInformation list.
     */
    public void runLevels(List<LevelInformation> levels) {
        // Go over the LevelInformation list
        for (LevelInformation levelInfo : levels) {
            // Create a new GameLevel
            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor, this.score,
                    this.lives);
            // Initialize the new level
            level.initialize();
            // While level has more blocks and player has more lives
            while ((this.lives.getValue() > 0) && (level.getRemainingBlocks() > 0))  {
                // Play one turn of the game
                level.playOneTurn();
            }
            // If no more lives left
            if (this.lives.getValue() <= 0) {
                break;
            }
        }
        // Create a new EndScreen and run its animation
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(lives, score)));
        // If the score is one of the highest scores in the game
        if (this.table.getRank(this.score.getValue()) <= this.table.size()) {
            // Ask the player to his name
            String name = this.dialog.showQuestionDialog("Name", "What is your name?", "");
            // Add the player to the score table
            this.table.add(new ScoreInfo(name, this.score.getValue()));
            try {
                // Save the table
                table.save(scoresFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Create a new HighScoresAnimation and run its animation
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(table)));
    }
}
