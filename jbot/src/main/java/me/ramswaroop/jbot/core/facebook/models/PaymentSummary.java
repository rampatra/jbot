package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 01/01/2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSummary {

    private String currency;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("is_test_payment")
    private boolean isTestPayment;
    @JsonProperty("merchant_name")
    private String merchantName;
    @JsonProperty("requested_user_info")
    private String[] requestedUserInfo;
    @JsonProperty("price_list")
    private Amount[] priceList;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isTestPayment() {
        return isTestPayment;
    }

    public void setTestPayment(boolean testPayment) {
        isTestPayment = testPayment;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String[] getRequestedUserInfo() {
        return requestedUserInfo;
    }

    public void setRequestedUserInfo(String[] requestedUserInfo) {
        this.requestedUserInfo = requestedUserInfo;
    }

    public Amount[] getPriceList() {
        return priceList;
    }

    public void setPriceList(Amount[] priceList) {
        this.priceList = priceList;
    }
}
