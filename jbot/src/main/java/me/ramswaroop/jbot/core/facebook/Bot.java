package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.BaseBot;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.facebook.models.Callback;
import me.ramswaroop.jbot.core.facebook.models.Entry;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;

/**
 * @author ramswaroop
 * @version 11/09/2016
 */
public abstract class Bot extends BaseBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FbService fbService;

    @Value("${fbSubscribeUrl}")
    private String subscribeUrl;

    @Value("${fbSendUrl}")
    private String fbSendUrl;

    @PostConstruct
    private void constructFbSendUrl() {
        this.fbSendUrl = fbSendUrl.replace("{PAGE_ACCESS_TOKEN}", getPageAccessToken());
    }

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
     * @param mode
     * @param verifyToken
     * @param challenge
     * @return
     */
    @GetMapping("/webhook")
    public ResponseEntity setupWebhookVerification(@RequestParam("hub.mode") String mode,
                                                   @RequestParam("hub.verify_token") String verifyToken,
                                                   @RequestParam("hub.challenge") String challenge) {
        if (EventType.SUBSCRIBE.name().equalsIgnoreCase(mode) && getFbToken().equals(verifyToken)) {
            return ResponseEntity.ok(challenge);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Add webhook endpoint
     *
     * @param callback
     * @return
     */
    @ResponseBody
    @PostMapping("/webhook")
    public ResponseEntity setupWebhookEndpoint(@RequestBody Callback callback) {
        try {
            // Checks this is an event from a page subscription
            if (!callback.getObject().equals("page")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            for (Entry entry : callback.getEntry()) {
                for (Event event : entry.getMessaging()) {
                    if (event.getMessage() != null) {
                        if (event.getMessage().isEcho() != null &&
                                event.getMessage().isEcho() &&
                                event.getMessage().getAppId() != null) {
                            event.setType(EventType.MESSAGE_ECHO);
                        } else {
                            event.setType(EventType.MESSAGE);
                        }
                    } else if (event.getDelivery() != null) {
                        event.setType(EventType.MESSAGE_DELIVERED);
                    } else if (event.getRead() != null) {
                        event.setType(EventType.MESSAGE_READ);
                    } else if (event.getPostback() != null) {
                        event.setType(EventType.POSTBACK_RECEIVED);
                    } else if (event.getOptin() != null) {
                        event.setType(EventType.OPT_IN);
                    } else if (event.getReferral() != null) {
                        event.setType(EventType.REFERRAL);
                    } else if (event.getAccountLinking() != null) {
                        event.setType(EventType.ACCOUNT_LINKING);
                    } else {
                        logger.error("Callback/Event type not supported: {}", event);
                        return ResponseEntity.ok("Callback not supported yet!");
                    }
                    if (isConversationOn(event.getSender().getId())) {
                        invokeChainedMethod(event);
                    } else {
                        invokeMethods(event);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error in fb webhook: {}. Callback: {}", e, callback.toString());
        }
        // fb advises to send a 200 response within 20 secs
        return ResponseEntity.ok("EVENT_RECEIVED");
    }

    public final ResponseEntity<Response> reply(Event event) {
        return restTemplate.postForEntity(fbSendUrl, event, Response.class);
    }

    /**
     * Invoke this method to make the bot subscribe to a page after which
     * your users can interact with your page or in other words, the bot.
     *
     * @return true or false depending on the operation being successful
     */
    @PostMapping("/subscribe")
    public final boolean subscribeAppToPage() {
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
     * @param event
     */
    private void invokeChainedMethod(Event event) {
        Queue<MethodWrapper> queue = conversationQueueMap.get(event.getSender().getId());

        if (queue != null && !queue.isEmpty()) {
            MethodWrapper methodWrapper = queue.peek();

            try {
                EventType[] eventTypes = methodWrapper.getMethod().getAnnotation(Controller.class).events();
                for (EventType eventType : eventTypes) {
                    if (eventType.name().equals(event.getType().name().toUpperCase())) {
                        methodWrapper.getMethod().invoke(this, event);
                        return;
                    }
                }
            } catch (Exception e) {
                logger.error("Error invoking chained method: ", e);
            }
        }
    }
}
