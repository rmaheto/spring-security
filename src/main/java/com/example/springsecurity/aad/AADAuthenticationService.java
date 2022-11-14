package com.example.springsecurity.aad;

import com.microsoft.aad.msal4j.*;
import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationProperties;
import com.microsoft.azure.spring.autoconfigure.aad.UserPrincipal;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("aadAuthenticationService")
public class AADAuthenticationService {


    public boolean validateUserToken(String idToken) {
        try {
            JWTClaimsSet jwtClaimsSet = JWTParser.parse(idToken).getJWTClaimsSet();
            JWSObject jwsObject = JWSObject.parse(idToken);
             new UserPrincipal(jwsObject, jwtClaimsSet);
        } catch (ParseException | JwtException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public String acquireTokenForGraphApi(String idToken, AADAuthenticationProperties aadAuthenticationProp) throws Throwable {

        final String auth_url = "https://login.microsoftonline.com/" + aadAuthenticationProp.getTenantId() + "/v2.0";
        ConfidentialClientApplication app = null;
        IAuthenticationResult result = null;
        ExecutorService service = null;
        Set<String> scopes = Collections.singleton("https://graph.microsoft.com/.default");

        UserAssertion assertion = new UserAssertion(idToken);
        IClientCredential credential = ClientCredentialFactory.createFromSecret(aadAuthenticationProp.getClientSecret());
        OnBehalfOfParameters params  = OnBehalfOfParameters.builder(scopes,assertion).build();
        ClientCredentialParameters parameters =
                ClientCredentialParameters.builder(scopes)
                        .build();

        try {
            service = Executors.newFixedThreadPool(1);
            app = ConfidentialClientApplication
                    .builder(aadAuthenticationProp.getClientId(), credential)
                    .authority(auth_url)
                    .executorService(service)
                    .build();
            final CompletableFuture<IAuthenticationResult> future = app.acquireToken(params);
            result = future.get();
        } catch (ExecutionException | MalformedURLException | InterruptedException e) {
            throw e.getCause();
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }

        if (result == null) {
            throw new ServiceUnavailableException("unable to acquire on-behalf-of azureservicetoken for client " + aadAuthenticationProp.getClientId());
        }
        return result.accessToken();
    }
}
