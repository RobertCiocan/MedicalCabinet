package org.proiect.gateway_3.filter.config;


import org.proiect.gateway_3.filter.JwtValidationFilter;
import org.proiect.gateway_3.filter.LoginFilter;
import org.proiect.gateway_3.filter.OncePerRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiltersConfiguration {

    @Bean
    public OncePerRequestFilter oncePerRequestFilter() {
        return new OncePerRequestFilter();
    }

    @Bean
    public JwtValidationFilter jwtValidationFilter() {
        return new JwtValidationFilter();
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    //TODO: add filter for data validation
}
