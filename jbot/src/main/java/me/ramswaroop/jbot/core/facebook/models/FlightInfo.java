package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 31/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightInfo {
    
    @JsonProperty("flight_number")
    private String flightNumber;
    @JsonProperty("departure_airport")
    private Airport departureAirport;
    @JsonProperty("arrival_airport")
    private Airport arrivalAirport;
    @JsonProperty("flight_schedule")
    private FlightSchedule flightSchedule;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("segment_id")
    private String segmentId;
    @JsonProperty("aircraft_type")
    private String aircraftType;
    @JsonProperty("travel_class")
    private String travelClass;

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightInfo setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public FlightInfo setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
        return this;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public FlightInfo setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
        return this;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public FlightInfo setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
        return this;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public FlightInfo setConnectionId(String connectionId) {
        this.connectionId = connectionId;
        return this;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public FlightInfo setSegmentId(String segmentId) {
        this.segmentId = segmentId;
        return this;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public FlightInfo setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public FlightInfo setTravelClass(String travelClass) {
        this.travelClass = travelClass;
        return this;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class FlightSchedule {
    
    @JsonProperty("boarding_time")
    private String boardingTime;
    @JsonProperty("departure_time")
    private String departureTime;
    @JsonProperty("arrival_time")
    private String arrivalTime;

    public String getBoardingTime() {
        return boardingTime;
    }

    public FlightSchedule setBoardingTime(String boardingTime) {
        this.boardingTime = boardingTime;
        return this;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public FlightSchedule setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public FlightSchedule setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }
}
