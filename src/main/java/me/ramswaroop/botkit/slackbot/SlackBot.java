package me.ramswaroop.botkit.slackbot;

import me.ramswaroop.botkit.slackbot.core.Bot;
import me.ramswaroop.botkit.slackbot.core.Controller;
import me.ramswaroop.botkit.slackbot.core.EventType;
import me.ramswaroop.botkit.slackbot.core.models.Event;
import me.ramswaroop.botkit.slackbot.core.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.regex.Matcher;

/**
 * Example Slack Bot. You can create multiple bots by just
 * extending {@link Bot} class like this one.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
@Component
public class SlackBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    /**
     * Slack token from application.properties file. You can get your slack token
     * after <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    /**
     * Invoked when the bot receives an event of type direct mention
     * or direct message. NOTE: These two event types are added by botkit
     * to make your task easier, Slack doesn't have any direct way to
     * determine these type of events.
     *
     * @param session
     * @param event
     */
    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, new Message("Hi, I am " + currentUser.getName()));
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying
     * the pattern {@code ([a-zA-Z ]{1,})(\d+)([a-zA-Z ]{1,})}. For example,
     * messages like "I want 5 pizzas", "I have 4 tasks" etc will invoke this 
     * method.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE, pattern = "([a-zA-Z ]{1,})(\\d+)([a-zA-Z ]{1,})")
    public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
        reply(session, event, new Message("First group: " + matcher.group(0) + "\n" +
                "Second group: " + matcher.group(1) + "\n" +
                "Third group: " + matcher.group(2) + "\n" +
                "Fourth group: " + matcher.group(3)));
    }

    /**
     * Invoked when bot receives an event of type file shared.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.FILE_SHARED)
    public void onFileShared(WebSocketSession session, Event event) {
        reply(session, event, new Message("Thanks for sharing the file!"));
    }

}
