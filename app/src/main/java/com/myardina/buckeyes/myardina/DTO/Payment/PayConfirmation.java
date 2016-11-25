package com.myardina.buckeyes.myardina.DTO.Payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/21/2016.
 */
public class PayConfirmation implements Parcelable {
    private String environment;
    private Pay payment;
    private ProofOfPay proofOfPayment;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Pay getPayment() {
        return payment;
    }

    public void setPayment(Pay payment) {
        this.payment = payment;
    }

    public ProofOfPay getProofOfPayment() {
        return proofOfPayment;
    }

    public void setProofOfPayment(ProofOfPay proofOfPayment) {
        this.proofOfPayment = proofOfPayment;
    }

    // PARCEL OBJECT

    public PayConfirmation() {}

    public PayConfirmation(PaymentConfirmation confirm) {
        this.environment = confirm.getEnvironment();
        this.payment = new Pay(confirm);
        this.proofOfPayment = new ProofOfPay(confirm);
    }

    public PayConfirmation(Parcel in){
        this.environment = in.readString();
        this.payment = in.readParcelable(Pay.class.getClassLoader());
        this.proofOfPayment = in.readParcelable(ProofOfPay.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(environment);
        dest.writeParcelable(payment, flags);
        dest.writeParcelable(proofOfPayment, flags);
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
