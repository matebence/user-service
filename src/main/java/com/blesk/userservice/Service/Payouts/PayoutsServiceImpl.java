package com.blesk.userservice.Service.Payouts;

import com.blesk.userservice.DAO.Payouts.PayoutsDAOImpl;
import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Payouts;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Service.Accounts.AccountServiceImpl;
import com.blesk.userservice.Service.Emails.EmailsServiceImpl;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.LockModeType;
import java.util.*;

@Service
public class PayoutsServiceImpl implements PayoutsService {

    @Value("${stripe.key.secret}")
    private String secretKey;

    private PayoutsDAOImpl payoutsDAO;

    private UsersDAOImpl usersDAO;

    private AccountServiceImpl accountService;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PayoutsServiceImpl(PayoutsDAOImpl payoutsDAO, UsersDAOImpl usersDAO, AccountServiceImpl accountService, EmailsServiceImpl emailsService) {
        this.payoutsDAO = payoutsDAO;
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
    public Payouts createPayout(Payouts payouts) {
        Users users = this.accountService.getUser(payouts.getUsers().getAccountId());
        payouts.setUsers(users);

        if (users.getBalance() < payouts.getAmount()) return null;
        users.setBalance(users.getBalance() - payouts.getAmount());

        Payouts payout = this.payoutsDAO.save(payouts);
        if ((!this.usersDAO.update(users)) && (payout == null)) return new Payouts();

        this.emailsService.sendHtmlMesseage("Úspešná tranzakcia", "payouts", new HashMap<>(), users);
        return payout;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePayout(Payouts payouts) {
        return this.payoutsDAO.delete(payouts);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePayout(Payouts payout, Payouts payouts) {
        Users users = usersDAO.getItemByColumn(Users.class, "userId", payout.getUsers().getUserId().toString());
        if (users.getBalance() < payout.getAmount()) return null;

        payout.setUsers(payouts.getUsers());
        payout.setIban(payouts.getIban());
        payout.setAmount(payouts.getAmount());
        payout.setAccapted(payouts.getAccapted());
        users.setBalance((users.getBalance()+payout.getAmount()) - payouts.getAmount());

        return this.payoutsDAO.update(payout);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payouts getPayout(Long payoutId) {
        return this.payoutsDAO.getItemByColumn(Payouts.class, "payoutId", payoutId.toString());
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payouts findPayoutByIban(String iban) {
        return this.payoutsDAO.getItemByColumn(Payouts.class, "iban", iban);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Payouts> getAllPayouts(int pageNumber, int pageSize) {
        return this.payoutsDAO.getAll(Payouts.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Payouts> getPayoutsForJoin(List<Long> ids, String columName) {
        return this.payoutsDAO.getJoinValuesByColumn(Payouts.class, ids, columName);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPayout(HashMap<String, HashMap<String, String>> criterias) {
        return this.payoutsDAO.searchBy(Payouts.class, criterias);
    }
}