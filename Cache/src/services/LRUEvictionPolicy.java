package services;

import interfaces.EvictionPolicy;

import java.util.LinkedHashSet;


public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    private final LinkedHashSet<K> dll = new LinkedHashSet<>();

    @Override
    public void keyAccessed(K key) {
        dll.remove(key);
        dll.add(key);
    }

    @Override
    public K evictKey() {
        return dll.removeFirst();
    }

    @Override
    public void removeKey(K key) {
        dll.remove(key);
    }
}
