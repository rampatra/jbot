package example.jbot.slack;

import java.util.regex.Matcher;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

/**
 * External (non Bot) controller. Be sure to register yourself in Bot
 *
 * @author Sergey Tarabukin
 */
@Component
public class HelpController {

    @Autowired
    private SlackBot bot;

    /**
     * Register controller
     */
    @PostConstruct
    public void init() {

        bot.registerController(this);
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying the pattern
     * {@code (help)}.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE,
            pattern = "^(help)$")
    public void onReceiveHelpMessage(SlackBot bot, WebSocketSession session, Event event,
            Matcher matcher) {

        bot.reply(session, event, new Message("I can't help you now. Try later"));
    }

}
