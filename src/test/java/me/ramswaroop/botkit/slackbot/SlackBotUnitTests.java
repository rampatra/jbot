package me.ramswaroop.botkit.slackbot;

import me.ramswaroop.botkit.slackbot.core.EventType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author ramswaroop
 * @version 20/06/2016
 */
public class SlackBotUnitTests {
    
    @Test
    public void numberOfEventsType() {
        EventType[] events = EventType.values();
        /**
         * Number of events in {@link EventType} should be
         * equal to the number of events supported by slack
         * plus 2 added by BotKit.
         */
        assertEquals(events.length, 70);
    }
}
