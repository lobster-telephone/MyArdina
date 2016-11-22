package com.myardina.buckeyes.myardina.DTO.Payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/21/2016.
 */
public class ProofOfPay implements Parcelable {
    private String createTime;
    private String intent;
    private String paymentId;
    private String state;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // PARCEL OBJECT

    public ProofOfPay() {}

    public ProofOfPay(PaymentConfirmation confirm) {
        this.createTime = confirm.getProofOfPayment().getCreateTime();
        this.intent = confirm.getProofOfPayment().getIntent();
        this.paymentId = confirm.getProofOfPayment().getPaymentId();
        this.state = confirm.getProofOfPayment().getState();
    }

    public ProofOfPay(Parcel in){
        this.createTime = in.readString();
        this.intent = in.readString();
        this.paymentId = in.readString();
        this.state = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTime);
        dest.writeString(intent);
        dest.writeString(paymentId);
        dest.writeString(state);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ProofOfPay createFromParcel(Parcel in) {
            return new ProofOfPay(in);
        }

        public ProofOfPay[] newArray(int size) {
            return new ProofOfPay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
