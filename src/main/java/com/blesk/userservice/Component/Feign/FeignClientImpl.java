package com.blesk.userservice.Component.Feign;

import com.blesk.userservice.DTO.Http.Response;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FeignClientImpl implements FeignClient, ErrorDecoder {

    @Value("${blesk.server-key}")
    private String serverKey;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            if (requestTemplate.path().equals("/api/accounts/join/accountId")) {
                requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, serverKey));
            } else {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, details.getTokenValue()));
            }
        }
    }

    @Override
    public UserServiceException decode(String s, feign.Response response) {
        try {
            if (response.status() >= 400 && response.status() <= 499) {
                return new UserServiceException(new ObjectMapper().readValue(CharStreams.toString(response.body().asReader(StandardCharsets.UTF_8)), Response.class).getMessage(), HttpStatus.BAD_REQUEST);
            } else if (response.status() >= 500 && response.status() <= 599) {
                return new UserServiceException(new ObjectMapper().readValue(CharStreams.toString(response.body().asReader(StandardCharsets.UTF_8)), Response.class).getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new UserServiceException(Messages.SERVER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new UserServiceException(Messages.EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}