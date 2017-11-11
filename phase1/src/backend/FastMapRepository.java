package backend;

import java.util.*;

/**
 * A fast mapping repository system, with O(1) lookups and O(n) additions / deletions,
 * but consumes a lot of memory.
 * @param <K> The item type
 * @param <V> The tag type
 */
public class FastMapRepository<K, V> extends Observable implements MapRepository<K, V>{

    // Stores the items as keys and the tags as values
    private Map<K, List<V>> itemToTags = new HashMap<>();

    // Stores the tags as keys and the items as values
    private Map<V, List<K>> tagToItems = new HashMap<>();

    /**
     * Add an item with no tags
     * If the item already exist in the repo, it will not do anything.
     * @param item A new item
     */
    @Override
    public void addItem(K item) {
        if (!itemToTags.containsKey(item)){
            itemToTags.put(item, new ArrayList<V>());
        }
    }

    /**
     * Get a copy of the list of items that has a specific tag.
     * If it cannot find the tag, it returns null.
     * @param tag The tag to search for
     * @return A list of items that has that tag
     */
    @Override
    public List<K> getItemsFromTag(V tag) {
        if (tagToItems.containsKey(tag))
            return new ArrayList<K>(tagToItems.get(tag));
        return null;
    }

    /**
     * Get a copy of the list of tags associated with that item
     * If it cannot find the item, it will return null.
     * @param item The item
     * @return The tags with that item
     */
    @Override
    public List<V> getTagsFromItem(K item) {
        if (itemToTags.containsKey(item))
            return new ArrayList<>(itemToTags.get(item));
        return null;
    }

    /**
     * Adds a tag to a specific item
     * If the tag already exist with the item, that tag will not be added
     * @param item The item to set the tag to
     * @param tag  The tag
     */
    @Override
    public void addTagToItem(K item, V tag) {
        if (itemToTags.containsKey(item)){
            List<V> tags = itemToTags.get(item);
            if (!tags.contains(tag)) {
                tags.add(tag);

                if (!tagToItems.containsKey(tag))
                    tagToItems.put(tag, new ArrayList<K>());
                tagToItems.get(tag).add(item);
            }
        }
    }

    /**
     * Adds an item with a tag
     * If the tag does not exist, it will add the tag to the repository.
     * If the item already exist, it will not do anything.
     * If the tag already exist, it will use the pre-existing tag to tag it with the new item.
     * @param item The item to set the tag to
     * @param tag  The tag
     */
    @Override
    public void addItemWithTag(K item, V tag) {
        if (!itemToTags.containsKey(item)) {
            itemToTags.put(item, new ArrayList<V>());

            // Check if the item already has the tag
            if (tagToItems.containsKey(tag)){
                if (tagToItems.get(tag).contains(item))
                    return;
            }

            itemToTags.get(item).add(tag);
            if (!tagToItems.containsKey(tag))
                tagToItems.put(tag, new ArrayList<K>());
            tagToItems.get(tag).add(item);
        }
    }

    /**
     * Replaces a tag with another tag
     *
     * @param oldTag The old tag
     * @param newTag The new tag
     */
    @Override
    public void replaceTag(V oldTag, V newTag) {

    }

    /**
     * Replaces an item with another item
     *
     * @param oldItem The old item
     * @param newItem The new item
     */
    @Override
    public void replaceItem(K oldItem, K newItem) {

    }

    /**
     * Deletes an item from the repository.
     * If deleting an item cause a tag to be unassociated,
     * it also deletes the tag in this repository too
     *
     * @param item The item to delete from the repository
     */
    @Override
    public void deleteItem(K item) {

    }

    /**
     * Deletes the tag from the repository
     * It will remove all the tags any item is associated with.
     *
     * @param tag The tag to delete from the repository
     */
    @Override
    public void deleteTag(V tag) {

    }

    /**
     * Deletes a tag from an item
     *
     * @param item The item with the tag
     * @param tag  The tag to remove
     */
    @Override
    public void deleteTagFromItem(K item, V tag) {

    }

    /**
     * Deletes all items with that tag
     *
     * @param tag The tag
     */
    @Override
    public void deleteItemsWithTag(V tag) {

    }

    /**
     * Merge two repositories together
     *
     * @param repository The repository to merge it with
     */
    @Override
    public void mergeMappings(MapRepository repository) {

    }

    @Override
    public String toString() {
        String output = "";
        for (Map.Entry<K, List<V>> pair : itemToTags.entrySet()){
            K item = pair.getKey();
            List<V> tags = pair.getValue();
            String curText = item.toString() + " : {";
            for (V tag : tags)
                curText += tag.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }
        return output;
    }
}
