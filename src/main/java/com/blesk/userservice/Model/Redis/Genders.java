package com.blesk.userservice.Model.Redis;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Genders")
public class Genders implements Serializable {

    private Long genderId;

    private String name;

    public Genders() {
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}