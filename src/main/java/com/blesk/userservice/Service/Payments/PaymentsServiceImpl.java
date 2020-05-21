package com.blesk.userservice.Service.Payments;

import com.blesk.userservice.DAO.Payments.PaymentsDAOImpl;
import com.blesk.userservice.DAO.Payouts.PayoutsDAOImpl;
import com.blesk.userservice.Model.Payments;
import com.blesk.userservice.Service.Emails.EmailsServiceImpl;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    private PaymentsDAOImpl paymentsDAO;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PaymentsServiceImpl(PaymentsDAOImpl paymentsDAO, EmailsServiceImpl emailsService) {
        this.paymentsDAO = paymentsDAO;
        this.emailsService = emailsService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Payments createPayment(Payments payments) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Payments createRefund(Payments payments) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePayment(Long paymentId) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePayment(Payments payments) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payments getPayment(Long genderId) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payments findPaymentByCreditCard(String creditCard) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Payments> getAllPayments(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPayment(HashMap<String, HashMap<String, String>> criteria) {
        return null;
    }
}