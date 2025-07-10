package com.Motiv.Motiv.Service;

import org.springframework.http.HttpHeaders;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.Motiv.Motiv.DTOs.RegistrationDTO;

import com.Motiv.Motiv.Exceptions.ExternalApiException;

import com.Motiv.Motiv.Repository.UserRepository;




@Service
public class AuthService {



 
    private final UserRepository repository;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final RestTemplate restTemplate;

    @Value("${supabase.project.url}")
    private String baseUrl;

    @Value("${supabase.project.signup}")
    private String signUpUrl;

    @Value("${supabase.api_key}")
    private String apiKey;

    @Value("${supabase.project.login}")
    private String loginUrl;

    public AuthService(UserRepository repository, RestTemplate restTemplate){
        this.repository= repository;
        this.restTemplate = restTemplate;
    }

 

    /**Sign up the user using this Route**/


    /** currently signup works, but add some error handling and also add some error handling to the controller
     * add edge cases like what if the response isn't a 200, how do you handle that
     */
    public ResponseEntity<String> signUp(RegistrationDTO user){
        String url = baseUrl+signUpUrl;


        try{

                RegistrationDTO newUser = new RegistrationDTO(user.getEmail(), user.getPassword());

               

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("apikey", apiKey);
                HttpEntity<RegistrationDTO> entity = new HttpEntity<>(newUser,headers);


                //String json = new ObjectMapper().writeValueAsString(newUser);
               // logger.info("Sending this to supabase JSON: {}", json);
            

                ResponseEntity<String> response =restTemplate.exchange(url,HttpMethod.POST, entity, String.class);

                if(response.getStatusCode() != HttpStatus.OK){
                    logger.error("Failed to register the new user, External API returned: {}", response.getStatusCode());
                    
                    throw new ExternalApiException("Failed to register user: " + response.getStatusCode());
                }

                return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
           

       }catch(Exception e){
        //put a logger here
        logger.error("Failed to send information to supabase: {} ", e.getMessage());
        
        //May change from internal server error
            return  new ResponseEntity<>("Something went wrong while registering user", HttpStatus.INTERNAL_SERVER_ERROR);

       }
    
        
    }

    
    public ResponseEntity<String> login(RegistrationDTO user){
        String signUpUrl = baseUrl + loginUrl;
     
        try{
            RegistrationDTO registeredUser = new RegistrationDTO(user.getEmail(), user.getPassword());

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            header.set("apikey", apiKey);
            HttpEntity<RegistrationDTO> entity = new HttpEntity<>(registeredUser, header);
            ResponseEntity<String> response = restTemplate.exchange(signUpUrl, HttpMethod.POST, entity, String.class);
        
            return new ResponseEntity<>(response.getBody(), response.getStatusCode());

        }catch(HttpClientErrorException e){
            logger.error("Failed to login user", e.getMessage());
            logger.info("Status code is {}:", e.getStatusCode());
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());

        }
    }




    



    
}
