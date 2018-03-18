package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delivery {
    
    private String[] mids;
    private Long watermark;
    private Integer seq;

    public String[] getMids() {
        return mids;
    }

    public void setMids(String[] mids) {
        this.mids = mids;
    }

    public Long getWatermark() {
        return watermark;
    }

    public void setWatermark(Long watermark) {
        this.watermark = watermark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
