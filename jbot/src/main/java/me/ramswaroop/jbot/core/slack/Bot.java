package me.ramswaroop.jbot.core.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.common.BaseBot;
import me.ramswaroop.jbot.core.common.BotWebSocketHandler;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Base class for making Slack Bots. Any class extending
 * this will get all powers of a Slack Bot.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
public abstract class Bot extends BaseBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    /**
     * Service to access Slack APIs.
     */
    @Autowired
    protected SlackService slackService;

    /**
     * Class extending this must implement this as it's
     * required to make the initial RTM.start() call.
     *
     * @return
     */
    public abstract String getSlackToken();

    /**
     * An instance of the Bot is required by
     * the {@link BotWebSocketHandler} class.
     *
     * @return
     */
    public abstract Bot getSlackBot();

    /**
     * Invoked after a successful web socket connection is
     * established. You can override this method in the child classes.
     *
     * @param session
     * @see WebSocketHandler#afterConnectionEstablished
     */
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.debug("WebSocket connected: {}", session);
    }

    /**
     * Invoked after the web socket connection is closed.
     * You can override this method in the child classes.
     *
     * @param session
     * @param status
     * @see WebSocketHandler#afterConnectionClosed
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("WebSocket closed: {}, Close Status: {}", session, status.toString());
    }

    /**
     * Handle an error from the underlying WebSocket message transport.
     *
     * @param session
     * @param exception
     * @see WebSocketHandler#handleTransportError
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport Error: {}", exception);
    }

    /**
     * Invoked when a new Slack event(WebSocket text message) arrives.
     *
     * @param session
     * @param textMessage
     * @throws Exception
     */
    public final void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Event event = mapper.readValue(textMessage.getPayload(), Event.class);
            if (event.getType() != null) {
                if (event.getType().equalsIgnoreCase(EventType.IM_OPEN.name())) {
                    slackService.addDmChannel(event.getChannelId());
                } else if (event.getType().equalsIgnoreCase(EventType.MESSAGE.name())) {
                    if (event.getText() != null && event.getText().contains(slackService.getCurrentUser().getId())) { // direct mention
                        event.setType(EventType.DIRECT_MENTION.name());
                    } else if (slackService.getDmChannels().contains(event.getChannelId())) { // direct message
                        event.setType(EventType.DIRECT_MESSAGE.name());
                    }
                }
            } else { // slack does not send any TYPE for acknowledgement messages
                event.setType(EventType.ACK.name());
            }

            if (isConversationOn(event)) {
                invokeChainedMethod(session, event);
            } else {
                invokeMethods(session, event);
            }
        } catch (Exception e) {
            logger.error("Error handling response from Slack: {}. \nException: ", textMessage.getPayload(), e);
        }
    }

    /**
     * Method to send a reply back to Slack after receiving an {@link Event}.
     * Learn <a href="https://api.slack.com/rtm">more on sending responses to Slack.</a>
     *
     * @param session
     * @param event
     * @param reply
     */
    public final void reply(WebSocketSession session, Event event, Message reply) {
        try {
            reply.setType(EventType.MESSAGE.name().toLowerCase());
            reply.setText(encode(reply.getText()));
            if (reply.getChannel() == null && event.getChannelId() != null) {
                reply.setChannel(event.getChannelId());
            }
            session.sendMessage(new TextMessage(reply.toJSONString()));
            if (logger.isDebugEnabled()) {  // For debugging purpose only
                logger.debug("Reply (Message): {}", reply.toJSONString());
            }
        } catch (IOException e) {
            logger.error("Error sending event: {}. Exception: {}", event.getText(), e.getMessage());
        }
    }

    /**
     * Call this method to start a conversation.
     *
     * @param event
     */
    public final void startConversation(Event event, String methodName) {
        startConversation(event.getChannelId(), methodName);
    }

    /**
     * Call this method to jump to the next method in a conversation.
     *
     * @param event
     */
    public final void nextConversation(Event event) {
        nextConversation(event.getChannelId());
    }

    /**
     * Call this method to stop the end the conversation.
     *
     * @param event
     */
    public final void stopConversation(Event event) {
        stopConversation(event.getChannelId());
    }

    /**
     * Check whether a conversation is up in a particular slack channel.
     *
     * @param event
     * @return true if a conversation is on, false otherwise.
     */
    public final boolean isConversationOn(Event event) {
        return isConversationOn(event.getChannelId());
    }

    /**
     * Invoke the methods with matching {@link Controller#events()}
     * and {@link Controller#pattern()} in events received from Slack.
     *
     * @param session
     * @param event
     */
    private void invokeMethods(WebSocketSession session, Event event) {
        invokeMethods(event, new Object[]{session, event}); // order of arguments must match that of controller method
    }

    /**
     * Invoke the appropriate method in a conversation.
     *
     * @param session
     * @param event
     */
    private void invokeChainedMethod(WebSocketSession session, Event event) {
        invokeChainedMethod(event.getChannelId(), event, new Object[]{session, event}); // order of arguments must 
                                                                                        // match that of controller method
    }

    /**
     * Encode the text before sending to Slack.
     * Learn <a href="https://api.slack.com/docs/formatting">more on message formatting in Slack</a>
     *
     * @param message
     * @return encoded text.
     */
    private String encode(String message) {
        return message == null ? null : message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private StandardWebSocketClient client() {
        return new StandardWebSocketClient();
    }

    private BotWebSocketHandler handler() {
        return new BotWebSocketHandler(getSlackBot());
    }

    /**
     * Entry point where the web socket connection starts
     * and after which your bot becomes live.
     */
    @PostConstruct
    private void startWebSocketConnection() {
        slackService.startRTM(getSlackToken());
        if (slackService.getWebSocketUrl() != null) {
            WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), slackService.getWebSocketUrl());
            manager.start();
        } else {
            logger.error("No websocket url returned by Slack.");
        }
    }
}
