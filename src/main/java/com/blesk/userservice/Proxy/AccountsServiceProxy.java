package com.blesk.userservice.Proxy;

import com.blesk.userservice.DTO.Http.JoinAccountCritirias;
import com.blesk.userservice.Model.Cache.Accounts;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Model.Users;
import com.blesk.userservice.Service.Accounts.Cache.CacheServiceImpl;
import com.blesk.userservice.Value.Messages;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

@Repository
@FeignClient(name = "account-service", fallback = AccountsServiceProxyFallback.class)
@RibbonClient(name = "account-service")
public interface AccountsServiceProxy {

    @GetMapping("api/accounts/{accountId}")
    @Headers("Content-Type: application/json")
    EntityModel<Users> retrieveAccounts(@PathVariable("accountId") long accountId);

    @GetMapping("api/accounts/page/{pageNumber}/limit/{pageSize}")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> retrieveAllAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize);

    @PostMapping("api/accounts/join/{columName}")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> joinAccounts(@PathVariable("columName") String columName, @RequestBody JoinAccountCritirias joinAccountCritirias);

    @PostMapping("api/accounts/search")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search);
}

@Component
class AccountsServiceProxyFallback implements AccountsServiceProxy {

    private CacheServiceImpl cachesService;

    @Autowired
    public AccountsServiceProxyFallback(CacheServiceImpl cachesService) {
        this.cachesService = cachesService;
    }

    @Override
    public CollectionModel<Users> joinAccounts(String columName, JoinAccountCritirias joinAccountCritirias) {
        Iterable<Accounts> caches = this.cachesService.getAllCache(joinAccountCritirias.getIds());
        List<Users> users = new ArrayList<>();
        caches.forEach(cache -> {
            users.add(new Users(true, Long.parseLong(cache.getAccountId()), cache.getUserName(), cache.getEmail()));
        });

        if (users.isEmpty()) throw new UserServiceException(Messages.SERVER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
        return new CollectionModel<Users>(users);
    }

    @Override
    public EntityModel<Users> retrieveAccounts(long accountId) {
        throw new NotImplementedException();
    }

    @Override
    public CollectionModel<Users> retrieveAllAccounts(int pageNumber, int pageSize) {
        throw new NotImplementedException();
    }

    @Override
    public CollectionModel<Users> searchForAccounts(HashMap<String, HashMap<String, String>> search) {
        throw new NotImplementedException();
    }
}