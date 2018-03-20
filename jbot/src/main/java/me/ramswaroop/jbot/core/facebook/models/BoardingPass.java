package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 31/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardingPass {

    @JsonProperty("passenger_name")
    private String passengerName;
    @JsonProperty("pnr_number")
    private String pnrNumber;
    @JsonProperty("travel_class")
    private String travelClass;
    private String seat;
    @JsonProperty("logo_image_url")
    private String logoImageUrl;
    @JsonProperty("header_image_url")
    private String headerImageUrl;
    @JsonProperty("qr_code")
    private String qrCode;
    @JsonProperty("above_bar_code_image_url")
    private String aboveBarCodeImageUrl;
    @JsonProperty("auxiliary_fields")
    private Field[] auxiliaryFields;
    @JsonProperty("secondary_fields")
    private Field[] secondaryFields;

    public String getPassengerName() {
        return passengerName;
    }

    public BoardingPass setPassengerName(String passengerName) {
        this.passengerName = passengerName;
        return this;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public BoardingPass setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
        return this;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public BoardingPass setTravelClass(String travelClass) {
        this.travelClass = travelClass;
        return this;
    }

    public String getSeat() {
        return seat;
    }

    public BoardingPass setSeat(String seat) {
        this.seat = seat;
        return this;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public BoardingPass setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
        return this;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public BoardingPass setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
        return this;
    }

    public String getQrCode() {
        return qrCode;
    }

    public BoardingPass setQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public String getAboveBarCodeImageUrl() {
        return aboveBarCodeImageUrl;
    }

    public BoardingPass setAboveBarCodeImageUrl(String aboveBarCodeImageUrl) {
        this.aboveBarCodeImageUrl = aboveBarCodeImageUrl;
        return this;
    }

    public Field[] getAuxiliaryFields() {
        return auxiliaryFields;
    }

    public BoardingPass setAuxiliaryFields(Field[] auxiliaryFields) {
        this.auxiliaryFields = auxiliaryFields;
        return this;
    }

    public Field[] getSecondaryFields() {
        return secondaryFields;
    }

    public BoardingPass setSecondaryFields(Field[] secondaryFields) {
        this.secondaryFields = secondaryFields;
        return this;
    }
}
