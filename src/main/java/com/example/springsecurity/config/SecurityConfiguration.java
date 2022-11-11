package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration {
private static final String ADMIN_ROLE="ADMIN";
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf()
                    .disable()
                    .addFilterAfter(
                    customAuthFilter(), BasicAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/","/login","/logout", "/oauth/authorize").permitAll()
                    .antMatchers(HttpMethod.DELETE)
                    .hasRole(ADMIN_ROLE)
                    .antMatchers("/admin/**")
                    .hasAnyRole(ADMIN_ROLE)
                    .antMatchers("/user/**")
                    .hasAnyRole("USER", ADMIN_ROLE)
                    .anyRequest()
                    .authenticated()
                    .and().formLogin();

            return http.build();
        }

    @Bean
    public MyCustomFilter customAuthFilter(){
        return new MyCustomFilter();
    }
}
