package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 01/01/2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Passenger {
    
    private String name;
    @JsonProperty("ticket_number")
    private String ticketNumber;
    @JsonProperty("passenger_id")
    private String passengerId;
    @JsonProperty("segment_id")
    private String segmentId;
    private String seat;
    @JsonProperty("seat_type")
    private String seatType;

    public String getName() {
        return name;
    }

    public Passenger setName(String name) {
        this.name = name;
        return this;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Passenger setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public Passenger setPassengerId(String passengerId) {
        this.passengerId = passengerId;
        return this;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public Passenger setSegmentId(String segmentId) {
        this.segmentId = segmentId;
        return this;
    }

    public String getSeat() {
        return seat;
    }

    public Passenger setSeat(String seat) {
        this.seat = seat;
        return this;
    }

    public String getSeatType() {
        return seatType;
    }

    public Passenger setSeatType(String seatType) {
        this.seatType = seatType;
        return this;
    }
}
