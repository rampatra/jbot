package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    
    @JsonProperty("is_echo")
    private Boolean isEcho;
    @JsonProperty("app_id")
    private String appId;
    private String metadata;
    private String mid;
    private Integer seq;
    private String text;
    private Attachment attachment;
    private Attachment[] attachments;
    @JsonProperty("quick_reply")
    private Button quickReply;
    @JsonProperty("quick_replies")
    private Button[] quickReplies;
    

    public Boolean isEcho() {
        return isEcho;
    }

    public Message setEcho(Boolean echo) {
        isEcho = echo;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public Message setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getMetadata() {
        return metadata;
    }

    public Message setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    public String getMid() {
        return mid;
    }

    public Message setMid(String mid) {
        this.mid = mid;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }

    public Message setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public Message setAttachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public Message setAttachments(Attachment[] attachments) {
        this.attachments = attachments;
        return this;
    }

    public Button getQuickReply() {
        return quickReply;
    }

    public Message setQuickReply(Button quickReply) {
        this.quickReply = quickReply;
        return this;
    }

    public Button[] getQuickReplies() {
        return quickReplies;
    }

    public Message setQuickReplies(Button[] quickReplies) {
        this.quickReplies = quickReplies;
        return this;
    }
}
