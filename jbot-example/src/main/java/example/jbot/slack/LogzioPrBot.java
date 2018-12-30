package example.jbot.slack;

import me.ramswaroop.jbot.core.common.Controller;
import me.ramswaroop.jbot.core.common.EventType;
import me.ramswaroop.jbot.core.common.JBot;
import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.models.Event;
import me.ramswaroop.jbot.core.slack.models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.WebSocketSession;

@JBot
@Profile("slack")
public class LogzioPrBot extends Bot {

    private static final Logger logger = LoggerFactory.getLogger(LogzioPrBot.class);

    /**
     * Slack token from application.properties file. You can get your slack token
     * next <a href="https://my.slack.com/services/new/bot">creating a new bot</a>.
     */
    @Value("${slackBotToken}")
    private String slackToken;

    @Value("${userId}")
    private String userId;

    @Override
    public String getSlackToken() {
        return slackToken;
    }

    @Override
    public Bot getSlackBot() {
        return this;
    }

    @Controller(pattern="Hi, Marry", next="choosePRsType",  events = {EventType.MESSAGE, EventType.DIRECT_MESSAGE})
    public void startSession(WebSocketSession session, Event event) {
        if (!userId.isEmpty() && !event.getUserId().equals(userId)) return;
        startConversation(event, "choosePRsType");
        reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName() + "\n Please choose PRs to work: mine or assigned");
       // nextConversation("choosePRsType");
    }

    @Controller(next="exit")
    public void choosePRsType(WebSocketSession session, Event event) {
        if (!userId.isEmpty() && !event.getUserId().equals(userId)) return;
        String txt = event.getText();
        if (txt.equals("mine")) {
            reply(session, event, " list of my prs");
        //    nextConversation("myPrsActions");
            //return;
        }
        if (txt.equals("assigned")) {
            reply(session, event, "list of assigned prs");
            stopConversation(event);
            //return;
        }

        nextConversation(event);

    }

//    @Controller(pattern="myPrsActions")
//    public void myPrsActions(WebSocketSession session, Event event) {
//        if (!userId.isEmpty() && !event.getUserId().equals(userId)) return;
//        // print list of the prs
//        reply(session, event, "list of prs data");
//    }
//
    @Controller(pattern = "exit")
    public void exit(WebSocketSession session, Event event) {
        if (!userId.isEmpty() && !event.getUserId().equals(userId)) return;
        reply(session, event, "Bye");
        stopConversation(event);
    }



}
