package com.blesk.userservice.Service.Accounts;

import com.blesk.userservice.DTO.Http.JoinAccountCritirias;
import com.blesk.userservice.Model.Cache.Accounts;
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
    public Users getUser(Long accountId) {
        Users users = this.usersDAO.getItemByColumn(Users.class, "accountId", accountId.toString());
        if (users == null) return null;
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", new JoinAccountCritirias(Collections.singletonList(users.getAccountId())));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        return this.performJoin(Collections.singletonList(users), accountDetails).iterator().next();
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Users> getAllUsers(int pageNumber, int pageSize) {
        List<Users> users = this.usersDAO.getAll(Users.class, pageNumber, pageSize);
        if (users == null) return null;
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", new JoinAccountCritirias(users.stream().map(Users::getAccountId).collect(Collectors.toList())));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        return this.performJoin(users, accountDetails);
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public Map<String, Object> searchForUser(HashMap<String, HashMap<String, String>> criterias) {
        ArrayList<String> roles = new ArrayList<String>();
        if (criterias.get(Keys.SEARCH) != null && criterias.get(Keys.SEARCH).get("roles") != null) roles.add(criterias.get(Keys.SEARCH).remove("roles"));
        Map<String, Object> users = this.usersDAO.searchBy(Users.class, criterias);
        if (users == null) return null;
        List<Users> user = (List<Users>) users.get("results");
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", new JoinAccountCritirias(user.stream().map(Users::getAccountId).collect(Collectors.toList()), roles));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        users.put("results", this.performJoin(user, accountDetails));
        return users;
    }

    @Override
    @Transactional
    @Lock(value = LockModeType.READ)
    public List<Users> getUsersForJoin(List<Long> ids, String columName) {
        List<Users> users = this.usersDAO.getJoinValuesByColumn(Users.class, ids, columName);
        if (users == null) return null;
        CollectionModel<Users> accountDetails = this.accountsServiceProxy.joinAccounts("accountId", new JoinAccountCritirias(users.stream().map(Users::getAccountId).collect(Collectors.toList())));
        this.cachesService.createOrUpdatCache(this.performCaching(accountDetails));
        return this.performJoin(users, accountDetails);
    }

    @Override
    public Users findUserByFirstName(String firstName) {
        return super.findUserByFirstName(firstName);
    }

    @Override
    public Users findUserByLastName(String lastName) {
        return super.findUserByLastName(lastName);
    }

    @Override
    public Users createUser(Users users) {
        return super.createUser(users);
    }

    @Override
    public Boolean deleteUser(Users users) {
        return super.deleteUser(users);
    }

    @Override
    public Boolean updateUser(Users user, Users users) {
        return super.updateUser(user, users);
    }

    private List<Users> performJoin(List<Users> users, CollectionModel<Users> accountDetails) {
        List<Users> joinedValues = new ArrayList<Users>();
        if (accountDetails != null) {
            Iterator<Users> usersIterator = users.iterator();
            Iterator<Users> accountDetailsIterator = accountDetails.getContent().iterator();

            while (usersIterator.hasNext()) {
                Users usersIteratorValue = usersIterator.next();
                while (accountDetailsIterator.hasNext()) {
                    Users accountDetailsIteratorValue = accountDetailsIterator.next();
                    if(usersIteratorValue.getAccountId().equals(accountDetailsIteratorValue.getAccountId())){
                        usersIteratorValue.setUserName(accountDetailsIteratorValue.getUserName());
                        usersIteratorValue.setEmail(accountDetailsIteratorValue.getEmail());
                        joinedValues.add(usersIteratorValue);
                    }
                }
                accountDetailsIterator = accountDetails.getContent().iterator();
            }
            users = null;
            accountDetails = null;
            return joinedValues;
        } else {
            return Collections.emptyList();
        }
    }

    private List<Accounts> performCaching(CollectionModel<Users> accountDetails){
        List<Accounts> accounts = new ArrayList<>();
        for (Users user : accountDetails.getContent()) {
            if (user.getCached()) break;

            Accounts account = new Accounts();
            account.setAccountId(user.getAccountId().toString());
            account.setEmail(user.getEmail());
            account.setUserName(user.getUserName());
            accounts.add(account);
        }
        return accounts;
    }
}