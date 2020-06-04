package com.blesk.userservice.Service.Payments;

import com.blesk.userservice.DAO.Payments.PaymentsDAOImpl;
import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Payments;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Service.Accounts.AccountServiceImpl;
import com.blesk.userservice.Service.Emails.EmailsServiceImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Token;
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

    private UsersDAOImpl usersDAO;

    private AccountServiceImpl accountService;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PaymentsServiceImpl(PaymentsDAOImpl paymentsDAO, UsersDAOImpl usersDAO, AccountServiceImpl accountService, EmailsServiceImpl emailsService) {
        this.paymentsDAO = paymentsDAO;
        this.usersDAO = usersDAO;
        this.accountService = accountService;
        this.emailsService = emailsService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Payments createPayment(Payments payments) throws StripeException {
        Users users = this.accountService.getUser(payments.getUsers().getUserId());

        if (payments.getAmount() < 10) return null;
        Map<String, Object> creditCard = new HashMap<>();
        creditCard.put("number", payments.getCreditCard());
        creditCard.put("exp_month", payments.getExpMonth());
        creditCard.put("exp_year", payments.getExpYear());
        creditCard.put("cvc", payments.getCvc());
        Map<String, Object> creditCardSummary = new HashMap<>();
        creditCardSummary.put("card", creditCard);
        Token token = Token.create(creditCardSummary);

        if (token.getId() == null) return null;
        Map<String, Object> paymentSummary = new HashMap<>();
        paymentSummary.put("amount", Math.round(payments.getAmount() * 100));
        paymentSummary.put("currency", payments.getCurrency());
        paymentSummary.put("source", token.getId());
        paymentSummary.put("description", String.format("User with email %s successfully  transferred money", users.getEmail()));
        Charge charge = Charge.create(paymentSummary);

        if (charge.getId() == null) return null;
        payments.setCreditCard(token.getId());
        payments.setCharge(charge.getId());
        users.setBalance(users.getBalance() + payments.getAmount());

        Payments payment = this.paymentsDAO.save(payments);
        if ((!this.usersDAO.update(users)) && (payment == null)) return new Payments();
        this.emailsService.sendHtmlMesseage("Úspešná platba", "payments", new HashMap<>(), users);
        return payment;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Payments createRefund(Payments payments) throws StripeException {
        Payments payment = this.paymentsDAO.getItemByColumn(Payments.class, "charge", payments.getCharge());

        if (payment == null) return null;
        Map<String, Object> params = new HashMap<>();
        params.put("charge", payment.getCharge());
        Refund refund = Refund.create(params);

        if (refund.getId() == null) return null;
        payment.setRefund(refund.getId());
        payment.setRefunded(refund.getStatus().equals("succeeded"));

        if ((!this.paymentsDAO.update(payment))) return new Payments();
        Users users = this.accountService.getUser(payment.getUsers().getUserId());
        this.emailsService.sendHtmlMesseage("Vrátenie platby", "refunds", new HashMap<>(), users);
        return payment;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePayment(Payments payments) {
        return this.paymentsDAO.delete(payments);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePayment(Payments payment, Payments payments) {
        payment.setUsers(payments.getUsers());
        payment.setCreditCard(payments.getCreditCard());
        payment.setCharge(payments.getCharge());
        payment.setRefund(payments.getRefund());

        return this.paymentsDAO.update(payment);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payments getPayment(Long paymentId) {
        return this.paymentsDAO.get(Payments.class, "paymentId", paymentId);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payments findPaymentByCreditCard(String creditCard) {
        return this.paymentsDAO.getItemByColumn(Payments.class, "creditCard", creditCard);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Payments> getAllPayments(int pageNumber, int pageSize) {
        return this.paymentsDAO.getAll(Payments.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPayment(HashMap<String, HashMap<String, String>> criterias) {
        return this.paymentsDAO.searchBy(Payments.class, criterias);
    }
}