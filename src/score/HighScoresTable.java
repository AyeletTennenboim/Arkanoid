package score;

import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HighScoresTable - this class manages a table of size high-scores in order to to present players with the
 *  scores-history of previous games.
 *
 * @author Ayelet Tennenboim
 */
public class HighScoresTable {
    // Number of high-scores the table can hold
    private int size;
    // A list of ScoreInfo
    private List<ScoreInfo> scoresTable;

    /**
     * Constructor - creates an empty high-scores table with the specified size.
     *  The size means that the table holds up to size top scores.
     *
     * @param size number of high-scores the table can hold.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.scoresTable = new ArrayList<ScoreInfo>();
    }
    /**
     * Adds a high-score.
     *
     * @param score a high-score to add to the table.
     */
    public void add(ScoreInfo score) {
        // Index to insert the new score
        int index = this.getRank(score.getScore()) - 1;
        // If the new score is one of the 'size' top scores
        if (index < this.size) {
            // Add the high-score to the table
            this.scoresTable.add(index, score);
        }
        // If the table exceeds its size
        if (this.scoresTable.size() > this.size) {
            // Remove the lowest score
            this.scoresTable.remove(this.size);
        }
    }
    /**
     * Returns table size.
     *
     * @return table size.
     */
    public int size() {
        return this.size;
    }
    /**
     * Returns the current high scores (the list is sorted such that the highest scores come first).
     *
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoresTable;
    }
    /**
     * Returns the rank of the current score.
     * (It means - Where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list).
     *
     * @param score a score to get its rank.
     * @return the rank of the current score.
     */
    public int getRank(int score) {
        if (this.scoresTable.isEmpty()) {
            return 1;
        } else {
            for (ScoreInfo s : scoresTable) {
                if (score > s.getScore()) {
                    return scoresTable.indexOf(s) + 1;
                }
            }
        }
        return this.scoresTable.size() + 1;
    }
    /**
     * Clears the table.
     */
    public void clear() {
        this.scoresTable.clear();
    }
    /**
     * Loads table data from file (current table data is cleared).
     *
     * @param filename a file to load the table from.
     * @throws IOException if there is a problem with reading the file.
     */
    public void load(File filename) throws IOException {
        // Create new BufferedReader
        BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        String name;
        int score;
        // Go over all the lines in the file
        while ((line = is.readLine()) != null) {
            // Split the name and the score
            String[] scoreInfo = line.split(", ");
            name = scoreInfo[0];
            score = Integer.parseInt(scoreInfo[1].replace(";", ""));
            // Add the new ScoreInfo to the table
            this.add(new ScoreInfo(name, score));
        }
        // Close the BufferedReader and the FileInputStream
        is.close();
    }
    /**
     * Saves table data to the specified file.
     *
     * @param filename a file to save the table data to.
     * @throws IOException if there is a problem with writing to the file.
     */
    public void save(File filename) throws IOException {
        // Create new PrintWriter
        PrintWriter os = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        // Print the table information to the file
        for (ScoreInfo s : scoresTable) {
            os.print(s.getName() + ", ");
            if (scoresTable.indexOf(s) < this.scoresTable.size() - 1) {
                os.println(s.getScore() + ";");
            } else {
                os.print(s.getScore() + ";");
            }
        }
        // Close the PrintWriter and the FileOutputStream
        os.close();
    }
    /**
     * Reads a table from file and returns it. If the file does not exist, or there is a problem with reading it,
     *  an empty table is returned.
     *
     * @param filename a file to read the table from.
     * @return the table that was read from the file.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable(5);
        try {
            table.load(filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return table;
    }
}
