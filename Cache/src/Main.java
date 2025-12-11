import interfaces.Cache;
import interfaces.EvictionPolicy;
import interfaces.Storage;
import services.CacheImpl;
import services.ExpiryManager;
import services.InMemoryStorage;
import services.LRUEvictionPolicy;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Storage<String, String> storage = new InMemoryStorage<>();
        EvictionPolicy<String> evictionPolicy = new LRUEvictionPolicy<>();
        ExpiryManager<String> expiryManager = new ExpiryManager<>();

        Cache<String, String> cache = new CacheImpl<>(
                 storage, evictionPolicy, expiryManager, 2);

        cache.put("A", "Apple");
        cache.put("B", "Ball");

        System.out.println(cache.get("A"));
        System.out.println(cache.get("B"));

        cache.put("C", "Cat");

        System.out.println(cache.get("A"));
        System.out.println(cache.get("B"));
        System.out.println(cache.get("C"));

        cache.put("D", "Dog", 1000);

        System.out.println("Before TTL expiry: " + cache.get("D"));
        Thread.sleep(1500);
        System.out.println("After TTL expiry: " + cache.get("D"));

        cache.put("X", "Xylophone");
        cache.put("Y", "Yellow");

        System.out.println("Before eviction, keys = " + storage.keys());

        cache.put("Z", "Zebra");

        System.out.println("After eviction, keys = " + storage.keys());
        System.out.println("Get Z: " + cache.get("Z"));

    }
}