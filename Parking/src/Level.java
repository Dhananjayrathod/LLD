import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 22/04/25.
 */

public class Level {

    private final int floorNumber;
    private final Map<VehicleType, List<ParkingSpot>> parkingSpotsMap;

    public Level(int floorNumber, int spotCount) {
        this.floorNumber = floorNumber;
        this.parkingSpotsMap = new ConcurrentHashMap<>();
        // Initializing parking spots in ration of 20:40:10:10:20
        int compactSpots = (int) (spotCount * 0.2);
        int regularSpots = (int) (spotCount * 0.4);
        int largeSpots = (int) (spotCount * 0.1);
        int handicapSpots = (int) (spotCount * 0.1);

        addSpotsToMap(VehicleType.MOTORCYCLE, 1, compactSpots);
        addSpotsToMap(VehicleType.CAR, compactSpots + 1, regularSpots);
        addSpotsToMap(VehicleType.TRUCK, regularSpots + 1, largeSpots);
        addSpotsToMap(VehicleType.HANDICAP, largeSpots + 1, handicapSpots);
        addSpotsToMap(VehicleType.ELECTRIC, handicapSpots + 1,
                spotCount - compactSpots - regularSpots - largeSpots - handicapSpots);
    }

    private void addSpotsToMap(VehicleType vehicleType,int start, int count) {
        List<ParkingSpot> spots = parkingSpotsMap.computeIfAbsent(vehicleType, k -> new ArrayList<>());
        int startIndex = spots.size();
        for (int i = start + startIndex; i < startIndex + count; i++) {
            spots.add(new ParkingSpot(i, vehicleType));
        }
    }

    public synchronized Optional<ParkingSpot> parkVehicle(Vehicle vehicle) {
        List<ParkingSpot> parkingSpots = parkingSpotsMap.get(vehicle.getVehicleType());
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isAvailable() && parkingSpot.getVehicleType() == vehicle.getVehicleType()) {
                return Optional.ofNullable(parkingSpot.parkVehicle(vehicle));
            }
        }
        return Optional.empty();
    }

    public synchronized void unparkVehicle(Vehicle vehicle) {
        List<ParkingSpot> parkingSpots = parkingSpotsMap.get(vehicle.getVehicleType());
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.getParkedVehicle() == vehicle) {
                parkingSpot.unparkVehicle();
                break;
            }
        }
    }

    public void displayAvailableSpots() {
        System.out.println("Available spots on floor " + floorNumber + ":");
        for (Map.Entry<VehicleType, List<ParkingSpot>> entry : parkingSpotsMap.entrySet()) {
            VehicleType vehicleType = entry.getKey();
            List<ParkingSpot> parkingSpots = entry.getValue();
            System.out.println(vehicleType + ": " + parkingSpots.stream().filter(ParkingSpot::isAvailable).count());
        }
//        for (ParkingSpot parkingSpot : parkingSpots) {
//            if (parkingSpot.isAvailable()) {
//                System.out.println("Spot number: " + parkingSpot.getSpotNumber() + ", Type: " + parkingSpot.getVehicleType());
//            }
//        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

}
