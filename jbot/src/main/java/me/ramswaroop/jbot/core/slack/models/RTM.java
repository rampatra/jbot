package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ramswaroop on 12/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTM {

    private String url;
    private User self;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User user) {
        this.self = user;
    }
}
