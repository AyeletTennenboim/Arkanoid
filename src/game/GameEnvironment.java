package game;

import collisiondetection.Collidable;
import collisiondetection.CollisionInfo;
import geometricshapes.Line;
import java.util.List;
import java.util.ArrayList;

/**
 * GameEnvironment - During the game, there are going to be many objects a ball can collide with. The GameEnvironment
 *  class will be a collection of such things. The ball will know the game environment, and will use it to check for
 *  collisions and direct its movement.
 *
 * @author Ayelet Tennenboim
 */
public class GameEnvironment {
    // An ArrayList of collidables
    private List<Collidable> collidables;

    /**
     * Constructor - creates a new GameEnvironment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**
     * Adds the given collidable to the environment.
     *
     * @param c the given collidable.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    /**
     * Removes the given collidable from the environment.
     *
     * @param c the given collidable.
     */
    public void removeCollidable(Collidable c) { this.collidables.remove(c); }
    /**
     * Gets the list of collidables.
     *
     * @return the list of collidables.
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }
    /**
     * Gets information about the closest collision that is going to occur.
     *
     * Assume an object moving from line.start() to line.end(). If this object will not collide with any of the
     *  collidables in this collection, return null. Else, return the information about the closest collision that is
     *  going to occur.
     *
     * @param trajectory a line that symbolizes the trajectory an object is moving through.
     * @return the information about the closest collision that is going to occur (or null if the object will not
     *  collide with any of the collidables in the collection).
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // The closest collidable
        Collidable closestCollidable = null;
        // Make a copy of the Collidables before iterating over them
        List<Collidable> collidablesList = new ArrayList<Collidable>(this.collidables);
        // Go over the list of collidables
        for (Collidable c : collidablesList) {
            // If the collidable is in the trajectory of the object
            if (trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle()) != null) {
                // If it is the first collidable that will collide with the object
                if (closestCollidable == null) {
                    // Change closestCollidable to be this collidable
                    closestCollidable = c;
                // Else, if this collidable's (c) distance from the object is smaller than closestCollidable's distance
                } else if (trajectory.start().distance(trajectory.closestIntersectionToStartOfLine(c.
                        getCollisionRectangle())) < trajectory.start().distance(trajectory.
                        closestIntersectionToStartOfLine(closestCollidable.getCollisionRectangle()))) {
                    // Change closestCollidable to be this collidable
                    closestCollidable = c;
                }
            }
        }
        // If the object will not collide with any of the collidables in the collection, return null
        if (closestCollidable == null) {
            return null;
        // Else, return the information about the closest collision that is going to occur
        } else {
            return new CollisionInfo(trajectory.closestIntersectionToStartOfLine(closestCollidable.
                    getCollisionRectangle()), closestCollidable);
        }
    }
}