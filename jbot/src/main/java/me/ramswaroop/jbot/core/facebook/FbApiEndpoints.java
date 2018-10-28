package me.ramswaroop.jbot.core.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author rpatra16
 * @since 30/08/2018
 */
@Service
public class FbApiEndpoints {

    /**
     * Endpoint for Fb Api
     */
    @Value("${fbGraphApi}")
    private String fbGraphApi;


    public String getFbGraphApi() {
        return fbGraphApi;
    }

    public String getUserApi() {
        return fbGraphApi + "/{userId}?access_token={token}";
    }

    public String getSubscribeUrl() {
        return fbGraphApi + "/me/subscribed_apps";
    }

    public String getFbSendUrl() {
        return fbGraphApi + "/me/messages?access_token={PAGE_ACCESS_TOKEN}";
    }

    public String getFbMessengerProfileUrl() {
        return fbGraphApi + "/me/messenger_profile?access_token={PAGE_ACCESS_TOKEN}";
    }
}
