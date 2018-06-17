package me.ramswaroop.jbot.core.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackApiEndpoints {

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

    public String getImListApi() {
        return slackApi + "/im.list?token={token}&limit={limit}&next_cursor={cursor}";
    }
}
