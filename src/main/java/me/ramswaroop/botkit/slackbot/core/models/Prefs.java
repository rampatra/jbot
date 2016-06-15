package me.ramswaroop.botkit.slackbot.core.models;

/**
 * Created by ramswaroop on 14/06/2016.
 */
public class Prefs {
    private String[] channels;
    private String[] groups;

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }
}
