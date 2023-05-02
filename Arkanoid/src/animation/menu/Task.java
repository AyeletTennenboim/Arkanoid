package animation.menu;

/**
 * The Task interface will be used by something that needs to happen, or something that we can run() and return
 *  a value.
 *
 * @param <T> a task to run.
 * @author Ayelet Tennenboim
 */
public interface Task<T> {
    /**
     * Runs the task.
     *
     * @return a value.
     */
    T run();
}
