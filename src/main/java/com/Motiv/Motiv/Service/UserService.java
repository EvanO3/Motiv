package com.Motiv.Motiv.Service;



import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.Motiv.Motiv.DTOs.UserDTO;
import com.Motiv.Motiv.Models.UserModel;
import com.Motiv.Motiv.Repository.UserRepository;
import com.Motiv.Motiv.Security.JwtUtils;

import jakarta.validation.Valid;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${supabase.project.url}")
    private String baseUrl;

    @Value("${supabase.project.setup}")
    private String setupUrl;

    private String fullSetupUrl = baseUrl +setupUrl;

    private final RestTemplate restTemplate;
    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    public UserService(UserRepository repository, RestTemplate restTemplate, JwtUtils jwtUtils){
        this.repository=repository;
        this.restTemplate = restTemplate;
        this.jwtUtils= jwtUtils;
    }



    //Updating the users information in the db
    @Transactional
    public UserModel profileSetUp(String token, @Valid UserDTO user) throws Exception{
        
        try{
            jwtUtils.applyRLSContextFromToken(token);
            String authUserId = jwtUtils.getSubFromClaim(token);
            if(!authUserId.isEmpty()){
                UUID authId = UUID.fromString(authUserId);
                String name = user.getName();
                double weight = user.getWeight();
                int height = user.getHeight();
                int age = user.getAge();
                UserModel newUser = new UserModel(name, age, weight,height ,authId);
                logger.info("User before persistence {}", newUser);
                return repository.save(newUser);

            }else{
                throw new Exception("no auth id found");
            }
        
        }catch(DataAccessException e){
            logger.error("Error saving user to db {}", e.getMessage());
            throw e;
        }

    }
    
}
