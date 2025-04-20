import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dhananjay.rathod at 21/04/25.
 */

public class LeakyBucketRateLimiter implements RateLimiter {

    private final int capacity;
    private final long leakRateInMillis;
    private AtomicLong waterLevel;
    private long lastLeakTimestamp;

    public LeakyBucketRateLimiter(int capacity, long leakRateInMillis) {
        this.capacity = capacity;
        this.leakRateInMillis = leakRateInMillis;
        this.waterLevel = new AtomicLong(0);
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean isAllowed(String key) {
        leakWaterIfNeeded();
        if (waterLevel.get() < capacity) {
            waterLevel.incrementAndGet();
            return true;
        }
        return false;
    }

    private void leakWaterIfNeeded() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastLeak = currentTime - lastLeakTimestamp;
        long waterToLeak = timeSinceLastLeak / leakRateInMillis;
        if (waterToLeak > 0) {
            waterLevel.set(Math.max(0, waterLevel.get() - waterToLeak));
            lastLeakTimestamp = currentTime;
        }
    }
}
