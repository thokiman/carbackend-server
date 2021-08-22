package com.packt.cardatabase.config;


import com.packt.cardatabase.AuthenticationFilter;
import com.packt.cardatabase.LoginFilter;
import com.packt.cardatabase.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override // by default configure method : we can use the default settings where all the endpoints are secured
    protected void configure(HttpSecurity http) throws Exception {
    // 1. add this row first to allow access to all endpoints on process front end
    //http.csrf().disable().cors().and().authorizeRequests().anyRequest().permitAll();

        //There, we stipulate that the POST method request to the /login
        //endpoint is allowed without authentication and that requests to all other
        //endpoints require authentication

    //2. add this after finish built front end
        http.csrf().disable().cors()
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
            .and()
                // Filter for the /api/ login requests
                .addFilterBefore(
                        new LoginFilter("/login",authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class
                        )
                // Filter for other requests to check JWT in header
                .addFilterBefore(
                        new AuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    // needed for the frontend which is sending requests from other origin.
    // The CORS filter intercepts requests and if are identified as cross origin, it add proper header to the request

    @Bean
     CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // TODO: 21 Agt 2021  true -> false, to avoid CORS missing header => reason: CORS header ‘Access-Control-Allow-Origin’ missing
        configuration.setAllowCredentials(false);
        configuration.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
