package me.ramswaroop.jbot.core.slack;

import me.ramswaroop.jbot.core.common.EventType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class EventTypeTest {

    /**
     * Number of events in {@link EventType} should be
     * equal to the number of events supported by slack
     * (including 3 added by JBot) plus number of events
     * supported in fb bot.
     */
    @Test
    public void numberOfEventsType() {
        EventType[] events = EventType.values();
        assertEquals(87, events.length);
    }

}
