package levels;

/**
 * LevelSet - this class holds the details of the level set.
 *
 * @author Ayelet Tennenboim
 */
public class LevelSet {
    private String key;
    private String description;
    private String path;

    /**
     * Constructor - creates a new LevelSet.
     *
     * @param key a key for choosing this level set.
     * @param description the level set description.
     * @param path the path to the level specifications file.
     */
    public LevelSet(String key, String description, String path) {
        this.key = key;
        this.description = description;
        this.path = path;
    }
    /**
     * Returns the key for choosing this level set.
     *
     * @return the key for choosing this level.
     */
    public String getKey() {
        return this.key;
    }
    /**
     * Returns the level set description.
     *
     * @return the level set description.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Returns the path to the level specifications file.
     *
     * @return the path to the level specifications file.
     */
    public String getPath() {
        return this.path;
    }
}
