package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.facebook.models.Event;
import me.ramswaroop.jbot.core.facebook.models.Message;
import me.ramswaroop.jbot.core.facebook.models.Response;
import me.ramswaroop.jbot.core.facebook.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author rpatra16
 * @since 30/08/2018
 */
@Service
public class FbService {
    private final RestTemplate restTemplate;
    private final FbApiEndpoints fbApiEndpoints;

    public FbService(RestTemplate restTemplate, FbApiEndpoints fbApiEndpoints) {
        this.restTemplate = restTemplate;
        this.fbApiEndpoints = fbApiEndpoints;
    }

    public User getUser(String id, String pageAccessToken) {
        return restTemplate.getForEntity(fbApiEndpoints.getUserApi(), User.class, id, pageAccessToken).getBody();
    }

    /**
     * Call this method to send a message without a previous interaction of the user.
     *
     * @param userId id of user you want to send the message to
     * @param messageText message that you want to send to user
     */
    public final void sendMessageToUser(String userId, String messageText) {
        User user = new User().setId(userId);
        Message message = new Message().setText(messageText);

        Event event = new Event().setRecipient(user).setMessage(message).setType(EventType.MESSAGE);

        restTemplate.postForEntity(fbApiEndpoints.getFbSendUrl(), event, Response.class);
    }
}
