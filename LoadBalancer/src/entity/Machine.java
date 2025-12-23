package entity;

public class Machine {

    String name; // pod1, pod2
    int ram;
    Status status;

    public Machine(String name, int ram){
        this.name = name;
        this.ram = ram;
        this.status = Status.ACTIVE;
    }

    public void execute(String msg) {
        System.out.println("Executed on the machine:  " + name + ": " + msg);
    }

    public String getName(){
        return name;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
