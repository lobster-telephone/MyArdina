package com.myardina.buckeyes.myardina.Sevice.Impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.Impl.PaymentDAOImpl;
import com.myardina.buckeyes.myardina.DAO.PaymentDAO;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.DTO.PendingPaymentDTO;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.myardina.buckeyes.myardina.Sevice.PatientService;
import com.myardina.buckeyes.myardina.Sevice.PaymentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class PaymentServiceImpl extends BaseServiceImpl implements PaymentService {

    private static final String LOG_TAG = "PAYMENT_SERVICE_LOG";

    private PaymentDAO mPaymentDAO;
    private DoctorService mDoctorService;
    private PatientService mPatientService;

    public PaymentServiceImpl() {
        mPaymentDAO = new PaymentDAOImpl();
        mDoctorService = new DoctorServiceImpl();
        mPatientService = new PatientServiceImpl();
    }

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        Log.d(LOG_TAG, "Entering savePayment...");
        mPaymentDAO.savePayment(paymentDTO);
        Log.d(LOG_TAG, "Exiting savePayment...");
    }

    @Override
    public List<PendingPaymentDTO> retrievePendingPayments(DataSnapshot snapshot) {
        Log.d(LOG_TAG, "Entering retrievePendingPayments...");
        List<PendingPaymentDTO> result = new ArrayList<>();
        DataSnapshot paymentsTable = getTableSnapshot(snapshot, CommonConstants.PAYMENTS_TABLE);
        DataSnapshot doctorsTable = getTableSnapshot(snapshot, CommonConstants.DOCTORS_TABLE);
        DataSnapshot patientsTable = getTableSnapshot(snapshot, CommonConstants.PATIENTS_TABLE);
        List<PaymentDTO> pendingPayments = mPaymentDAO.retrievePendingPayments(paymentsTable);
        for (PaymentDTO paymentDTO : pendingPayments) {
            PendingPaymentDTO pendingPaymentDTO = new PendingPaymentDTO();
            pendingPaymentDTO.setPaymentDTO(paymentDTO);
            DoctorDTO doctorDTO = (DoctorDTO) mDoctorService.retrieveFromId(doctorsTable, paymentDTO.getDoctorId());
            pendingPaymentDTO.setDoctorDTO(doctorDTO);
            PatientDTO patientDTO = (PatientDTO) mPatientService.retrieveFromId(patientsTable, paymentDTO.getPatientId());
            pendingPaymentDTO.setPatientDTO(patientDTO);
            result.add(pendingPaymentDTO);
        }

        Log.d(LOG_TAG, "Exiting retrievePendingPayments...");
        return result;
    }

    @Override
    public void updatePaidPendingPayment(PaymentDTO paymentDTO) {
        Log.d(LOG_TAG, "Entering updatePaidPendingPayment...");
        mPaymentDAO.updatePaidPendingPayment(paymentDTO);
        Log.d(LOG_TAG, "Exiting updatePaidPendingPayment...");
    }

    @Override
    public void updatePaymentWithDoctor(PaymentDTO paymentDTO) {
        Log.d(LOG_TAG, "Entering updatePaymentWithDoctor...");
        mPaymentDAO.updatePaymentWithDoctor(paymentDTO);
        Log.d(LOG_TAG, "Entering updatePaymentWithDoctor...");
    }
}
