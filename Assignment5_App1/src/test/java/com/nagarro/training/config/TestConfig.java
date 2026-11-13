package com.nagarro.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.training.util.ServiceUtil;

@Configuration
public class TestConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ServiceUtil serviceUtil(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new ServiceUtil(restTemplate, objectMapper);
    }
}
