package com.blesk.userservice.Service.Emails;

import com.blesk.userservice.Model.Users;

import java.util.Map;

public interface EmailsService {

    void sendMessage(String subject, String text, Users users);

    void sendHtmlMesseage(String subject, String htmlfile, Map<String, Object> variables, Users users);
}