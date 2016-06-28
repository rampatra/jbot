package me.ramswaroop.botkit.slackbot.core;

import me.ramswaroop.botkit.slackbot.core.EventType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author ramswaroop
 * @version 20/06/2016
 */
public class SlackBotUnitTests {
    
    /**
     * Number of events in {@link EventType} should be
     * equal to the number of events supported by slack
     * plus 3 added by BotKit.
     */
    @Test
    public void numberOfEventsType() {
        EventType[] events = EventType.values();
        assertEquals(events.length, 71);
    }
}
