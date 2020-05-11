package com.blesk.userservice.Model;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@RedisHash("Caches")
public class Caches implements Serializable {

    @Id private Long accountId;

    private String userName;

    private String email;

    public Caches(Long accountId, String userName, String email) {
        this.accountId = accountId;
        this.userName = userName;
        this.email = email;
    }

    public Caches(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Caches() {
    }

    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
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