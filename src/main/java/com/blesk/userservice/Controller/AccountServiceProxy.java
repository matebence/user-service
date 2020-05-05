package com.blesk.userservice.Controller;

import com.blesk.userservice.Model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "account-service", url = "192.168.99.100:7000/api")
public interface AccountServiceProxy {

    @GetMapping("/accounts/page/{pageNumber}/limit/{pageSize}")
    CollectionModel<Users> retrieveAllAccounts(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize);
}