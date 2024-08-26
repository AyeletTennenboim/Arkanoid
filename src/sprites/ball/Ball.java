package sprites.ball;

import game.GameLevel;
import geometricshapes.Line;
import geometricshapes.Point;
import game.GameEnvironment;
import collisiondetection.Collidable;
import collisiondetection.CollisionInfo;
import sprites.Sprite;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * ball - A ball has size (radius), color, and location of its center (a Point). A ball also knows how to draw itself
 *  on a DrawSurface, and it can move on the screen according to its velocity.
 *
 * @author Ayelet Tennenboim
 */
public class Ball implements Sprite {
    // Location
    private Point center;
    // Size
    private int radius;
    // Color
    private java.awt.Color color;
    // sprites.ball.Velocity
    private Velocity vel;
    // The motion range of the ball on the screen
    private int minWidthLimit;
    private int maxWidthLimit;
    private int minHeightLimit;
    private int maxHeightLimit;
    // The game environment
    private GameEnvironment environment;

    /**
     * Constructor number 1 - according to a center point, radius and color.
     *
     * @param center the center point of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }
    /**
     * Constructor number 2 - according to x and y values of the center point, radius and color.
     *
     * @param x the x value of the center point of the ball.
     * @param y the y value of the center point of the ball.
     * @param r the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
    }
    /**
     * Gets the x value of the center point of this ball.
     *
     * @return the x value of the center point of this ball.
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**
     * Gets the y value of the center point of this ball.
     *
     * @return the y value of the center point of this ball.
     */
    public int getY() {
        return (int) this.center.getY();
    }
    /**
     * Gets the size (radius) of this ball.
     *
     * @return the size (radius) of this ball.
     */
    public int getSize() {
        return this.radius;
    }
    /**
     * Gets the color of this ball.
     *
     * @return the color of this ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface a surface to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }
    /**
     * Sets the velocity of this ball according to a given velocity value.
     *
     * @param v a velocity value.
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }
    /**
     * Sets the velocity of this ball according to dx and dy values.
     *
     * @param dx the change in position on the x axis.
     * @param dy the change in position on the y axis.
     */
    public void setVelocity(double dx, double dy) {
        this.vel = new Velocity(dx, dy);
    }
    /**
     * Gets the velocity of this ball.
     *
     * @return the velocity of this ball.
     */
    public Velocity getVelocity() {
        return this.vel;
    }
    /**
     * Sets the motion range of this ball on the screen.
     *
     * @param minWidth the minimum value of x that the ball can get to.
     * @param maxWidth the maximum value of x that the ball can get to.
     * @param minHeight the minimum value of y that the ball can get to.
     * @param maxHeight the maximum value of y that the ball can get to.
     */
    public void setLimits(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this.minWidthLimit = minWidth;
        this.maxWidthLimit = maxWidth;
        this.minHeightLimit = minHeight;
        this.maxHeightLimit = maxHeight;
    }
    /**
     * Sets the game environment of the ball.
     *
     * @param gEnvironment the game environment of the ball.
     */
    public void setGameEnvironment(GameEnvironment gEnvironment) {
        this.environment = gEnvironment;
    }
    /**
     * Gets the game environment of the ball.
     *
     * @return the game environment of the ball.
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }
    /**
     * Move the ball to its next position on the screen.
     *
     * The method changes the position of the center point from (x,y) to (x+dx, y+dy). When the ball hits a collidable
     *  to the left or to the right, it changes its horizontal direction, and when the ball hits a collidable at the
     *  top or the bottom, it changes its vertical direction. If the ball hits the paddle - it changes its direction
     *  according to the region it hits the paddle.
     */
    public void moveOneStep() {
        Line trajectory;
        Point collisionPoint;
        Collidable collisionObject;
        // dx and dy values of this velocity before the change
        double originalDx = this.vel.getDx();
        double originalDy = this.vel.getDy();
        // If the velocity is not set
        if (this.vel == null) {
            // Print an error message
            System.out.println("Error! Velocity is not set");
            // Close the program
            System.exit(-1);
        }
        // Compute the ball trajectory
        trajectory = new Line(this.center.getX(), this.center.getY(), this.center.getX() + this.vel.getDx(),
                this.center.getY() + this.vel.getDy());
        // Get the collision information
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);
        // If there is no collision point
        if (collision == null) {
            // Change the position of the center point from (x,y) to (x+dx, y+dy)
             this.center = this.getVelocity().applyToPoint(this.center);
         // If there is a collision point
        } else {
            // Save the collision point
            collisionPoint = collision.collisionPoint();
            // Save the collision object
            collisionObject = collision.collisionObject();
            // Update the velocity to the new velocity returned by the hit() method
            this.vel = collisionObject.hit(this, collisionPoint, this.vel);
            // If the velocity didn't change (it means the next step will be into the paddle)
            if (GameLevel.compareDoubles(this.vel.getDx(), originalDx)
                    && GameLevel.compareDoubles(this.vel.getDy(), originalDy)) {
                // Update the next step to be out of the screen
                this.center = new Point(this.center.getX(), this.center.getY() + 20);
            }
        }
    }
    /**
     * Notifies the sprite that time has passed - move one step.
     */
    public void timePassed() {
        moveOneStep();
    }
    /**
     * Adds this ball to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
    /**
     * Removes this ball from the game.
     *
     * @param g the game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}