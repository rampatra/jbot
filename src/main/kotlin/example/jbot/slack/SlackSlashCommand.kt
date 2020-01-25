package example.jbot.slack

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import me.ramswaroop.jbot.core.slack.models.Attachment
import me.ramswaroop.jbot.core.slack.models.RichMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Sample Slash Command Handler.
 *
 * @author ramswaroop
 * @version 1.0.0, 20/06/2016
 */
@RestController
@Profile("slack")
class SlackSlashCommand {
    /**
     * The token you get while creating a new Slash Command. You
     * should paste the token in application.properties file.
     */
    @Value("\${slashCommandToken}")
    private val slackToken: String? = null

    /**
     * Slash Command handler. When a user types for example "/app help"
     * then slack sends a POST request to this endpoint. So, this endpoint
     * should match the url you set while creating the Slack Slash Command.
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @param responseUrl
     * @return
     */
    @RequestMapping(value = ["/slash-command"], method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun onReceiveSlashCommand(@RequestParam("token") token: String,
                              @RequestParam("team_id") teamId: String?,
                              @RequestParam("team_domain") teamDomain: String?,
                              @RequestParam("channel_id") channelId: String?,
                              @RequestParam("channel_name") channelName: String?,
                              @RequestParam("user_id") userId: String?,
                              @RequestParam("user_name") userName: String?,
                              @RequestParam("command") command: String?,
                              @RequestParam("text") text: String?,
                              @RequestParam("response_url") responseUrl: String?): RichMessage { // validate token
        if (token != slackToken) {
            return RichMessage("Sorry! You're not lucky enough to use our slack command.")
        }
        /** build response  */
        val richMessage = RichMessage("The is Slash Commander!")
        richMessage.responseType = "in_channel"
        // set attachments
        val attachments = arrayOfNulls<Attachment>(1)
        attachments[0] = Attachment()
        attachments[0]!!.text = "I will perform all tasks for you."
        richMessage.attachments = attachments
        // For debugging purpose only
        if (logger.isDebugEnabled) {
            try {
                logger.debug("Reply (RichMessage): {}", ObjectMapper().writeValueAsString(richMessage))
            } catch (e: JsonProcessingException) {
                logger.debug("Error parsing RichMessage: ", e)
            }
        }
        return richMessage.encodedMessage() // don't forget to send the encoded message to Slack
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SlackSlashCommand::class.java)
    }
}