package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ramswaroop on 12/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RTM {

    private String webSocketUrl;
    private List<String> dmChannels;
    private User user;
    private List<User> users;

    public String getWebSocketUrl() {
        return webSocketUrl;
    }

    public void setWebSocketUrl(String webSocketUrl) {
        this.webSocketUrl = webSocketUrl;
    }

    public List<String> getDmChannels() {
        return dmChannels;
    }

    public void setDmChannels(List<String> dmChannels) {
        this.dmChannels = dmChannels;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }
}
