package me.ramswaroop.jbot.core.common;

import me.ramswaroop.jbot.core.slack.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @author ramswaroop
 * @version 05/06/2016
 */
public class BotWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(BotWebSocketHandler.class);

    private Bot bot;

    public BotWebSocketHandler(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        bot.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        bot.handleTextMessage(session, message);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        logger.error("Binary messages are not supported in Slack RTM API");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        bot.afterConnectionClosed(session, status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        bot.handleTransportError(session, exception);
    }
}