package com.blesk.userservice.Model.Cache;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@RedisHash("Accounts")
public class Accounts implements Serializable {

    @Id private String accountId;

    private String userName;

    private String email;

    public Accounts(String accountId, String userName, String email) {
        this.accountId = accountId;
        this.userName = userName;
        this.email = email;
    }

    public Accounts(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Accounts() {
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}