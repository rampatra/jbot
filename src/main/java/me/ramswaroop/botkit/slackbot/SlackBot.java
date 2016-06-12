package me.ramswaroop.botkit.slackbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.botkit.slackbot.core.Bot;
import me.ramswaroop.botkit.slackbot.core.Controller;
import me.ramswaroop.botkit.slackbot.core.EventType;
import me.ramswaroop.botkit.slackbot.core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

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

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info("Message: {}", message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(message.getPayload(), Message.class);
            if ("message".equals(msg.getType())) {
                session.sendMessage(new TextMessage("{\n" +
                        "    \"id\": 1,\n" +
                        "    \"type\": \"message\",\n" +
                        "    \"channel\": \"" + msg.getChannel() + "\",\n" +
                        "    \"text\": \"Hello world\"\n" +
                        "}"));
            }
        } catch (Exception e) {
            logger.error("Error", e);
        }

    }
}
