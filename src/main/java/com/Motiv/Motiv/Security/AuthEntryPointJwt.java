package com.Motiv.Motiv.Security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{
private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                // log any incoming request blocked requests logs the request path(url) and logs the error message 
                logger.error("Unauthourized error at {}: {}", request.getServletPath(), authException.getMessage());
                System.out.println("Printing the exception: " + authException);


                // setting the response that should be given back to the user, if they are found to be unauthorized
                //will be sent out if the user trys to access an endpoint they dont have access to
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                //Build the response that will be given to the user by storing the following information in a hashmap

                final Map<String, Object> body = new HashMap<>();
                body.put("status",HttpServletResponse.SC_UNAUTHORIZED );
                body.put("error", "unauthorized");
                body.put("message", authException.getMessage());
                body.put("path",request.getServletPath());

                //Returning the response to the user in JSON format
                final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(),body);

    

    }
  
}

