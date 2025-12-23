import entity.LoadBalancer;
import strategy.LbStrategy;

public class LoadBalancerManager {

    private static volatile LoadBalancer loadBalancer;

    private LoadBalancerManager(LbStrategy strategy){
        if (loadBalancer == null) {
            this.loadBalancer = new LoadBalancer(strategy);
        }
    }

    public static LoadBalancer getLoadBalancerInstance(LbStrategy lbStrategy) {
        new LoadBalancerManager(lbStrategy);
        return loadBalancer;
    }
}
