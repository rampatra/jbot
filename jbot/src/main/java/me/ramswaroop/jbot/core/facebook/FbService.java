package me.ramswaroop.jbot.core.facebook;

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
}
