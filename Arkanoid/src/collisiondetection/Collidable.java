package collisiondetection;

import geometricshapes.Point;
import sprites.ball.Ball;
import sprites.ball.Velocity;
import geometricshapes.Rectangle;


/**
 * The Collidable interface will be used by things that can be collided with.
 *
 * @author Ayelet Tennenboim
 */
public interface Collidable {
    /**
     * Returns the "collision shape" of the object.
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *  The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter the ball that's doing the hitting.
     * @param collisionPoint the point we collided with the object.
     * @param currentVelocity the velocity before the hit.
     * @return the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}