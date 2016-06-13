package me.ramswaroop.botkit.slackbot.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ramswaroop on 05/06/2016.
 */
public abstract class Bot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private static final String RTM_ENDPOINT = "https://slack.com/api/rtm.start?token={token}&simple_latest&no_unreads";

    private String webSocketUrl;

    private String[] dmChannels;

    public abstract String getSlackToken();

    public abstract Bot getSlackBot();

    public void afterConnectionEstablished(WebSocketSession session) {
        logger.debug("WebSocket connected: {}", session);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("WebSocket closed: {}, Close Status: {}", session, status.toString());
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport Error: {}", exception);
    }

    public final void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    public final void reply(WebSocketSession session, Message message, String reply) {
        try {
            message.setId(1);
            message.setText(reply);
            message.setUser(null);
            session.sendMessage(new TextMessage(message.toJSONString()));
        } catch (IOException e) {
            logger.error("Error sending message: {}. Exception: {}", message.getText(), e.getMessage());
        }
    }

    /**
     * TODO: For sending attachments via chat.postMessage
     * {@see: https://api.slack.com/docs/attachments}
     */
    public final void reply(Attachment attachment) {

    }

    private void startRTM() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // Custom Deserializers
            List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
            mapperBuilder.deserializerByType(RTM.class, new JsonDeserializer<RTM>() {
                @Override
                public RTM deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

                    return null;
                }
            });
            jsonConverter.setObjectMapper(mapperBuilder.build());
            httpMessageConverters.add(jsonConverter);
            restTemplate.setMessageConverters(httpMessageConverters);

            ResponseEntity<RTM> response = restTemplate.getForEntity(RTM_ENDPOINT, RTM.class, getSlackToken());
            webSocketUrl = response.getBody().getUrl();
            dmChannels = response.getBody().getDmChannels();
            logger.debug("RTM connection successful. WebSocket URL: {}", webSocketUrl);
        } catch (RestClientException e) {
            logger.error("RTM connection error. Exception: {}", e.getMessage());
        }
    }

    private StandardWebSocketClient client() {
        return new StandardWebSocketClient();
    }

    private SlackWebSocketHandler handler() {
        return new SlackWebSocketHandler(getSlackBot());
    }

    @PostConstruct
    private final void startWebSocketConnection() {
        startRTM();
        WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), webSocketUrl);
        manager.start();
    }
}
