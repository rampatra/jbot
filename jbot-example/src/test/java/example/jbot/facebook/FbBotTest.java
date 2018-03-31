package example.jbot.facebook;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.facebook.Bot;
import me.ramswaroop.jbot.core.facebook.models.Callback;
import me.ramswaroop.jbot.core.facebook.models.Event;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author ramswaroop
 * @version 11/03/2018
 */
@SpringBootTest
@ActiveProfiles("facebook")
@RunWith(MockitoJUnitRunner.class)
public class FbBotTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TestBot bot;

    @Rule
    public OutputCapture capture = new OutputCapture();
    
    @Test
    public void When_PostbackInCallback_Then_InvokeOnReceivePostback() throws IOException {
        Callback callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854538851225832\",\"time\":1520772895257,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801119391393\"},\"recipient\":{\"id\":\"1854538851225832\"},\"timestamp\":1520772895257," +
                "\"postback\":{\"payload\":\"hi\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("Postback with payload `hi|hello|hey` received from facebook."));
    }

    @Test
    public void When_QuickReplyInCallback_Then_InvokeOnReceiveQuickReply() throws IOException {
        Callback callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854538851225832\",\"time\":1520773081394,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801119391393\"},\"recipient\":{\"id\":\"1854538851225832\"},\"timestamp\":1520773080631," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRmjuN1iFSOpOsmcC\",\"seq\":87625,\"text\":\"Sure\"," +
                "\"quick_reply\":{\"payload\":\"yes\"}}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("Quick reply button clicked with payload 'yes'"));
    }

    @Test
    public void When_ConversationPatternInCallback_Then_StartConversation() throws IOException {
        Callback callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"setup meeting\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("At what time (ex. 15:30) do you want me to set up the meeting?"));

        callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"11:45\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("Would you like to repeat it tomorrow?"));

        callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"yes\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("Would you like me to set a reminder for you"));

        callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"yes\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("I will remind you tomorrow before the meeting"));
    }

    @Test
    public void Given_InConversation_When_AnswerNoInCallback_Then_StopConversation() throws IOException {
        Callback callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"setup meeting\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("At what time (ex. 15:30) do you want me to set up the meeting?"));

        callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"11:45\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("Would you like to repeat it tomorrow?"));

        callback = new ObjectMapper().readValue("{\"object\":\"page\",\"entry\":[{\"id\":" +
                "\"1854218851225832\",\"time\":1520770458290,\"messaging\":[{\"sender\":{\"id\":" +
                "\"1373801529391393\"},\"recipient\":{\"id\":\"1854218851225832\"},\"timestamp\":1520770457527," +
                "\"message\":{\"mid\":\"mid.$cAAaWsSxbt_VoRkDnt1iFPuiv7I5q\",\"seq\":87611,\"text\":" +
                "\"no\"}}]}]}", Callback.class);
        bot.setupWebhookEndpoint(callback);
        assertThat(capture.toString(), containsString("You can always schedule one with 'setup meeting' command"));
    }


    /**
     * Facebook Bot for unit tests.
     */
    public static class TestBot extends Bot {
        @Override
        public String getFbToken() {
            return "fb_token";
        }

        @Override
        public String getPageAccessToken() {
            return "page_access_token";
        }

        @Controller(events = EventType.POSTBACK, pattern = "^(?i)(hi|hello|hey)$")
        public void onReceivePostback(Event event) {
            reply(event, "Postback with payload `hi|hello|hey` received from facebook.");
        }
        
        @Controller(events = EventType.QUICK_REPLY, pattern = "(yes|no)")
        public void onReceiveQuickReply(Event event) {
            if ("yes".equals(event.getMessage().getQuickReply().getPayload())) {
                reply(event, "Quick reply button clicked with payload 'yes'");
            } else {
                reply(event, "Quick reply button clicked with payload 'no'");
            }
        }

        /**
         * Conversation feature of JBot.
         */

        @Controller(pattern = "(setup meeting)", next = "confirmTiming")
        public void setupMeeting(Event event) {
            startConversation(event, "confirmTiming");   // start conversation
            System.out.println("Cool! At what time (ex. 15:30) do you want me to set up the meeting?");
        }

        @Controller(next = "askTimeForMeeting")
        public void confirmTiming(Event event) {
            System.out.println("Your meeting is set at " + event.getMessage().getText() +
                    ". Would you like to repeat it tomorrow?");
            nextConversation(event);    // jump to next question in conversation
        }

        @Controller(next = "askWhetherToRepeat")
        public void askTimeForMeeting(Event event) {
            if (event.getMessage().getText().contains("yes")) {
                System.out.println("Okay. Would you like me to set a reminder for you?");
                nextConversation(event);    // jump to next question in conversation
            } else {
                System.out.println("No problem. You can always schedule one with 'setup meeting' command.");
                stopConversation(event);    // stop conversation only if user says no
            }
        }

        @Controller
        public void askWhetherToRepeat(Event event) {
            if (event.getMessage().getText().contains("yes")) {
                System.out.println("Great! I will remind you tomorrow before the meeting.");
            } else {
                System.out.println("Okay, don't forget to attend the meeting tomorrow :)");
            }
            stopConversation(event);    // stop conversation
        }
    }
}
