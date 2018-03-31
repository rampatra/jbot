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
public class CheckoutUpdate {
    
    private String payload;
    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;

    public String getPayload() {
        return payload;
    }

    public CheckoutUpdate setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public CheckoutUpdate setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }
}
