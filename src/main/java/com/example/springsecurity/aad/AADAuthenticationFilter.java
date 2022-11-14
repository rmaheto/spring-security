package com.example.springsecurity.aad;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.microsoft.azure.spring.autoconfigure.aad.ServiceEndpointsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AADAuthenticationFilter extends OncePerRequestFilter {


    private static final String TOKEN_TYPE = "Bearer ";
    private static final String TOKEN_HEADER = "Authorization";
    private static final String CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN = "CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN";

    private AADAuthenticationProperties aadAuthProps;
    private ServiceEndpointsProperties serviceEndpointsProps;

    public AADAuthenticationFilter(AADAuthenticationProperties aadAuthProps, ServiceEndpointsProperties serviceEndpointsProps) {
        this.aadAuthProps = aadAuthProps;
        this.serviceEndpointsProps = serviceEndpointsProps;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasLength(authHeader) && authHeader.startsWith(TOKEN_TYPE)) {
            try {
                final String idToken = authHeader.replace(TOKEN_TYPE, "");

                AADAuthenticationService aadAuthenticationService = (AADAuthenticationService) WebApplicationContextUtils
                        .getWebApplicationContext(request.getServletContext()).getBean("aadAuthenticationService");

                if (aadAuthenticationService.validateUserToken(idToken)) {
                    String graphApiToken = aadAuthenticationService.acquireTokenForGraphApi(idToken, aadAuthProps);
                    request.setAttribute(CURRENT_USER_PRINCIPAL_GRAPHAPI_TOKEN, graphApiToken);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } else {
            filterChain.doFilter(request, response);
            System.out.println("Here I am out");
        }

        filterChain.doFilter(request, response);
    }
}
