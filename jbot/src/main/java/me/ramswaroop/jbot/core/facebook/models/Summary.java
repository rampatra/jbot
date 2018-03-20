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
public class Summary {

    private Double subtotal;
    @JsonProperty("shipping_cost")
    private Double shippingCost;
    @JsonProperty("total_tax")
    private Double totalTax;
    @JsonProperty("total_cost")
    private Double totalCost;

    public Double getSubtotal() {
        return subtotal;
    }

    public Summary setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public Summary setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public Summary setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Summary setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
