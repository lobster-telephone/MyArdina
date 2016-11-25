package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.myardina.buckeyes.myardina.DTO.Payment.PayConfirmation;

/**
 * @author Tyler Lacks on 11/5/2016.
 */
public class PaymentDTO extends BaseDTO {
    private PayConfirmation paymentConfirmation;
    private String patientId;
    private String doctorId;
    private String amountOwedToDoctor;
    private boolean doctorPaid;

    public PayConfirmation getPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(PayConfirmation payment) { this.paymentConfirmation = payment; }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getAmountOwedToDoctor() {
        return amountOwedToDoctor;
    }

    public void setAmountOwedToDoctor(String amountOwedToDoctor) {
        this.amountOwedToDoctor = amountOwedToDoctor;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public boolean isDoctorPaid() {
        return doctorPaid;
    }

    public void setDoctorPaid(boolean doctorPaid) {
        this.doctorPaid = doctorPaid;
    }

    // PARCEL OBJECT

    public PaymentDTO() {
        super();
        doctorPaid = false;
    }

    public PaymentDTO(Parcel in){
        super(in);
        this.paymentConfirmation = in.readParcelable(PayConfirmation.class.getClassLoader());
        this.patientId = in.readString();
        this.doctorId = in.readString();
        this.amountOwedToDoctor = in.readString();
        this.doctorPaid = in.readByte() != 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(paymentConfirmation, flags);
        dest.writeString(patientId);
        dest.writeString(doctorId);
        dest.writeString(amountOwedToDoctor);
        dest.writeByte((byte) (doctorPaid ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PaymentDTO createFromParcel(Parcel in) {
            return new PaymentDTO(in);
        }

        public PaymentDTO[] newArray(int size) {
            return new PaymentDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
