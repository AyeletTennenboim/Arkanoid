package levels;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * BlockDefinitionsReader - this class is in charge of reading block definitions from a file.
 *
 * @author Ayelet Tennenboim
 */
public class BlockDefinitionsReader {
    /**
     * Reads levels information specification from a file and returns a list of LevelInformation objects.
     *
     * @param reader a reader to read the Block Definitions File.
     * @return blocks factory.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        // Create a blocks factory
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        // Line reader to read the Block Definitions File
        BufferedReader lineReader = new BufferedReader(reader);
        // Default values for blocks
        HashMap<String, String> defaultMap = new HashMap<>();
        // Create a colors parser
        ColorsParser colorsParser = new ColorsParser();
        String line;
        try {
            // Go over all the lines in the file
            while ((line = lineReader.readLine()) != null) {
                // Split the line by spaces
                String[] splitLine = line.split(" ");
                // Default values for blocks
                if (splitLine[0].equals("default")) {
                    for (int i = 1; i < splitLine.length; i++) {
                        String[] defaultField = splitLine[i].split(":");
                        defaultMap.put(defaultField[0], defaultField[1]);
                    }
                // Block definitions
                } else if (splitLine[0].equals("bdef")) {
                    HashMap<String, String> blockDefMap = new HashMap<>();
                    for (int i = 1; i < splitLine.length; i++) {
                        String[] blockField = splitLine[i].split(":");
                        blockDefMap.put(blockField[0], blockField[1]);
                    }
                    // Values for the block Creator
                    double width = 0;
                    double height = 0;
                    String fill = null;
                    Color strokeColor = null;
                    int hitPoints = 0;
                    // Get the values from the HashMaps
                    if (blockDefMap.containsKey("width")) {
                        width = Double.parseDouble(blockDefMap.get("width"));
                    } else if (defaultMap.containsKey("width")) {
                        width = Double.parseDouble(defaultMap.get("width"));
                    }
                    if (blockDefMap.containsKey("height")) {
                        height = Double.parseDouble(blockDefMap.get("height"));
                    } else if (defaultMap.containsKey("height")) {
                        height = Double.parseDouble(defaultMap.get("height"));
                    }
                    if (blockDefMap.containsKey("fill")) {
                        fill = blockDefMap.get("fill");
                    } else if (defaultMap.containsKey("fill")) {
                        fill = defaultMap.get("fill");
                    }
                    if (blockDefMap.containsKey("stroke")) {
                        strokeColor = colorsParser.colorFromString(blockDefMap.get("stroke"));
                    } else if (defaultMap.containsKey("stroke")) {
                        strokeColor = colorsParser.colorFromString(defaultMap.get("stroke"));
                    }
                    if (blockDefMap.containsKey("hit_points")) {
                        hitPoints = Integer.parseInt(blockDefMap.get("hit_points"));
                    } else if (defaultMap.containsKey("hit_points")) {
                        hitPoints = Integer.parseInt(defaultMap.get("hit_points"));
                    }
                    // Create a fill HashMap
                    HashMap<Integer, String> fillHashMap = new HashMap<>();
                    for (int i = 0; i <= hitPoints; i++) {
                        if (blockDefMap.containsKey("fill-" + i)) {
                            fillHashMap.put(i, blockDefMap.get("fill-" + i));
                        }
                    }
                    for (int i = 0; i <= hitPoints; i++) {
                        if (defaultMap.containsKey("fill-" + i)) {
                            fillHashMap.put(i, defaultMap.get("fill-" + i));
                        }
                    }
                    // In case of a missing value, print a suitable message and close the program
                    if ((width == 0) || (height == 0) || (hitPoints == 0)
                            || ((fill == null) && (fillHashMap.size() < hitPoints))) {
                        System.err.println("Error: Missing value in the block definitions file.");
                        System.exit(0);
                    }
                    // Create a new block creator
                    Creator blockCreator = new Creator(width, height, fill, strokeColor, fillHashMap, hitPoints);
                    // Add the block creator to the factory
                    factory.addBlockCreator(blockDefMap.get("symbol"), blockCreator);
                // Spacers definitions
                } else if (splitLine[0].equals("sdef")) {
                    HashMap<String, String> spacerDefMap = new HashMap<>();
                    for (int i = 1; i < splitLine.length; i++) {
                        String[] spacerField = splitLine[i].split(":");
                        spacerDefMap.put(spacerField[0], spacerField[1]);
                    }
                    String symbol = spacerDefMap.get("symbol");
                    int width = Integer.parseInt(spacerDefMap.get("width"));
                    // Add the spacer to the factory
                    factory.addSpacer(symbol, width);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed reading Block Definitions file");
        } finally {
            try {
                lineReader.close();
            } catch (IOException e) {
                System.err.println("Failed closing Block Definitions file");
            }
        }
        // Return the blocks factory
        return factory;
    }
}
