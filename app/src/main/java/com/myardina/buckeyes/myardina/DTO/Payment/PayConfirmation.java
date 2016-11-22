package com.myardina.buckeyes.myardina.DTO.Payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/21/2016.
 */
public class PayConfirmation implements Parcelable {
    private String environment;
    private Pay pay;
    private ProofOfPay proofOfPay;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public ProofOfPay getProofOfPay() {
        return proofOfPay;
    }

    public void setProofOfPay(ProofOfPay proofOfPay) {
        this.proofOfPay = proofOfPay;
    }

    // PARCEL OBJECT

    public PayConfirmation() {}

    public PayConfirmation(PaymentConfirmation confirm) {
        this.environment = confirm.getEnvironment();
        this.pay = new Pay(confirm);
        this.proofOfPay = new ProofOfPay(confirm);
    }

    public PayConfirmation(Parcel in){
        this.environment = in.readString();
        this.pay = in.readParcelable(Pay.class.getClassLoader());
        this.proofOfPay = in.readParcelable(ProofOfPay.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(environment);
        dest.writeParcelable(pay, flags);
        dest.writeParcelable(proofOfPay, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PayConfirmation createFromParcel(Parcel in) {
            return new PayConfirmation(in);
        }

        public PayConfirmation[] newArray(int size) {
            return new PayConfirmation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
