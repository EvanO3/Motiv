package com.Motiv.Motiv.Controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Motiv.Motiv.DTOs.RegistrationDTO;
import com.Motiv.Motiv.Service.AuthService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    

    private final AuthService userService;

    public AuthController(AuthService userService){
        this.userService = userService;
    }

    
    @PostMapping("/signup")
    public ResponseEntity<String> signUp( @Valid @RequestBody RegistrationDTO userDTO){
        //Might need new error checking for controller as it may always give response
        try{
            ResponseEntity<String> response = userService.signUp(userDTO);
            System.out.println("Status code is: " + response.getStatusCode());
            
            if(response.getStatusCode() == HttpStatus.CREATED){
                return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
            }else{
                return  new ResponseEntity<>("Failed to sign up user", HttpStatus.BAD_REQUEST);
            }

           

        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
          
        }
        
    }

    

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid  @RequestBody RegistrationDTO registrationDTO){
        try{
            ResponseEntity<String> response = userService.login(registrationDTO);
           if(response.getStatusCode() == HttpStatus.OK){
               return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

           }
           return new ResponseEntity<>(response.getBody(), response.getStatusCode());
        }catch(Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


   
    


}
