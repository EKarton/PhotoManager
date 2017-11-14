package backend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A simple generic class that stores the mappings between an item and its tags.
 * Note that an item can have multiple tags, but a tag cannot have no item.
 * @param <K> The item type
 * @param <V> The tag type
 */
public interface BiDirectionalMap<K, V> extends Serializable{

    /**
     * Add an item with no tags
     * @param item A new item
     */
    void addItem(K item);

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
     * Add a tag to a specific item
     * @param item The item to set the tag to
     * @param tag The tag
     */
    void addTagToItem(K item, V tag);

    /**
     * Adds an item with a tag
     * If the tag does not exist, it will add the tag to the repository.
     * If the item already exist, it will not do anything.
     * If the tag already exist, it will use the pre-existing tag to tag it with the new item.
     * @param item The item to set the tag to
     * @param tag  The tag
     */
    void addItemWithTag(K item, V tag);

    /**
     * Replaces a tag with another tag
     * @param oldTag The old tag
     * @param newTag The new tag
     */
    void replaceTag(V oldTag, V newTag);

    /**
     * Replaces an item with another item
     * @param oldItem The old item
     * @param newItem The new item
     */
    void replaceItem(K oldItem, K newItem);

    /**
     * Deletes an item from the repository.
     * If deleting an item cause a tag to be unassociated,
     * it also deletes the tag in this repository too
     * @param item The item to delete from the repository
     */
    void deleteItem(K item);

    /**
     * Deletes the tag from the repository
     * It will remove all the tags any item is associated with.
     * @param tag The tag to delete from the repository
     */
    void deleteTag(V tag);

    /**
     * Deletes a tag from an item
     * @param item The item with the tag
     * @param tag The tag to remove
     */
    void deleteTagFromItem(K item, V tag);

    /**
     * Returns a copy of the mappings
     * @return A copy of the mappings
     */
    Map<K, List<V>> getCopyOfMappings();
}
