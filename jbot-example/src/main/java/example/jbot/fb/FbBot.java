package example.jbot.fb;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Button;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import me.ramswaroop.jbot.core.facebook.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

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
    public void onReceiveSimpleMessage(Event event) {
        reply(event, "Hello, I am Jbot.");
    }

    @Controller(events = EventType.MESSAGE, pattern = "(how are you?)")
    public void onReceivePatternMessage(Event event) {
        Button[] quickReplies = new Button[]{new Button().setTitle(""), new Button().setTitle("")};
        reply(event, new Message().setText("I am good. How about you?").setQuickReplies(quickReplies));
    }
}
