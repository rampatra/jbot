package example.jbot.fb;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Attachment;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Element;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import me.ramswaroop.jbot.core.facebook.models.Payload;
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
        setGetStartedButton("hi");
    }

    @Controller(events = EventType.POSTBACK, pattern = "hi")
    public void onReceiveMessage(Event event) {
        // quick reply buttons
        Button[] quickReplies = new Button[]{
                new Button().setContentType("text").setTitle("Sure").setPayload("yes"),
                new Button().setContentType("text").setTitle("Nope").setPayload("no")
        };
        reply(event, new Message().setText("Hello, I am JBot. Would you like to see more?").setQuickReplies(quickReplies));
    }

    @Controller(events = EventType.QUICK_REPLY, pattern = "(yes|no)")
    public void onReceiveQuickReply(Event event) {
        if ("yes".equals(event.getMessage().getQuickReply().getPayload())) {
            reply(event, "Type: Show Buttons or Show List");
        } else {
            reply(event, "See you soon!");
        }
    }

    @Controller(events = EventType.MESSAGE, pattern = "(?i)(button)")
    public void showButtons(Event event) {
        Button[] buttons = new Button[]{
                new Button().setType("web_url").setUrl("http://blog.ramswaroop.me").setTitle("JBot Docs"),
                new Button().setType("web_url").setUrl("https://goo.gl/uKrJWX").setTitle("Buttom Template")
        };
        reply(event, new Message().setAttachment(new Attachment().setType("template").setPayload(new Payload()
                .setTemplateType("button").setText("These are 2 link buttons.").setButtons(buttons))));
    }

    @Controller(events = EventType.MESSAGE, pattern = "(?i)(list)")
    public void showList(Event event) {
        Element[] elements = new Element[] {
                new Element().setTitle("").setSubtitle("").setDefaultAction(new Button().setType("")),
                new Element().setTitle("").setSubtitle("").setDefaultAction(new Button().setType("")),
                new Element().setTitle("").setSubtitle("").setDefaultAction(new Button().setType(""))
        };
        reply(event, new Message().setAttachment(new Attachment().setType("template").setPayload(new Payload()
                .setTemplateType("list").setTopElementStyle("compact").setText("These are 2 link buttons.")
                .setElements(elements))));
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
            reply(event, "Great! I will remind you tomorrow before the meeting.");
        } else {
            reply(event, "Okay, don't forget to attend the meeting tomorrow :)");
        }
        stopConversation(event);    // stop conversation
    }
}
