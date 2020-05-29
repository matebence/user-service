package com.blesk.userservice.DTO;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;

public class Response {

    private String timestamp;

    private String message;

    private boolean error;

    private HashMap<String, String> nav;

    public Response() {
    }

    {
        this.nav = new HashMap<>();
        this.nav.put("home", ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString());
    }

    public Response(String timestamp, String message, boolean error) {
        this.timestamp = timestamp;
        this.message = message;
        this.error = error;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return this.error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public HashMap getNav() {
        return this.nav;
    }

    public void setNav(String name, String url) {
        this.nav.put(name, url);
    }
}