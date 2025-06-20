package com.Motiv.Motiv.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationDTO {

    @Email(message = "Email is mandatory for sign up")
    @NotNull
    private String email;
    
    // look at the regex again to see if it should be more restrictive
    //Fix password restriction 
    
    @Size(min=8, max =16, message = "Password must be between 8 to 20 chars")
    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$", 
    message = "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number (0-9), and one special character (@, $, !, %, , ?, &)")
    private String password;


    public RegistrationDTO(String email, String password){
        this.email= email;
        this.password=password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    
    
}
