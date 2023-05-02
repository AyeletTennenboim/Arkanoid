package collisiondetection;

import geometricshapes.Point;

/**
 * CollisionInfo - the CollisionInfo class holds information about a collision with an object.
 *
 * @author Ayelet Tennenboim
 */
public class CollisionInfo {
    // The point at which the collision occurs
    private Point collisionPoint;
    // The collidable object involved in the collision
    private Collidable collisionObject;

    /**
     * Constructor - creates a new CollisionInfo for a collision with an object.
     *
     * @param colPoint the point at which the collision occurs.
     * @param colObject the collidable object involved in the collision.
     */
    public CollisionInfo(Point colPoint, Collidable colObject) {
        this.collisionPoint = colPoint;
        this.collisionObject = colObject;
    }
    /**
     * Gets the point at which the collision occurs.
     *
     * @return the point at which the collision occurs
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    /**
     * Gets the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}