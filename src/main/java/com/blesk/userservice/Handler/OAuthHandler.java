package com.blesk.userservice.Handler;

import com.blesk.userservice.DTO.Response;
import com.blesk.userservice.Value.Messages;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class OAuthHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.AUTH_REQUIRED_EXCEPTION, true);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorObj);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}