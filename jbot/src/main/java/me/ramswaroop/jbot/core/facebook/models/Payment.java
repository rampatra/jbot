package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 06/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {

    private String payload;
    @JsonProperty("requested_user_info")
    private User requestedUserInfo;
    @JsonProperty("payment_credential")
    private PaymentCredential paymentCredential;
    private Amount amount;
    @JsonProperty("shipping_option_id")
    private String shippingOptionId;

    public String getPayload() {
        return payload;
    }

    public Payment setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public User getRequestedUserInfo() {
        return requestedUserInfo;
    }

    public Payment setRequestedUserInfo(User requestedUserInfo) {
        this.requestedUserInfo = requestedUserInfo;
        return this;
    }

    public PaymentCredential getPaymentCredential() {
        return paymentCredential;
    }

    public Payment setPaymentCredential(PaymentCredential paymentCredential) {
        this.paymentCredential = paymentCredential;
        return this;
    }

    public Amount getAmount() {
        return amount;
    }

    public Payment setAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public String getShippingOptionId() {
        return shippingOptionId;
    }

    public Payment setShippingOptionId(String shippingOptionId) {
        this.shippingOptionId = shippingOptionId;
        return this;
    }

}

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentCredential {

    private String providerType;
    private String chargeId;

    public String getProviderType() {
        return providerType;
    }

    public PaymentCredential setProviderType(String providerType) {
        this.providerType = providerType;
        return this;
    }

    public String getChargeId() {
        return chargeId;
    }

    public PaymentCredential setChargeId(String chargeId) {
        this.chargeId = chargeId;
        return this;
    }
    
}


