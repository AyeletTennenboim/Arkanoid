package sprites;

import game.GameLevel;
import geometricshapes.Point;
import sprites.ball.Ball;
import sprites.ball.Velocity;
import geometricshapes.Rectangle;
import collisiondetection.Collidable;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * Paddle - The Paddle is the player in the game. It is a rectangle that is controlled by the arrow keys, and moves
 *  according to the player key presses.
 *
 * @author Ayelet Tennenboim
 */
public class Paddle implements Sprite, Collidable {
    // A keyboard to read the key presses
    private biuoop.KeyboardSensor keyboard;
    // The location and size of the paddle (specified using a Rectangle)
    private Rectangle rec;
    // The change in position on the x axis by pressing the right key
    private double dx;
    // Color
    private Color color;

    /**
     * Constructor - creates a new paddle.
     *
     * @param keyboard a keyboard to read the key presses.
     * @param rec the rectangle that defines the paddle.
     * @param dx the change in position on the x axis by pressing the right key.
     * @param color the color of the paddle.
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle rec, Double dx, Color color) {
        this.rec = rec;
        this.dx = dx;
        this.color = color;
        this.keyboard = keyboard;
    }
    /**
     * Moves the paddle to the left.
     */
    public void moveLeft() {
        // If after the next step the paddle will still be all inside the screen
        if (this.rec.getUpperLeft().getX() >= 20 + dx) {
            // Move one step to the left
            this.rec.setUpperLeft(new Point(this.rec.getUpperLeft().getX() - this.dx,
                    this.rec.getUpperLeft().getY()));
        }
    }
    /**
     * Moves the paddle to the right.
     */
    public void moveRight() {
        // If after the next step the paddle will still be all inside the screen
        if (this.rec.getUpperLeft().getX() + this.rec.getWidth() <= 780 - dx) {
            // Move one step to the right
            this.rec.setUpperLeft(new Point(this.rec.getUpperLeft().getX() + this.dx,
                    this.rec.getUpperLeft().getY()));
        }
    }
    /**
     * Checks if the "left" or "right" keys are pressed, and if so moves the paddle accordingly.
     */
    public void timePassed() {
        // If the left key is pressed
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            // Move to the left
            this.moveLeft();
            // If the right key is pressed
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            // Move to the right
            this.moveRight();
        }
    }
    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d a surface to draw on it.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
    }
    /**
     * Returns the "collision shape" of the object.
     *
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }
    /**
     * Returns a new velocity expected after the hit.
     * <p>
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *  The return is the new velocity expected after the hit (based on the force the object inflicted on us):
     *  if the hit is at the left or right of the paddle - change only the dx, and if the hit is at the top of the
     *  paddle - change the velocity according to the region the ball hits the paddle.
     *
     * @param hitter the ball that's doing the hitting.
     * @param collisionPoint  the point we collided with the object.
     * @param currentVelocity the velocity before the hit.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        double sizeOfRegion = this.rec.getWidth() / 5;
        Velocity newVelocity = currentVelocity;
        // Calculate the speed of the ball
        double speed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());
        // if the hit is at the top of the paddle - change the velocity according to the collision region
        if (((currentVelocity.getDy() > 0) && (GameLevel.compareDoubles(collisionPoint.getY(),
                this.rec.getUpperLeft().getY()))) || ((currentVelocity.getDy() < 0)
                && GameLevel.compareDoubles(collisionPoint.getY(),
                this.rec.getUpperLeft().getY() + this.rec.getHeight()))) {
            if (collisionPoint.getX() <= this.rec.getUpperLeft().getX() + sizeOfRegion) {
                newVelocity = Velocity.fromAngleAndSpeed(300, speed);
            } else if (collisionPoint.getX() <= this.rec.getUpperLeft().getX() + 2 * sizeOfRegion) {
                newVelocity = Velocity.fromAngleAndSpeed(330, speed);
            } else if (collisionPoint.getX() <= this.rec.getUpperLeft().getX() + 3 * sizeOfRegion) {
                newVelocity = Velocity.fromAngleAndSpeed(360, speed);
            } else if (collisionPoint.getX() <= this.rec.getUpperLeft().getX() + 4 * sizeOfRegion) {
                newVelocity = Velocity.fromAngleAndSpeed(30, speed);
            } else {
                newVelocity = Velocity.fromAngleAndSpeed(60, speed);
            }
        // If the hit is at the left or right of the paddle - change dx and dy
        } else if (((currentVelocity.getDx() > 0) && (GameLevel.compareDoubles(collisionPoint.getX(),
                this.rec.getUpperLeft().getX()))) || ((currentVelocity.getDx() < 0)
                && (GameLevel.compareDoubles(collisionPoint.getX(), this.rec.getUpperLeft().getX()
                + this.rec.getWidth())))) {
            newDx = (-currentVelocity.getDx());
            newDy = (-currentVelocity.getDy());
            // Update the new velocity
            newVelocity = new Velocity(newDx, newDy);
        }
        // Return the new velocity
        return newVelocity;
    }
    /**
     * Adds this paddle to the game.
     *
     * @param g the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * Removes this paddle from the game.
     *
     * @param g the game.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}