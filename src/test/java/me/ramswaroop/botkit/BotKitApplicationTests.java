package me.ramswaroop.botkit;

import me.ramswaroop.botkit.slackbot.core.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BotKitApplicationTests {

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
