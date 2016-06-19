package me.ramswaroop.botkit.slackbot.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ramswaroop on 15/06/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DndStatus {
    private boolean dndEnabled;
    private long nextDndStartTs;
    private long nextDndEndTs;
    private boolean snoozeEnabled;
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
