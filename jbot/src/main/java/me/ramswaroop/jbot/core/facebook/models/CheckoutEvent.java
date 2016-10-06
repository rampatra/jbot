package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ramswaroop
 * @version 06/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutEvent {

    private String object;
    private Entry[] entry;
    
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Entry {

    private String id;
    private long time;
    private Event[] messaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Event[] getMessaging() {
        return messaging;
    }

    public void setMessaging(Event[] messaging) {
        this.messaging = messaging;
    }
}
