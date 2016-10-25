package com.myardina.buckeyes.myardina;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Tyler on 10/23/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String firstName;
    private String lastName;
    private String userId;
    private String phoneNumber;
    private String available;
    private String doctor;
    private String verifiedDoctor;
    private String requested;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getVerifiedDoctor() {
        return verifiedDoctor;
    }

    public void setVerifiedDoctor(String verifiedDoctor) {
        this.verifiedDoctor = verifiedDoctor;
    }

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }
}
