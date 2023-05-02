package animation.menu;

import animation.Animation;

/**
 * The Menu interface will be used by classes that have a list of several options of what to do next.
 *
 * @param <T> the Generic type.
 * @author Ayelet Tennenboim
 */
public interface Menu<T> extends Animation {
    /**
     * Adds a selection to the menu.
     *
     * @param key a key to wait for.
     * @param message line to print.
     * @param returnVal value to return.
     */
    void addSelection(String key, String message, T returnVal);
    /**
     * Returns the status of the keys pressed.
     *
     * @return the status of the keys pressed.
     */
    T getStatus();
    /**
     * Adds a sub-menu to this menu.
     *
     * @param key a key to wait for.
     * @param message line to print.
     * @param subMenu a sub-menu to add to this menu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
