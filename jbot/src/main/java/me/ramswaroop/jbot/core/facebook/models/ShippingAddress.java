package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingAddress {
    
    private long id;
    private String country;
    private String city;
    private String street1;
    private String street2;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    
}
