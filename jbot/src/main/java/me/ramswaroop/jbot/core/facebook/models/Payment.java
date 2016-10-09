package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 06/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public User getRequestedUserInfo() {
        return requestedUserInfo;
    }

    public void setRequestedUserInfo(User requestedUserInfo) {
        this.requestedUserInfo = requestedUserInfo;
    }

    public PaymentCredential getPaymentCredential() {
        return paymentCredential;
    }

    public void setPaymentCredential(PaymentCredential paymentCredential) {
        this.paymentCredential = paymentCredential;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getShippingOptionId() {
        return shippingOptionId;
    }

    public void setShippingOptionId(String shippingOptionId) {
        this.shippingOptionId = shippingOptionId;
    }

}

class PaymentCredential {

    private String providerType;
    private String chargeId;

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
    
}

class Amount {

    private String currency;
    private String amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}


