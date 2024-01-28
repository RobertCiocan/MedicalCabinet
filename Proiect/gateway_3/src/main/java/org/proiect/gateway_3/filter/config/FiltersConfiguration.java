package org.proiect.gateway_3.filter.config;


import org.proiect.gateway_3.filter.*;
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
    public GRPcCallsFilter authGatewayFilter() {
        return new GRPcCallsFilter();
    }
    @Bean
    public SqlInjectionFilter sqlInjectionFilter() {
        return new SqlInjectionFilter();
    }

    //TODO: add filter for data validation
}
