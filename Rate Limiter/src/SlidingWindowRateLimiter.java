import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class SlidingWindowRateLimiter implements RateLimiter{

    private final int maxLimit;
    private final long windowSizeInMillis;
    private Map<String, Queue<Long>> requestTimestamps;

    public SlidingWindowRateLimiter(int maxLimit, long windowSizeInMillis) {
        this.maxLimit = maxLimit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requestTimestamps = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String key) {
        long currentTime = System.currentTimeMillis();
        requestTimestamps.putIfAbsent(key, new LinkedList<>());

        final Queue<Long> timestamps = requestTimestamps.get(key);
        while (!timestamps.isEmpty() && currentTime - timestamps.peek() >= windowSizeInMillis){
            timestamps.poll();
        }

        if (timestamps.size() < maxLimit) {
            timestamps.offer(currentTime);
            return true;
        }

        return false;
    }
}
