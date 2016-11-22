package com.myardina.buckeyes.myardina.Sevice.Impl;

import com.myardina.buckeyes.myardina.DTO.Payment.PayConfirmation;
import com.myardina.buckeyes.myardina.Sevice.PaymentService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Tyler Lacks on 11/18/2016.
 */
public class PaymentServiceImpl extends BaseServiceImpl implements PaymentService {

    @Override
    public PayConfirmation convertPayConfirm(PaymentConfirmation confirm) {
        return new PayConfirmation(confirm);
    }
}
