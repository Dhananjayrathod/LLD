import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by dhananjay.rathod at 22/04/25.
 */

public class ParkingLot {

    private static ParkingLot instance;
    private List<Level> levels;

    private ParkingLot() {
        this.levels = new ArrayList<>();
    }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            Optional<ParkingSpot> spot = level.parkVehicle(vehicle);
            if (spot.isPresent()) {
                System.out.println("Vehicle " + vehicle.getRegistrationNumber() + " parked on level "
                        + level.getFloorNumber() + " in spot " + spot.get().getSpotNumber());
                return true;
            }
        }
        return false;
    }

    public void unparkVehicle(Vehicle vehicle) {
        for (Level level : levels) {
            level.unparkVehicle(vehicle);
        }
    }

    public void displayParkingLotStatus() {
        for (Level level : levels) {
            level.displayAvailableSpots();
        }
    }
}
