package strategy;

import entity.Machine;

import java.util.List;

public class RoundRobinStrategy implements LbStrategy {

    int count = 0;

    @Override
    public void executeRequest(List<Machine> activeMachineList, String msg) {
        int index = ++count % activeMachineList.size();
        activeMachineList.get(index).execute(msg);
    }
}
