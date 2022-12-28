package com.example.springsecurity.shared.restframework.request;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequestScope
@Data
public class RequestContext {
//  the following fields are used for request processing
    private String accept;
    private Locale locale=Locale.US;
    private  String etag;

//  The following fields are used for timing requests
    private long executionStartTime;
    private long sla;

//    the following fields are for tracking requests
    private String callingApplication;
    private String spanId;
    private String traceId;
    private String workflowId;
    private String callerId;
    private String deviceLocationId;

//    This field is provided for the user as a location to store request scoped data;
    private Map<String, Object> customData = new HashMap<>();
}
