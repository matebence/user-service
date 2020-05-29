package com.blesk.userservice.Component.Mailer;

import java.util.Map;

public interface HtmlMailer {

    String generateMailHtml(String htmlfile, Map<String, Object> variables);
}