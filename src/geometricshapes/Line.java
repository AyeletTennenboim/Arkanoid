package geometricshapes;

/**
 * Line - A line connects two points - a start point and an end point. Lines have lengths, and may intersect with
 *  other lines. It can also tell if it is the same as another line segment.
 *
 * @author Ayelet Tennenboim
 */
public class Line {
    // Start and end points of the line
    private Point start;
    private Point end;

    /**
     * Constructor number 1 - according to two points.
     *
     * @param start the start point of the line.
     * @param end   the end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructor number 2 - according to x and y values of two points.
     *
     * @param x1 the x value of the start point.
     * @param y1 the y value of the start point.
     * @param x2 the x value of the end point.
     * @param y2 the y value of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Measures the length of this line by calculating the distance between the start point and the end point.
     *
     * @return the length of the line.
     */
    public double length() {
        double length = this.start.distance(this.end);
        return length;
    }

    /**
     * Calculates the middle point of the line.
     *
     * @return the middle point of this line.
     */
    public Point middle() {
        // Calculate the average of the x values of the start point and the end point
        double midX = ((this.start.getX() + this.end.getX()) / 2);
        // Calculate the average of the y values of the start point and the end point
        double midY = ((this.start.getY() + this.end.getY()) / 2);
        // Create a new point
        Point midPoint = new Point(midX, midY);
        return midPoint;
    }

    /**
     * Gets the start point of the line.
     *
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Gets the end point of the line.
     *
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks whether this line intersects with another line.
     *
     * @param other a line that the method checks if it intersects with this line.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // If there is an intersection point - return true, and if there is no intersection point - return false
        return (this.intersectionWith(other) != null);
    }

    /**
     * Finds the intersection point of this line with another line (if there is one).
     * <p>
     * The method finds the line equation of this line and another line according to the start point and the end point
     * of each line. Then it solves the equations, finds a point of intersection and checks whether the
     * intersection point is on both segments. If so, it returns the intersection point. If not (or if the lines are
     * parallel), it returns null.
     *
     * @param other a line that the method looks for its intersection point with this line.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        // Indication of whether a point is on the line
        boolean onLine1 = false, onLine2 = false;
        // Delta x and delta y of this line
        double deltaX1 = this.start().getX() - this.end().getX();
        double deltaY1 = this.start().getY() - this.end().getY();
        // Delta x and delta y of the other line
        double deltaX2 = other.start().getX() - other.end().getX();
        double deltaY2 = other.start().getY() - other.end().getY();
        // If at least one of the lines is vertical to the X axis - check the intersection with another method
        if ((deltaX1 == 0) || (deltaX2 == 0)) {
            return this.intersectionHelper(other, deltaX1, deltaY1, deltaX2, deltaY2);
        }
        // This line equation: y=m1x+b1
        double m1 = deltaY1 / deltaX1;
        double b1 = (this.start().getY() - (m1 * this.start().getX()));
        // Other line equation: y=m2x+b2
        double m2 = deltaY2 / deltaX2;
        double b2 = (other.start().getY() - (m2 * other.start().getX()));
        // If the lines are parallel - they don't intersect
        if (m1 == m2) {
            return null;
        }
        // Calculate the intersection point
        double intersectionX = ((b2 - b1) / (m1 - m2));
        double intersectionY = ((b1 * m2) - (b2 * m1)) / (m2 - m1);
        // Checks if the intersection point is on the two lines
        if ((intersectionX <= Math.max(this.start().getX(), this.end().getX()) + 0.0000001)
                && (intersectionX >= Math.min(this.start().getX(), this.end().getX()) - 0.0000001)) {
            // An indication that the point is on this line
            onLine1 = true;
        }
        if ((intersectionX <= Math.max(other.start().getX(), other.end().getX()) + 0.0000001)
                && (intersectionX >= Math.min(other.start().getX(), other.end().getX()) - 0.0000001)) {
            // An indication that the point is on the other line
            onLine2 = true;
        }
        // If the point is on the two lines - this is the intersection point
        if (onLine1 && onLine2) {
            // Create a new point to the intersection point
            Point intersectionPoint = new Point(intersectionX, intersectionY);
            // Return the point
            return intersectionPoint;
        }
        // If there is no intersection point - return null
        return null;
    }

    /**
     * Finds the intersection point of this line with another line (if there is one) when one of them is vertical to
     * the X axis.
     * <p>
     * The method checks whether the x value of the line that is vertical to the X axis is on the second line. If so,
     * it finds the second line equation, and according to the equation it calculates the appropriate y value on the
     * second line. Then it checks whether the y value is on the vertical line. If so, this is the intersection point
     * and the method returns it. If not (or if the lines are parallel), it returns null.
     *
     * @param other   a line that the method looks for its intersection point with this line.
     * @param deltaX1 delta x of this line.
     * @param deltaY1 delta y of this line.
     * @param deltaX2 delta x of the other line.
     * @param deltaY2 delta y of the other line.
     * @return the intersection point if the lines intersect, and null otherwise.
     */
    private Point intersectionHelper(Line other, double deltaX1, double deltaY1, double deltaX2, double deltaY2) {
        // If the lines are parallel - they don't intersect
        if ((deltaX1 == 0) && (deltaX2 == 0)) {
            return null;
            // If this line is vertical to the X axis
        } else if (deltaX1 == 0) {
            // Check whether the x value of this line is on the other line
            if ((this.start().getX() <= Math.max(other.start().getX(), other.end().getX()))
                    && (this.start().getX() >= Math.min(other.start().getX(), other.end().getX()))) {
                // Other line equation: y=m2x+b2
                double m2 = deltaY2 / deltaX2;
                double b2 = (other.start().getY() - (m2 * other.start().getX()));
                // Calculate the appropriate y value
                double y2 = (m2 * this.start().getX()) + b2;
                // Check whether the y value is on this line
                if (y2 <= Math.max(this.start().getY(), this.end().getY())
                        && (y2 >= Math.min(this.start().getY(), this.end().getY()))) {
                    // Return the intersection point
                    return new Point(this.start().getX(), y2);
                }
            }
            // If the other line is vertical to the X axis
        } else {
            // Check whether the x value of the other line is on this line
            if ((other.start().getX() <= Math.max(this.start().getX(), this.end().getX()))
                    && (other.start().getX() >= Math.min(this.start().getX(), this.end().getX()))) {
                // This line equation: y=m1x+b1
                double m1 = deltaY1 / deltaX1;
                double b1 = (this.start().getY() - (m1 * this.start().getX()));
                // Calculate the appropriate y value
                double y1 = (m1 * other.start().getX()) + b1;
                // Check whether the y value is on the other line
                if (y1 <= Math.max(other.start().getY(), other.end().getY())
                        && (y1 >= Math.min(other.start().getY(), other.end().getY()))) {
                    // Return the intersection point
                    return new Point(other.start().getX(), y1);
                }
            }
        }
        // If there is no intersection point - return null
        return null;
    }

    /**
     * Checks if this line is equal to another line.
     * <p>
     * The method checks whether the start point and the end points of the two lines are the same (not necessarily
     * respectively).
     *
     * @param other a line that the method checks whether it is equal to this line.
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        // If both points at the edges of the segments are the same - return true, else - return false
        if ((this.start.getX() == other.start().getX()) && (this.start.getY() == other.start().getY())
                && (this.end.getX() == other.end().getX()) && (this.end.getY() == other.end().getY())) {
            return true;
        } else {
            return ((this.start.getX() == other.end().getX()) && (this.start.getY() == other.end().getY())
                    && (this.end.getX() == other.start().getX()) && (this.end.getY() == other.start().getY()));
        }
    }

    /**
     * Checks if the line intersect with a rectangle and finds the closest intersection point to the start of the line.
     * <p>
     * If this line does not intersect with the rectangle, return null. Otherwise, return the closest intersection
     * point to the start of the line.
     *
     * @param rect a rectangle.
     * @return the closest intersection point to the start of the line if there is one, and null otherwise.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point closestPoint;
        Double minDistance;
        // Get a list of intersection points of this line with the rectangle
        java.util.List<Point> intersectionPoints = rect.intersectionPoints(new Line(this.start, this.end));
        // If this line does not intersect with the rectangle - return null
        if (intersectionPoints.size() == 0) {
            return null;
        }
        closestPoint = intersectionPoints.get(0);
        minDistance = this.start.distance(intersectionPoints.get(0));
        // If there is only one intersection point - return this point
        if (intersectionPoints.size() == 1) {
            return closestPoint;
            // If there are two or more intersection points - return the closest point to the start of the line
        } else {
            for (int i = 1; i < intersectionPoints.size(); i++) {
                if (this.start.distance(intersectionPoints.get(i)) < minDistance) {
                    minDistance = this.start.distance(intersectionPoints.get(i));
                    closestPoint = intersectionPoints.get(i);
                }
            }
            return closestPoint;
        }
    }
}