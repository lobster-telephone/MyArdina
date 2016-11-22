package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

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

    public void setRequesterPhoneNumber(String requesterPhoneNumber) { this.requesterPhoneNumber = requesterPhoneNumber; }

    public boolean isAvailable() { return available; }

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

    // PARCEL OBJECT

    public DoctorDTO() { super(); }

    public DoctorDTO(Parcel in){
        super(in);
        this.location = in.readString();
        this.requesterPhoneNumber = in.readString();
        this.available = in.readByte() != 0;
        this.verifiedDoctor = in.readByte() != 0;
        this.requested = in.readByte() != 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(location);
        dest.writeString(requesterPhoneNumber);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeByte((byte) (verifiedDoctor ? 1 : 0));
        dest.writeByte((byte) (requested ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DoctorDTO createFromParcel(Parcel in) {
            return new DoctorDTO(in);
        }

        public DoctorDTO[] newArray(int size) {
            return new DoctorDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
