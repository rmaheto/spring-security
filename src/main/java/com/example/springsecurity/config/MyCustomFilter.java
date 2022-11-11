package com.example.springsecurity.config;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyCustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("X-HEADER");
        if (StringUtils.hasLength(authHeader)) {
            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("Here I am in");
        } else {
            filterChain.doFilter(request, response);
            System.out.println("Here I am out");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }
}
