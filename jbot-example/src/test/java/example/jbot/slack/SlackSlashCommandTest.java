package example.jbot.slack;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ramswaroop
 * @version 05/08/2016
 */
@ActiveProfiles("slack")
@RunWith(SpringRunner.class)
@WebMvcTest(SlackSlashCommand.class)
public class SlackSlashCommandTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void onReceiveSlashCommand_When_IncorrectToken_Should_ReturnSorryRichMessage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/slash-command?" +
                        "token={token}&" +
                        "team_id={team_id}&" +
                        "team_domain={team_domain}&" +
                        "channel_id={channel_id}&" +
                        "channel_name={channel_name}&" +
                        "user_id={user_id}&" +
                        "user_name={user_name}&" +
                        "command={command}&" +
                        "text={text}&" +
                        "response_url={response_url}&",
                "incorrect_token",
                "any_team_id",
                "any_domain",
                "UHASHB8JB",
                "test-channel",
                "UNJSD9OKM",
                "uname",
                "/command",
                "help",
                "http://example.com")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Sorry! You're not lucky enough to use our slack command."));
    }
}
