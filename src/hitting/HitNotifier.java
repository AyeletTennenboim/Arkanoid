package hitting;

/**
 * HitNotifier - this interface indicates that objects that implement it send notifications when they are being hit.
 *
 * @author Ayelet Tennenboim
 */
public interface HitNotifier {
    /**
     * Adds hl as a listener to hit events.
     *
     * @param hl a HitListener.
     */
    void addHitListener(HitListener hl);
    /**
     * Removes hl from the list of listeners to hit events.
     *
     * @param hl a HitListener.
     */
    void removeHitListener(HitListener hl);
}
