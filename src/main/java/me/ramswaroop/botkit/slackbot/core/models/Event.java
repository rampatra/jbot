package me.ramswaroop.botkit.slackbot.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ramswaroop on 10/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    private static final Logger logger = LoggerFactory.getLogger(Event.class);

    private int id;
    private String type;
    private String channel;
    private String user;
    private String text;
    private boolean isStarred;
    private String[] pinnedTo;
    private Channel channelObj;
    private String emailDomain;
    private String subteamId;
    private Event item;
    private Bot bot;
    private File file;
    private Reaction[] reactions;
    private boolean upload;
    private boolean hidden;
    private String latest;
    private String ts;
    private String deletedTs;
    private String eventTs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(JsonNode jsonNode) {
        JsonNode channelNode = jsonNode.get("channel");
        if (channelNode.isObject()) {
            try {
                this.channelObj = new ObjectMapper().treeToValue(channelNode, Channel.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: {}", e.getMessage());
            }
        } else {
            this.channel = channelNode.asText();
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
