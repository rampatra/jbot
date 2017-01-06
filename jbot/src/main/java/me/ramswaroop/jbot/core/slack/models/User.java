package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ramswaroop on 14/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    @JsonProperty("team_id")
    private String teamId;    
    private String name;
    private boolean deleted;
    private String color;
    private Profile profile;
    @JsonProperty("is_admin")
    private boolean isAdmin;
    @JsonProperty("is_owner")
    private boolean isOwner;
    @JsonProperty("is_primary_owner")
    private boolean isPrimaryOwner;
    @JsonProperty("is_restricted")
    private boolean isRestricted;
    @JsonProperty("is_ultra_restricted")
    private boolean isUltraRestricted;
    private boolean has2fa;
    @JsonProperty("two_factor_type")
    private String twoFactorType;
    @JsonProperty("has_files")
    private boolean hasFiles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isPrimaryOwner() {
        return isPrimaryOwner;
    }

    public void setPrimaryOwner(boolean primaryOwner) {
        isPrimaryOwner = primaryOwner;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }

    public boolean isUltraRestricted() {
        return isUltraRestricted;
    }

    public void setUltraRestricted(boolean ultraRestricted) {
        isUltraRestricted = ultraRestricted;
    }

    public boolean isHas2fa() {
        return has2fa;
    }

    public void setHas2fa(boolean has2fa) {
        this.has2fa = has2fa;
    }

    public String getTwoFactorType() {
        return twoFactorType;
    }

    public void setTwoFactorType(String twoFactorType) {
        this.twoFactorType = twoFactorType;
    }

    public boolean isHasFiles() {
        return hasFiles;
    }

    public void setHasFiles(boolean hasFiles) {
        this.hasFiles = hasFiles;
    }
}
