package com.blesk.userservice.Service.Payments;

import com.blesk.userservice.Model.Payments;
import com.stripe.exception.StripeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentsService {

    Payments createPayment(Payments payments) throws StripeException;

    Payments createRefund(Payments payments) throws StripeException;

    Boolean deletePayment(Payments payments);

    Boolean updatePayment(Payments payment, Payments payments);

    Payments getPayment(Long paymentId);

    Payments findPaymentByCreditCard(String creditCard);

    List<Payments> getAllPayments(int pageNumber, int pageSize);

    Map<String, Object> searchForPayment(HashMap<String, HashMap<String, String>> criterias);
}