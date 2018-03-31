package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This model is also used for Optin.
 * See <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/optins">Opt-in Callback</a>
 * 
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Referral {
    
    private String ref;
    private String source;
    private String type;

    public String getRef() {
        return ref;
    }

    public Referral setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Referral setSource(String source) {
        this.source = source;
        return this;
    }

    public String getType() {
        return type;
    }

    public Referral setType(String type) {
        this.type = type;
        return this;
    }
}
