package model;

import java.time.LocalDateTime;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class TripBooking {

    private String tripId;
    private String cabId;
    private String fromCityId;
    private String toCityId;
    private LocalDateTime bookingTime;
    private LocalDateTime tripEndTime;


    public TripBooking(String tripId, String cabId, String fromCityId, String toCityId) {
        this.tripId = tripId;
        this.cabId = cabId;
        this.fromCityId = fromCityId;
        this.toCityId = toCityId;
        this.bookingTime = LocalDateTime.now();
    }

    public String getTripId() {
        return tripId;
    }

    public String getCabId() {
        return cabId;
    }

    public String getFromCityId() {
        return fromCityId;
    }

    public String getToCityId() {
        return toCityId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public LocalDateTime getTripEndTime() {
        return tripEndTime;
    }

    public void completeTrip() {
        this.tripEndTime = LocalDateTime.now();
    }

    public boolean isTripCompleted() {
        return tripEndTime != null;
    }

}
