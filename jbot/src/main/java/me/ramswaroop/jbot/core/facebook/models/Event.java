package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    
    private User sender;
    private User recipient;
    private long timestamp;
    private Message message;
    private Postback postback;
    private Optin optin;
    @JsonProperty("account_linking")
    private AccountLinking accountLinking;
    
}
