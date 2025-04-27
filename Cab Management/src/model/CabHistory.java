package model;

import enums.CabState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhananjay.rathod at 27/04/25.
 */

public class CabHistory {

    private final String cabId;
    private List<CabStateChange> cabStateChangeList;
    private LocalDateTime lastIdleTime;

    public CabHistory(String cabId) {
        this.cabId = cabId;
        this.cabStateChangeList = new ArrayList<>();
        this.lastIdleTime = LocalDateTime.now();
    }

    public void addStateChange(CabState fromState, CabState toState, String fromCity, String toCity) {
        CabStateChange cabStateChange = new CabStateChange(fromState, toState, fromCity, toCity);
        cabStateChangeList.add(cabStateChange);

        if (toState == CabState.IDLE) {
            lastIdleTime = LocalDateTime.now();
        }
    }

    public String getCabId() {
        return cabId;
    }

    public List<CabStateChange> getCabStateChangeList() {
        return cabStateChangeList;
    }

    public LocalDateTime getLastIdleTime() {
        return lastIdleTime;
    }

    public Duration getIdleDuration(LocalDateTime fromTime, LocalDateTime toTime) {
        Duration idleDuration = Duration.ZERO;
        LocalDateTime startTime = fromTime;

        for (CabStateChange cabStateChange: cabStateChangeList) {
            if (cabStateChange.getTimestamp().isBefore(fromTime)) continue;
            if (cabStateChange.getTimestamp().isAfter(toTime)) break;

            if (cabStateChange.getFromState() == CabState.IDLE
                    && cabStateChange.getToState() == CabState.ON_TRIP) {
                idleDuration = idleDuration.plus(Duration.between(startTime, cabStateChange.getTimestamp()));
            } else if (cabStateChange.getFromState() == CabState.ON_TRIP
                    && cabStateChange.getToState() == CabState.IDLE) {
                startTime = cabStateChange.getTimestamp();
            }
        }

        if (cabStateChangeList.size() > 0
                && cabStateChangeList.get(cabStateChangeList.size() - 1).getToState() == CabState.IDLE
                && startTime.isBefore(toTime)) {

            idleDuration = idleDuration.plus(Duration.between(startTime, toTime));
        }

        return idleDuration;
    }


}
