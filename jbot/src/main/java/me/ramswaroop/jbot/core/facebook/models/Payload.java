package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {
    
    private String url;
    @JsonProperty("coordinates.lat")
    private String coordinatesLat;
    @JsonProperty("coordinates.long")
    private String coordinatesLong;
    @JsonProperty("is_reusable")
    private boolean isReusable;
    @JsonProperty("attachment_id")
    private String attachmentId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoordinatesLat() {
        return coordinatesLat;
    }

    public void setCoordinatesLat(String coordinatesLat) {
        this.coordinatesLat = coordinatesLat;
    }

    public String getCoordinatesLong() {
        return coordinatesLong;
    }

    public void setCoordinatesLong(String coordinatesLong) {
        this.coordinatesLong = coordinatesLong;
    }

    public boolean isReusable() {
        return isReusable;
    }

    public void setReusable(boolean reusable) {
        isReusable = reusable;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}
