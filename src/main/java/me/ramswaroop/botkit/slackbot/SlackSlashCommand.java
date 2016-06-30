package me.ramswaroop.botkit.slackbot;

import me.ramswaroop.botkit.slackbot.core.models.Attachment;
import me.ramswaroop.botkit.slackbot.core.models.RichMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample Slash Command Handler.
 *
 * @author ramswaroop
 * @version 1.0.0, 20/06/2016
 */
@RestController
public class SlackSlashCommand {

    /**
     * The token you get while creating a new Slash Command. You
     * should paste the token in application.properties file.
     */
    @Value("${slashCommandToken}")
    private String slackToken;


    /**
     * Slash Command handler. When a user types for example "/app help"
     * then slack sends a POST request to this endpoint. So, this endpoint
     * should match the url you set while creating the Slack Slash Command.
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @param responseUrl
     * @return
     */
    @RequestMapping(value = "/slash-command",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
                                             @RequestParam("team_id") String teamId,
                                             @RequestParam("team_domain") String teamDomain,
                                             @RequestParam("channel_id") String channelId,
                                             @RequestParam("channel_name") String channelName,
                                             @RequestParam("user_id") String userId,
                                             @RequestParam("user_name") String userName,
                                             @RequestParam("command") String command,
                                             @RequestParam("text") String text,
                                             @RequestParam("response_url") String responseUrl) {
        // validate token
        if (!token.equals(slackToken)) {
            return new RichMessage("Sorry! You're not lucky enough to use our slack command.");
        }

        /** build response */
        RichMessage richMessage = new RichMessage("The is Slash Commander!");
        richMessage.setResponseType("in_channel");
        // set attachments
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText("I will perform all tasks for you.");
        richMessage.setAttachments(attachments);
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }
}
