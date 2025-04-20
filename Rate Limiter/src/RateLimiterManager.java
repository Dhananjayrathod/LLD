/**
 * Created by dhananjay.rathod at 21/04/25.
 */

public class RateLimiterManager {

    private static RateLimiterManager instance;

    private RateLimiter rateLimiter;

    private RateLimiterConfig rateLimiterConfig;

    private RateLimiterManager() {
        rateLimiterConfig = new RateLimiterConfig("fixed", 10, 1000);
        this.rateLimiter = RateLimiterFactory.createRateLimiter(rateLimiterConfig.getType(),
                rateLimiterConfig.getMaxLimit(), rateLimiterConfig.getWindowSizeInMillis());
    }

    public static RateLimiterManager getInstance() {
        if (instance == null) {
            instance = new RateLimiterManager();
        }
        return instance;
    }

    public boolean isAllowed(String clientId) {
        return rateLimiter.isAllowed(clientId);
    }

    public synchronized void updateRateLimiterConfig(String type, int maxLimit, long windowSizeInMillis) {
        rateLimiterConfig = new RateLimiterConfig(type, maxLimit, windowSizeInMillis);
        this.rateLimiter = RateLimiterFactory.createRateLimiter(type, maxLimit, windowSizeInMillis);
    }
}
