package com.example.springsecurity.security;

import com.example.springsecurity.entity.user.UserDTO;
import com.example.springsecurity.entity.user.UserPrincipal;
import com.example.springsecurity.service.graphapi.GraphService;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static com.example.springsecurity.utils.Constants.CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserInfoFilter extends OncePerRequestFilter {

    @Autowired
    private GraphService graphService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String graphAPIToken = (String) request.getAttribute(CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN);
        UserDTO userDTO = graphService.retrieveUser(graphAPIToken);

        if (SecurityContextHolder.getContext().getAuthentication() != null && CollectionUtils
                .isNotEmpty(SecurityContextHolder.getContext().getAuthentication().getAuthorities())) {
            SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(new UserPrincipal(userDTO), null,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        } else {
            SecurityContextHolder.getContext().setAuthentication(new PreAuthenticatedAuthenticationToken(new UserPrincipal(userDTO), null, null));
        }

        filterChain.doFilter(request, response);
    }
}
