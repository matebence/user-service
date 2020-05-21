package com.blesk.userservice.Service.Payouts;

import com.blesk.userservice.DAO.Payouts.PayoutsDAOImpl;
import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Payouts;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Proxy.AccountsServiceProxy;
import com.blesk.userservice.Service.Caches.CachesServiceImpl;
import com.blesk.userservice.Service.Emails.EmailsServiceImpl;
import com.blesk.userservice.Value.Keys;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.hateoas.CollectionModel;
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

    private AccountsServiceProxy accountsServiceProxy;

    private CachesServiceImpl cachesService;

    private EmailsServiceImpl emailsService;

    @Autowired
    public PayoutsServiceImpl(PayoutsDAOImpl payoutsDAO, UsersDAOImpl usersDAO, AccountsServiceProxy accountsServiceProxy, CachesServiceImpl cachesService, EmailsServiceImpl emailsService) {
        this.payoutsDAO = payoutsDAO;
        this.usersDAO = usersDAO;
        this.accountsServiceProxy = accountsServiceProxy;
        this.cachesService = cachesService;
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
        Users users = usersDAO.getItemByColumn(Users.class, "userId", payouts.getUsers().getUserId().toString());
        if (users.getBalance() < payouts.getAmount()) return null;
        users.setBalance(users.getBalance() - payouts.getAmount());

        Payouts payout = this.payoutsDAO.save(payouts);
        if ((!this.usersDAO.update(users)) && (payout == null)) return new Payouts();

        if (users.getEmail() == null){
            CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", Arrays.asList(new Long[]{users.getAccountId()}));
            this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
            users = this.performJoin(Collections.singletonList(users), accountDetails).iterator().next();
        }
        this.emailsService.sendHtmlMesseage("Úspešná tranzakcia", "payouts", new HashMap<>(), users);
        return payout;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean deletePayout(Payouts payouts, boolean su) {
        if (su) {
            return this.payoutsDAO.delete("payouts", "payout_id", payouts.getPayoutId());
        } else {
            return this.payoutsDAO.softDelete(payouts);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.WRITE)
    public Boolean updatePayout(Payouts payouts) {
        return this.payoutsDAO.update(payouts);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payouts getPayout(Long payoutId, boolean su) {
        if (su) {
            return this.payoutsDAO.getItemByColumn(Payouts.class,"payoutId", payoutId.toString());
        } else {
            return this.payoutsDAO.getItemByColumn("payoutId", payoutId.toString(), false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Payouts findPayoutByIban(String iban, boolean isDeleted) {
        return this.payoutsDAO.getItemByColumn("iban", iban, isDeleted);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Payouts> getAllPayouts(int pageNumber, int pageSize, boolean su) {
        if (su) {
            return this.payoutsDAO.getAll(Payouts.class, pageNumber, pageSize);
        } else {
            return this.payoutsDAO.getAll(pageNumber, pageSize, false);
        }
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForPayout(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        if (su) {
            return this.payoutsDAO.searchBy(Payouts.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            return this.payoutsDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
    }
}