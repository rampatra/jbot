package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 15/10/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Button {
    
    private String type;
    private String url;
    private String title;
    private String payload;
    @JsonProperty("webview_height_ratio")
    private String webviewHeightRatio;
    @JsonProperty("messenger_extensions")
    private boolean messengerExtensions;
    @JsonProperty("fallback_url")
    private String fallbackUrl;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("payment_summary")
    private PaymentSummary paymentSummary;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getWebviewHeightRatio() {
        return webviewHeightRatio;
    }

    public void setWebviewHeightRatio(String webviewHeightRatio) {
        this.webviewHeightRatio = webviewHeightRatio;
    }

    public boolean isMessengerExtensions() {
        return messengerExtensions;
    }

    public void setMessengerExtensions(boolean messengerExtensions) {
        this.messengerExtensions = messengerExtensions;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PaymentSummary getPaymentSummary() {
        return paymentSummary;
    }

    public void setPaymentSummary(PaymentSummary paymentSummary) {
        this.paymentSummary = paymentSummary;
    }
}
