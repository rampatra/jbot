package example.jbot.fb;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import org.springframework.beans.factory.annotation.Value;

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

    @Controller(events = EventType.MESSAGE)
    public void onReceiveMessage(Event event) {
        reply(event, "Hello, I am Jbot.");
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
}
