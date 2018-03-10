package example.jbot.fb;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author ramswaroop
 * @version 17/09/2016
 */
@JBot
public class FbBot extends Bot {

    @Value("${fbBotToken}")
    private String fbToken;

    @Value("${fbPageAccessToken}")
    private String pageAccessToken;

    @Override
    public String getFbToken() {
        return fbToken;
    }

    @Override
    public String getPageAccessToken() {
        return pageAccessToken;
    }

    @PostConstruct
    public void setGetStartedButton() {
        
    }
    
    @Controller(events = EventType.POSTBACK, pattern = "hi")
    public void onReceiveMessage(Event event) {
        reply(event, "Hello, I am JBot.");
    }

    @Controller(events = EventType.MESSAGE, pattern = "(?i)(How are you?)")
    public void onReceivePatternMessage(Event event) {
        Button[] quickReplies = new Button[]{
                new Button().setContentType("text").setTitle("Great").setPayload("good"),
                new Button().setContentType("text").setTitle("Bad").setPayload("bad")
        };
        reply(event, new Message().setText("I am good. How about you?").setQuickReplies(quickReplies));
    }

    @Controller(events = EventType.QUICK_REPLY, pattern = "(?i)(Great|Bad)")
    public void onReceiveQuickReply(Event event) {
        if ("good".equals(event.getMessage().getQuickReply().getPayload())) {
            reply(event, "Nice to hear that!");
        } else {
            reply(event, "Oh, sorry to hear that.");
        }
    }


    
    
    /**
     * Conversations
     */
    
    
    @Controller(pattern = "(setup meeting)", next = "confirmTiming")
    public void setupMeeting(Event event) {
        startConversation(event, "confirmTiming");   // start conversation
        reply(event, "Cool! At what time (ex. 15:30) do you want me to set up the meeting?");
    }

    @Controller(next = "askTimeForMeeting")
    public void confirmTiming(Event event) {
        reply(event, "Your meeting is set at " + event.getMessage().getText() +
                ". Would you like to repeat it tomorrow?");
        nextConversation(event);    // jump to next question in conversation
    }

    @Controller(next = "askWhetherToRepeat")
    public void askTimeForMeeting(Event event) {
        if (event.getMessage().getText().contains("yes")) {
            reply(event, "Okay. Would you like me to set a reminder for you?");
            nextConversation(event);    // jump to next question in conversation  
        } else {
            reply(event, "No problem. You can always schedule one with 'setup meeting' command.");
            stopConversation(event);    // stop conversation only if user says no
        }
    }
    
    @Controller
    public void askWhetherToRepeat(Event event) {
        if (event.getMessage().getText().contains("yes")) {
            reply(event,"Great! I will remind you tomorrow before the meeting.");
        } else {
            reply(event,"Okay, don't forget to attend the meeting tomorrow :)");
        }
        stopConversation(event);    // stop conversation
    }
}
