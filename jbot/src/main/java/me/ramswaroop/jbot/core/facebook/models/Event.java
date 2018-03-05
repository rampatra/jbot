package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.ramswaroop.jbot.core.common.EventType;

/**
 * Model for the webhook events.
 *
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    private User sender;
    private User recipient;
    private Long timestamp;
    private Message message;
    @JsonProperty("messaging_type")
    private String messagingType;
    private Postback postback;
    private Referral optin;
    private Referral referral;
    @JsonProperty("account_linking")
    private AccountLinking accountLinking;
    private Read read;
    private Delivery delivery;
    @JsonProperty("checkout_update")
    private CheckoutUpdate checkoutUpdate;
    private Payment payment;
    @JsonProperty("sender_action")
    private String senderAction;
    @JsonProperty("setting_type")
    private String settingType;
    @JsonProperty("account_linking_url")
    private String accountLinkingUrl;
    @JsonProperty("whitelisted_domains")
    private String[] whitelistedDomains;
    @JsonProperty("domain_action_type")
    private String domainActionType;
    @JsonProperty("thread_state")
    private String threadState;
    @JsonProperty("payment_privacy_url")
    private String paymentPrivacyUrl;
    @JsonProperty("hub.mode")
    private String mode;
    @JsonProperty("hub.verify_token")
    private String token;
    @JsonProperty("hub.challenge")
    private String challenge;
    private EventType type;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getMessagingType() {
        return messagingType;
    }

    public void setMessagingType(String messagingType) {
        this.messagingType = messagingType;
    }

    public Postback getPostback() {
        return postback;
    }

    public void setPostback(Postback postback) {
        this.postback = postback;
    }

    public Referral getOptin() {
        return optin;
    }

    public void setOptin(Referral optin) {
        this.optin = optin;
    }

    public Referral getReferral() {
        return referral;
    }

    public void setReferral(Referral referral) {
        this.referral = referral;
    }

    public AccountLinking getAccountLinking() {
        return accountLinking;
    }

    public void setAccountLinking(AccountLinking accountLinking) {
        this.accountLinking = accountLinking;
    }

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public CheckoutUpdate getCheckoutUpdate() {
        return checkoutUpdate;
    }

    public void setCheckoutUpdate(CheckoutUpdate checkoutUpdate) {
        this.checkoutUpdate = checkoutUpdate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(String senderAction) {
        this.senderAction = senderAction;
    }

    public String getSettingType() {
        return settingType;
    }

    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    public String getAccountLinkingUrl() {
        return accountLinkingUrl;
    }

    public void setAccountLinkingUrl(String accountLinkingUrl) {
        this.accountLinkingUrl = accountLinkingUrl;
    }

    public String[] getWhitelistedDomains() {
        return whitelistedDomains;
    }

    public void setWhitelistedDomains(String[] whitelistedDomains) {
        this.whitelistedDomains = whitelistedDomains;
    }

    public String getDomainActionType() {
        return domainActionType;
    }

    public void setDomainActionType(String domainActionType) {
        this.domainActionType = domainActionType;
    }

    public String getThreadState() {
        return threadState;
    }

    public void setThreadState(String threadState) {
        this.threadState = threadState;
    }

    public String getPaymentPrivacyUrl() {
        return paymentPrivacyUrl;
    }

    public void setPaymentPrivacyUrl(String paymentPrivacyUrl) {
        this.paymentPrivacyUrl = paymentPrivacyUrl;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
