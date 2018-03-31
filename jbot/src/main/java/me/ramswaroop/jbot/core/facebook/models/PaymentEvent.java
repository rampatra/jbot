package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ramswaroop
 * @version 06/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentEvent {
    
    private String object;
    private Entry[] entry;

    public String getObject() {
        return object;
    }

    public PaymentEvent setObject(String object) {
        this.object = object;
        return this;
    }

    public Entry[] getEntry() {
        return entry;
    }

    public PaymentEvent setEntry(Entry[] entry) {
        this.entry = entry;
        return this;
    }
}