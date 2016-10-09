package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 26/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    
    private User sender;
    private User recipient;
    private long timestamp;
    private Message message;
    private Postback postback;
    private Optin optin;
    @JsonProperty("account_linking")
    private AccountLinking accountLinking;
    private Read read;
    private Delivery delivery;
    @JsonProperty("checkout_update")
    private CheckoutUpdate checkoutUpdate;
    private Payment payment;

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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Postback getPostback() {
        return postback;
    }

    public void setPostback(Postback postback) {
        this.postback = postback;
    }

    public Optin getOptin() {
        return optin;
    }

    public void setOptin(Optin optin) {
        this.optin = optin;
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
}
