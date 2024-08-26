package geometricshapes;

/**
 * Point - A point has an x and a y value, and can measure the distance to other points, and if it is equal to another
 *  point.
 *
 * @author Ayelet Tennenboim
 */
public class Point {
    // x and y values of the point
    private double x;
    private double y;

    /**
     * Constructor.
     *
     * @param x the x value of the point.
     * @param y the y value of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Measures the distance between this point and another point.
     *
     * @param other a point that the method measures the distance between it and this point.
     * @return the distance between the two points.
     */
    public double distance(Point other) {
        // Calculation of the distance
        double beforeRoot = ((this.x - other.getX()) * (this.x - other.getX())) + ((this.y - other.getY())
                            * (this.y - other.getY()));
        double distance = Math.sqrt(beforeRoot);
        return distance;
    }
    /**
     * Checks if this point is equal to another point.
     *
     * @param other a point that the method compares to this point.
     * @return true if the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        // Compare x and y values of the points, return true if they are equal and false if they are not
        return ((other.getX() == this.x) && (other.getY() == this.y));
    }
    /**
     * Gets the x value of this point.
     *
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * Gets the y value of this point.
     *
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }
    /**
     * Sets the x value of this point.
     *
     * @param x1 a number to set the x value of this point.
     */
    public void setX(double x1) {
        this.x = x1;
    }
    /**
     * Sets the y value of this point.
     *
     * @param y1 a number to set the y value of this point.
     */
    public void setY(double y1) {
        this.y = y1;
    }
}