package levels;

import java.awt.Color;

/**
 * ColorsParser - parse color definition and return the specified color.
 *
 * @author Ayelet Tennenboim
 */
public class ColorsParser {
    /**
     * Parses color definition and returns the specified color.
     *
     * @param s string of the color.
     * @return the specified color.
     */
    public java.awt.Color colorFromString(String s) {
        // Delete the ')' char from the string
        s = s.replace(")", "");
        // Split the string by '('
        String[] splitColor = s.split("\\(");

        if (splitColor[0].equals("color")) {
            if (splitColor[1].equals("RGB")) {
                String[] rgbValues = splitColor[2].split(",");
                int r = Integer.parseInt(rgbValues[0]);
                int g = Integer.parseInt(rgbValues[1]);
                int b = Integer.parseInt(rgbValues[2]);
                return new Color(r, g, b);
            } else {
                if (splitColor[1].equals("black")) {
                    return Color.black;
                } else if (splitColor[1].equals("blue")) {
                    return Color.blue;
                } else if (splitColor[1].equals("cyan")) {
                    return Color.cyan;
                } else if (splitColor[1].equals("gray")) {
                    return Color.gray;
                } else if (splitColor[1].equals("lightGray")) {
                    return Color.lightGray;
                } else if (splitColor[1].equals("green")) {
                    return Color.green;
                } else if (splitColor[1].equals("orange")) {
                    return Color.orange;
                } else if (splitColor[1].equals("pink")) {
                    return Color.pink;
                } else if (splitColor[1].equals("red")) {
                    return Color.red;
                } else if (splitColor[1].equals("white")) {
                    return Color.white;
                } else if (splitColor[1].equals("yellow")) {
                    return Color.yellow;
                }
            }
        }
        return null;
    }
}
