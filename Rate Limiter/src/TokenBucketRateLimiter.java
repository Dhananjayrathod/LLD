import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dhananjay.rathod at 21/04/25.
 */

public class TokenBucketRateLimiter implements RateLimiter {

    private final int maxTokens;
    private final long refillInterval;
    private final int refillTokens;
    private AtomicLong availableTokens;
    private long lastRefillTimestamp;

    public TokenBucketRateLimiter(int maxTokens, long refillInterval,
                                  int refillTokens) {
        this.maxTokens = maxTokens;
        this.refillInterval = refillInterval;
        this.refillTokens = refillTokens;
        availableTokens = new AtomicLong(maxTokens);
        lastRefillTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean isAllowed(String key) {
        refillTokensIfNeeded();
        if (availableTokens.get() > 0) {
            availableTokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refillTokensIfNeeded() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRefill = currentTime - lastRefillTimestamp;
        if (timeSinceLastRefill >= refillInterval) {
            long tokensToAdd = (timeSinceLastRefill / refillInterval) * refillTokens;
            availableTokens.set(Math.min(maxTokens, availableTokens.get() + tokensToAdd));
            lastRefillTimestamp = currentTime;
        }
    }
}
