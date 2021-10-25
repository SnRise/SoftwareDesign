import java.util.Set;

/**
 * @author Madiyar Nurgazin
 */
public interface Cache<K, V> {
    void put(K key, V value);

    V get(K key);

    Set<K> keys();
}
