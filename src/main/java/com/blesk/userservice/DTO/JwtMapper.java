package com.blesk.userservice.DTO;

import java.util.Collection;

public class JwtMapper {

    private Integer login_id;

    private Integer account_id;

    private String user_name;

    private Long exp;

    private String jti;

    private String client_id;

    private Boolean activated;

    private Collection<String> grantedPrivileges;

    public JwtMapper() {
    }

    public JwtMapper(Collection<String> grantedPrivileges) {
        this.grantedPrivileges = grantedPrivileges;
    }

    public Integer getLogin_id() {
        return this.login_id;
    }

    public void setLogin_id(Integer login_id) {
        this.login_id = login_id;
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

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Collection<String> getGrantedPrivileges() {
        return grantedPrivileges;
    }

    public void setGrantedPrivileges(Collection<String> grantedPrivileges) {
        this.grantedPrivileges = grantedPrivileges;
    }

    @Override
    public String toString() {
        return "JwtMapper{" +
                "login_id=" + login_id +
                ", account_id=" + account_id +
                ", user_name='" + user_name + '\'' +
                ", exp=" + exp +
                ", jti='" + jti + '\'' +
                ", client_id='" + client_id + '\'' +
                ", activated=" + activated +
                ", grantedPrivileges=" + grantedPrivileges +
                '}';
    }
}