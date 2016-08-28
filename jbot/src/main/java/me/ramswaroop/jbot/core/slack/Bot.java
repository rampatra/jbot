package me.ramswaroop.jbot.core.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for making Slack Bots. Any class extending
 * this will get all powers of a Slack Bot.
 *
 * @author ramswaroop
 * @version 1.0.0, 05/06/2016
 */
public abstract class Bot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    /**
     * A Map of all methods annotated with {@link Controller} where key is the {@link EventType#name()} and
     * value is a list of {@link MethodWrapper}. NOTE: It does not contain methods which are part of any
     * conversation.
     */
    private final Map<String, List<MethodWrapper>> eventToMethodsMap = new HashMap<>();
    /**
     * A Map of all methods annotated with {@link Controller} where key is the {@link Method#getName()} and
     * value is the respective {@link MethodWrapper}.
     */
    private final Map<String, MethodWrapper> methodNameMap = new HashMap<>();
    /**
     * A List of names of the methods which are part of any conversation.
     */
    private final List<String> conversationMethodNames = new ArrayList<>();
    /**
     * A List of Queues with each Queue holding all methods for a particular conversation. Methods
     * can be chained into a conversation by {@link Controller#next()}.
     */
    private final Map<String, Queue<MethodWrapper>> conversationQueueMap = new HashMap<>();

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
     * Construct a map of all the controller methods to handle RTM Events.
     */
    public Bot() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Controller.class)) {
                Controller controller = method.getAnnotation(Controller.class);
                EventType[] eventTypes = controller.events();
                String pattern = controller.pattern();
                String next = controller.next();

                if (!StringUtils.isEmpty(next)) {
                    conversationMethodNames.add(next);
                }

                MethodWrapper methodWrapper = new MethodWrapper();
                methodWrapper.setMethod(method);
                methodWrapper.setPattern(pattern);
                methodWrapper.setNext(next);

                if (!conversationMethodNames.contains(method.getName())) {
                    for (EventType eventType : eventTypes) {
                        List<MethodWrapper> methodWrappers = eventToMethodsMap.get(eventType.name());

                        if (methodWrappers == null) {
                            methodWrappers = new ArrayList<>();
                        }

                        methodWrappers.add(methodWrapper);
                        eventToMethodsMap.put(eventType.name(), methodWrappers);
                    }
                }
                methodNameMap.put(method.getName(), methodWrapper);
            }
        }
    }

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
     * Call this method to start a conversation.
     *
     * @param event
     */
    public void startConversation(Event event, String methodName) {
        String channelId = event.getChannelId();

        if (!StringUtils.isEmpty(channelId)) {
            Queue<MethodWrapper> queue = formConversationQueue(new LinkedList<>(), methodName);
            conversationQueueMap.put(channelId, queue);
        }
    }

    /**
     * Call this method to jump to the next method in a conversation.
     *
     * @param event
     */
    public void nextConversation(Event event) {
        Queue<MethodWrapper> queue = conversationQueueMap.get(event.getChannelId());
        if (queue != null) queue.poll();
    }

    /**
     * Call this method to stop the end the conversation.
     *
     * @param event
     */
    public void stopConversation(Event event) {
        conversationQueueMap.remove(event.getChannelId());
    }

    /**
     * Check whether a conversation is up in a particular slack channel.
     *
     * @param event
     * @return true if a conversation is on, false otherwise.
     */
    public boolean isConversationOn(Event event) {
        return conversationQueueMap.get(event.getChannelId()) != null;
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
     * Encode the text before sending to Slack.
     * Learn <a href="https://api.slack.com/docs/formatting">more on message formatting in Slack</a>
     *
     * @param message
     * @return encoded text.
     */
    private String encode(String message) {
        return message == null ? null : message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * Form a Queue with all the methods responsible for a particular conversation.
     *
     * @param queue
     * @param methodName
     * @return
     */
    private Queue<MethodWrapper> formConversationQueue(Queue<MethodWrapper> queue, String methodName) {
        MethodWrapper methodWrapper = methodNameMap.get(methodName);
        queue.add(methodWrapper);
        if (StringUtils.isEmpty(methodName)) {
            return queue;
        } else {
            return formConversationQueue(queue, methodWrapper.getNext());
        }
    }

    /**
     * Invoke the methods with matching {@link Controller#events()}
     * and {@link Controller#pattern()} in events received from Slack.
     *
     * @param session
     * @param event
     */
    private void invokeMethods(WebSocketSession session, Event event) {
        try {
            List<MethodWrapper> methodWrappers = eventToMethodsMap.get(event.getType().toUpperCase());
            if (methodWrappers == null) return;

            methodWrappers = new ArrayList<>(methodWrappers);
            MethodWrapper matchedMethod = getMethodWithMatchingPatternAndFilterUnmatchedMethods(event, methodWrappers);
            if (matchedMethod != null) {
                methodWrappers = new ArrayList<>();
                methodWrappers.add(matchedMethod);
            }

            if (methodWrappers != null) {
                for (MethodWrapper methodWrapper : methodWrappers) {
                    Method method = methodWrapper.getMethod();
                    if (method.getParameterCount() == 3) {
                        method.invoke(this, session, event, methodWrapper.getMatcher());
                    } else {
                        method.invoke(this, session, event);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error invoking controller: ", e);
        }
    }

    /**
     * Invoke the appropriate method in a conversation.
     *
     * @param session
     * @param event
     */
    private void invokeChainedMethod(WebSocketSession session, Event event) {
        Queue<MethodWrapper> queue = conversationQueueMap.get(event.getChannelId());

        if (queue != null && !queue.isEmpty()) {
            MethodWrapper methodWrapper = queue.peek();

            try {
                EventType[] eventTypes = methodWrapper.getMethod().getAnnotation(Controller.class).events();
                for (EventType eventType : eventTypes) {
                    if (eventType.name().equals(event.getType().toUpperCase())) {
                        methodWrapper.getMethod().invoke(this, session, event);
                        return;
                    }
                }
            } catch (Exception e) {
                logger.error("Error invoking chained method: ", e);
            }
        }
    }

    /**
     * Search for a method whose {@link Controller#pattern()} match with the {@link Event#text}
     * in events received from Slack and also filter out the methods whose {@link Controller#pattern()} do not
     * match with slack message received ({@link Event#text}) for cases where there are no matched methods.
     *
     * @param event
     * @param methodWrappers
     * @return the MethodWrapper whose method pattern match with that of the slack message received, {@code null} if no
     * such method is found.
     */
    private MethodWrapper getMethodWithMatchingPatternAndFilterUnmatchedMethods(Event event, List<MethodWrapper> methodWrappers) {
        if (methodWrappers != null) {
            Iterator<MethodWrapper> listIterator = methodWrappers.listIterator();

            while (listIterator.hasNext()) {
                MethodWrapper methodWrapper = listIterator.next();
                String pattern = methodWrapper.getPattern();
                String text = event.getText();

                if (!StringUtils.isEmpty(pattern) && !StringUtils.isEmpty(text)) {
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(text);
                    if (m.find()) {
                        methodWrapper.setMatcher(m);
                        return methodWrapper;
                    } else {
                        listIterator.remove();  // remove methods from the original list whose pattern do not match
                    }
                }
            }
        }
        return null;
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

    /**
     * Wrapper class for methods annotated with {@link Controller}.
     */
    private class MethodWrapper {
        private Method method;
        private String pattern;
        private Matcher matcher;
        private String next;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public Matcher getMatcher() {
            return matcher;
        }

        public void setMatcher(Matcher matcher) {
            this.matcher = matcher;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodWrapper that = (MethodWrapper) o;

            if (!method.equals(that.method)) return false;
            if (pattern != null ? !pattern.equals(that.pattern) : that.pattern != null) return false;
            if (matcher != null ? !matcher.equals(that.matcher) : that.matcher != null) return false;
            return next != null ? next.equals(that.next) : that.next == null;

        }

        @Override
        public int hashCode() {
            int result = method.hashCode();
            result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
            result = 31 * result + (matcher != null ? matcher.hashCode() : 0);
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }
}
