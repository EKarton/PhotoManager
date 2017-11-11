package backend;

import java.util.*;

/**
 * A concrete example of a mapRepository repository system
 * @param <K>
 * @param <V>
 */
public class ConcreteMapRepository<K, V> extends Observable implements MapRepository<K, V>, Observer{

    // Stores the keys and its associated list of values
    // Keys are the pictures, and values are its tags.
    private Map<K, List<V>> mapRepository = new HashMap<K, List<V>>();

    @Override
    public List<K> getItemsFromTag(V tag) {
        return null;
    }

    @Override
    public List<V> getTagsFromItem(K item) {
        return null;
    }

    @Override
    public List<V> getHistoricalTagsOfItem(K item) {
        return null;
    }

    @Override
    public void setTagToItem(K item, V tag) {

    }

    @Override
    public void setItemToTag(K item, V tag) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Picture){
            // change where the Picture instance is being stored
        }
        else if (o instanceof Tag){
            // change where the Tag is now being stored
        }
    }
}
