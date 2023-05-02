package sprites.ball;

import geometricshapes.Point;

/**
 * Velocity - Velocity specifies the change in position on the `x` and the `y` axes.
 *
 * @author Ayelet Tennenboim
 */
public class Velocity {
    // The change in position on the `x` and the `y` axes
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx the change in position on the x axis.
     * @param dy the change in position on the y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = (double) ((float) (dx));
        this.dy = (double) ((float) (dy));
    }
    /**
     * Sets the dx value of this velocity.
     *
     * @param dX the change in position on the x axis.
     */
    public void setDx(double dX) {
        this.dx = dX;
    }
    /**
     * Sets the dy value of this velocity.
     *
     * @param dY the change in position on the y axis.
     */
    public void setDy(double dY) {
        this.dy = dY;
    }
    /**
     * Gets the dx value of this velocity.
     *
     * @return the dx value of this velocity.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * Gets the dy value of this velocity.
     *
     * @return the dy value of this velocity.
     */
    public double getDy() {
        return this.dy;
    }
    /**
     * Converts velocity from angle and speed to velocity with dx and dy.
     *
     * @param angle the direction (in degrees) to move in.
     * @param speed number of units to move.
     * @return new velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = Math.cos(radians - Math.toRadians(90)) * speed;
        double dy = Math.sin(radians - Math.toRadians(90)) * speed;
        return new Velocity(dx, dy);
    }
    /**
     * Takes a point with position (x,y) and returns a new point with position (x+dx, y+dy).
     *
     * @param p a point with position (x,y).
     * @return a new point with position (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        return new Point((p.getX() + dx), (p.getY() + dy));
    }
}