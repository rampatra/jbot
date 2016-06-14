package me.ramswaroop.botkit.slackbot.core.models;

/**
 * Created by ramswaroop on 14/06/2016.
 */
public class Channel {
    private String id;
    private String user;
    private int created;
    boolean isIm;
    boolean isOrgShared;
    boolean isUserDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
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
