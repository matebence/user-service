package com.blesk.userservice.Proxy;

import com.blesk.userservice.Model.Users;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Repository
@FeignClient(name = "account-service", url = "192.168.99.100:7000/api")
public interface AccountsServiceProxy {

    @GetMapping("/accounts/{accountId}")
    @Headers("Content-Type: application/json")
    EntityModel<Users> retrieveAccounts(@PathVariable("accountId") long accountId);

    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> retrieveAllAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize);

    @PostMapping("/accounts/search")
    @Headers("Content-Type: application/json")
    CollectionModel<Users> searchForAccounts(@RequestBody HashMap<String, HashMap<String, String>> search);
}