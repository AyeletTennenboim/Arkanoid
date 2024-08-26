package levels;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * LevelSetsReader - this class is in charge of reading the LevelSets file.
 *
 * @author Ayelet Tennenboim
 */
public class LevelSetsReader {

    /**
     * Reads the LevelSets file and returns a list of LevelSets.
     *
     * @param reader a reader to read the Block Definitions File.
     * @return a list of LevelSets.
     */
    public static List<LevelSet> fromReader(java.io.Reader reader) {
        // Create an ArrayList of levelSets
        List<LevelSet> levelSetsList = new ArrayList<LevelSet>();
        String line = null;
        String key = null;
        String description = null;
        String path = null;
        // Create a LineNumberReader
        LineNumberReader lineReader = new LineNumberReader(reader);
        try {
            while ((line = lineReader.readLine()) != null) {
                // If the line number is odd
                if (lineReader.getLineNumber() % 2 == 1) {
                    String[] splitLine = line.split(":");
                    key = splitLine[0];
                    description = splitLine[1];
                // If the line number is even
                } else {
                    path = line;
                    levelSetsList.add(new LevelSet(key, description, path));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed reading Level Sets file");
        } finally {
            try {
                lineReader.close();
            } catch (IOException e) {
                System.err.println("Failed closing Level Sets file");
            }
        }
        // Return the list of levelSets
        return levelSetsList;
    }
}
