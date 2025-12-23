import entity.LoadBalancer;
import entity.Machine;
import entity.Status;
import strategy.LbStrategy;


public class Main {
    public static void main(String[] args) {

        LbStrategy roundRobinStrategy = LoadBalancerStrategyFactory.createLoadBalancerStrategy(Constants.ROUND_ROBIN);

        LoadBalancer loadBalancer = LoadBalancerManager.getLoadBalancerInstance(roundRobinStrategy);

        Machine m1 = new Machine("m1", 2024);
        Machine m2 = new Machine("m2", 2024);
        Machine m3 = new Machine("m3", 2024);
        Machine m4 = new Machine("m4", 2024);
        Machine m5 = new Machine("m5", 2024);

        loadBalancer.addMachine(m5);
        loadBalancer.addMachine(m1);
        loadBalancer.addMachine(m2);
        loadBalancer.addMachine(m3);
        loadBalancer.addMachine(m4);

        for(int i = 1; i <= 10; i++) {
            loadBalancer.scheduleRequest("Message" + i);
        }

        loadBalancer.removeMachine(m2);
        m4.setStatus(Status.INACTIVE);

        System.out.println("======== Machine states updated ==========");

        for(int i = 1; i <= 5; i++) {
            loadBalancer.scheduleRequest("Message" + i);
        }

        System.out.println("======== Scheduling messages with Random scheduler ==========");

        LbStrategy randomStrategy = LoadBalancerStrategyFactory.createLoadBalancerStrategy(Constants.RANDOM);
        LoadBalancer loadBalancer1 = LoadBalancerManager.getLoadBalancerInstance(randomStrategy);

        loadBalancer1.addMachine(m1);
        loadBalancer1.addMachine(m2);
        loadBalancer1.addMachine(m3);
        loadBalancer1.addMachine(m4);
        loadBalancer1.addMachine(m5);

        for(int i = 1; i <= 10; i++) {
            loadBalancer1.scheduleRequest("Message" + i);
        }

        loadBalancer1.removeMachine(m5);
        m4.setStatus(Status.ACTIVE);

        System.out.println("======== Machine states updated ==========");

        for(int i = 1; i <= 5; i++) {
            loadBalancer.scheduleRequest("Message" + i);
        }



    }
}
/*
Write load balancer,
1. Add machines
2. Remove machines
3. Avoid sending load to an unhealthy machine
4. Strategies for LB for request forwarding
 */