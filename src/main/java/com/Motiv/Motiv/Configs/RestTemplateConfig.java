package com.Motiv.Motiv.Configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
   
    @Value("${supabase.api_key}")
    private String api;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.defaultHeader("apikey", api).build();
    }

  

}
