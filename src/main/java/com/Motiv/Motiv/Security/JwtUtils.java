package com.Motiv.Motiv.Security
;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import com.Motiv.Motiv.Configs.RestTemplateConfig;
import com.Motiv.Motiv.DTOs.RegistrationDTO;
import com.Motiv.Motiv.Exceptions.ExternalApiException;
import com.Motiv.Motiv.Service.SupabaseContextService;
import com.Motiv.Motiv.Service.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    private final SupabaseContextService supabaseContextService;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final RestTemplate restTemplate;

    public JwtUtils(RestTemplate restTemplate, SupabaseContextService supabaseContextService){
        this.restTemplate = restTemplate;
        this.supabaseContextService = supabaseContextService;
    }    
   

    @Value("${supabase.project.url}")
    private String baseUrl;
    
    @Value("${supabase.project.auth.uri}")
    private String authUri;

   

    /*This snippet of code will take the jwt from the request header, then it will return it */
    public String getJwtFromHeader(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    } 

    /**Validate the jwt using a get request  */
    public String validateJwt(String token){
    

       try{
        final String fullUrl = baseUrl + authUri;
        //first set the headers going to be used in the rq
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token);
        logger.info("uri is : " + fullUrl);
        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity <String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity,String.class);

        if(response.getBody()==null){
            throw new ExternalApiException("Failed to Verify users Token" + response.getStatusCode());
        }
        
        return response.getBody();

        //HTTPClient is thrown when you recieve any type of 400 response
       }catch(HttpClientErrorException e){
        logger.error("Client Error Verifying JWT Claim", e.getMessage());
        throw new ExternalApiException(e.getMessage() + e.getStatusCode());
        
        //HTTPServer  is thrown when you recieve any type of 500 response
       }catch(HttpServerErrorException e){
          logger.error("Server Error", e.getMessage());
          throw new ExternalApiException(e.getMessage() + e.getStatusCode());

       }//This is throw if there was any type of network exception
       catch(ResourceAccessException e){
        logger.error("Network Error");
        throw new ExternalApiException(e.getMessage() + e.getStackTrace());
       // if any other error occurs, this will catch it
    }catch(Exception e){
        logger.error("Error accessing resource", e.getMessage());
        throw new ExternalApiException(e.getMessage() + e.getStackTrace());         
       }
    
       
    
    }



    public String getEmailFromJwt(String token){
        try{
            String response = validateJwt(token);

            if(response !=null){
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(response);

                return node.has("email") ? node.get("email").asText() : null;
            }
            return null;
            
        }catch(Exception e){
        logger.error("Error accessing resource", e.getMessage());
        throw new ExternalApiException(e.getMessage() + e.getStackTrace());         
       }
    }


public String getRoleFromJWT(String token){
        String response = validateJwt(token);
    try {
         if(response !=null){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
    
            return node.has("role") ? node.get("role").asText(): "anon";
    
    }
     return null;
    
} catch (Exception e) {
     logger.error("Error accessing resource", e.getMessage());
    throw new ExternalApiException(e.getMessage() + e.getStackTrace());      
    
}

}



public String getSubFromClaim(String token){
       String response = validateJwt(token);
    try {
         if(response !=null){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response);
    
            return node.has("id") ? node.get("id").asText(): null;
    
    }
     return null;
    
} catch (Exception e) {
     logger.error("Error accessing resource", e.getMessage());
    throw new ExternalApiException(e.getMessage() + e.getStackTrace());      
    
}
}


//Last function to add is adding RLS from token helper method

public void applyRLSContextFromToken(String token){
    String userId = getSubFromClaim(token);
    supabaseContextService.setUserContext(userId);
}

}



