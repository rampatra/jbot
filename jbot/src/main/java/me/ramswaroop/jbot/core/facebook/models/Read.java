package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Read {
    
    private Long watermark;
    private Integer seq;

    public Long getWatermark() {
        return watermark;
    }

    public Read setWatermark(Long watermark) {
        this.watermark = watermark;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }

    public Read setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }
}
