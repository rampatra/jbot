package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    
    @JsonProperty("is_echo")
    private boolean isEcho;
    @JsonProperty("app_id")
    private String appId;
    private String metadata;
    private String mid;
    private int seq;
    private String text;
    private Attachment[] attachments;
    @JsonProperty("quick_reply")
    private Postback quickReply;
    
    
}
