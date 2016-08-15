package me.ramswaroop.jbot;

import me.ramswaroop.jbot.slackbot.core.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JBotApplicationTests {

    /**
     * Number of events in {@link EventType} should be
     * equal to the number of events supported by slack
     * plus 3 added by JBot.
     */
    @Test
    public void numberOfEventsType() {
        EventType[] events = EventType.values();
        assertEquals(events.length, 71);
    }

}
