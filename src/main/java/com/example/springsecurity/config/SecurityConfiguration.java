package com.example.springsecurity.config;

import com.example.springsecurity.aad.AADAuthenticationFilter;
import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.microsoft.azure.spring.autoconfigure.aad.ServiceEndpointsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableConfigurationProperties({AADAuthenticationProperties.class, ServiceEndpointsProperties.class})
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

private final AADAuthenticationProperties aadAuthFilterProperties;
private final ServiceEndpointsProperties serviceEndpointsProperties;

private static final String ADMIN_ROLE="ADMIN";
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.cors().and()
            .csrf()
                    .disable()
                    .addFilterAfter(
                    new AADAuthenticationFilter(
                            aadAuthFilterProperties,serviceEndpointsProperties), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/","/login","/logout", "/oauth/authorize").permitAll()
                    .anyRequest().permitAll();
//                    .authenticated()
//                    .and().formLogin();
            return http.build();
        }

}
