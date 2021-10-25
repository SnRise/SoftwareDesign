import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Madiyar Nurgazin
 */
public class LRUCache<K, V> implements Cache<K, V> {

    static class Node<T, U> {
        Node<T, U> previous;
        Node<T, U> next;
        T key;
        U value;

        public Node(Node<T, U> previous, Node<T, U> next, T key, U value) {
            this.previous = previous;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }

    private final int maxSize;
    private final Map<K, Node<K, V>> cache;

    private Node<K, V> leastRecentlyUsed;
    private Node<K, V> mostRecentlyUsed;
    private int currentSize;

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        leastRecentlyUsed = new Node<>(null, null, null, null);
        mostRecentlyUsed = leastRecentlyUsed;
        cache = new HashMap<>();
    }

    public V get(K key) {
        if (cache.get(key) == null) {
            return null;
        }

        Node<K, V> tempNode = cache.get(key);

        if (tempNode.key.equals(mostRecentlyUsed.key)) {
            assert (mostRecentlyUsed.key.equals(key));
            return mostRecentlyUsed.value;
        }

        Node<K, V> nextNode = tempNode.next;
        Node<K, V> previousNode = tempNode.previous;

        if (tempNode.key.equals(leastRecentlyUsed.key)) {
            nextNode.previous = null;
            leastRecentlyUsed = nextNode;
        } else {
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }

        tempNode.previous = mostRecentlyUsed;
        mostRecentlyUsed.next = tempNode;
        mostRecentlyUsed = tempNode;
        mostRecentlyUsed.next = null;

        assert (tempNode.key.equals(key));
        return tempNode.value;

    }

    public void put(K key, V value) {
        if (cache.containsKey(key)) {
            get(key);
            assert (cache.containsKey(key));
            return;
        }

        Node<K, V> myNode = new Node<>(mostRecentlyUsed, null, key, value);
        mostRecentlyUsed.next = myNode;
        cache.put(key, myNode);
        mostRecentlyUsed = myNode;

        if (currentSize == maxSize) {
            cache.remove(leastRecentlyUsed.key);
            leastRecentlyUsed = leastRecentlyUsed.next;
            leastRecentlyUsed.previous = null;
        } else if (currentSize < maxSize) {
            if (currentSize == 0) {
                leastRecentlyUsed = myNode;
            }
            currentSize++;
        }

        assert (currentSize > 0);
        assert (currentSize <= maxSize);
        assert (cache.containsKey(key));
    }

    public Set<K> keys() {
        return cache.keySet();
    }
}
