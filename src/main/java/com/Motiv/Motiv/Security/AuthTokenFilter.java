package com.Motiv.Motiv.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Motiv.Motiv.Security.JwtUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContext;

import org.springframework.security.core.userdetails.UserDetails;


/**Ledt off in authToken parsing the token and getting all the claims needed  */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;

    

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt= parseJwt(request);
            logger.info("Request: {} {}",request.getMethod(), request.getRequestURI());

            if(jwt !=null && !jwt.isEmpty()){
                //if there is a jwt log the jwt

                logger.info("JWT being Processed: {}",jwt.substring(9,Math.min(10, jwt.length())) + "..." );

                //Validating to see if the jwt passed is valid
                String jsonResponse =jwtUtils.validateJwt(jwt);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(jsonResponse);

                logger.info("JWT validation response: {}", jsonResponse);

                if(jsonResponse !=null){
                    String email = node.has("email") ? node.get("email").asText() : null;
                    String role = node.has("role") ? node.get("role").asText() : "anon";
                     logger.info("Token validation - email: {}, role: {}", email, role);


                     if(email !=null && "authenticated".equals(role)){
                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                     UsernamePasswordAuthenticationToken authentication = 
                         new UsernamePasswordAuthenticationToken(email, null, authorities);
                     
                     SecurityContextHolder.getContext().setAuthentication(authentication);
                     logger.info("Authentication set for {}", email);
                     
                     }
                }else{
                     logger.info("No JWT found in request");
                }

            }



        }catch(Exception e){
            //deal with the exception if it occurs
        logger.error("JWT processing error", e);
        }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    logger.info("Current authentication: {}", auth);
    if (auth != null) {
        logger.info("Current authentication: {}", auth);
    }
    
     
     filterChain.doFilter(request, response);
    }


    //Helper function that will take the JWT from the header

    private String parseJwt(HttpServletRequest request){
        String jwt = jwtUtils.getJwtFromHeader(request);
        logger.debug("Authentication.java {}", jwt);
        return jwt;
    }


}
