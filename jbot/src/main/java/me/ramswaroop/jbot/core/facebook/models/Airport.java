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
public class Airport {

    @JsonProperty("airport_code")
    private String airportCode;
    private String city;
    private String terminal;
    private String gate;

    public String getAirportCode() {
        return airportCode;
    }

    public Airport setAirportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Airport setCity(String city) {
        this.city = city;
        return this;
    }

    public String getTerminal() {
        return terminal;
    }

    public Airport setTerminal(String terminal) {
        this.terminal = terminal;
        return this;
    }

    public String getGate() {
        return gate;
    }

    public Airport setGate(String gate) {
        this.gate = gate;
        return this;
    }
}
