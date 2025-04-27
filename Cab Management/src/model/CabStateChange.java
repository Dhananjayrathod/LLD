package model;

import enums.CabState;

import java.time.LocalDateTime;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class CabStateChange {

    private CabState fromState;
    private CabState toState;
    private String fromCity;
    private String toCity;
    private LocalDateTime timestamp;

    public CabStateChange(CabState fromState, CabState toState, String fromCity, String toCity) {
        this.fromState = fromState;
        this.toState = toState;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.timestamp = LocalDateTime.now();
    }
    
    public CabState getFromState() {
        return fromState;
    }

    public CabState getToState() {
        return toState;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "CabStateChange{" +
                "fromState=" + fromState +
                ", toState=" + toState +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
