package com.blesk.userservice.DTO.Mapper;

public class JwtMapper {

    private Integer account_id;

    private String user_name;

    private Long exp;

    private String jti;

    private String client_id;

    public JwtMapper() {
    }

    public Integer getAccount_id() {
        return this.account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getExp() {
        return this.exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getJti() {
        return this.jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getClient_id() {
        return this.client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}