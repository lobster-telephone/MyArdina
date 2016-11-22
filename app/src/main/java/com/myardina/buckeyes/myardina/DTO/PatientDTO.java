package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

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

    // PARCEL OBJECT

    public PatientDTO() { super(); }

    public PatientDTO(Parcel in){
        super(in);
        this.phoneNumber = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(phoneNumber);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PatientDTO createFromParcel(Parcel in) {
            return new PatientDTO(in);
        }

        public PatientDTO[] newArray(int size) {
            return new PatientDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
