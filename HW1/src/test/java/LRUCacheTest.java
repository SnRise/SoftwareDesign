import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Madiyar Nurgazin
 */
public class LRUCacheTest {

    @Test
    public void putTest() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(5);
        for (int i = 0; i < 5; i++) {
            lruCache.put(String.valueOf(i), i);
        }

        Set<String> keys = Set.of("0", "1", "2", "3", "4");
        assertEquals(keys, lruCache.keys());
    }

    @Test
    public void putMoreThanMaxSizeTest() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(3);
        for (int i = 0; i < 6; i++) {
            lruCache.put(String.valueOf(i), i);
        }

        Set<String> keys = Set.of("3", "4", "5");
        assertEquals(keys, lruCache.keys());
    }


    @Test
    public void getTest() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(3);
        for (int i = 0; i < 5; i++) {
            lruCache.put(i, i + 5);
        }

        assertEquals(9, lruCache.get(4));
        assertEquals(8, lruCache.get(3));
        assertEquals(7, lruCache.get(2));
        assertNull(lruCache.get(1));
        assertNull(lruCache.get(0));

        lruCache.put(5, 10);

        assertEquals(10, lruCache.get(5));
        assertNull(lruCache.get(4));
    }


}
