package com.myardina.buckeyes.myardina.DAO;

import com.google.firebase.database.DataSnapshot;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;

import java.util.List;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface PaymentDAO extends BaseDAO {

    /**
     * Insert payment recorded into the database.
     */
    void savePayment(PaymentDTO paymentDTO);

    /**
     * Retrieve the payments that Ardina has collected, but not yet paid to the doctor yet.
     */
    List<PaymentDTO> retrievePendingPayments(DataSnapshot snapshot);
}
