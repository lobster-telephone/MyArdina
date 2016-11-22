package com.myardina.buckeyes.myardina.DTO.Payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/21/2016.
 */
public class Pay implements Parcelable {
    private String amountAsLocalizedString;
    private boolean enablePayPalShippingAddressesRetrieval;
    private boolean noShipping;
    private boolean processable;

    public String getAmountAsLocalizedString() {
        return amountAsLocalizedString;
    }

    public void setAmountAsLocalizedString(String amountAsLocalizedString) {
        this.amountAsLocalizedString = amountAsLocalizedString;
    }

    public boolean isEnablePayPalShippingAddressesRetrieval() {
        return enablePayPalShippingAddressesRetrieval;
    }

    public void setEnablePayPalShippingAddressesRetrieval(boolean enablePayPalShippingAddressesRetrieval) {
        this.enablePayPalShippingAddressesRetrieval = enablePayPalShippingAddressesRetrieval;
    }

    public boolean isNoShipping() {
        return noShipping;
    }

    public void setNoShipping(boolean noShipping) {
        this.noShipping = noShipping;
    }

    public boolean isProcessable() {
        return processable;
    }

    public void setProcessable(boolean processable) {
        this.processable = processable;
    }

    // PARCEL OBJECT

    public Pay() {}

    public Pay(PaymentConfirmation confirm) {
        this.amountAsLocalizedString = confirm.getPayment().getAmountAsLocalizedString();
        this.enablePayPalShippingAddressesRetrieval = confirm.getPayment().isEnablePayPalShippingAddressesRetrieval();
        this.noShipping = confirm.getPayment().isNoShipping();
        this.processable = confirm.getPayment().isProcessable();
    }

    public Pay(Parcel in){
        this.amountAsLocalizedString = in.readString();
        this.enablePayPalShippingAddressesRetrieval = in.readByte() != 0;
        this.noShipping = in.readByte() != 0;
        this.processable = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amountAsLocalizedString);
        dest.writeByte((byte) (enablePayPalShippingAddressesRetrieval ? 1 : 0));
        dest.writeByte((byte) (noShipping ? 1 : 0));
        dest.writeByte((byte) (processable ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Pay createFromParcel(Parcel in) {
            return new Pay(in);
        }

        public Pay[] newArray(int size) {
            return new Pay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
