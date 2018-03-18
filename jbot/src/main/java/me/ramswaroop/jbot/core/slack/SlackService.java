package me.ramswaroop.jbot.core.slack;

import me.ramswaroop.jbot.core.slack.models.RTM;
import me.ramswaroop.jbot.core.slack.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ramswaroop
 * @version 14/08/2016
 */
@Service
@Scope("prototype")
public class SlackService {

    @Autowired
    private SlackDao slackDao;
    private User currentUser;
    private List<String> dmChannels;
    private String webSocketUrl;
    private List<User> users;

    /**
     * Start a RTM connection. Fetch the web socket url to connect to, current user details
     * and list of channel ids where the current user has had conversation.
     *
     * @param slackToken slack token which you get from slack for the integration you create
     */
    public void startRTM(String slackToken) {
        RTM rtm = slackDao.startRTM(slackToken);
        currentUser = rtm.getUser();
        dmChannels = rtm.getDmChannels();
        webSocketUrl = rtm.getWebSocketUrl();
        users = rtm.getUsers();
    }

    /**
     * @return user representing the bot.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return list of channel ids where the current user has had conversation.
     */
    public List<String> getDmChannels() {
        return dmChannels;
    }

    public void setDmChannels(List<String> dmChannels) {
        this.dmChannels = dmChannels;
    }

    public boolean addDmChannel(String channelId) {
        if (dmChannels == null) dmChannels = new ArrayList<>();
        return dmChannels.add(channelId);
    }

    /**
     * @return web socket url to connect to.
     */
    public String getWebSocketUrl() {
        return webSocketUrl;
    }

    public void setWebSocketUrl(String webSocketUrl) {
        this.webSocketUrl = webSocketUrl;
    }

    /**
     * @return list of users that are known
     */
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
