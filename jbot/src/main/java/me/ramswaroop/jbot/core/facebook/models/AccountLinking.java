package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLinking {
    
    private String status;
    @JsonProperty("authorization_code")
    private String authorizationCode;

    public String getStatus() {
        return status;
    }

    public AccountLinking setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public AccountLinking setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }
}
