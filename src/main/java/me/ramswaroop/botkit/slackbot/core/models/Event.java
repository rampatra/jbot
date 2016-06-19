package me.ramswaroop.botkit.slackbot.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    private String channelId;
    private String userId;
    private String text;
    private Edited edited;
    private Error error;
    private boolean ok;
    private int replyTo;
    private boolean isStarred;
    private String[] pinnedTo;
    private Channel channel;
    private Event item;
    private Bot bot;
    private File file;
    private String fileId;
    private User user;
    private boolean hasPins;
    private Reaction[] reactions;
    private boolean upload;
    private boolean hidden;
    private String latest;
    private String presence;
    private Comment comment;
    private String commentId;
    private String reaction;
    private String itemUser;
    private String[] names;
    private String value;
    private String plan;
    private String url;
    private String domain;
    private String emailDomain;
    private UserGroup subteam;
    private String subteamId;
    private DndStatus dndStatus;
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

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Edited getEdited() {
        return edited;
    }

    public void setEdited(Edited edited) {
        this.edited = edited;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(int replyTo) {
        this.replyTo = replyTo;
    }

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public String[] getPinnedTo() {
        return pinnedTo;
    }

    public void setPinnedTo(String[] pinnedTo) {
        this.pinnedTo = pinnedTo;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Event getItem() {
        return item;
    }

    public void setItem(Event item) {
        this.item = item;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isHasPins() {
        return hasPins;
    }

    public void setHasPins(boolean hasPins) {
        this.hasPins = hasPins;
    }

    public Reaction[] getReactions() {
        return reactions;
    }

    public void setReactions(Reaction[] reactions) {
        this.reactions = reactions;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getItemUser() {
        return itemUser;
    }

    public void setItemUser(String itemUser) {
        this.itemUser = itemUser;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public UserGroup getSubteam() {
        return subteam;
    }

    public void setSubteam(UserGroup subteam) {
        this.subteam = subteam;
    }

    public String getSubteamId() {
        return subteamId;
    }

    public void setSubteamId(String subteamId) {
        this.subteamId = subteamId;
    }

    public DndStatus getDndStatus() {
        return dndStatus;
    }

    public void setDndStatus(DndStatus dndStatus) {
        this.dndStatus = dndStatus;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getDeletedTs() {
        return deletedTs;
    }

    public void setDeletedTs(String deletedTs) {
        this.deletedTs = deletedTs;
    }

    public String getEventTs() {
        return eventTs;
    }

    public void setEventTs(String eventTs) {
        this.eventTs = eventTs;
    }

    public String getChannelId() {
        return channelId;
    }

    @JsonSetter("channel")
    public void setChannel(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            try {
                this.channel = new ObjectMapper().treeToValue(jsonNode, Channel.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: {}", e.getMessage());
            }
        } else if (jsonNode.isTextual()) {
            this.channelId = jsonNode.asText();
        }
    }

    @JsonSetter("file")
    public void setFile(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            try {
                this.file = new ObjectMapper().treeToValue(jsonNode, File.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: {}", e.getMessage());
            }
        } else if (jsonNode.isTextual()) {
            this.fileId = jsonNode.asText();
        }
    }

    @JsonSetter("comment")
    public void setComment(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            try {
                this.comment = new ObjectMapper().treeToValue(jsonNode, Comment.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: {}", e.getMessage());
            }
        } else if (jsonNode.isTextual()) {
            this.commentId = jsonNode.asText();
        }
    }

    @JsonSetter("user")
    public void setUser(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            try {
                this.user = new ObjectMapper().treeToValue(jsonNode, User.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: {}", e.getMessage());
            }
        } else if (jsonNode.isTextual()) {
            this.userId = jsonNode.asText();
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
