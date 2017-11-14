package backend;

import java.util.*;

/**
 * A fast mapping repository system, with O(1) lookups and O(n) additions / deletions,
 * but consumes a lot of memory.
 * @param <K> The key type
 * @param <V> The value type
 */
public class BiDirectionalHashMap<K, V> extends Observable implements BiDirectionalMap<K, V> {

    /**
     * Stores the keys as keys and the values as values
     */
    private Map<K, List<V>> keysToValues = new HashMap<>();

    /**
     * Stores the values as keys and the keys as values
     */
    private Map<V, List<K>> valuesToKeys = new HashMap<>();

    /**
     * Add an key with no value
     * If the key already exist in the repo, it will not do anything.
     * @param key A new key
     */
    @Override
    public void addKey(K key) {
        if (!keysToValues.containsKey(key)){
            keysToValues.put(key, new ArrayList<V>());

            super.setChanged();
            super.notifyObservers(key);
        }
    }

    /**
     * Get a copy of the list of keys that has a specific value.
     * If it cannot find the value, it returns null.
     * @param value The value to search for
     * @return A list of keys that has that value
     */
    @Override
    public List<K> getKeysFromValue(V value) {
        if (valuesToKeys.containsKey(value))
            return new ArrayList<K>(valuesToKeys.get(value));
        return null;
    }

    /**
     * Get a copy of the list of values associated with that key
     * If it cannot find the key, it will return null.
     * @param key The key
     * @return The values with that key
     */
    @Override
    public List<V> getValuesFromKey(K key) {
        if (keysToValues.containsKey(key))
            return new ArrayList<>(keysToValues.get(key));
        return null;
    }

    /**
     * Determines whether a key is in this mapping
     * @param key A key
     * @return True if it is in the map; else false
     */
    public boolean containsKey(K key){
        return keysToValues.containsKey(key);
    }

    /**
     * Determines whether a value is in this map.
     * @param value A value
     * @return True if it is in this map; else false.
     */
    public boolean containsValue(V value){
        return valuesToKeys.containsKey(value);
    }

    /**
     * Adds a value to a specific key
     * If the value already exist with the key, that value will not be added
     * @param key The key to set the value to
     * @param value  The value
     */
    @Override
    public void addValueToKey(K key, V value) {
        if (keysToValues.containsKey(key)){
            List<V> values = keysToValues.get(key);
            if (!values.contains(value)) {
                values.add(value);

                if (!valuesToKeys.containsKey(value))
                    valuesToKeys.put(value, new ArrayList<K>());
                valuesToKeys.get(value).add(key);

                super.setChanged();
                super.notifyObservers(key);
            }
        }
    }

    /**
     * Adds an key with a value
     * If the value does not exist, it will add the value to the repository.
     * If the key already exist, it will not do anything.
     * If the value already exist, it will use the pre-existing value to value it with the new key.
     * @param key The key to set the value to
     * @param value  The value to include
     */
    @Override
    public void addKeyWithValue(K key, V value) {
        if (!keysToValues.containsKey(key)) {
            keysToValues.put(key, new ArrayList<V>());

            // Check if the key already has the value
            if (valuesToKeys.containsKey(value)){
                if (valuesToKeys.get(value).contains(key))
                    return;
            }

            keysToValues.get(key).add(value);
            if (!valuesToKeys.containsKey(value))
                valuesToKeys.put(value, new ArrayList<K>());
            valuesToKeys.get(value).add(key);

            super.setChanged();
            super.notifyObservers(key);
        }
    }

    /**
     * Replaces a value with another value
     * If the original value does not exist, it will not do anything.
     * If the new value already exist, it will not do anything.
     * @param oldValue The old value to remove
     * @param newValue The new value to replace the old value
     */
    @Override
    public void replaceValue(V oldValue, V newValue) {
        if (valuesToKeys.containsKey(oldValue)){
            if (!valuesToKeys.containsKey(newValue)) {
                // Update the value key in the dictionary
                List<K> keys = valuesToKeys.get(oldValue);
                valuesToKeys.remove(oldValue);
                valuesToKeys.put(newValue, keys);

                // Update the keys in the dictionary
                for (K key : keys) {
                    List<V> curvalues = keysToValues.get(key);
                    curvalues.remove(oldValue);
                    curvalues.add(newValue);
                }

                super.setChanged();
                super.notifyObservers();
            }
        }
    }

    /**
     * Replaces an key with another key
     * If the old key does not exist it will not do anything.
     * If the new key already exist, it will not do anything.
     * @param oldKey The key to remove
     * @param newKey The new key to replace the removed key
     */
    @Override
    public void replaceKey(K oldKey, K newKey) {
        if (keysToValues.containsKey(oldKey)) {
            if (!keysToValues.containsKey(newKey)) {
                // Update the key in the keys dictionary
                List<V> curvalues = keysToValues.get(oldKey);
                keysToValues.remove(oldKey);
                keysToValues.put(newKey, curvalues);

                // Update the values in the values dictionary
                for (V value : curvalues) {
                    List<K> curkeys = valuesToKeys.get(value);
                    curkeys.remove(oldKey);
                    curkeys.add(newKey);
                }

                super.setChanged();
                super.notifyObservers();
            }
        }
    }

    /**
     * Deletes an key from the repository.
     * If the key to be deleted does not exist, it will not do anything.
     * If deleting an key cause a value to be unvalueged to any other key,
     * it also deletes the value in this repository too.
     * @param key The key to delete from the repository
     */
    @Override
    public void deleteKey(K key) {
        if (keysToValues.containsKey(key)){
            // Update the keys dictionary
            List<V> values = keysToValues.get(key);
            keysToValues.remove(key);

            // Update the values dictionary
            List<V> valuesToDelete = new ArrayList<>();
            for (V value : values){
                List<K> keys = valuesToKeys.get(value);
                if (keys.size() == 1)
                    valuesToDelete.add(value);
                keys.remove(key);
            }

            // Remove any values that are not valueged to any key
            for (V value : valuesToDelete)
                valuesToKeys.remove(value);

            // Update the observers about the changes
            super.setChanged();
            super.notifyObservers();
        }
    }

    /**
     * Deletes the value from the repository
     * It will remove the values from all keys it is valueged with.
     *  If the value does not exist, it will not do anything.
     * @param value The value to delete from the repository
     */
    @Override
    public void deleteValue(V value) {
        if (valuesToKeys.containsKey(value)){
            // Delete the value from the values dictionary
            List<K> keys = valuesToKeys.get(value);
            valuesToKeys.remove(value);

            // Delete the value from each key
            for (K key : keys)
                keysToValues.get(key).remove(value);

            // Notify observers about the changes
            super.setChanged();
            super.notifyObservers();
        }
    }

    /**
     * Deletes a value from an key
     * If the key and the value does not exist, it will not do anything.
     * If deleting the value cause the value to become unvalueged to any key,
     * it will delete the value.
     * @param key The key with the value
     * @param value  The value to remove
     */
    @Override
    public void deleteValueFromKey(K key, V value) {
        if (valuesToKeys.containsKey(value)){
            if (keysToValues.containsKey(key)){
                valuesToKeys.get(value).remove(key);
                keysToValues.get(key).remove(value);

                if (valuesToKeys.get(value).size() == 0)
                    valuesToKeys.remove(value);

                super.setChanged();
                super.notifyObservers(value);
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
        for (Map.Entry<K, List<V>> pair : keysToValues.entrySet()){
            K key = pair.getKey();
            List<V> copyOfvalues = new ArrayList<>(pair.getValue());
            mapCopies.put(key, copyOfvalues);
        }
        return mapCopies;
    }

    @Override
    public String toString() {
        String output = "key to values: \n";
        for (Map.Entry<K, List<V>> pair : keysToValues.entrySet()){
            K key = pair.getKey();
            List<V> values = pair.getValue();
            String curText = key.toString() + " : {";
            for (V value : values)
                curText += value.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }

        output += "\n\nvalue to keys: \n";
        for (Map.Entry<V, List<K>> pair : valuesToKeys.entrySet()){
            V value = pair.getKey();
            List<K> keys = pair.getValue();
            String curText = value.toString() + " : {";
            for (K key : keys)
                curText += key.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }
        return output;
    }
}
