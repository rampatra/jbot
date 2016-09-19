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
    private Attachment[] attachment;
    @JsonProperty("quick_reply")
    private Postback quickReply;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachment[] getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment[] attachment) {
        this.attachment = attachment;
    }

    public Postback getQuickReply() {
        return quickReply;
    }

    public void setQuickReply(Postback quickReply) {
        this.quickReply = quickReply;
    }
}
