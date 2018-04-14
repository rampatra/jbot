package me.ramswaroop.jbot.core.facebook;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Maciej Walkowiak
 * @version 14/04/2018
 */
@ConfigurationProperties("jbot.facebook")
public class FacebookProperties {
    private String subscribeUrl;
    private String sendUrl;
    private String messengerProfileUrl;
    private String botToken;
    private String pageAccessToken;

    public String getSubscribeUrl() {
        return subscribeUrl;
    }

    public void setSubscribeUrl(String subscribeUrl) {
        this.subscribeUrl = subscribeUrl;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getMessengerProfileUrl() {
        return messengerProfileUrl;
    }

    public void setMessengerProfileUrl(String messengerProfileUrl) {
        this.messengerProfileUrl = messengerProfileUrl;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getPageAccessToken() {
        return pageAccessToken;
    }

    public void setPageAccessToken(String pageAccessToken) {
        this.pageAccessToken = pageAccessToken;
    }
}
