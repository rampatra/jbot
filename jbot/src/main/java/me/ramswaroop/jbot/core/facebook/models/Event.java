package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramswaroop.jbot.core.common.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model for the webhook events.
 *
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    private static final Logger logger = LoggerFactory.getLogger(Event.class);

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
    @JsonProperty("get_started")
    private Postback getStarted;
    private Payload[] greeting;
    private EventType type;

    public User getSender() {
        return sender;
    }

    public Event setSender(User sender) {
        this.sender = sender;
        return this;
    }

    public User getRecipient() {
        return recipient;
    }

    public Event setRecipient(User recipient) {
        this.recipient = recipient;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Event setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Message getMessage() {
        return message;
    }

    public Event setMessage(Message message) {
        this.message = message;
        return this;
    }

    public String getMessagingType() {
        return messagingType;
    }

    public Event setMessagingType(String messagingType) {
        this.messagingType = messagingType;
        return this;
    }

    public Postback getPostback() {
        return postback;
    }

    public Event setPostback(Postback postback) {
        this.postback = postback;
        return this;
    }

    public Referral getOptin() {
        return optin;
    }

    public Event setOptin(Referral optin) {
        this.optin = optin;
        return this;
    }

    public Referral getReferral() {
        return referral;
    }

    public Event setReferral(Referral referral) {
        this.referral = referral;
        return this;
    }

    public AccountLinking getAccountLinking() {
        return accountLinking;
    }

    public Event setAccountLinking(AccountLinking accountLinking) {
        this.accountLinking = accountLinking;
        return this;
    }

    public Read getRead() {
        return read;
    }

    public Event setRead(Read read) {
        this.read = read;
        return this;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Event setDelivery(Delivery delivery) {
        this.delivery = delivery;
        return this;
    }

    public CheckoutUpdate getCheckoutUpdate() {
        return checkoutUpdate;
    }

    public Event setCheckoutUpdate(CheckoutUpdate checkoutUpdate) {
        this.checkoutUpdate = checkoutUpdate;
        return this;
    }

    public Payment getPayment() {
        return payment;
    }

    public Event setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public String getSenderAction() {
        return senderAction;
    }

    public Event setSenderAction(String senderAction) {
        this.senderAction = senderAction;
        return this;
    }

    public String getSettingType() {
        return settingType;
    }

    public Event setSettingType(String settingType) {
        this.settingType = settingType;
        return this;
    }

    public String getAccountLinkingUrl() {
        return accountLinkingUrl;
    }

    public Event setAccountLinkingUrl(String accountLinkingUrl) {
        this.accountLinkingUrl = accountLinkingUrl;
        return this;
    }

    public String[] getWhitelistedDomains() {
        return whitelistedDomains;
    }

    public Event setWhitelistedDomains(String[] whitelistedDomains) {
        this.whitelistedDomains = whitelistedDomains;
        return this;
    }

    public String getDomainActionType() {
        return domainActionType;
    }

    public Event setDomainActionType(String domainActionType) {
        this.domainActionType = domainActionType;
        return this;
    }

    public String getThreadState() {
        return threadState;
    }

    public Event setThreadState(String threadState) {
        this.threadState = threadState;
        return this;
    }

    public String getPaymentPrivacyUrl() {
        return paymentPrivacyUrl;
    }

    public Event setPaymentPrivacyUrl(String paymentPrivacyUrl) {
        this.paymentPrivacyUrl = paymentPrivacyUrl;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public Event setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Event setToken(String token) {
        this.token = token;
        return this;
    }

    public String getChallenge() {
        return challenge;
    }

    public Event setChallenge(String challenge) {
        this.challenge = challenge;
        return this;
    }

    public Postback getGetStarted() {
        return getStarted;
    }

    public Event setGetStarted(Postback getStarted) {
        this.getStarted = getStarted;
        return this;
    }

    public Payload[] getGreeting() {
        return greeting;
    }

    public Event setGreeting(Payload[] greetings) {
        this.greeting = greetings;
        return this;
    }

    public EventType getType() {
        return type;
    }

    public Event setType(EventType type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error("Error serializing object: ", e);
            return null;
        }
    }
}
