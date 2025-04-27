package model;

import enums.CabState;

import java.time.LocalDateTime;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class Cab {

    private final String cabId;
    private CabState state;
    private CabHistory cabHistory;
    private String currentCityId;

    public Cab(String cabId, CabState state, String currentCityId) {
        this.cabId = cabId;
        this.state = state;
        this.currentCityId = currentCityId;
        this.cabHistory = new CabHistory(cabId);

        if (state == CabState.IDLE) {
            cabHistory.addStateChange(null, state, null, currentCityId);
        }
    }

    public String getCabId() {
        return cabId;
    }

    public CabState getState() {
        return state;
    }

    public String getCurrentCityId() {
        return currentCityId;
    }

    public void changeState(CabState newCabState) {
        String oldCityId = this.currentCityId;

        if (newCabState == CabState.ON_TRIP) {
            this.cabHistory.addStateChange(this.state, newCabState, oldCityId, "IN_TRANSIT");
            this.currentCityId = null;
        } else {
            this.cabHistory.addStateChange(this.state, newCabState, oldCityId, this.currentCityId);
        }
        this.state = newCabState;
    }

    public void changeCity(String newCity) {
        String oldCity = this.currentCityId;
        this.currentCityId = newCity;
        this.cabHistory.addStateChange(this.state, this.state, oldCity, newCity);
    }

    public void completeTrip(String destinationCityId) {
        if (this.state != CabState.ON_TRIP) {
            throw new IllegalStateException("Cab is not in ON_TRIP state");
        }

        this.cabHistory.addStateChange(this.state, CabState.IDLE, "IN_TRANSIT", destinationCityId);
        this.state = CabState.IDLE;
        this.currentCityId = destinationCityId;
    }

    public CabHistory getCabHistory() {
        return cabHistory;
    }

    public LocalDateTime getLastIdleTime() {
        return cabHistory.getLastIdleTime();
    }
}
