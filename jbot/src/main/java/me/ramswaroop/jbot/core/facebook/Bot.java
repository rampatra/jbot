package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.BaseBot;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.facebook.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author ramswaroop
 * @version 11/09/2016
 */
public abstract class Bot extends BaseBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Value("${fbSubscribeUrl}")
    private String subscribeUrl;

    @Value("${fbSendUrl")
    private String fbSendUrl;

    /**
     * Class extending this must implement this as it's
     * required to setup the webhook.
     *
     * @return facebook token
     */
    public abstract String getFbToken();

    /**
     * Class extending this must implement this as it's
     * required for Send API.
     * 
     * @return facebook page access token
     */
    public abstract String getPageAccessToken();

    /**
     * 
     * @param event
     * @return
     */
    @ResponseBody
    @GetMapping("/webhook")
    public ResponseEntity<String> setupWebhook(Event event) {
        if (EventType.SUBSCRIBE.name().equalsIgnoreCase(event.getMode()) && getFbToken().equals(event.getToken())) {
            return ResponseEntity.ok(event.getChallenge());
        } else if (EventType.SUBSCRIBE.name().equalsIgnoreCase(event.getMode()) && !getFbToken().equals(event.getToken())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else if (event.getMessage() != null) {
            event.setType(EventType.MESSAGE);
            invokeMethods(event);
            
        }
    }

    /**
     * Invoke the methods with matching {@link Controller#events()}
     * and {@link Controller#pattern()} in events received from Slack.
     *
     * @param event
     */
    private void invokeMethods(Event event) {
        try {
            List<MethodWrapper> methodWrappers = eventToMethodsMap.get(event.getType().name().toUpperCase());
            if (methodWrappers == null) return;

            methodWrappers = new ArrayList<>(methodWrappers);
            MethodWrapper matchedMethod = getMethodWithMatchingPatternAndFilterUnmatchedMethods(event.getMessage().getText(), methodWrappers);
            if (matchedMethod != null) {
                methodWrappers = new ArrayList<>();
                methodWrappers.add(matchedMethod);
            }

            if (methodWrappers != null) {
                for (MethodWrapper methodWrapper : methodWrappers) {
                    Method method = methodWrapper.getMethod();
                    if (Arrays.asList(method.getParameterTypes()).contains(Matcher.class)) {
                        method.invoke(this, event, methodWrapper.getMatcher());
                    } else {
                        method.invoke(this, event);
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
                    if (eventType.name().equals(event.getType().name().toUpperCase())) {
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
     * 
     * @return
     */
    public boolean subscribeAppToPage() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("access_token", getPageAccessToken());
            restTemplate.postForEntity(subscribeUrl, params, String.class);
            return true;
        } catch (Exception e) {
            logger.error("Error subscribing fb app to page: ", e);
            return false;
        }
    }
}
