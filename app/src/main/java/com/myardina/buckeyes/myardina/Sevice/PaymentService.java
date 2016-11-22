package com.myardina.buckeyes.myardina.Sevice;

import com.myardina.buckeyes.myardina.DTO.Payment.PayConfirmation;
import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public interface PaymentService extends BaseService {
    PayConfirmation convertPayConfirm(PaymentConfirmation confirm);
}
