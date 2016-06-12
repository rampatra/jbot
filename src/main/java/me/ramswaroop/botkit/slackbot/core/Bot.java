package me.ramswaroop.botkit.slackbot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by ramswaroop on 05/06/2016.
 */
public abstract class Bot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private static final String RTM_ENDPOINT = "https://slack.com/api/rtm.start?token={token}&simple_latest&no_unreads";

    private String webSocketUrl;

    private String[] dmChannels;

    @Autowired
    private RestTemplate restTemplate;

    public abstract String getSlackToken();

    public abstract Bot getSlackBot();

    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("WebSocket connected: {}", session);
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("WebSocket closed: {}, Close Status: {}", session, status.toString());
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport Error: {}", exception);
    }

    public final void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    };

    public final void reply(WebSocketSession session, Message message) {
        try {
            session.sendMessage(new TextMessage(message.toString()));
        } catch (IOException e) {
            logger.error("Error sending message: {}", message.getText());
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
            ResponseEntity<RTM> response = restTemplate.getForEntity(RTM_ENDPOINT, RTM.class, getSlackToken());
            webSocketUrl = response.getBody().getUrl();
            dmChannels = response.getBody().getDmChannels();
            logger.info("RTM connection successful. WebSocket URL: {}", webSocketUrl);
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

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
