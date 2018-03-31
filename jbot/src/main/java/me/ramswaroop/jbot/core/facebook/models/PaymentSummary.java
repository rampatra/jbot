package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 01/01/2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentSummary {

    private String currency;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("is_test_payment")
    private Boolean isTestPayment;
    @JsonProperty("merchant_name")
    private String merchantName;
    @JsonProperty("requested_user_info")
    private String[] requestedUserInfo;
    @JsonProperty("price_list")
    private Amount[] priceList;

    public String getCurrency() {
        return currency;
    }

    public PaymentSummary setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public PaymentSummary setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public Boolean isTestPayment() {
        return isTestPayment;
    }

    public PaymentSummary setTestPayment(Boolean testPayment) {
        isTestPayment = testPayment;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public PaymentSummary setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public String[] getRequestedUserInfo() {
        return requestedUserInfo;
    }

    public PaymentSummary setRequestedUserInfo(String[] requestedUserInfo) {
        this.requestedUserInfo = requestedUserInfo;
        return this;
    }

    public Amount[] getPriceList() {
        return priceList;
    }

    public PaymentSummary setPriceList(Amount[] priceList) {
        this.priceList = priceList;
        return this;
    }
}
