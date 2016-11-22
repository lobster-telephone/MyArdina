package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;

/**
 * @author by Tyler on 10/23/2016.
 */
public abstract class UserDTO extends BaseDTO {

    private String firstName;
    private String lastName;
    private String userAccountId;
    private String email;
    private String userKey;

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

    public String getUserAccountId() { return userAccountId; }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    // PARCEL OBJECT

    public UserDTO() { super(); }

    public UserDTO(Parcel in){
        super(in);
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.userAccountId = in.readString();
        this.email = in.readString();
        this.userKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.userAccountId);
        dest.writeString(this.email);
        dest.writeString(this.userKey);
    }
}
