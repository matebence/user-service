package com.blesk.userservice.Service.Payments;

import com.blesk.userservice.Model.Payments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentsService {

    Payments createPayment(Payments payments);

    Payments createRefund(Payments payments);

    Boolean deletePayment(Long paymentId);

    Boolean updatePayment(Payments payments);

    Payments getPayment(Long genderId);

    Payments findPaymentByCreditCard(String creditCard);

    List<Payments> getAllPayments(int pageNumber, int pageSize);

    Map<String, Object> searchForPayment(HashMap<String, HashMap<String, String>> criteria);
}