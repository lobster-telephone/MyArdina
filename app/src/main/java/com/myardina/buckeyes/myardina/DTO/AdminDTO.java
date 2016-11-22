package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class AdminDTO extends UserDTO {
    private boolean verifiedAdmin;

    public boolean isVerifiedAdmin() { return verifiedAdmin; }

    public void setVerifiedAdmin(boolean verifiedAdmin) { this.verifiedAdmin = verifiedAdmin; }

    // PARCEL OBJECT

    public AdminDTO() {
        super();
        verifiedAdmin = false;
    }

    public AdminDTO(Parcel in){
        super(in);
        this.verifiedAdmin = in.readByte() != 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (verifiedAdmin ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AdminDTO createFromParcel(Parcel in) {
            return new AdminDTO(in);
        }

        public AdminDTO[] newArray(int size) {
            return new AdminDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
