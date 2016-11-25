package com.myardina.buckeyes.myardina.DTO;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Tyler Lacks on 11/23/2016.
 */
public class PendingPaymentDTO implements Parcelable {
    private PaymentDTO paymentDTO;
    private DoctorDTO doctorDTO;
    private PatientDTO patientDTO;

    public PaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public void setPaymentDTO(PaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
    }

    public DoctorDTO getDoctorDTO() {
        return doctorDTO;
    }

    public void setDoctorDTO(DoctorDTO doctorDTO) {
        this.doctorDTO = doctorDTO;
    }

    public PatientDTO getPatientDTO() {
        return patientDTO;
    }

    public void setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
    }

    // PARCEL OBJECT

    public PendingPaymentDTO() {}

    public PendingPaymentDTO(Parcel in){
        this.paymentDTO = in.readParcelable(PaymentDTO.class.getClassLoader());
        this.doctorDTO = in.readParcelable(DoctorDTO.class.getClassLoader());
        this.patientDTO = in.readParcelable(PatientDTO.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.paymentDTO, flags);
        dest.writeParcelable(this.doctorDTO, flags);
        dest.writeParcelable(this.patientDTO, flags);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PendingPaymentDTO createFromParcel(Parcel in) {
            return new PendingPaymentDTO(in);
        }

        public PendingPaymentDTO[] newArray(int size) {
            return new PendingPaymentDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
