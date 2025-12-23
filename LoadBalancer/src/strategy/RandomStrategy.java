package strategy;

import entity.Machine;

import java.util.List;

public class RandomStrategy implements LbStrategy {


    @Override
    public void executeRequest(List<Machine> machineList, String msg) {
        int randomNumber = (int) (Math.random() * machineList.size());
        machineList.get(randomNumber).execute(msg);
    }
}
