package interfaces;

import java.util.Set;

public interface Storage <K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    boolean containsKey(K key);
    int getSize();
    Set<K> keys();
}
