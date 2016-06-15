package me.ramswaroop.botkit.slackbot.core.models;

/**
 * Created by ramswaroop on 14/06/2016.
 */
public class Channel {
    private String id;
    private String name;
    private String user;
    private long created;
    private String creator;
    boolean isIm;
    boolean isOrgShared;
    boolean isUserDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isIm() {
        return isIm;
    }

    public void setIm(boolean im) {
        isIm = im;
    }

    public boolean isOrgShared() {
        return isOrgShared;
    }

    public void setOrgShared(boolean orgShared) {
        isOrgShared = orgShared;
    }

    public boolean isUserDeleted() {
        return isUserDeleted;
    }

    public void setUserDeleted(boolean userDeleted) {
        isUserDeleted = userDeleted;
    }
}
