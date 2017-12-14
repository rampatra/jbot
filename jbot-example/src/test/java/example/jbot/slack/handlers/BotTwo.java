package example.jbot.slack.handlers;
import org.springframework.stereotype.Component;

import me.ramswaroop.jbot.core.slack.Bot;
/**
 * @author Sergey Tarabukin
 */
@Component
public class BotTwo extends Bot {
    @Override
    public String getSlackToken() {
        return "";
    }
    @Override
    public Bot getSlackBot() {
        return this;
    }
}
