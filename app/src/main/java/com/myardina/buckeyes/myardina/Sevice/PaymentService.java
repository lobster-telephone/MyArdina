package com.myardina.buckeyes.myardina.Sevice;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.DTO.PendingPaymentDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface PaymentService extends BaseService {

    /**
     * Insert payment recorded into the database.
     */
    void savePayment(PaymentDTO paymentDTO);

    /**
     * Retrieves all payments from the patients that still have a doctor to pay.
     */
    List<PendingPaymentDTO> retrievePendingPayments(DataSnapshot dataSnapshot);

    /**
     * Update a payment to say that the doctor has been paid for the visit.
     */
    void updatePaidPendingPayment(PaymentDTO paymentDTO);

    /**
     * Update a payment to include the doctorId in it.
     */
    void updatePaymentWithDoctor(PaymentDTO paymentDTO);
}
