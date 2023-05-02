package animation.menu;

import animation.AnimationRunner;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.LevelInformation;
import levels.LevelSpecificationReader;
import score.HighScoresTable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This task runs the game.
 *
 * @author Ayelet Tennenboim
 */
public class PlayGameTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private DialogManager dialog;
    private HighScoresTable table;
    private File scoresFile;
    private String pathToLevel;

    /**
     * Constructor - creates a new ShowHiScoresTask.
     *
     * @param runner ar a runner to run the animation object.
     * @param keyboard a keyboard to read the key presses.
     * @param dialog a DialogManager.
     * @param table the high-scores table.
     * @param scoresFile the high-scores file.
     * @param path path to the level file.
     */
    public PlayGameTask(AnimationRunner runner, KeyboardSensor keyboard, DialogManager dialog, HighScoresTable table,
                        File scoresFile, String path) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.dialog = dialog;
        this.table = table;
        this.scoresFile = scoresFile;
        this.pathToLevel = path;
    }
    /**
     * Runs the game.
     *
     * @return null.
     */
    public Void run() {
        InputStream is = null;
        try {
            // Create an input stream
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.pathToLevel);
            // Create a buffered reader
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                // Get a list of levelInformation
                List<LevelInformation> levelsList = LevelSpecificationReader.fromReader(reader);
                // Create a new GameFlow
                GameFlow game = new GameFlow(this.runner, this.keyboard, this.dialog, this.table, this.scoresFile);
                // Run the levels
                game.runLevels(levelsList);
            }
        } catch (Exception e) {
            System.err.println("Missing file.");
            System.exit(0);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.err.println("Failed closing level file");
            }
        }
        return null;
    }
}