package backend;

import java.util.List;

public interface MapRepository<K, V> {

    /**
     * Get a list of items that has a specific tag
     * @param tag The tag to search for
     * @return A list of items that has that tag
     */
    List<K> getItemsFromTag(V tag);

    /**
     * Get a list of tags associated with that item
     * @param item The item
     * @return The tags with that item
     */
    List<V> getTagsFromItem(K item);

    /**
     * Returns a list of the historical tags of that item
     * @param item The item
     * @return A list of all the historical tags of that item
     */
    List<V> getHistoricalTagsOfItem(K item);

    /**
     * Set the tag to a specific item
     * @param item The item to set the tag to
     * @param tag The tag
     */
    void setTagToItem(K item, V tag);

    /**
     * Set an item to a specific tag
     * @param item The item to set the tag to
     * @param tag The tag
     */
    void setItemToTag(K item, V tag);
}
