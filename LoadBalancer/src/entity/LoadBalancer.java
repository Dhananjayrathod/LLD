package entity;

import strategy.LbStrategy;

import java.util.ArrayList;
import java.util.List;

public class LoadBalancer {

    LbStrategy lbStrategy;
    List<Machine> machineList;

    public LoadBalancer(LbStrategy lbStrategy) {
        this.lbStrategy = lbStrategy;
        this.machineList = new ArrayList<>();
    }

    public LoadBalancer(LbStrategy lbStrategy, List<Machine> machineList) {
        this.lbStrategy = lbStrategy;
        this.machineList = machineList;
    }

    public void addMachine(Machine machine) {
        machineList.add(machine);
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void removeMachine(Machine machine) {
        machineList.remove(machine);
    }

    private List<Machine> getActiveMachines() {
        List<Machine> activeMachines = new ArrayList<>();
        for (Machine machine: machineList) {
            if (machine.getStatus() == Status.ACTIVE) {
                activeMachines.add(machine);
            }
        }
        return activeMachines;
    }

    public void scheduleRequest(String message){
        lbStrategy.executeRequest(getActiveMachines(), message);
    }
}
