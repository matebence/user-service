package com.blesk.userservice.Service.Payments;

import com.blesk.userservice.Model.Payments;
import com.stripe.exception.StripeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentsService {

    Payments createPayment(Payments payments, boolean su) throws StripeException;

    Payments createRefund(Payments payments, boolean su) throws StripeException;

    Boolean deletePayment(Payments payments, boolean su);

    Boolean updatePayment(Payments payment, Payments payments);

    Payments getPayment(Long paymentId, boolean su);

    Payments findPaymentByCreditCard(String iban, boolean isDeleted);

    List<Payments> getAllPayments(int pageNumber, int pageSize, boolean su);

    Map<String, Object> searchForPayment(HashMap<String, HashMap<String, String>> criterias, boolean su);
}