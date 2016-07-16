package me.ramswaroop.botkit.slackbot.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.botkit.slackbot.core.models.Event;
import me.ramswaroop.botkit.slackbot.core.models.Message;
import me.ramswaroop.botkit.slackbot.core.models.RTM;
import me.ramswaroop.botkit.slackbot.core.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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
     * Endpoint for RTM.start()
     */
    @Value("${rtmUrl}")
    private String rtmUrl;
    /**
     * A Map of all methods annotated with {@link Controller}.
     */
    private final Map<String, List<MethodWrapper>> eventToMethodMap = new HashMap<>();
    /**
     * Websocket url to connect to.
     */
    private String webSocketUrl;
    /**
     * Current logged in user.
     */
    protected User currentUser;
    /**
     * List of channel ids to determine direct messages.
     */
    protected List<String> dmChannels;

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

    // Construct a map of all the controller methods to handle RTM Events. 
    {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Controller.class)) {
                Controller controller = method.getAnnotation(Controller.class);
                EventType[] eventTypes = controller.events();
                String pattern = controller.pattern();

                for (EventType eventType : eventTypes) {
                    List<MethodWrapper> methodWrappers = eventToMethodMap.get(eventType.name());

                    if (methodWrappers == null) {
                        methodWrappers = new ArrayList<>();
                    }

                    MethodWrapper methodWrapper = new MethodWrapper();
                    methodWrapper.setMethod(method);
                    methodWrapper.setPattern(pattern);
                    methodWrappers.add(methodWrapper);

                    eventToMethodMap.put(eventType.name(), methodWrappers);
                }
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
                    dmChannels.add(event.getChannelId());
                } else if (event.getType().equalsIgnoreCase(EventType.MESSAGE.name())) {
                    if (event.getText() != null && event.getText().contains(currentUser.getId())) { // direct mention
                        event.setType(EventType.DIRECT_MENTION.name());
                    } else if (dmChannels.contains(event.getChannelId())) { // direct message
                        event.setType(EventType.DIRECT_MESSAGE.name());
                    }
                }
            } else { // slack does not send any TYPE for acknowledgement messages
                event.setType(EventType.ACK.name());
            }
            invokeMethods(session, event);
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
     * Invoke the methods with matching {@link Controller#events()}
     * and {@link Controller#pattern()} in events received from Slack.
     *
     * @param session
     * @param event
     */
    private void invokeMethods(WebSocketSession session, Event event) {
        try {
            List<MethodWrapper> methodWrappers = eventToMethodMap.get(event.getType().toUpperCase());
            if (methodWrappers == null) return;
            
            List<MethodWrapper> newMethodWrappers = new ArrayList<>(methodWrappers);
            filterMethodsBasedOnPattern(event, newMethodWrappers);
            
            if (newMethodWrappers != null) {
                for (MethodWrapper methodWrapper : newMethodWrappers) {
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
     * Filter methods whose {@link Controller#pattern()} match the {@link Event#text}
     * in events received from Slack.
     * 
     * @param event
     * @param methodWrappers
     */
    private void filterMethodsBasedOnPattern(Event event, List<MethodWrapper> methodWrappers) {
        if (methodWrappers == null) return;
        
        Iterator<MethodWrapper> listIterator = methodWrappers.listIterator();
        while (listIterator.hasNext()) {
            MethodWrapper methodWrapper = listIterator.next();
            String pattern = methodWrapper.getPattern();
            String text = event.getText();
            
            if (!StringUtils.isEmpty(pattern) && !StringUtils.isEmpty(text)) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(text);
                if (!m.find()) {
                    listIterator.remove();
                    continue;
                }
                methodWrapper.setMatcher(m);
            }
        }
    }
    
    /**
     * Fetch the web socket url to connect to and
     * also constructs the RTM object.
     */
    private void startRTM() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // Custom Deserializers
            List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
            mapperBuilder.deserializerByType(RTM.class, new JsonDeserializer<RTM>() {
                @Override
                public RTM deserialize(JsonParser p, DeserializationContext ctxt) {
                    try {
                        JsonNode node = p.readValueAsTree();
                        RTM rtm = new RTM();
                        rtm.setUrl(node.get("url").asText());
                        rtm.setUser(new ObjectMapper().treeToValue(node.get("self"), User.class));
                        List<String> dmChannels = new ArrayList<>();
                        Iterator<JsonNode> iterator = node.get("ims").iterator();
                        while (iterator.hasNext()) {
                            dmChannels.add(iterator.next().get("id").asText());
                        }
                        rtm.setDmChannels(dmChannels);
                        return rtm;
                    } catch (Exception e) {
                        logger.error("Error de-serializing RTM.start(): ", e);
                        return null;
                    }
                }
            });
            jsonConverter.setObjectMapper(mapperBuilder.build());
            httpMessageConverters.add(jsonConverter);
            restTemplate.setMessageConverters(httpMessageConverters);

            ResponseEntity<RTM> response = restTemplate.getForEntity(rtmUrl, RTM.class, getSlackToken());
            if (response.getBody() != null) {
                webSocketUrl = response.getBody().getUrl();
                dmChannels = response.getBody().getDmChannels();
                currentUser = response.getBody().getUser();
                logger.debug("RTM connection successful. WebSocket URL: {}", webSocketUrl);
            } else {
                logger.debug("RTM response invalid. Response: {}", response);
            }
        } catch (RestClientException e) {
            logger.error("RTM connection error. Exception: {}", e.getMessage());
        }
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
        startRTM(); // fetches the web socket url
        if (webSocketUrl != null) {
            WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), webSocketUrl);
            manager.start();
        } else {
            logger.error("No websocket url returned by Slack.");
        }
    }

    private class MethodWrapper {
        private Method method;
        private String pattern;
        private Matcher matcher;

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
    }
}
