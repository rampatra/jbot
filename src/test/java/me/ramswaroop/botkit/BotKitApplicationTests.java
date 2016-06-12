package me.ramswaroop.botkit;

import me.ramswaroop.botkit.slackbot.core.EventType;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BotKitApplication.class)
public class BotKitApplicationTests {

	@Ignore
	@Test
	public void contextLoads() {
	}

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
