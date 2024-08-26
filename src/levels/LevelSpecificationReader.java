package levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * LevelSpecificationReader - this class is in charge of reading levels information specification from a file.
 *
 * @author Ayelet Tennenboim
 */
public class LevelSpecificationReader {
    /**
     * Reads levels information specification from a file and returns a list of LevelInformation objects.
     *
     * @param reader a reader to read the Levels Specification File.
     * @return a list of LevelInformation objects.
     */
    public static List<LevelInformation> fromReader(java.io.Reader reader) {
        // List of LevelInformation objects
        List<LevelInformation> levelInformationList = new ArrayList<>();
        // Line reader to read the Levels Specification File
        BufferedReader lineReader = new BufferedReader(reader);
        String line;
        try {
            // Go over all the lines in the file
            while ((line = lineReader.readLine()) != null) {
                // List to keep the lines read from the file
                List<String> levelByLines = new ArrayList<>();
                // List to keep the block Layout
                List<String> blockLayout = new ArrayList<>();
                // HashMap to keep fields and their values
                HashMap<String, String> fieldsHashMap = new HashMap<>();
                // Read the information of one level
                if (line.equals("START_LEVEL")) {
                    while (!line.equals("END_LEVEL")) {
                        line = lineReader.readLine();
                        levelByLines.add(line);
                    }
                    int endFields = levelByLines.indexOf("START_BLOCKS");
                    // Go over the fields of this level
                    for (int j = levelByLines.indexOf("START_LEVEL") + 1; j < endFields; j++) {
                        // Split the field and its value
                        String[] field = levelByLines.get(j).split(":");
                        try {
                            // Put the field and its value in the HashMap
                            fieldsHashMap.put(field[0], field[1]);
                        // In case of a missing field, print a suitable message and close the program
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.err.println("Error: Missing field in the levels specification file.");
                            System.exit(0);
                        }
                    }
                    // If there are less than 10 field, print a suitable message and close the program
                    if (fieldsHashMap.values().size() < 10) {
                        System.err.println("Error: Missing field in the levels specification file.");
                        System.exit(0);
                    }
                    int endBlocks = levelByLines.indexOf("END_BLOCKS");
                    // Go over the rows of blocks in the block layout
                    for (int i = levelByLines.indexOf("START_BLOCKS") + 1; i < endBlocks; i++) {
                        // Add each row to the block layout list
                        blockLayout.add(levelByLines.get(i));
                    }
                // Produce LevelInformation object
                LevelInformation levelInfo = new Level(fieldsHashMap, blockLayout);
                // Add the LevelInformation objects to the list
                levelInformationList.add(levelInfo);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed reading Level Specification file");
        } finally {
            try {
                lineReader.close();
            } catch (IOException e) {
                System.err.println("Failed closing Level Specification file");
            }
        }
        // Return the list of LevelInformation objects
        return levelInformationList;
    }
}