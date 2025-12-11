package services;

import interfaces.Storage;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage<K, V> implements Storage<K, V> {

    private final Map<K, V> cacheMap = new ConcurrentHashMap<>();

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public void put(K key, V value) {
        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public int getSize() {
        return cacheMap.size();
    }

    @Override
    public Set<K> keys() {
        return cacheMap.keySet();
    }
}
