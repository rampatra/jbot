package example.jbot.slack;

import org.springframework.web.socket.WebSocketSession;

import java.util.regex.Matcher;

import me.ramswaroop.jbot.core.slack.Controller;
import me.ramswaroop.jbot.core.slack.EventType;
import me.ramswaroop.jbot.core.slack.Handler;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;

/**
 * External (non Bot) controller. Be sure to register yourself in Bot
 *
 * @author Sergey Tarabukin
 */
@Handler(SlackBot.class)
public class HelpController {

    /**
     * Invoked when bot receives an event of type message with text satisfying the pattern
     * {@code (help)}.
     *
     * @param session
     * @param event
     */
    @Controller(events = EventType.MESSAGE,
            pattern = "^(help)$")
    public void onReceiveHelpMessage(SlackBot bot, WebSocketSession session,
        Event event, Matcher matcher) {

        bot.reply(session, event,
            new Message("I can't help you now. Try later"));
    }

}
