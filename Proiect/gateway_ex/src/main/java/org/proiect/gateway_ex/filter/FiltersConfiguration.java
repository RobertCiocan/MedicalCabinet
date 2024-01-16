package org.proiect.gateway_ex.filter;


import org.proiect.gateway_ex.filter.internal.JwtValidationFilter;
import org.proiect.gateway_ex.filter.internal.OncePerRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FiltersConfiguration {

    @Bean
    public OncePerRequestFilter oncePerRequestFilter() {
        return new OncePerRequestFilter();
    }

}
