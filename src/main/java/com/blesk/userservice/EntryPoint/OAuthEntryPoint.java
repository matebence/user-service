package com.blesk.userservice.EntryPoint;

import com.blesk.userservice.DTO.Response;
import com.blesk.userservice.Value.Messages;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class OAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.AUTH_EXCEPTION,true);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), errorObj);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}