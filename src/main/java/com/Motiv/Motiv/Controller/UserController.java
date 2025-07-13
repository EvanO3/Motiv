package com.Motiv.Motiv.Controller;



import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Motiv.Motiv.DTOs.UserDTO;

import com.Motiv.Motiv.Models.UserModel;
import com.Motiv.Motiv.Security.JwtUtils;
import com.Motiv.Motiv.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
   
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public UserController(UserService userService, JwtUtils jwtUtils){
        this.userService= userService;
        this.jwtUtils=jwtUtils;
    }



    @PostMapping("/account/setup")
    public ResponseEntity<String>setUp(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response){
        try{
            String jwtToken = jwtUtils.getJwtFromHeader(request);
            if(jwtToken ==null || jwtToken.isEmpty()){
                return new ResponseEntity<>("Failed to retrieve JWT Token",HttpStatus.BAD_REQUEST);
            }

            userService.profileSetUp(jwtToken, userDTO);
            return new ResponseEntity<>("User Successfully Saved", HttpStatus.CREATED);
        }catch(Exception e){
            logger.error("Logging DB error {}", e.getMessage());
            logger.error("error found", e);
           return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR); 

        }

    }

    @GetMapping("/retrieve/user")
    public ResponseEntity<Optional<UserModel>> retrieveUser(HttpServletRequest request){
        try{
            String jwtToken = jwtUtils.getJwtFromHeader(request);
            if(jwtToken ==null || jwtToken.isEmpty()){
             return new ResponseEntity<Optional<UserModel>>(HttpStatus.BAD_REQUEST);

            }
            Optional<UserModel> user = userService.getUserDetails(jwtToken);
            if(!user.isPresent()){
                logger.info("This is to say the user was not  {}", user);
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            }
            logger.info("user found {}", user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(Exception e){
            logger.error("Internal Server Error ", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }


    }


    
}
