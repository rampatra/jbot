package me.ramswaroop.botkit.slackbot.core;


import me.ramswaroop.botkit.slackbot.core.models.Event;
import me.ramswaroop.botkit.slackbot.core.models.RTM;
import me.ramswaroop.botkit.slackbot.core.models.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author ramswaroop
 * @version 20/06/2016
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SlackBotTest {

    @Mock
    private WebSocketSession session;

    @Mock
    private SlackService slackService;

    @InjectMocks
    private SlackBot bot;

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Before
    public void init() {
        // set user
        User user = new User();
        user.setName("SlackBot");
        user.setId("UEADGH12S");
        // set rtm
        when(slackService.getDmChannels()).thenReturn(Arrays.asList("D1E79BACV", "A1E79BABH"));
        when(slackService.getCurrentUser()).thenReturn(user);
        when(slackService.getWebSocketUrl()).thenReturn("");
    }

    @Test
    public void when_DirectMention_Should_InvokeOnDirectMention() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"<@UEADGH12S>: Hello\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("Hi, I am"));
    }

    @Test
    public void when_DirectMessage_Should_InvokeOnDirectMessage() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"channel\": \"D1E79BACV\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"Hello\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("this is a direct message"));
    }
}

/**
 * Slack Bot for unit tests.
 */
class SlackBot extends Bot {
    @Override
    public String getSlackToken() {
        return "slackToken";
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(events = EventType.DIRECT_MENTION)
    public void onDirectMention(WebSocketSession session, Event event) {
        System.out.println("Hi, I am SlackBot");
    }

    @Controller(events = EventType.DIRECT_MESSAGE)
    public void onDirectMessage(WebSocketSession session, Event event) {
        System.out.println("Hi, this is a direct message.");
    }
}
