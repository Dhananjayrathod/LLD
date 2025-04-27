package service;

import enums.CabState;
import model.Cab;
import model.CabStateChange;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class CabService {

    private final Map<String, Cab> cabs = new ConcurrentHashMap<>();

    public void registerCab(String cabId, String cityId) {
        if (cabs.containsKey(cabId)) {
            throw new IllegalArgumentException("Cab already registered");
        }
        Cab cab = new Cab(cabId, CabState.IDLE, cityId);
        cabs.put(cabId, cab);
        System.out.println("Cab " + cabId + " registered in city " + cityId);
    }

    public void changeCabCity(String cabId, String newCityId) {
        Cab cab = getCab(cabId);
        if (cab.getState() == CabState.ON_TRIP) {
            throw new IllegalStateException("Cannot change city of cab that is on trip");
        }
        cab.changeCity(newCityId);
        System.out.println("Cab " + cabId + " moved to city " + newCityId);
    }

    public void changeCabState(String cabId, CabState newState) {
        Cab cab = getCab(cabId);
        if (cab.getState() == newState) {
            System.out.println("Cab " + cabId + " is already in state " + newState);
            return;
        }
        if (cab.getState() == CabState.ON_TRIP && newState == CabState.IDLE) {
            throw new IllegalStateException("Cannot change state of cab that is on trip to IDLE");
        }
        cab.changeState(newState);
        System.out.println("Cab " + cabId + " changed state to " + newState);
    }

    public Cab getCab(String cabId) {
        if (!cabs.containsKey(cabId)) {
            throw new IllegalArgumentException("Cab not found");
        }
        return cabs.get(cabId);
    }

    public List<Cab> getAllCabs() {
        return new ArrayList<>(cabs.values());
    }

    public void loadCabSnapshot(List<Map<String, String>> cabsData, CityService cityService) {
        for (Map<String, String> cabData : cabsData) {
            String cabId = cabData.get("cabId");
            CabState cabState = CabState.valueOf(cabData.get("cabState"));
            String cityId = cabData.get("cityId");

            if (!cabs.containsKey(cabId)) {
                if (cityId != null && !cityId.isEmpty() && cityService.getCity(cityId) == null) {
                    throw new IllegalArgumentException("City " + cityId + " does not exist");
                }
                Cab cab = new Cab(cabId, cabState, cityId);
                cabs.put(cabId, cab);
            } else {
                Cab cab = cabs.get(cabId);
                if (cabState == CabState.ON_TRIP) {
                    cab.changeState(cabState);
                } else {
                    if (cityId == null || cityId.isEmpty()) {
                        throw new IllegalArgumentException("City ID cannot be empty for IDLE cab");
                    }
                    if (cityService.getCity(cityId) == null) {
                        throw new IllegalArgumentException("City " + cityId + " does not exist");
                    }
                    if (cab.getState() == CabState.ON_TRIP) {
                        cab.completeTrip(cityId);
                    } else {
                        cab.changeCity(cityId);
                    }
                }
            }
        }
    }

    public Duration getCabIdleTime(String cabId, LocalDateTime from, LocalDateTime to) {
        if (!cabs.containsKey(cabId)) {
            throw new IllegalArgumentException("Cab does not exist");
        }

        return cabs.get(cabId).getCabHistory().getIdleDuration(from, to);
    }

    public List<CabStateChange> getCabStateChangeList(String cabId) {
        return getCab(cabId).getCabHistory().getCabStateChangeList();
    }
}
