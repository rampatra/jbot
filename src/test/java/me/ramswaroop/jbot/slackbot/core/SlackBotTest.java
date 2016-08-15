package me.ramswaroop.jbot.slackbot.core;


import me.ramswaroop.jbot.slackbot.core.models.Event;
import me.ramswaroop.jbot.slackbot.core.models.User;
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
import java.util.regex.Matcher;

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
        when(slackService.getDmChannels()).thenReturn(Arrays.asList("D1E79BACV", "C0NDSV5B8"));
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

    @Test
    public void when_MessageWithPattern_Should_InvokeOnReceiveMessageWithPattern() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"channel\": \"D1E78BACV\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"as12sd\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("First group: as12sd"));
        assertThat(capture.toString(), containsString("Second group: as"));
        assertThat(capture.toString(), containsString("Third group: 12"));
        assertThat(capture.toString(), containsString("Fourth group: sd"));
    }

    @Test
    public void when_DirectMessageWithPattern_Should_InvokeOnDirectMessage() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\": \"message\"," +
                "\"ts\": \"1358878749.000002\"," +
                "\"channel\": \"D1E79BACV\"," +
                "\"user\": \"U023BECGF\"," +
                "\"text\": \"as12sd\"}"); // this matches the pattern but it's a direct message instead of message
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("this is a direct message"));
    }

    @Test
    public void when_DirectMessage_Should_InvokeOnPinAdded() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\":\"pin_added\"," +
                "\"user\":\"U0MCAEX8A\",\"channel_id\":\"C0NDSV5B8\"," +
                "\"item\":{\"type\":\"message\",\"channel\":\"C0NDSV5B8\"," +
                "\"message\":{\"type\":\"message\",\"user\":\"U1G0EBU2G\"," +
                "\"text\":\"Hi, I am nexagebot\",\"ts\":\"1471213096.000004\"," +
                "\"permalink\":\"https://aol.slack.com/archives/house-hunting/p1471213096000004\"," +
                "\"pinned_to\":[\"C0NDSV5B8\"]}," +
                "\"created\":1471213126}," +
                "\"item_user\":\"U1G0EBU2G\"," +
                "\"event_ts\":\"1471213126.954738\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("Thanks for the pin"));
    }

    @Test
    public void when_DirectMessage_Should_InvokeOnFileShared() throws Exception {
        TextMessage textMessage = new TextMessage("{\"type\":\"file_shared\"," +
                "\"file_id\":\"F219AF6VD\"," +
                "\"user_id\":\"U0MCAEX8A\"," +
                "\"file\":{\"id\":\"F219AF6VD\"}," +
                "\"event_ts\":\"1471213812.962298\"}");
        bot.handleTextMessage(session, textMessage);
        assertThat(capture.toString(), containsString("File shared"));
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

    @Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
    public void onReceiveMessageWithPattern(WebSocketSession session, Event event, Matcher matcher) {
        System.out.println("First group: " + matcher.group(0) + "\n" +
                "Second group: " + matcher.group(1) + "\n" +
                "Third group: " + matcher.group(2) + "\n" +
                "Fourth group: " + matcher.group(3));
    }

    @Controller(events = EventType.PIN_ADDED)
    public void onPinAdded(WebSocketSession session, Event event) {
        System.out.println("Thanks for the pin! You can find all pinned items under channel details.");
    }

    @Controller(events = EventType.FILE_SHARED)
    public void onFileShared(WebSocketSession session, Event event) {
        System.out.println("File shared.");
    }
}
