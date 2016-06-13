package me.ramswaroop.botkit.slackbot.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ramswaroop on 12/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTM {

    private String url;
    private String[] dmChannels;

    public String getUrl() {
        return url;
    }

    public String[] getDmChannels() {
        return dmChannels;
    }
}
