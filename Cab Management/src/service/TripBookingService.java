package service;

import enums.CabState;
import model.Cab;
import model.TripBooking;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class TripBookingService {

    private final List<TripBooking> tripBookings = new ArrayList<>();
    private final CabService cabService;
    private final CityService cityService;

    public TripBookingService(CabService cabService, CityService cityService) {
        this.cabService = cabService;
        this.cityService = cityService;
    }

    public TripBooking bookCab(String fromCityId, String toCityId) {
        cityService.getCity(fromCityId);
        cityService.getCity(toCityId);

        List<Cab> availableCabs = cabService.getAllCabs().stream()
                .filter(cab -> cab.getState() == CabState.IDLE && fromCityId.equals(cab.getCurrentCityId()))
                .collect(Collectors.toList());

        if (availableCabs.isEmpty()) {
            throw new IllegalStateException("No cabs available in " + fromCityId);
        }

        Cab selectedCab = Collections.max(availableCabs, Comparator.comparing(Cab::getLastIdleTime));
        cabService.changeCabState(selectedCab.getCabId(), CabState.ON_TRIP);

        String tripId = "TRIP-" + UUID.randomUUID().toString().substring(0, 8);
        TripBooking booking = new TripBooking(tripId, selectedCab.getCabId(), fromCityId, toCityId);
        tripBookings.add(booking);

        System.out.println("Cab " + selectedCab.getCabId() + " booked for trip from " +
                fromCityId + " to " + toCityId);
        return booking;
    }

    public void completeTrip(String cabId, String destinationCityId) {
        cityService.getCity(destinationCityId);
        Cab cab = cabService.getCab(cabId);

        if (cab.getState() != CabState.ON_TRIP) {
            throw new IllegalStateException("Cab is not on trip");
        }
        cab.completeTrip(destinationCityId);
        for (TripBooking booking : tripBookings) {
            if (booking.getCabId().equals(cabId) && !booking.isTripCompleted()) {
                booking.completeTrip();
                break;
            }
        }
        System.out.println("Trip completed for cab " + cabId + " to city " + destinationCityId);
    }

    public List<TripBooking> getAllBookings() {
        return new ArrayList<>(tripBookings);
    }
}
