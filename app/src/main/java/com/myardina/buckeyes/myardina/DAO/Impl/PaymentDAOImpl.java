package com.myardina.buckeyes.myardina.DAO.Impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.PaymentDAO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class PaymentDAOImpl extends BaseDAOImpl implements PaymentDAO {

    private static final String LOG_TAG = "PAYMENT_DAO_LOG";

    public PaymentDAOImpl() { super(); }

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        Log.d(LOG_TAG, "Entering savePayment...");
        insert2(paymentDTO, CommonConstants.PAYMENTS_TABLE);
        Log.d(LOG_TAG, "Exiting savePayment...");
    }

    @Override
    public List<PaymentDTO> retrievePendingPayments(DataSnapshot snapshot) {
        Log.d(LOG_TAG, "Entering retrieveAvailableDoctors...");
        List<PaymentDTO> pendingPayments = new ArrayList<>();
        for (DataSnapshot user : snapshot.getChildren()) {
            PaymentDTO payment = (PaymentDTO) retrieve(user, PaymentDTO.class);
            if (!payment.isDoctorPaid() && !TextUtils.isEmpty(payment.getDoctorId())) {
                pendingPayments.add(payment);
                payment.setPaymentId(user.getKey());
            }
        }
        Log.d(LOG_TAG, "Exiting retrieveAvailableDoctors...");
        return pendingPayments;
    }
}
