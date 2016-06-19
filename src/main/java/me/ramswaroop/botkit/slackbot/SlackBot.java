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

/**
 * Created by ramswaroop on 05/06/2016.
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

    /*@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info("Event: {}", message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Event msg = mapper.readValue(message.getPayload(), Event.class);
            if ("message".equals(msg.getType())) {
                session.sendMessage(new TextMessage("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"type\": \"message\",\n" +
                        "    \"channel\": \"" + msg.getChannelId() + "\",\n" +
                        "    \"text\": \"Hello world\"\n" +
                        "}"));
            }
        } catch (Exception e) {
            logger.error("Error", e);
        }

    }*/

    @Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
    public void onReceiveDM(WebSocketSession session, Event event) {
        reply(session, event, new Message("hi"));
    }
}
