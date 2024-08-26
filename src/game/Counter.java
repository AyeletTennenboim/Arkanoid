package game;

/**
 * Counter - a simple class that is used for counting things.
 *
 * @author Ayelet Tennenboim
 */
public class Counter {
    // Value of the count
    private int count;

    /**
     * Constructor - creates a new counter.
     *
     * @param number a number to initialize the count.
     */
    public Counter(int number) {
        this.count = number;
    }
    /**
     * Adds number to current count.
     *
     * @param number a number to add to current count.
     */
    public void increase(int number) {
        this.count += number;
    }
    /**
     * Subtracts number from current count.
     *
     * @param number a number to subtract from current count.
     */
    public void decrease(int number) {
        this.count -= number;
    }
    /**
     * Gets current count.
     *
     * @return current count.
     */
    public int getValue() {
        return this.count;
    }
}
