package com.example.springsecurity.config;

import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;
import com.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.example.springsecurity.security.UserInfoFilter;
import com.example.springsecurity.shared.restframework.aad.AADAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableConfigurationProperties({AADAuthenticationProperties.class})
@RequiredArgsConstructor
public class SecurityConfiguration extends AADWebSecurityConfigurerAdapter {

    private final AADAuthenticationProperties aadAuthFilterProperties;
//    private final ServiceEndpointsProperties serviceEndpointsProperties;

    @Autowired
    private UserInfoFilter userInfoFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAfter(
                        new AADAuthenticationFilter(aadAuthFilterProperties), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(userInfoFilter, AADAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/home").permitAll()
                .anyRequest().permitAll();

    }
}
