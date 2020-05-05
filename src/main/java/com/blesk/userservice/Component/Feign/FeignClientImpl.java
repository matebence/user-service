package com.blesk.userservice.Component.Feign;

import com.blesk.userservice.DTO.Response;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Value.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static feign.FeignException.errorStatus;

@Component
public class FeignClientImpl implements FeignClient, ErrorDecoder {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, details.getTokenValue()));
        }
    }

    @Override
    public Exception decode(String s, feign.Response response) {
        UserServiceException userServiceException;
        try {
            if (response.status() >= 400 && response.status() <= 499) {
                userServiceException = new UserServiceException(new ObjectMapper().readValue(CharStreams.toString(response.body().asReader(StandardCharsets.UTF_8)), Response.class).getMessage());
                userServiceException.setHttpStatus(HttpStatus.BAD_REQUEST);
                return userServiceException;
            } else if (response.status() >= 500 && response.status() <= 599) {
                userServiceException = new UserServiceException(new ObjectMapper().readValue(CharStreams.toString(response.body().asReader(StandardCharsets.UTF_8)), Response.class).getMessage());
                userServiceException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                return userServiceException;
            }
        } catch (IOException e) {
            userServiceException = new UserServiceException(Messages.EXCEPTION);
            userServiceException.setHttpStatus(HttpStatus.NOT_FOUND);
            return userServiceException;
        }
        return errorStatus(s, response);
    }
}