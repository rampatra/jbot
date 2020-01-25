package example.jbot.slack

import me.ramswaroop.jbot.core.common.Controller
import me.ramswaroop.jbot.core.common.EventType
import me.ramswaroop.jbot.core.common.JBot
import me.ramswaroop.jbot.core.slack.Bot
import me.ramswaroop.jbot.core.slack.models.Event
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.web.socket.WebSocketSession
import java.util.regex.Matcher

@JBot
@Profile("slack")
class SlackBot : Bot() {
    /**
     * Slack token from application.properties file. You can get your slack token
     * next [creating a new bot](https://my.slack.com/services/new/bot).
     */
    @Value("\${slackBotToken}")
    private val slackToken: String? = null

    override fun getSlackToken(): String {
        return slackToken!!
    }

    override fun getSlackBot(): Bot {
        return this
    }

    /**
     * Invoked when the bot receives a direct mention (@botname: message)
     * or a direct message. NOTE: These two event types are added by jbot
     * to make your task easier, Slack doesn't have any direct way to
     * determine these type of events.
     *
     * @param session
     * @param event
     */
    @Controller(events = [EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE])
    fun onReceiveDM(session: WebSocketSession?, event: Event?) {
        reply(session, event, "Greetings, I am ${slackService.currentUser.name}")
    }

    /**
     * Invoked when bot receives an event of type message with text satisfying
     * the pattern `([a-z ]{2})(\d+)([a-z ]{2})`. For example,
     * messages like "ab12xy" or "ab2bc" etc will invoke this method.
     *
     * @param session
     * @param event
     */
    @Controller(events = [EventType.MESSAGE], pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
    fun onReceiveMessage(session: WebSocketSession?, event: Event?, matcher: Matcher) {
        reply(session, event, "First group: " + matcher.group(0) + "\n" +
                "Second group: " + matcher.group(1) + "\n" +
                "Third group: " + matcher.group(2) + "\n" +
                "Fourth group: " + matcher.group(3))
    }

    /**
     * Invoked when bot receives an event of type file shared.
     * NOTE: You can't reply to this event as slack doesn't send
     * a channel id for this event type. You can learn more about
     * [file_shared](https://api.slack.com/events/file_shared)
     * event from Slack's Api documentation.
     *
     * @param session
     * @param event
     */
    @Controller(events = [EventType.FILE_SHARED])
    fun onFileShared(session: WebSocketSession?, event: Event?) {
        logger.info("File shared: {}", event)
    }

    /**
     * Conversation feature of JBot. This method is the starting point of the conversation (as it
     * calls [Bot.startConversation] within it. You can chain methods which will be invoked
     * one after the other leading to a conversation. You can chain methods with [Controller.next] by
     * specifying the method name to chain with.
     *
     * @param session
     * @param event
     */
    @Controller(pattern = "(setup meeting)", next = "confirmTiming")
    fun setupMeeting(session: WebSocketSession?, event: Event?) {
        startConversation(event, "confirmTiming") // start conversation
        reply(session, event, "Cool! At what time (ex. 15:30) do you want me to set up the meeting?")
    }

    /**
     * This method will be invoked after [SlackBot.setupMeeting].
     *
     * @param session
     * @param event
     */
    @Controller(next = "askTimeForMeeting")
    fun confirmTiming(session: WebSocketSession?, event: Event) {
        reply(session, event, "Your meeting is set at " + event.text +
                ". Would you like to repeat it tomorrow?")
        nextConversation(event) // jump to next question in conversation
    }

    /**
     * This method will be invoked after [SlackBot.confirmTiming].
     *
     * @param session
     * @param event
     */
    @Controller(next = "askWhetherToRepeat")
    fun askTimeForMeeting(session: WebSocketSession?, event: Event) {
        if (event.text.contains("yes")) {
            reply(session, event, "Okay. Would you like me to set a reminder for you?")
            nextConversation(event) // jump to next question in conversation
        } else {
            reply(session, event, "No problem. You can always schedule one with 'setup meeting' command.")
            stopConversation(event) // stop conversation only if user says no
        }
    }

    /**
     * This method will be invoked after [SlackBot.askTimeForMeeting].
     *
     * @param session
     * @param event
     */
    @Controller
    fun askWhetherToRepeat(session: WebSocketSession?, event: Event) {
        if (event.text.contains("yes")) {
            reply(session, event, "Great! I will remind you tomorrow before the meeting.")
        } else {
            reply(session, event, "Okay, don't forget to attend the meeting tomorrow :)")
        }
        stopConversation(event) // stop conversation
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SlackBot::class.java)
    }
}