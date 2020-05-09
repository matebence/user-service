package com.blesk.userservice.Proxy;

import com.blesk.userservice.Model.MySQL.Users;
import feign.Headers;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;

@Repository
@RibbonClient(name = "account-service")
@FeignClient(name = "gateway-server", fallback = AccountsServiceProxyFallback.class)
public interface AccountsServiceProxy {

    @GetMapping("account-service/api/accounts/{accountId}")
    @Headers("Content-Type: application/json")
    EntityModel<Users> retrieveAccounts(@PathVariable("accountId") long accountId);

    @GetMapping("account-service/api/accounts/page/{pageNumber}/limit/{pageSize}")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> retrieveAllAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize);

    @PostMapping("account-service/api/accounts/join/{columName}")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> joinAccounts(@PathVariable("columName") String columName, @RequestBody List<Long> ids);

    @PostMapping("account-ssservice/api/accounts/search")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search);
}

@Component
class AccountsServiceProxyFallback implements AccountsServiceProxy {

    @Override
    public EntityModel<Users> retrieveAccounts(long accountId) {
        throw new NotImplementedException();
    }

    @Override
    public CollectionModel<Users> retrieveAllAccounts(int pageNumber, int pageSize) {
        throw new NotImplementedException();
    }

    @Override
    public CollectionModel<Users> joinAccounts(String columName, List<Long> ids) {
        System.out.println("here");
        return null;
    }

    @Override
    public CollectionModel<Users> searchForAccounts(HashMap<String, HashMap<String, String>> search) {
        throw new NotImplementedException();
    }
}