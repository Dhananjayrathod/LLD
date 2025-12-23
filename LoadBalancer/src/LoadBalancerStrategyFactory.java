import strategy.LbStrategy;
import strategy.RandomStrategy;
import strategy.RoundRobinStrategy;

public class LoadBalancerStrategyFactory {

    public static LbStrategy createLoadBalancerStrategy(String type) {
        return switch (type) {
            case Constants.RANDOM -> new RandomStrategy();
            case Constants.ROUND_ROBIN -> new RoundRobinStrategy();
            default -> throw new IllegalArgumentException( type + " this strategy not supported");
        };
    }
}
