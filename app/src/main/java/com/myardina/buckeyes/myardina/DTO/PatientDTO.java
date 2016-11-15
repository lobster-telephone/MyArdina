package com.myardina.buckeyes.myardina.DTO;

/**
 * Created by Tyler on 11/5/2016.
 */
public class PatientDTO extends UserDTO {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
