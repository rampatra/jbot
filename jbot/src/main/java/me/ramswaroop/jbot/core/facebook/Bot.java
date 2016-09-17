package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ramswaroop
 * @version 11/09/2016
 */
public abstract class Bot {

    /**
     * Class extending this must implement this as it's
     * required to setup the webhook.
     *
     * @return
     */
    public abstract String getFbToken();

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
}
