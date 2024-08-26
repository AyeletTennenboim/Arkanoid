package geometricshapes;

/**
 * Rectangle - a rectangle has location, width and height, and it may intersect with lines.
 *
 * @author Ayelet Tennenboim
 */
public class Rectangle {
    // Four vertices of the rectangle
    private Point upperLeft;
    // Width and height
    private double width;
    private double height;

    /**
     * Constructor - creates a new rectangle with four vertices and width/height.
     *
     * @param upperLeft the upper left point of the rectangle.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        // Width and height
        this.width = width;
        this.height = height;
        // Four vertices of the rectangle
        this.upperLeft = upperLeft;
    }
    /**
     * Finds and returns a (possibly empty) list of intersection points with the specified line.
     *
     * @param line a specified line.
     * @return a list of intersection points with the specified line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        Point upperRight = new Point((upperLeft.getX() + width), upperLeft.getY());
        Point lowerLeft = new Point(upperLeft.getX(), (upperLeft.getY() + height));
        Point lowerRight = new Point((upperLeft.getX() + width), (upperLeft.getY() + height));
        // Create the four sides of the rectangle
        Line upperLine = new Line(this.upperLeft, upperRight);
        Line lowerLine = new Line(lowerLeft, lowerRight);
        Line leftLine = new Line(this.upperLeft, lowerLeft);
        Line rightLine = new Line(upperRight, lowerRight);
        // Create an arrayList of points
        java.util.List<Point> intersectionList = new java.util.ArrayList<Point>();
        // Add the intersection points between the rectangle and the line to the list
        if (upperLine.isIntersecting(line)) {
            intersectionList.add(upperLine.intersectionWith(line));
        }
        if (lowerLine.isIntersecting(line)) {
            intersectionList.add(lowerLine.intersectionWith(line));
        }
        if (leftLine.isIntersecting(line)) {
            intersectionList.add(leftLine.intersectionWith(line));
        }
        if (rightLine.isIntersecting(line)) {
            intersectionList.add(rightLine.intersectionWith(line));
        }
        // Return the intersection points list
        return intersectionList;
    }
    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Sets the upper-left point of the rectangle.
     *
     * @param upLeft the new upper-left point of the rectangle.
     */
    public void setUpperLeft(Point upLeft) {
        this.upperLeft = upLeft;
    }
}
