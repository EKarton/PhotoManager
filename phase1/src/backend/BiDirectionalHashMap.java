package backend;

import java.util.*;

/**
 * A fast mapping repository system, with O(1) lookups and O(n) additions / deletions,
 * but consumes a lot of memory.
 * @param <K> The item type
 * @param <V> The tag type
 */
public class BiDirectionalHashMap<K, V> extends Observable implements BiDirectionalMap<K, V> {

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

            super.setChanged();
            super.notifyObservers(item);
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

                super.setChanged();
                super.notifyObservers(item);
            }
        }
    }

    /**
     * Adds an item with a tag
     * If the tag does not exist, it will add the tag to the repository.
     * If the item already exist, it will not do anything.
     * If the tag already exist, it will use the pre-existing tag to tag it with the new item.
     * @param item The item to set the tag to
     * @param tag  The tag to include
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

            super.setChanged();
            super.notifyObservers(item);
        }
    }

    /**
     * Replaces a tag with another tag
     * If the original tag does not exist, it will not do anything.
     * If the new tag already exist, it will not do anything.
     * @param oldTag The old tag to remove
     * @param newTag The new tag to replace the old tag
     */
    @Override
    public void replaceTag(V oldTag, V newTag) {
        if (tagToItems.containsKey(oldTag)){
            if (!tagToItems.containsKey(newTag)) {
                // Update the tag key in the dictionary
                List<K> items = tagToItems.get(oldTag);
                tagToItems.remove(oldTag);
                tagToItems.put(newTag, items);

                // Update the items in the dictionary
                for (K item : items) {
                    List<V> curTags = itemToTags.get(item);
                    curTags.remove(oldTag);
                    curTags.add(newTag);
                }

                super.setChanged();
                super.notifyObservers();
            }
        }
    }

    /**
     * Replaces an item with another item
     * If the old item does not exist it will not do anything.
     * If the new item already exist, it will not do anything.
     * @param oldItem The item to remove
     * @param newItem The new item to replace the removed item
     */
    @Override
    public void replaceItem(K oldItem, K newItem) {
        if (itemToTags.containsKey(oldItem)) {
            if (!itemToTags.containsKey(newItem)) {
                // Update the item in the items dictionary
                List<V> curTags = itemToTags.get(oldItem);
                itemToTags.remove(oldItem);
                itemToTags.put(newItem, curTags);

                // Update the tags in the tags dictionary
                for (V tag : curTags) {
                    List<K> curItems = tagToItems.get(tag);
                    curItems.remove(oldItem);
                    curItems.add(newItem);
                }

                super.setChanged();
                super.notifyObservers();
            }
        }
    }

    /**
     * Deletes an item from the repository.
     * If the item to be deleted does not exist, it will not do anything.
     * If deleting an item cause a tag to be untagged to any other item,
     * it also deletes the tag in this repository too.
     * @param item The item to delete from the repository
     */
    @Override
    public void deleteItem(K item) {
        if (itemToTags.containsKey(item)){
            // Update the items dictionary
            List<V> tags = itemToTags.get(item);
            itemToTags.remove(item);

            // Update the tags dictionary
            List<V> tagsToDelete = new ArrayList<>();
            for (V tag : tags){
                List<K> items = tagToItems.get(tag);
                if (items.size() == 1)
                    tagsToDelete.add(tag);
                items.remove(item);
            }

            // Remove any tags that are not tagged to any item
            for (V tag : tagsToDelete)
                tagToItems.remove(tag);

            // Update the observers about the changes
            super.setChanged();
            super.notifyObservers();
        }
    }

    /**
     * Deletes the tag from the repository
     * It will remove the tags from all items it is tagged with.
     *  If the tag does not exist, it will not do anything.
     * @param tag The tag to delete from the repository
     */
    @Override
    public void deleteTag(V tag) {
        if (tagToItems.containsKey(tag)){
            // Delete the tag from the tags dictionary
            List<K> items = tagToItems.get(tag);
            tagToItems.remove(tag);

            // Delete the tag from each item
            for (K item : items)
                itemToTags.get(item).remove(tag);

            // Notify observers about the changes
            super.setChanged();
            super.notifyObservers();
        }
    }

    /**
     * Deletes a tag from an item
     * If the item and the tag does not exist, it will not do anything.
     * If deleting the tag cause the tag to become untagged to any item,
     * it will delete the tag.
     * @param item The item with the tag
     * @param tag  The tag to remove
     */
    @Override
    public void deleteTagFromItem(K item, V tag) {
        if (tagToItems.containsKey(tag)){
            if (itemToTags.containsKey(item)){
                tagToItems.get(tag).remove(item);
                itemToTags.get(item).remove(tag);

                if (tagToItems.get(tag).size() == 0)
                    tagToItems.remove(tag);

                super.setChanged();
                super.notifyObservers();
            }
        }
    }

    /**
     * Returns a copy of the mappings
     * @return A copy of the mappings
     */
    @Override
    public Map<K, List<V>> getCopyOfMappings() {
        Map<K, List<V>> mapCopies = new HashMap<>();
        for (Map.Entry<K, List<V>> pair : itemToTags.entrySet()){
            K item = pair.getKey();
            List<V> copyOfTags = new ArrayList<>(pair.getValue());
            mapCopies.put(item, copyOfTags);
        }
        return mapCopies;
    }

    @Override
    public String toString() {
        String output = "Item to tags: \n";
        for (Map.Entry<K, List<V>> pair : itemToTags.entrySet()){
            K item = pair.getKey();
            List<V> tags = pair.getValue();
            String curText = item.toString() + " : {";
            for (V tag : tags)
                curText += tag.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }

        output += "\n\nTag to items: \n";
        for (Map.Entry<V, List<K>> pair : tagToItems.entrySet()){
            V tag = pair.getKey();
            List<K> items = pair.getValue();
            String curText = tag.toString() + " : {";
            for (K item : items)
                curText += item.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }
        return output;
    }
}
