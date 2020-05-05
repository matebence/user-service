package com.blesk.userservice.Controller;

import com.blesk.userservice.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class UserController {

    @Autowired
    private AccountServiceProxy accountServiceProxy;

    @GetMapping("/test")
    public CollectionModel<Users> testing(){
        return accountServiceProxy.retrieveAllAccounts(0, 2);
    }
}