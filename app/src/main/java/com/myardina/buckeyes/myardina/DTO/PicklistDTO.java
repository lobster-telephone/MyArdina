package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tyler Lacks on 11/19/2016.
 */
public class PicklistDTO extends BaseDTO {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // PARCEL OBJECT

    public PicklistDTO() {
        super();
    }

    public PicklistDTO(Parcel in){
        super(in);
        this.value = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(value);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PicklistDTO createFromParcel(Parcel in) {
            return new PicklistDTO(in);
        }

        public PicklistDTO[] newArray(int size) {
            return new PicklistDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
