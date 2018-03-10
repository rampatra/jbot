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

    public Payload setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCoordinatesLat() {
        return coordinatesLat;
    }

    public Payload setCoordinatesLat(String coordinatesLat) {
        this.coordinatesLat = coordinatesLat;
        return this;
    }

    public String getCoordinatesLong() {
        return coordinatesLong;
    }

    public Payload setCoordinatesLong(String coordinatesLong) {
        this.coordinatesLong = coordinatesLong;
        return this;
    }

    public Boolean isReusable() {
        return isReusable;
    }

    public Payload setReusable(Boolean reusable) {
        isReusable = reusable;
        return this;
    }

    public Boolean getReusable() {
        return isReusable;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public Payload setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public String getTemplateType() {
        return templateType;
    }

    public Payload setTemplateType(String templateType) {
        this.templateType = templateType;
        return this;
    }

    public String getIntroMessage() {
        return introMessage;
    }

    public Payload setIntroMessage(String introMessage) {
        this.introMessage = introMessage;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public Payload setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public String getTopElementStyle() {
        return topElementStyle;
    }

    public Payload setTopElementStyle(String topElementStyle) {
        this.topElementStyle = topElementStyle;
        return this;
    }

    public String getText() {
        return text;
    }

    public Payload setText(String text) {
        this.text = text;
        return this;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public Payload setRecipientName(String recipientName) {
        this.recipientName = recipientName;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Payload setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Payload setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public Payload setBasePrice(String basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public Payload setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getTax() {
        return tax;
    }

    public Payload setTax(String tax) {
        this.tax = tax;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Payload setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public Payload setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
        return this;
    }

    public ShippingAddress getAddress() {
        return address;
    }

    public Payload setAddress(ShippingAddress address) {
        this.address = address;
        return this;
    }

    public Summary getSummary() {
        return summary;
    }

    public Payload setSummary(Summary summary) {
        this.summary = summary;
        return this;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public Payload setButtons(Button[] buttons) {
        this.buttons = buttons;
        return this;
    }

    public Element[] getElements() {
        return elements;
    }

    public Payload setElements(Element[] elements) {
        this.elements = elements;
        return this;
    }

    public Amount[] getAdjustments() {
        return adjustments;
    }

    public Payload setAdjustments(Amount[] adjustments) {
        this.adjustments = adjustments;
        return this;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public Payload setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
        return this;
    }

    public String getCheckinUrl() {
        return checkinUrl;
    }

    public Payload setCheckinUrl(String checkinUrl) {
        this.checkinUrl = checkinUrl;
        return this;
    }

    public BoardingPass[] getBoardingPass() {
        return boardingPass;
    }

    public Payload setBoardingPass(BoardingPass[] boardingPass) {
        this.boardingPass = boardingPass;
        return this;
    }

    public FlightInfo[] getFlightInfo() {
        return flightInfo;
    }

    public Payload setFlightInfo(FlightInfo[] flightInfo) {
        this.flightInfo = flightInfo;
        return this;
    }

    public FlightInfo getUpdateFlightInfo() {
        return updateFlightInfo;
    }

    public Payload setUpdateFlightInfo(FlightInfo updateFlightInfo) {
        this.updateFlightInfo = updateFlightInfo;
        return this;
    }

    public Passenger[] getPassengerInfo() {
        return passengerInfo;
    }

    public Payload setPassengerInfo(Passenger[] passengerInfo) {
        this.passengerInfo = passengerInfo;
        return this;
    }

    public Passenger[] getPassengerSegmentInfo() {
        return passengerSegmentInfo;
    }

    public Payload setPassengerSegmentInfo(Passenger[] passengerSegmentInfo) {
        this.passengerSegmentInfo = passengerSegmentInfo;
        return this;
    }

    public Amount[] getPriceInfo() {
        return priceInfo;
    }

    public Payload setPriceInfo(Amount[] priceInfo) {
        this.priceInfo = priceInfo;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Payload setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
