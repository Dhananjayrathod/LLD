package strategy;

import entity.Machine;

import java.util.List;

public interface LbStrategy {

    void executeRequest(List<Machine> machineList, String msg);
}
