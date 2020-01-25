package example.jbot.slack

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import me.ramswaroop.jbot.core.slack.models.Attachment
import me.ramswaroop.jbot.core.slack.models.RichMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct

/**
 * This is a Slack Webhook sample. Webhooks are nothing but POST calls to
 * Slack with data relevant to your users. You can send the data
 * in the POST call in either ways:
 * 1) Send as a JSON string as the payload parameter in a POST request
 * 2) Send as a JSON string as the body of a POST request
 *
 * @author ramswaroop
 * @version 1.0.0, 21/06/2016
 */
@Component
@Profile("slack")
class SlackWebhooks {
    /**
     * The Url you get while configuring a new incoming webhook
     * on Slack. You can setup a new incoming webhook
     * [here](https://my.slack.com/services/new/incoming-webhook/).
     */
    @Value("\${slackIncomingWebhookUrl}")
    private val slackIncomingWebhookUrl: String? = null

    /**
     * Make a POST call to the incoming webhook url.
     */
    @PostConstruct
    fun invokeSlackWebhook() {
        val restTemplate = RestTemplate()
        val richMessage = RichMessage("Just to test Slack's incoming webhooks.")
        // set attachments
        val attachments = arrayOfNulls<Attachment>(1)
        attachments[0] = Attachment()
        attachments[0]!!.text = "Some data relevant to your users."
        richMessage.attachments = attachments
        // For debugging purpose only
        try {
            logger.debug("Reply (RichMessage): {}", ObjectMapper().writeValueAsString(richMessage))
        } catch (e: JsonProcessingException) {
            logger.debug("Error parsing RichMessage: ", e)
        }
        // Always remember to send the encoded message to Slack
        try {
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String::class.java)
        } catch (e: RestClientException) {
            logger.error("Error posting to Slack Incoming Webhook: ", e)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SlackWebhooks::class.java)
    }
}