package me.ramswaroop.botkit.slackbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

/**
 * @author ramswaroop
 * @version 21/06/2016
 */
@Controller
public class SlackWebhooks {
    
    @Value("${slackWebhookToken}")
    private String slackToken;
    
    
}
