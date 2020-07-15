package com.blesk.userservice.DTO.Http;

import java.util.List;

public class JoinAccountCritirias {

    private List<Long> ids;
    private List<String> roles;

    public JoinAccountCritirias(List<Long> ids, List<String> roles) {
        this.ids = ids;
        this.roles = roles;
    }

    public JoinAccountCritirias(List<Long> ids) {
        this.ids = ids;
    }

    public JoinAccountCritirias() {
    }

    public List<Long> getIds() {
        return this.ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}