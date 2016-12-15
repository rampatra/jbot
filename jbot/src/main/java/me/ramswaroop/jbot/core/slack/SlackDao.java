package me.ramswaroop.jbot.core.slack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.slack.models.RTM;
import me.ramswaroop.jbot.core.slack.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ramswaroop
 * @version 14/08/2016
 */
@Repository
public class SlackDao {

    private static final Logger logger = LoggerFactory.getLogger(SlackDao.class);
    /**
     * Endpoint for RTM.start()
     */
    @Value("${rtmUrl}")
    private String rtmUrl;
    /**
     * RTM object constructed from <a href="https://api.slack.com/methods/rtm.start">RTM.start()</a>.
     */
    private RTM rtm;
    /**
     * Rest template to make http calls.
     */
    private RestTemplate restTemplate;

    public RTM startRTM(String slackToken) {
        try {
            restTemplate = new RestTemplate();
            rtm = new RTM();
            // Custom Deserializers
            List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>();
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
            Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
            mapperBuilder.deserializerByType(RTM.class, new JsonDeserializer<RTM>() {
                @Override
                public RTM deserialize(JsonParser p, DeserializationContext ctxt) {
                    try {
                        final ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode node = p.readValueAsTree();
                        RTM rtm = new RTM();
                        rtm.setWebSocketUrl(node.get("url").asText());
                        rtm.setUser(objectMapper.treeToValue(node.get("self"), User.class));
                        List<String> dmChannels = new ArrayList<>();
                        Iterator<JsonNode> iterator = node.get("ims").iterator();
                        while (iterator.hasNext()) {
                            dmChannels.add(iterator.next().get("id").asText());
                        }
                        rtm.setDmChannels(dmChannels);
                        List<User> users = new ArrayList<>();
                        Iterator<JsonNode> userIterator = node.get("users").iterator();
                        while (userIterator.hasNext()) {
                            users.add(objectMapper.treeToValue(userIterator.next(), User.class));
                        }
                        rtm.setUsers(users);
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

            ResponseEntity<RTM> response = restTemplate.getForEntity(rtmUrl, RTM.class, slackToken);
            if (response.getBody() != null) {
                rtm.setWebSocketUrl(response.getBody().getWebSocketUrl());
                rtm.setDmChannels(response.getBody().getDmChannels());
                rtm.setUser(response.getBody().getUser());
                rtm.setUsers(response.getBody().getUsers());
                logger.debug("RTM connection successful. WebSocket URL: {}", rtm.getWebSocketUrl());
            } else {
                logger.debug("RTM response invalid. Response: {}", response);
            }
        } catch (RestClientException e) {
            logger.error("RTM connection error. Exception: {}", e.getMessage());
        }

        return rtm;
    }
}
