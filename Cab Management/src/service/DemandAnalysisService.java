package service;

import model.City;
import model.TripBooking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class DemandAnalysisService {

    private final TripBookingService tripBookingService;
    private final CityService cityService;

    public DemandAnalysisService(TripBookingService tripBookingService, CityService cityService) {
        this.tripBookingService = tripBookingService;
        this.cityService = cityService;
    }

    public Map<String, Integer> getCityDemand() {
        Map<String, Integer> cityDemand = new HashMap<>();
        for (TripBooking booking : tripBookingService.getAllBookings()) {
            String cityId = booking.getFromCityId();
            cityDemand.put(cityId, cityDemand.getOrDefault(cityId, 0) + 1);
        }
        return cityDemand;
    }

    public List<Map.Entry<Integer, Integer>> getPeakHoursForCity(String cityId) {
        if (!cityService.getAllCities().stream()
                .anyMatch(city -> city.getCityId().equals(cityId))) {
            throw new IllegalArgumentException("City does not exist");
        }

        Map<Integer, Integer> hourlyBookings = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            hourlyBookings.put(i, 0);
        }

        // Count bookings per hour for this city
        for (TripBooking booking : tripBookingService.getAllBookings()) {
            if (booking.getFromCityId().equals(cityId)) {
                int hour = booking.getBookingTime().getHour();
                hourlyBookings.put(hour, hourlyBookings.get(hour) + 1);
            }
        }

        // Sort hours by booking count
        return hourlyBookings.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    public Map<String, Map<Integer, Integer>> getCityDemandAnalysis() {
        Map<String, Map<Integer, Integer>> cityHourlyDemand = new HashMap<>();

        for (City city : cityService.getAllCities()) {
            Map<Integer, Integer> hourlyDemand = new HashMap<>();
            for (int hour = 0; hour < 24; hour++) {
                hourlyDemand.put(hour, 0);
            }
            cityHourlyDemand.put(city.getCityId(), hourlyDemand);
        }

        // Count bookings by city and hour
        for (TripBooking booking : tripBookingService.getAllBookings()) {
            String cityId = booking.getFromCityId();
            int hour = booking.getBookingTime().getHour();

            Map<Integer, Integer> hourlyDemand = cityHourlyDemand.get(cityId);
            hourlyDemand.put(hour, hourlyDemand.get(hour) + 1);
        }

        return cityHourlyDemand;
    }
}
