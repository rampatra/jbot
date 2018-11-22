package com.rampatra.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author rampatra
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Postback {

    private String payload;
    private Referral referral;

    public String getPayload() {
        return payload;
    }

    public Postback setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public Referral getReferral() {
        return referral;
    }

    public Postback setReferral(Referral referral) {
        this.referral = referral;
        return this;
    }
}
