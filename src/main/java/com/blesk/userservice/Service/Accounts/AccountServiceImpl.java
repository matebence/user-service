package com.blesk.userservice.Service.Accounts;

import com.blesk.userservice.Cache.Accounts;
import com.blesk.userservice.DAO.Users.UsersDAOImpl;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Proxy.AccountsServiceProxy;
import com.blesk.userservice.Service.Accounts.Cache.CacheServiceImpl;
import com.blesk.userservice.Service.Users.UsersServiceImpl;
import com.blesk.userservice.Value.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl extends UsersServiceImpl implements AccountService {

    private CacheServiceImpl cachesService;

    private AccountsServiceProxy accountsServiceProxy;

    private UsersDAOImpl usersDAO;

    @Autowired
    public AccountServiceImpl(UsersDAOImpl usersDAO, CacheServiceImpl cachesService, AccountsServiceProxy accountsServiceProxy){
        this.usersDAO = usersDAO;
        this.cachesService = cachesService;
        this.accountsServiceProxy = accountsServiceProxy;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Users getUser(Long userId, boolean su) {
        Users users;
        if (su) {
            users = this.usersDAO.getItemByColumn(Users.class, "userId", userId.toString());
        } else {
            users =  this.usersDAO.getItemByColumn("userId", userId.toString(), false);
        }
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", Collections.singletonList(users.getAccountId()));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        return this.performJoin(Collections.singletonList(users), accountDetails).iterator().next();
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Users> getAllUsers(int pageNumber, int pageSize, boolean su) {
        List<Users> users;
        if (su) {
            users = this.usersDAO.getAll(Users.class, pageNumber, pageSize);
        } else {
            users = this.usersDAO.getAll(pageNumber, pageSize, false);
        }
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", users.stream().map(Users::getAccountId).collect(Collectors.toList()));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        return this.performJoin(users, accountDetails);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criteria, boolean su) {
        Map<String, Object> users;
        if (su) {
            users = this.usersDAO.searchBy(Users.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
        } else {
            users = this.usersDAO.searchBy(criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), false);
        }
        List<Users> user = (List<Users>) users.get("results");
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", user.stream().map(Users::getAccountId).collect(Collectors.toList()));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        users.put("results", this.performJoin(user, accountDetails));
        return users;
    }

    @Override
    public Users findUserByFirstName(String firstName, boolean isDeleted) {
        return super.findUserByFirstName(firstName, isDeleted);
    }

    @Override
    public Users findUserByLastName(String lastName, boolean isDeleted) {
        return super.findUserByLastName(lastName, isDeleted);
    }

    @Override
    public Users createUser(Users users) {
        return super.createUser(users);
    }

    @Override
    public Boolean deleteUser(Users users, boolean su) {
        return super.deleteUser(users , su);
    }

    @Override
    public Boolean updateUser(Users user, Users users) {
        return super.updateUser(user, users);
    }

    private List<Users> performJoin(List<Users> users, CollectionModel<Users> accountDetails) {
        if (accountDetails != null && accountDetails.getContent().size() == users.size()) {
            Iterator<Users> usersIterator = users.iterator();
            Iterator<Users> accountDetailsIterator = accountDetails.iterator();

            while (usersIterator.hasNext() && accountDetailsIterator.hasNext()) {
                Users usersIteratorValue = usersIterator.next();
                Users accountDetailsIteratorValue = accountDetailsIterator.next();
                usersIteratorValue.setUserName(accountDetailsIteratorValue.getUserName());
                usersIteratorValue.setEmail(accountDetailsIteratorValue.getEmail());
            }
            return users;
        } else {
            return Collections.emptyList();
        }
    }

    private List<Accounts> performCaching(CollectionModel<Users> accountDetails){
        List<Accounts> accounts = new ArrayList<>();
        for (Users user : accountDetails.getContent()) {
            if (user.getCached()) break;

            Accounts account = new Accounts();
            account.setAccountId(user.getAccountId());
            account.setEmail(user.getEmail());
            account.setUserName(user.getUserName());
            accounts.add(account);
        }
        return accounts;
    }
}