package services;

import interfaces.EvictionPolicy;
import interfaces.Storage;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ExpiryManager<K> {

    private static class Entry<K> {
        K key;
        long expiry;

        public Entry(K key, long expiry) {
            this.key = key;
            this.expiry = expiry;
        }
    }

    private final PriorityQueue<Entry<K>> minHeap
            = new PriorityQueue<>(Comparator.comparingLong(e -> e.expiry));

    private final Map<K, Long> expiryMap = new ConcurrentHashMap<>();

    public void setExpiry(K key, Long ttl) {
        long expiry = System.currentTimeMillis() + ttl;
        expiryMap.put(key, expiry);
        minHeap.add(new Entry<>(key, expiry));
    }

    public boolean isExpired(K key) {
        Long expiryTime = expiryMap.get(key);
        return expiryTime != null && expiryTime < System.currentTimeMillis();
    }

    public void evictExpired(Storage<K, ?> storage, EvictionPolicy<K> evictionPolicy) {
        long now = System.currentTimeMillis();
        while(!minHeap.isEmpty() && minHeap.peek().expiry < now) {
            Entry<K> entry = minHeap.poll();
            storage.remove(entry.key);
            evictionPolicy.removeKey(entry.key);
        }
    }

    public void remove(K key) {
        minHeap.remove(key);
    }
}
