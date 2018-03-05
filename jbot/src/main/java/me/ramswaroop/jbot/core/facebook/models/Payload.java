package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 18/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    private String url;
    @JsonProperty("coordinates.lat")
    private String coordinatesLat;
    @JsonProperty("coordinates.long")
    private String coordinatesLong;
    @JsonProperty("is_reusable")
    private Boolean isReusable;
    @JsonProperty("attachment_id")
    private String attachmentId;
    @JsonProperty("template_type")
    private String templateType;
    @JsonProperty("intro_message")
    private String introMessage;
    private String locale;
    @JsonProperty("top_element_style")
    private String topElementStyle;
    private String text;
    @JsonProperty("recipient_name")
    private String recipientName;
    @JsonProperty("order_number")
    private String orderNumber;
    private String currency;
    @JsonProperty("base_price")
    private String basePrice;
    @JsonProperty("total_price")
    private String totalPrice;
    private String tax;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("order_url")
    private String orderUrl;
    private ShippingAddress address;
    private Summary summary;
    private Button[] buttons;
    private Element[] elements;
    private Amount[] adjustments;
    @JsonProperty("pnr_number")
    private String pnrNumber;
    @JsonProperty("checkin_url")
    private String checkinUrl;
    @JsonProperty("boarding_pass")
    private BoardingPass[] boardingPass;
    @JsonProperty("flight_info")
    private FlightInfo[] flightInfo;
    @JsonProperty("update_flight_info")
    private FlightInfo updateFlightInfo;
    private Passenger[] passengerInfo;
    private Passenger[] passengerSegmentInfo;
    @JsonProperty("price_info")
    private Amount[] priceInfo;
    private Long timestamp;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoordinatesLat() {
        return coordinatesLat;
    }

    public void setCoordinatesLat(String coordinatesLat) {
        this.coordinatesLat = coordinatesLat;
    }

    public String getCoordinatesLong() {
        return coordinatesLong;
    }

    public void setCoordinatesLong(String coordinatesLong) {
        this.coordinatesLong = coordinatesLong;
    }

    public boolean isReusable() {
        return isReusable;
    }

    public void setReusable(boolean reusable) {
        isReusable = reusable;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getIntroMessage() {
        return introMessage;
    }

    public void setIntroMessage(String introMessage) {
        this.introMessage = introMessage;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTopElementStyle() {
        return topElementStyle;
    }

    public void setTopElementStyle(String topElementStyle) {
        this.topElementStyle = topElementStyle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public ShippingAddress getAddress() {
        return address;
    }

    public void setAddress(ShippingAddress address) {
        this.address = address;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public void setButtons(Button[] buttons) {
        this.buttons = buttons;
    }

    public Element[] getElements() {
        return elements;
    }

    public void setElements(Element[] elements) {
        this.elements = elements;
    }

    public Amount[] getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(Amount[] adjustments) {
        this.adjustments = adjustments;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getCheckinUrl() {
        return checkinUrl;
    }

    public void setCheckinUrl(String checkinUrl) {
        this.checkinUrl = checkinUrl;
    }

    public BoardingPass[] getBoardingPass() {
        return boardingPass;
    }

    public void setBoardingPass(BoardingPass[] boardingPass) {
        this.boardingPass = boardingPass;
    }

    public FlightInfo[] getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(FlightInfo[] flightInfo) {
        this.flightInfo = flightInfo;
    }

    public FlightInfo getUpdateFlightInfo() {
        return updateFlightInfo;
    }

    public void setUpdateFlightInfo(FlightInfo updateFlightInfo) {
        this.updateFlightInfo = updateFlightInfo;
    }

    public Passenger[] getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(Passenger[] passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public Passenger[] getPassengerSegmentInfo() {
        return passengerSegmentInfo;
    }

    public void setPassengerSegmentInfo(Passenger[] passengerSegmentInfo) {
        this.passengerSegmentInfo = passengerSegmentInfo;
    }

    public Amount[] getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(Amount[] priceInfo) {
        this.priceInfo = priceInfo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
