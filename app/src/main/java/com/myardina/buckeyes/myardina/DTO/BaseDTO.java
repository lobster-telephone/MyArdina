package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public abstract class BaseDTO implements Parcelable {
    private String tableKey;

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    // PARCEL OBJECT

    public BaseDTO() {}

    public BaseDTO(Parcel in){
        this.tableKey = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tableKey);
    }
}
