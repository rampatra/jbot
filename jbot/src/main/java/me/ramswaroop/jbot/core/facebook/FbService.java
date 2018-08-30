package me.ramswaroop.jbot.core.facebook;

import me.ramswaroop.jbot.core.facebook.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author rpatra16
 * @since 30/08/2018
 */
@Service
public class FbService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FacebookApiEndpoints facebookApiEndpoints;

    public User getUser(String id) {
        return restTemplate.getForEntity("", User.class).getBody();
    }
}
