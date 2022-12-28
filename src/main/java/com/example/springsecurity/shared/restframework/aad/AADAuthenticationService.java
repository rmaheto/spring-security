package com.example.springsecurity.shared.restframework.aad;

import com.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.azure.spring.autoconfigure.aad.UserPrincipal;
import com.microsoft.aad.msal4j.*;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.net.MalformedURLException;
import java.nio.file.AccessDeniedException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("aadAuthenticationService")
public class AADAuthenticationService {

    @Value("${azure.activedirectory.signInUrl}")
    private String signInUrl;

    private static final String GRAPH_API_SCOPE = "https://graph.microsoft.com/.default";

    public boolean validateUserToken(String idToken) throws AccessDeniedException {
        try {
            JWTClaimsSet jwtClaimsSet = JWTParser.parse(idToken).getJWTClaimsSet();
            JWSObject jwsObject = JWSObject.parse(idToken);

            if(jwtClaimsSet.getExpirationTime().before(new Date())){
                throw new AccessDeniedException("Token has expired");
            }

            if(!jwtClaimsSet.getIssuer().contains(signInUrl)){
                throw new AccessDeniedException("Invalid token issuer");
            }

             new UserPrincipal(idToken,jwsObject, jwtClaimsSet);
        } catch (ParseException | JwtException e) {
            throw new AccessDeniedException(e.getLocalizedMessage());
        }
        return true;
    }

    public String acquireTokenForGraphApi(String idToken, AADAuthenticationProperties aadAuthFilterProp) throws Throwable {

        IAuthenticationResult result = null;
        String authorityUrl = signInUrl + aadAuthFilterProp.getTenantId() + "/v2.0";

        try {
            final CompletableFuture<IAuthenticationResult> future =
                    getAuthenticationResult(idToken, aadAuthFilterProp.getClientId(), aadAuthFilterProp.getClientSecret(),
                            Collections.singleton(GRAPH_API_SCOPE), authorityUrl);
            result = future.get();
        } catch (InterruptedException e) {
//            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        }

        if (result == null) {
            throw new ServiceUnavailableException("unable to acquire on-behalf-of azureservicetoken for client " + aadAuthFilterProp.getClientId());
        }
        return result.accessToken();
    }

    public CompletableFuture<IAuthenticationResult> getAuthenticationResult(String idToken, String clientId, String clientSecret,
                                                                            Set<String> scopes,
                                                                            String authorityUrl) throws MalformedURLException,MsalServiceException {
        ExecutorService service = null;
        UserAssertion assertion = new UserAssertion(idToken);
        IClientCredential credential = ClientCredentialFactory.createFromSecret(clientSecret);
        OnBehalfOfParameters params = OnBehalfOfParameters.builder(scopes, assertion).build();

        try {
            service = Executors.newFixedThreadPool(1);
            ConfidentialClientApplication app = ConfidentialClientApplication
                    .builder(clientId, credential)
                    .authority(authorityUrl)
                    .executorService(service)
                    .build();

            return app.acquireToken(params);
        }  finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }
}
