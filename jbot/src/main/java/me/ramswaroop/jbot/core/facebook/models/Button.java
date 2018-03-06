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
    private Boolean messengerExtensions;
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

    public Button setType(String type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Button setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Button setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPayload() {
        return payload;
    }

    public Button setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public String getWebviewHeightRatio() {
        return webviewHeightRatio;
    }

    public Button setWebviewHeightRatio(String webviewHeightRatio) {
        this.webviewHeightRatio = webviewHeightRatio;
        return this;
    }

    public Boolean isMessengerExtensions() {
        return messengerExtensions;
    }

    public Button setMessengerExtensions(Boolean messengerExtensions) {
        this.messengerExtensions = messengerExtensions;
        return this;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public Button setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public Button setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Button setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public PaymentSummary getPaymentSummary() {
        return paymentSummary;
    }

    public Button setPaymentSummary(PaymentSummary paymentSummary) {
        this.paymentSummary = paymentSummary;
        return this;
    }
}
