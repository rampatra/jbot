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

    private final Ticket ticket = new Ticket();
    private String name;
    @JsonProperty("passenger_id")
    private String passengerId;

    public String getName() {
        return name;
    }

    public Passenger setName(String name) {
        this.name = name;
        return this;
    }

    public String getTicketNumber() {
        return ticket.getTicketNumber();
    }

    public Passenger setTicketNumber(String ticketNumber) {
        this.ticket.ticketNumber = ticketNumber;
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
        return ticket.getSegmentId();
    }

    public Passenger setSegmentId(String segmentId) {
        this.ticket.segmentId = segmentId;
        return this;
    }

    public String getSeat() {
        return ticket.getSeat();
    }

    public Passenger setSeat(String seat) {
        this.ticket.seat = seat;
        return this;
    }

    public String getSeatType() {
        return ticket.getSeatType();
    }

    public Passenger setSeatType(String seatType) {
        this.ticket.seatType = seatType;
        return this;
    }
}
