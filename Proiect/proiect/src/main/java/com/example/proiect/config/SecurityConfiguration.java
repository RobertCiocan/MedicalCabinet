package com.example.proiect.config;

import com.example.proiect.utils.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private JwtTokenFilter jwtTokenFilter;

    public SecurityConfiguration(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try{
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    // daca de ex am avea rute publice (doar ex)
                                    .requestMatchers(HttpMethod.POST, "/api/medical_office/ceva-public").permitAll()
                                    // rute pentru care verificam roul din jwt
                                    // doctori
                                    .requestMatchers(HttpMethod.POST, "/api/medical_office/doctors").hasAuthority("admin")
                                    //.requestMatchers(HttpMethod.GET, "/api/medical_office/doctors/**").hasAnyAuthority("admin", "user", "doctor")
                                    .requestMatchers(HttpMethod.GET, "/api/medical_office/doctors/**/patients").hasAnyAuthority("admin", "doctor", "user")
                                    .requestMatchers(HttpMethod.GET, "/api/medical_office/doctors").hasAnyAuthority("admin", "user", "doctor")
                                    .requestMatchers(HttpMethod.GET, "/api/medical_office/doctors/**").hasAnyAuthority("admin", "user", "doctor")
                                    // programari
                                    .requestMatchers(HttpMethod.POST, "/api/medical_office/appointments").hasAnyAuthority("admin", "doctor", "user")
                                    .requestMatchers(HttpMethod.GET, "/api/medical_office/appointments/**").hasAnyAuthority("admin", "user", "doctor")
                                    .requestMatchers(HttpMethod.PUT, "/api/medical_office/appointments/**").hasAnyAuthority("admin", "doctor")
                                    .requestMatchers(HttpMethod.DELETE, "/api/medical_office/appointments/**").hasAnyAuthority("admin", "doctor", "user")
                                    // pacienti
                                    .requestMatchers(HttpMethod.GET, "/api/medical_office/patients/**").hasAnyAuthority("admin", "user", "doctor")
                                    .requestMatchers(HttpMethod.POST, "/api/medical_office/patients/**").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/api/medical_office/patients/**").hasAnyAuthority("admin", "user", "doctor")
                                    .requestMatchers(HttpMethod.DELETE, "/api/medical_office/patients/**").hasAnyAuthority("admin", "user", "doctor")
                    )
                    .httpBasic(AbstractHttpConfigurer::disable);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return http.build();
    }
}


