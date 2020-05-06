package com.blesk.userservice.Proxy;

import com.blesk.userservice.Model.Users;
import feign.Headers;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Repository
@FeignClient(name = "gateway-server")
@RibbonClient(name = "account-service")
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

    @PostMapping("account-service/api/accounts/search")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search);
}