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


}
