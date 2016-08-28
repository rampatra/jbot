package me.ramswaroop.jbot.core.slack.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ramswaroop on 15/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DndStatus {
    @JsonProperty("dnd_enabled")
    private boolean dndEnabled;
    @JsonProperty("next_dnd_start_ts")
    private long nextDndStartTs;
    @JsonProperty("next_dnd_end_ts")
    private long nextDndEndTs;
    @JsonProperty("snooze_enabled")
    private boolean snoozeEnabled;
    @JsonProperty("snooze_endtime")
    private long snoozeEndtime;

    public boolean isDndEnabled() {
        return dndEnabled;
    }

    public void setDndEnabled(boolean dndEnabled) {
        this.dndEnabled = dndEnabled;
    }

    public long getNextDndStartTs() {
        return nextDndStartTs;
    }

    public void setNextDndStartTs(long nextDndStartTs) {
        this.nextDndStartTs = nextDndStartTs;
    }

    public long getNextDndEndTs() {
        return nextDndEndTs;
    }

    public void setNextDndEndTs(long nextDndEndTs) {
        this.nextDndEndTs = nextDndEndTs;
    }

    public boolean isSnoozeEnabled() {
        return snoozeEnabled;
    }

    public void setSnoozeEnabled(boolean snoozeEnabled) {
        this.snoozeEnabled = snoozeEnabled;
    }

    public long getSnoozeEndtime() {
        return snoozeEndtime;
    }

    public void setSnoozeEndtime(long snoozeEndtime) {
        this.snoozeEndtime = snoozeEndtime;
    }
}
