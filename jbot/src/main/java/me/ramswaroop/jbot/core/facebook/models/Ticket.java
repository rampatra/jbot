package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    @JsonProperty("ticket_number")
    String ticketNumber;
    @JsonProperty("segment_id")
    String segmentId;
    String seat;
    @JsonProperty("seat_type")
    String seatType;

    public Ticket() {
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public String getSeat() {
        return seat;
    }

    public String getSeatType() {
        return seatType;
    }
}