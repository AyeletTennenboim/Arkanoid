package game;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.menu.ShowHiScoresTask;
import animation.menu.Menu;
import animation.menu.MenuAnimation;
import animation.menu.PlayGameTask;
import animation.menu.Task;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelSet;
import levels.LevelSetsReader;
import score.HighScoresTable;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Run the game.
 *
 * @author Ayelet Tennenboim
 */
public class Ass7Game {
    /**
     * Creates a list of LevelInformation and runs the game.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // Create a file to save the high-scores
        File scoresFile = new File("highscores.txt");
        HighScoresTable table = null;
        try {
            // If a high-scores file does not exist
            if (!scoresFile.exists()) {
                // Create a new file
                scoresFile.createNewFile();
                // Create a new table
                table = new HighScoresTable(5);
                // Save the table to the file
                table.save(scoresFile);
            // If a high-scores file exists
            } else {
                // Load the table from the file
                table = HighScoresTable.loadFromFile(scoresFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Create a window with the title "Arkanoid" which is 800 pixels wide and 600 pixels high
        GUI gui = new GUI("Arkanoid", 800 , 600);
        // Create an AnimationRunner to run the animation object
        AnimationRunner runner = new AnimationRunner(gui);
        // Create a keyboard to read the key presses
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        // Create a DialogManager
        DialogManager dialog = gui.getDialogManager();
        // Create a new menu
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid Menu", runner, keyboard);
        // Create a new sub-menu
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("   Level Sets", runner, keyboard);
        // Anonymous Class - this class is in charge of quit the game
        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(1);
                return null;
            }
        };
        InputStream is = null;
        List<LevelSet> levelSets = null;
        // If the method received a parameter
        if (args.length > 0) {
            // Read the file from the path received as a parameter
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
        } else {
            // Read a default level-sets file
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("level_sets.txt");
        }
        // Create a buffered reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        // Get a list of level sets
        levelSets = LevelSetsReader.fromReader(reader);
        for (LevelSet level : levelSets) {
            // Add selection to the sub-menu
            subMenu.addSelection(level.getKey(), level.getDescription(), new PlayGameTask(runner, keyboard, dialog,
                    table, scoresFile, level.getPath()));
        }
        // Add sub-menu to the menu
        menu.addSubMenu("s", "Start New Game", subMenu);
        // Add selections to the menu
        menu.addSelection("h", "High Scores Table", new ShowHiScoresTask(runner, keyboard,
                new HighScoresAnimation(table)));
        menu.addSelection("q", "Quit", quit);
        while (true) {
            // Run the menu
            runner.run(menu);
            // Wait for user selection
            Task<Void> task = menu.getStatus();
            // Run the selected task
            task.run();
        }
    }
}