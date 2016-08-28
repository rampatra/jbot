package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ramswaroop on 14/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {
    private String id;
    private long created;
    private long timestamp;
    private String name;
    private String title;
    private String mimetype;
    private String filetype;
    @JsonProperty("pretty_type")
    private String prettyType;
    private String user;
    private String mode;
    private boolean editable;
    @JsonProperty("is_external")
    private boolean isExternal;
    @JsonProperty("external_type")
    private String externalType;
    private String username;
    private int size;
    @JsonProperty("url_private")
    private String urlPrivate;
    @JsonProperty("url_private_download")
    private String urlPrivateDownload;
    private String thumb64;
    private String thumb80;
    private String thumb360;
    @JsonProperty("thumb360_gif")
    private String thumb360Gif;
    @JsonProperty("thumb360_w")
    private String thumb360W;
    @JsonProperty("thumb360_h")
    private String thumb360H;
    private String thumb480;
    @JsonProperty("thumb480_w")
    private String thumb480W;
    @JsonProperty("thumb480_h")
    private String thumb480H;
    private String thumb160;
    private String permalink;
    @JsonProperty("permalink_public")
    private String permalinkPublic;
    @JsonProperty("edit_link")
    private String editLink;
    private String preview;
    @JsonProperty("preview_highlight")
    private String previewHighlight;
    private int lines;
    @JsonProperty("lines_more")
    private int linesMore;
    @JsonProperty("is_public")
    private boolean isPublic;
    @JsonProperty("public_url_shared")
    private boolean publicUrlShared;
    @JsonProperty("display_as_bot")
    private boolean displayAsBot;
    private String[] channels;
    private String[] groups;
    private String[] ims;
    @JsonProperty("initial_comment")
    private Comment initialComment;
    @JsonProperty("num_stars")
    private int numStars;
    @JsonProperty("is_starred")
    private boolean isStarred;
    @JsonProperty("pinned_to")
    private String[] pinnedTo;
    private Reaction reactions;
    @JsonProperty("comments_count")
    private int commentsCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getPrettyType() {
        return prettyType;
    }

    public void setPrettyType(String prettyType) {
        this.prettyType = prettyType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public String getExternalType() {
        return externalType;
    }

    public void setExternalType(String externalType) {
        this.externalType = externalType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrlPrivate() {
        return urlPrivate;
    }

    public void setUrlPrivate(String urlPrivate) {
        this.urlPrivate = urlPrivate;
    }

    public String getUrlPrivateDownload() {
        return urlPrivateDownload;
    }

    public void setUrlPrivateDownload(String urlPrivateDownload) {
        this.urlPrivateDownload = urlPrivateDownload;
    }

    public String getThumb64() {
        return thumb64;
    }

    public void setThumb64(String thumb64) {
        this.thumb64 = thumb64;
    }

    public String getThumb80() {
        return thumb80;
    }

    public void setThumb80(String thumb80) {
        this.thumb80 = thumb80;
    }

    public String getThumb360() {
        return thumb360;
    }

    public void setThumb360(String thumb360) {
        this.thumb360 = thumb360;
    }

    public String getThumb360Gif() {
        return thumb360Gif;
    }

    public void setThumb360Gif(String thumb360Gif) {
        this.thumb360Gif = thumb360Gif;
    }

    public String getThumb360W() {
        return thumb360W;
    }

    public void setThumb360W(String thumb360W) {
        this.thumb360W = thumb360W;
    }

    public String getThumb360H() {
        return thumb360H;
    }

    public void setThumb360H(String thumb360H) {
        this.thumb360H = thumb360H;
    }

    public String getThumb480() {
        return thumb480;
    }

    public void setThumb480(String thumb480) {
        this.thumb480 = thumb480;
    }

    public String getThumb480W() {
        return thumb480W;
    }

    public void setThumb480W(String thumb480W) {
        this.thumb480W = thumb480W;
    }

    public String getThumb480H() {
        return thumb480H;
    }

    public void setThumb480H(String thumb480H) {
        this.thumb480H = thumb480H;
    }

    public String getThumb160() {
        return thumb160;
    }

    public void setThumb160(String thumb160) {
        this.thumb160 = thumb160;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPermalinkPublic() {
        return permalinkPublic;
    }

    public void setPermalinkPublic(String permalinkPublic) {
        this.permalinkPublic = permalinkPublic;
    }

    public String getEditLink() {
        return editLink;
    }

    public void setEditLink(String editLink) {
        this.editLink = editLink;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPreviewHighlight() {
        return previewHighlight;
    }

    public void setPreviewHighlight(String previewHighlight) {
        this.previewHighlight = previewHighlight;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getLinesMore() {
        return linesMore;
    }

    public void setLinesMore(int linesMore) {
        this.linesMore = linesMore;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPublicUrlShared() {
        return publicUrlShared;
    }

    public void setPublicUrlShared(boolean publicUrlShared) {
        this.publicUrlShared = publicUrlShared;
    }

    public boolean isDisplayAsBot() {
        return displayAsBot;
    }

    public void setDisplayAsBot(boolean displayAsBot) {
        this.displayAsBot = displayAsBot;
    }

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String[] getIms() {
        return ims;
    }

    public void setIms(String[] ims) {
        this.ims = ims;
    }

    public Comment getInitialComment() {
        return initialComment;
    }

    public void setInitialComment(Comment initialComment) {
        this.initialComment = initialComment;
    }

    public int getNumStars() {
        return numStars;
    }

    public void setNumStars(int numStars) {
        this.numStars = numStars;
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

    public Reaction getReactions() {
        return reactions;
    }

    public void setReactions(Reaction reactions) {
        this.reactions = reactions;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
