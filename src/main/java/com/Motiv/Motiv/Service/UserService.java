package com.Motiv.Motiv.Service;



import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.Motiv.Motiv.DTOs.UserDTO;
import com.Motiv.Motiv.Enums.ActivityLevel;
import com.Motiv.Motiv.Enums.Experience;
import com.Motiv.Motiv.Enums.Roles;
import com.Motiv.Motiv.Exceptions.AuthIdNotFoundException;
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



    //Updating the users information in the db, transactional is only applied when you are editing the db, i.e update, delete, add
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
                Experience experience = user.getExperience();
                ActivityLevel activityLevel = user.getActivityLevel();
                UserModel newUser = new UserModel(name, age, weight,height, experience, activityLevel ,authId);
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


    /**
     * when you want to remove the cached information
     * for example user goes from user role to prem
     * @CacheEvict(value = "userRoles", key = "#authUserId")
     * use this anotation in the update route

     **/
    @Cacheable("userRoles")
    public Roles getUserRole(String token) throws Exception{
        try{
            //applies RLS so the user can query their info from the db
            jwtUtils.applyRLSContextFromToken(token);
            String authUserId = jwtUtils.getSubFromClaim(token);

            if(authUserId.isEmpty()){
                throw new Exception("Failed to retrieve the users Role");
            }
            UUID authId = UUID.fromString(authUserId);
            logger.info("Fetching role from DB for user: {}", authId);
            return repository.findRoleByAuthUserId(authId);

        }catch(DataAccessException e){
        logger.error("Error retriving the role {}", e.getMessage());
            throw e;
        }
    }

    //issue currently is since the UUID returns an optional, its the wrong type and connot be used to retrieve user

    public Optional <UserModel> getUserDetails(String token) throws Exception{
        try{   
                jwtUtils.applyRLSContextFromToken(token);
                //retrieve the student by their auth_id

                String authUserId = jwtUtils.getSubFromClaim(token);

                if(authUserId.isEmpty()){
                    throw new AuthIdNotFoundException("Authenticated user id has not been found"); // make an authId not found Exception
                }
                //add Error checking for if findbyId returns nothing
                //Got the authUser id
                UUID authId = UUID.fromString(authUserId);

                //Getting the userId
                Optional<UUID> userId = repository.findIdByAuthUserId(authId);
                if(!userId.isPresent()){
                 throw new Exception("Failed to retrieve the users information");
                }
                logger.info("Fetching user from DB for user: {}", userId.get());
                return repository.findById(userId.get());



                

        }catch(DataAccessException e){
            //log the error here when done
            logger.error("Error accessing the users data", e);
            throw e;
        }
    }
    
}
