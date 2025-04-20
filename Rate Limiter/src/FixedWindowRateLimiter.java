import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 20/04/25.
 */

public class FixedWindowRateLimiter implements RateLimiter{

    private final int maxLimit;
    private final long windowSizeInMillis;
    private final Map<String, Integer> requestCounts;
    private final Map<String, Long> windowStartTimes;

    public FixedWindowRateLimiter(int maxLimit, long windowSizeInMillis) {
        this.maxLimit = maxLimit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.requestCounts = new ConcurrentHashMap<>();
        this.windowStartTimes = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String key) {
        long currentTime = System.currentTimeMillis();
        windowStartTimes.putIfAbsent(key, currentTime);
        requestCounts.putIfAbsent(key, 0);

        long windowStartTime = windowStartTimes.get(key);
        if (currentTime - windowStartTime >= windowSizeInMillis){
            windowStartTimes.put(key, currentTime);
            requestCounts.put(key, 0);
        }

        int requestCount = requestCounts.get(key);

        if (requestCount < maxLimit) {
            requestCounts.put(key, requestCount + 1);
            return true;
        }

        return false;
    }
}
