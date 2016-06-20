package me.ramswaroop.botkit.slackbot;

import me.ramswaroop.botkit.slackbot.core.Bot;
import me.ramswaroop.botkit.slackbot.core.Controller;
import me.ramswaroop.botkit.slackbot.core.EventType;
import me.ramswaroop.botkit.slackbot.core.models.Event;
import me.ramswaroop.botkit.slackbot.core.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Example Slack Bot. You can create multiple bots by just
 * extending {@link Bot} class like this one.
 *
 * @author ramswaroop
 * @date 05/06/2016
 */
@Component
public class SlackBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(SlackBot.class);

    @Value("${slackToken}")
    private String slackToken;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, new Message("Hi, I am " + currentUser.getName()));
    }

    @Controller(events = EventType.MESSAGE)
    public void onReceiveMessage(WebSocketSession session, Event event) {
        reply(session, event, new Message("Hi, this is a message!"));
    }

    @RequestMapping("/slash-command")
    public void onReceiveSlashCommand(HttpServletRequest request, HttpServletResponse response) {

    }
}
