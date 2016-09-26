package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author ramswaroop
 * @version 11/09/2016
 */
public abstract class Bot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    /**
     * Class extending this must implement this as it's
     * required to setup the webhook.
     *
     * @return
     */
    public abstract String getFbToken();

    @Value("${fbSubscribeUrl}")
    private String subscribeUrl;

    @Value("${fbPageAccessToken}")
    private String pageAccessToken;

    /**
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
     * @return
     */
    public boolean subscribeAppToPage() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("access_token", pageAccessToken);
            restTemplate.postForEntity(subscribeUrl, params, String.class);
            return true;
        } catch (Exception e) {
            logger.error("Error subscribing fb app to page: ", e);
            return false;
        }
    }
}
