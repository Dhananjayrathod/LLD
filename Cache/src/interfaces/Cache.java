package interfaces;

public interface Cache <K, V>{

    V get(K key);
    void put(K key, V value);
    void put(K key, V value, long ttl);
    void remove(K key);
    boolean containsKey(K key);
    int getSize();
}
