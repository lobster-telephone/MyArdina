package com.myardina.buckeyes.myardina.DTO;

import java.io.Serializable;

/**
 * @author by Tyler on 10/23/2016.
 */
public abstract class UserDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
