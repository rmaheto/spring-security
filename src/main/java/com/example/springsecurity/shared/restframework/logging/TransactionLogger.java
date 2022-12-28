package com.example.springsecurity.shared.restframework.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static com.example.springsecurity.shared.restframework.response.RequestResponseHeaders.*;


public abstract class TransactionLogger {

  private JsonLoggingMasker jsonLoggingMasker;

  public static final String CALL_TYPE = "call-type";
  public static final String REQUEST_CALL_TYPE = "REQUEST";
  public static final String RESPONSE_CALL_TYPE = "RESPONSE";
  public static final String HTTP_STATUS_CODE = "http-status-code";
  public static final String HTTP_REQUEST_PATH = "http-request-path";
  public static final String HTTP_REQUEST_METHOD = "http-request-method";
  public static final String HTTP_REQUEST_BODY = "http-request-body";
  public static final String HTTP_RESPONSE_BODY = "http-response-body";

  static final List<String> TRACE_HEADERS = Arrays.asList(SPAN_ID, TRACE_ID, ACCEPT);

  final Logger defaultLogger = LoggerFactory.getLogger(TransactionLogger.class);

  public TransactionLogger(JsonLoggingMasker jsonLoggingMasker) {
    this.jsonLoggingMasker = jsonLoggingMasker;
  }

  public void logRequest(HttpServletRequest request) {
  }

  public void logResponse(HttpServletRequest request, HttpServletResponse response, Object handler) {
  }

  protected LoggingMap createRequestLoggingMap(HttpServletRequest request) {
    LoggingMap requestLogMap = new LoggingMap();

    requestLogMap.put(HTTP_REQUEST_PATH, request.getServletPath());
    requestLogMap.put(HTTP_REQUEST_METHOD, request.getMethod());
    TRACE_HEADERS.forEach(header -> requestLogMap.put(header, request.getHeader(header)));

    try {
      //TODO cannot log request Input Stream(), unable to view request later.
      //String json = IOUtils.toString(request.getInputStream(), CharEncoding.UTF_8);
      //String maskedBody = jsonLoggingMasker.mask(json);

      //requestLogMap.put(HTTP_REQUEST_BODY, maskedBody);

    } catch (Exception e) {
      defaultLogger.warn("Exception occurred during request logging.", e);
    }

    return requestLogMap;
  }

  protected LoggingMap createResponseLoggingMap(HttpServletRequest request, HttpServletResponse response) {
    LoggingMap responseLogMap = new LoggingMap();

    responseLogMap.put(HTTP_REQUEST_PATH, request.getServletPath());
    responseLogMap.put(HTTP_REQUEST_METHOD, request.getMethod());
    responseLogMap.put(HTTP_STATUS_CODE, String.valueOf(response.getStatus()));
    TRACE_HEADERS.forEach(header -> responseLogMap.put(header, request.getHeader(header)));

    try {
      //TODO This is possibly causing issues, removing this for now
      //ResponseWrapper responseWrapper = (ResponseWrapper) response;
      //String json = responseWrapper.getResponseOutput().toString();
      //String maskedBody = jsonLoggingMasker.mask(json);

      //responseLogMap.put(HTTP_RESPONSE_BODY, maskedBody);

    } catch (Exception e) {
      defaultLogger.warn("Exception occurred during response logging.", e);
    }

    return responseLogMap;
  }

}
