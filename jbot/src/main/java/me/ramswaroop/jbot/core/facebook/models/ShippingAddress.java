package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingAddress {
    
    private Long id;
    private String name;
    private String country;
    private String city;
    @JsonProperty("street_1")
    private String street1;
    @JsonProperty("street_2")
    private String street2;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    
    public Long getId() {
        return id;
    }

    public ShippingAddress setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShippingAddress setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ShippingAddress setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ShippingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet1() {
        return street1;
    }

    public ShippingAddress setStreet1(String street1) {
        this.street1 = street1;
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public ShippingAddress setStreet2(String street2) {
        this.street2 = street2;
        return this;
    }

    public String getState() {
        return state;
    }

    public ShippingAddress setState(String state) {
        this.state = state;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public ShippingAddress setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }
}
