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

    public void setEcho(Boolean echo) {
        isEcho = echo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment[] attachments) {
        this.attachments = attachments;
    }

    public Button getQuickReply() {
        return quickReply;
    }

    public void setQuickReply(Button quickReply) {
        this.quickReply = quickReply;
    }

    public Button[] getQuickReplies() {
        return quickReplies;
    }

    public void setQuickReplies(Button[] quickReplies) {
        this.quickReplies = quickReplies;
    }
}
