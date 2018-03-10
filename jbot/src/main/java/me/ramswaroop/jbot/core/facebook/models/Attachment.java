package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ramswaroop
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachment {
    
    private String type;
    private Payload payload;

    public String getType() {
        return type;
    }

    public Attachment setType(String type) {
        this.type = type;
        return this;
    }

    public Payload getPayload() {
        return payload;
    }

    public Attachment setPayload(Payload payload) {
        this.payload = payload;
        return this;
    }
}
