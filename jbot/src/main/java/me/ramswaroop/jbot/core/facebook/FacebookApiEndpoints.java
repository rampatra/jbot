package me.ramswaroop.jbot.core.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author rpatra16
 * @since 30/08/2018
 */
@Service
public class FacebookApiEndpoints {

    /**
     * Endpoint for Slack Api
     */
    @Value("${slackApi}")
    private String slackApi;

    /**
     * @return endpoint for RTM.connect()
     */
    public String getRtmConnectApi() {
        return slackApi + "/rtm.connect?token={token}";
    }
}
