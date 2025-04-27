import model.CabStateChange;
import model.TripBooking;
import service.CityService;
import service.CabService;
import service.TripBookingService;
import service.DemandAnalysisService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        CityService cityService = new CityService();
        CabService cabService = new CabService();
        TripBookingService tripBookingService = new TripBookingService(cabService, cityService);
        DemandAnalysisService demandAnalysisService = new DemandAnalysisService(tripBookingService, cityService);

        // Register cities
        cityService.registerCity("DEL", "Delhi");
        cityService.registerCity("BLR", "Bengaluru");
        cityService.registerCity("MUM", "Mumbai");
        cityService.registerCity("PUN", "Pune");

        // Register cabs
        cabService.registerCab("CAB001", "MUM");
        cabService.registerCab("CAB002", "MUM");
        cabService.registerCab("CAB003", "DEL");
        cabService.registerCab("CAB004", "BLR");
        cabService.registerCab("CAB005", "PUN");

        try {
            // Book and complete trips
            TripBooking booking1 = tripBookingService.bookCab("MUM", "PUN");
            System.out.println("Booking confirmed: " + booking1.getTripId());

            TripBooking booking2 = tripBookingService.bookCab("MUM", "BLR");
            System.out.println("Booking confirmed: " + booking2.getTripId());

            try {
                tripBookingService.bookCab("MUM", "DEL");
            } catch (IllegalStateException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            tripBookingService.completeTrip(booking1.getCabId(), "PUN");

            TripBooking booking3 = tripBookingService.bookCab("PUN", "DEL");
            System.out.println("Booking confirmed: " + booking3.getTripId());

            // Load a snapshot for additional cabs
            List<Map<String, String>> snapshot = new ArrayList<>();
            Map<String, String> cab1 = new HashMap<>();
            cab1.put("cabId", "CAB006");
            cab1.put("cabState", "IDLE");
            cab1.put("cityId", "DEL");

            Map<String, String> cab2 = new HashMap<>();
            cab2.put("cabId", "CAB007");
            cab2.put("cabState", "ON_TRIP");
            cab2.put("cityId", "");

            snapshot.add(cab1);
            snapshot.add(cab2);
            cabService.loadCabSnapshot(snapshot, cityService);

            // Display cab history for a specific cab
            System.out.println("\nCab History for CAB001:");
            List<CabStateChange> history = cabService.getCabStateChangeList("CAB001");
            for (CabStateChange change : history) {
                System.out.println(change);
            }

            // Display high demand cities using demand analysis service
            System.out.println("\nHigh Demand Cities:");
            Map<String, Integer> cityDemand = demandAnalysisService.getCityDemand();
            for (Map.Entry<String, Integer> entry : cityDemand.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " bookings");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}