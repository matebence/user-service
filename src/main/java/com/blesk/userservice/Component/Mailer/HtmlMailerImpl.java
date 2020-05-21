package com.blesk.userservice.Component.Mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

@Component
public class HtmlMailerImpl implements HtmlMailer {

    private TemplateEngine templateEngine;

    @Autowired
    public HtmlMailerImpl(TemplateEngine template) {
        this.templateEngine = template;
    }

    @Override
    public String generateMailHtml(String htmlfile,  Map<String, Object> variables) {
        return this.templateEngine.process(htmlfile, new Context(Locale.getDefault(), variables));
    }
}