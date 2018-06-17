package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String subtype;
    @JsonProperty("bot_id")
    private String botId;
    @JsonProperty("channel_id")
    private String channelId;
    @JsonProperty("user_id")
    private String userId;
    private String text;
    private Edited edited;
    private Error error;
    private boolean ok;
    @JsonProperty("reply_to")
    private int replyTo;
    @JsonProperty("is_starred")
    private boolean isStarred;
    @JsonProperty("pinned_to")
    private String[] pinnedTo;
    private Channel channel;
    private Channel[] ims;
    private Item item;
    private Bot bot;
    private File file;
    @JsonProperty("file_id")
    private String fileId;
    private User user;
    @JsonProperty("has_pins")
    private boolean hasPins;
    private Reaction[] reactions;
    private boolean upload;
    private boolean hidden;
    private String latest;
    private String presence;
    private Comment comment;
    @JsonProperty("comment_id")
    private String commentId;
    private String reaction;
    @JsonProperty("item_user")
    private String itemUser;
    private String[] names;
    private String value;
    private String plan;
    private String url;
    private String domain;
    @JsonProperty("email_domain")
    private String emailDomain;
    @JsonProperty("team")
    private String teamId;
    private UserGroup subteam;
    @JsonProperty("subteam_id")
    private String subteamId;
    @JsonProperty("dnd_status")
    private DndStatus dndStatus;
    private String ts;
    @JsonProperty("thread_ts")
    private String threadTs;
    @JsonProperty("deleted_ts")
    private String deletedTs;
    @JsonProperty("event_ts")
    private String eventTs;
    private Message message;
    private Attachment[] attachments;
    @JsonProperty("response_metadata")
    private ResponseMetadata responseMetadata;

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
    
	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getBotId() {
		return botId;
	}

	public void setBotId(String botId) {
		this.botId = botId;
	}

    public String getChannelId() {
        return channelId;
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

    public Channel[] getIms() {
        return ims;
    }

    public void setIms(Channel[] ims) {
        this.ims = ims;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
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
    
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

    public String getThreadTs() {
        return threadTs;
    }

    public void setThreadTs(String threadTs) {
        this.threadTs = threadTs;
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

    @JsonSetter("channel")
    public void setChannel(JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            try {
                this.channel = new ObjectMapper().treeToValue(jsonNode, Channel.class);
            } catch (JsonProcessingException e) {
                logger.error("Error deserializing json: ", e);
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
                logger.error("Error deserializing json: ", e);
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
                logger.error("Error deserializing json: ", e);
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
                logger.error("Error deserializing json: ", e);
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment[] attachments) {
        this.attachments = attachments;
    }

    public ResponseMetadata getResponseMetadata() {
        return responseMetadata;
    }

    public void setResponseMetadata(ResponseMetadata responseMetadata) {
        this.responseMetadata = responseMetadata;
    }
}
