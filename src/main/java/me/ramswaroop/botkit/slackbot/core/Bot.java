package me.ramswaroop.botkit.slackbot.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.botkit.slackbot.core.models.*;
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
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ramswaroop
 * @version 05/06/2016
 */
public abstract class Bot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    /**
     * Endpoint for RTM.start()
     */
    private static final String RTM_ENDPOINT = "https://slack.com/api/rtm.start?token={token}&simple_latest&no_unreads";
    /**
     * A Map for all controller methods.
     */
    private final Map<String, Method> methodMap = new HashMap<>();
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
     * the {@link SlackWebSocketHandler} class.
     * 
     * @return
     */
    public abstract Bot getSlackBot();

    /**
     * Constructs a map of all the controller methods to handle RTM Events.
     */
    {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Controller.class)) {
                Controller controller = method.getAnnotation(Controller.class);
                EventType[] eventTypes = controller.events();
                for (EventType eventType : eventTypes) {
                    methodMap.put(eventType.name(), method);
                }
            }
        }
    }

    /**
     * Invoked after a successful web socket connection is 
     * established. You can override this method in the child classes.
     * 
     * @see WebSocketHandler#afterConnectionEstablished
     * @param session
     */
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.debug("WebSocket connected: {}", session);
    }

    /**
     * Invoked after the web socket connection is closed.
     * You can override this method in the child classes.
     * 
     * @see WebSocketHandler#afterConnectionClosed
     * @param session
     * @param status
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("WebSocket closed: {}, Close Status: {}", session, status.toString());
    }

    /**
     * Handles an error from the underlying WebSocket message transport.
     * 
     * @see WebSocketHandler#handleTransportError
     * @param session
     * @param exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("Transport Error: {}", exception);
    }

    /**
     * 
     * @param session
     * @param textMessage
     * @throws Exception
     */
    public final void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Event event = mapper.readValue(textMessage.getPayload(), Event.class);
        if (event.getType() != null && event.getType().equalsIgnoreCase(EventType.IM_OPEN.name())) {
            dmChannels.add(event.getChannelId());
        } else if (event.getType() != null && event.getType().equalsIgnoreCase(EventType.MESSAGE.name())) {
            if (event.getText().contains(currentUser.getId())) { // direct mention
                event.setType(EventType.DIRECT_MENTION.name());
            } else if (dmChannels.contains(event.getChannelId())) { // direct message
                event.setType(EventType.DIRECT_MESSAGE.name());
            }
        }
        invokeMethods(session, event);
    }

    /**
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
        } catch (IOException e) {
            logger.error("Error sending event: {}. Exception: {}", event.getText(), e.getMessage());
        }
    }

    // TODO
    public final void reply(String url, Message message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, message, Message.class);
    }

    // TODO
    public final void reply(String url, Attachment attachment) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(url, attachment, Attachment.class);
    }

    /**
     * 
     * @param message
     * @return
     */
    private String encode(String message) {
        return message.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * 
     * @param session
     * @param event
     */
    private void invokeMethods(WebSocketSession session, Event event) {
        try {
            Method method = methodMap.get(event.getType().toUpperCase());
            if (method != null) {
                method.invoke(this, session, event);
            }
        } catch (Exception e) {
            logger.error("Error invoking controller: {}", e.getMessage());
        }
    }

    /**
     * Fetches the web socket url to connect to and 
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
                    } catch (IOException e) {
                        logger.error("Error de-serializing RTM.start(): {}", e.getMessage());
                        return null;
                    }
                }
            });
            jsonConverter.setObjectMapper(mapperBuilder.build());
            httpMessageConverters.add(jsonConverter);
            restTemplate.setMessageConverters(httpMessageConverters);

            ResponseEntity<RTM> response = restTemplate.getForEntity(RTM_ENDPOINT, RTM.class, getSlackToken());
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

    private SlackWebSocketHandler handler() {
        return new SlackWebSocketHandler(getSlackBot());
    }

    /**
     * Entry point where the web socket connection starts
     * and after which your bot becomes live.
     */
    @PostConstruct
    private void startWebSocketConnection() {
        startRTM(); // fetches the web socket url
        WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), webSocketUrl);
        manager.start();
    }
}
