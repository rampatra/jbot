package me.ramswaroop.jbot.core.slack;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Maciej Walkowiak
 * @version 14/04/2018
 */
@ConfigurationProperties("jbot.slack")
public class SlackProperties {

    /**
     * Endpoint for RTM.start()
     */
    private String rtmUrl;

    /**
     * Slack token from application.properties file. You can get your slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    private String slackBotToken;

    /**
     * The token you get while creating a new Slash Command. You
     * should paste the token in application.properties file.
     */
    private String slashCommandToken;

    /**
     * The Url you get while configuring a new incoming webhook
     * on Slack. You can setup a new incoming webhook
     * <a href="https://my.slack.com/services/new/incoming-webhook/">here</a>.
     */
    private String slackIncomingWebhookUrl;

    public String getRtmUrl() {
        return rtmUrl;
    }

    public void setRtmUrl(String rtmUrl) {
        this.rtmUrl = rtmUrl;
    }

    public String getSlackBotToken() {
        return slackBotToken;
    }

    public void setSlackBotToken(String slackBotToken) {
        this.slackBotToken = slackBotToken;
    }

    public String getSlashCommandToken() {
        return slashCommandToken;
    }

    public void setSlashCommandToken(String slashCommandToken) {
        this.slashCommandToken = slashCommandToken;
    }

    public String getSlackIncomingWebhookUrl() {
        return slackIncomingWebhookUrl;
    }

    public void setSlackIncomingWebhookUrl(String slackIncomingWebhookUrl) {
        this.slackIncomingWebhookUrl = slackIncomingWebhookUrl;
    }
}
