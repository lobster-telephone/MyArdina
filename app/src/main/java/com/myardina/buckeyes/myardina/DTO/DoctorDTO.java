package com.myardina.buckeyes.myardina.DTO;

/**
 * Created by Tyler on 11/5/2016.
 */
public class DoctorDTO extends UserDTO {

    private String location;
    private String requesterPhoneNumber;
    private boolean available;
    private boolean verifiedDoctor;
    private boolean requested;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequesterPhoneNumber() {
        return requesterPhoneNumber;
    }

    public void setRequesterPhoneNumber(String requesterPhoneNumber) {
        this.requesterPhoneNumber = requesterPhoneNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isVerifiedDoctor() {
        return verifiedDoctor;
    }

    public void setVerifiedDoctor(boolean verifiedDoctor) {
        this.verifiedDoctor = verifiedDoctor;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }
}
