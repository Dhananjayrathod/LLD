/**
 * Created by dhananjay.rathod at 22/04/25.
 */

public class ParkingSpot {

    private final int spotNumber;
    private final VehicleType vehicleType;
    private Vehicle parkedVehicle;

    public ParkingSpot(int spotNumber, VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.vehicleType = spotType;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public synchronized boolean isAvailable() {
        return parkedVehicle == null;
    }

    public synchronized ParkingSpot parkVehicle(Vehicle vehicle) {
        if (isAvailable()) {
            parkedVehicle = vehicle;
            return this;
        }
        return null;
    }

    public void unparkVehicle() {
        parkedVehicle = null;
    }

}
