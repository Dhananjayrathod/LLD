package services;

import interfaces.Cache;
import interfaces.EvictionPolicy;
import interfaces.Storage;

public class CacheImpl<K, V> implements Cache<K, V> {

    private final Storage<K,V> storage;
    private final EvictionPolicy<K> evictionPolicy;
    private final ExpiryManager<K> expiryManager;
    private final int capacity;

    public CacheImpl(Storage<K, V> storage, EvictionPolicy<K> evictionPolicy,
                     ExpiryManager<K> expiryManager, int capacity) {

        this.storage = storage;
        this.evictionPolicy = evictionPolicy;
        this.expiryManager = expiryManager;
        this.capacity = capacity;
    }

    @Override
    public V get(K key) {
        if (expiryManager.isExpired(key)) {
            storage.remove(key);
            evictionPolicy.removeKey(key);
            expiryManager.remove(key);
            return null;
        }

        V value = storage.get(key);
        if (value != null) {
            evictionPolicy.keyAccessed(key);
        }
        return value;
    }

    @Override
    public void put(K key, V value) {
        expiryManager.evictExpired(storage, evictionPolicy);

        if (storage.getSize() >= capacity) {
            K evictedKey = evictionPolicy.evictKey();
            if (evictedKey != null) {
                storage.remove(evictedKey);
            }
        }
        storage.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    @Override
    public void put(K key, V value, long ttl) {
        put(key, value);
        expiryManager.setExpiry(key, ttl);
    }

    @Override
    public void remove(K key) {
         storage.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return storage.containsKey(key);
    }

    @Override
    public int getSize() {
        return storage.getSize();
    }
}
