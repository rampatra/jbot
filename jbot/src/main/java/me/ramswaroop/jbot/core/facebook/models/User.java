package me.ramswaroop.jbot.core.facebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ramswaroop
 * @version 17/09/2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("profile_pic")
    private String profilePic;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String locale;
    private Double timezone;
    private String gender;
    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;
    @JsonProperty("contact_name")
    private String contactName;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("contact_phone")
    private String contactPhone;    

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public User setProfilePic(String profilePic) {
        this.profilePic = profilePic;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public User setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public Double getTimezone() {
        return timezone;
    }

    public User setTimezone(Double timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public User setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public String getContactName() {
        return contactName;
    }

    public User setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public User setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public User setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }
}
