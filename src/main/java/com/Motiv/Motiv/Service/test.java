package com.Motiv.Motiv.Service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import com.Motiv.Motiv.Jwt.JwtUtils;

public class test {

       
    
    public static void main(String[] args) {
     

    RestTemplateBuilder builder = new RestTemplateBuilder();
    RestTemplate restTemplate = builder.defaultHeader("apikey", "").build();

//     JwtUtils util = new JwtUtils(restTemplate);
//     String result = util.getEmailFromJwt("");
//    System.out.println(result);
}
}
