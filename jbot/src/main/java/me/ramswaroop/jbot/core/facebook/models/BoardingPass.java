package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 31/12/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAboveBarCodeImageUrl() {
        return aboveBarCodeImageUrl;
    }

    public void setAboveBarCodeImageUrl(String aboveBarCodeImageUrl) {
        this.aboveBarCodeImageUrl = aboveBarCodeImageUrl;
    }

    public Field[] getAuxiliaryFields() {
        return auxiliaryFields;
    }

    public void setAuxiliaryFields(Field[] auxiliaryFields) {
        this.auxiliaryFields = auxiliaryFields;
    }

    public Field[] getSecondaryFields() {
        return secondaryFields;
    }

    public void setSecondaryFields(Field[] secondaryFields) {
        this.secondaryFields = secondaryFields;
    }
}
