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
    private Map<K, ArrayList<V>> keysToValues = new HashMap<>();

    /**
     * Stores the values as keys and the keys as values
     */
    private Map<V, ArrayList<K>> valuesToKeys = new HashMap<>();

    /**
     * Add an key with no value
     * If the key already exist in the repo, it will not do anything.
     * @param key A new key
     */
    @Override
    public void addKey(K key) {
        if (!keysToValues.containsKey(key)){
            keysToValues.put(key, new ArrayList<V>());

            Map<K, ArrayList<V>> map = new HashMap<>();
            map.put(key, new ArrayList<V>());

            super.setChanged();
            super.notifyObservers(map);
        }
    }

    /**
     * Get a list of keys stored in this map.
     * Note: they are not in order.
     * @return A list of keys stored in this map.
     */
    @Override
    public List<K> getKeys() {
        return new ArrayList<K>(keysToValues.keySet());
    }

    /**
     * Get a list of values stored in this map.
     * Note: they are not in order.
     * @return A list of keys stored in this map.
     */
    @Override
    public List<V> getValues() {
        return new ArrayList<V>(valuesToKeys.keySet());
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
            ArrayList<V> values = keysToValues.get(key);
            if (!values.contains(value)) {

                Map<K, List<V>> oldMap = new HashMap<>();
                oldMap.put(key, values);

                values.add(value);

                if (!valuesToKeys.containsKey(value))
                    valuesToKeys.put(value, new ArrayList<K>());
                valuesToKeys.get(value).add(key);

                super.setChanged();
                super.notifyObservers(oldMap);
            }
        }
    }

    /**
     * Adds an key with a value
     * If the value does not exist, it will add the value to the repository.
     * If the key already exist, it will not do anything.
     * If the value already exist, it will use the pre-existing value to value it with the new key.
     * It will notify the observers
     * @param key The key to set the value to
     * @param value  The value to include
     */
    @Override
    public void addKeyWithValue(K key, V value) {
        if (!keysToValues.containsKey(key)) {
            keysToValues.put(key, new ArrayList<V>());

            Map<K, List<V>> oldMap = new HashMap<>();
            oldMap.put(key, new ArrayList<V>());

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
            super.notifyObservers(oldMap);
        }
    }

    /**
     * Deletes an key from the repository.
     * If the key to be deleted does not exist, it will not do anything.
     * If deleting an key cause a value to be unmapped to any other key,
     * it also deletes the value in this repository too.
     * It will also notify the observer the key that has been deleted
     * @param key The key to delete from the repository
     */
    @Override
    public void deleteKey(K key) {
        if (keysToValues.containsKey(key)){

            // Make a copy of its values
            ArrayList<V> oldValues = new ArrayList<>(keysToValues.get(key));
            Map<K, ArrayList<V>> oldMap = new HashMap<>();
            oldMap.put(key, oldValues);


            // Update the keys dictionary
            ArrayList<V> values = keysToValues.get(key);
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
            super.notifyObservers(oldMap);
        }
    }

    /**
     * Deletes the value from the repository
     * It will remove the values from all keys it is changed with.
     *  If the value does not exist, it will not do anything.
     *  It will notify the observers, sending the observers
     *  a dictionary of {key:values} that were affected.
     * @param value The value to delete from the repository
     */
    @Override
    public void deleteValue(V value) {
        if (valuesToKeys.containsKey(value)){

            // Get a copy of the keys that were affected
            ArrayList<K> keys = valuesToKeys.get(value);

            // Get the keys:values that were affected.
            Map<K, ArrayList<V>> affectedKeys = new HashMap<>();
            for (K key : keys)
                affectedKeys.put(key, new ArrayList<V>(keysToValues.get(key)));

            // Delete the value from the values dictionary
            valuesToKeys.remove(value);

            // Delete the value from each key
            for (K key : keys)
                keysToValues.get(key).remove(value);

            // Notify observers about the changes
            super.setChanged();
            super.notifyObservers(affectedKeys);
        }
    }

    /**
     * Deletes a value from an key
     * If the key and the value does not exist, it will not do anything.
     * If deleting the value cause the value to become unmapped to any key,
     * it will delete the value.
     * It will notify the observer, by returning a {key:value} which was affected.
     * @param key The key with the value
     * @param value  The value to remove
     */
    @Override
    public void deleteValueFromKey(K key, V value) {
        if (valuesToKeys.containsKey(value)){
            if (keysToValues.containsKey(key)){

                Map<K, V> affectedPair = new HashMap<>();
                affectedPair.put(key, value);

                valuesToKeys.get(value).remove(key);
                keysToValues.get(key).remove(value);

                if (valuesToKeys.get(value).size() == 0)
                    valuesToKeys.remove(value);

                super.setChanged();
                super.notifyObservers(affectedPair);
            }
        }
    }

    public BiDirectionalMap<K, V> getClone(){
        BiDirectionalMap<K, V> copy = new BiDirectionalHashMap<>();
        for (Map.Entry<K, ArrayList<V>> pair : keysToValues.entrySet()){
            for (V value : pair.getValue())
                copy.addKeyWithValue(pair.getKey(), value);
        }
        return copy;
    }

    @Override
    public String toString() {
        String output = "key to values: \n";
        for (Map.Entry<K, ArrayList<V>> pair : keysToValues.entrySet()){
            K key = pair.getKey();
            List<V> values = pair.getValue();
            String curText = key.toString() + " : {";
            for (V value : values)
                curText += value.toString() + ", ";
            curText += "}";

            output += curText + "\n";
        }

        output += "\n\nvalue to keys: \n";
        for (Map.Entry<V, ArrayList<K>> pair : valuesToKeys.entrySet()){
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
