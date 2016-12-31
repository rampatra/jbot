package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
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

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author ramswaroop
 * @version 11/09/2016
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
     * 
     * @param mode
     * @param token
     * @param challenge
     * @return
     */
    @ResponseBody
    @GetMapping("/webhook")
    public ResponseEntity<String> setupWebhook(@RequestParam("hub.mode") String mode,
                                               @RequestParam("hub.verify_token") String token,
                                               @RequestParam("hub.challenge") String challenge) {
        if (EventType.SUBSCRIBE.name().equalsIgnoreCase(mode) && getFbToken().equals(token)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    /**
     * 
     * @return
     */
    protected boolean subscribeAppToPage() {
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
