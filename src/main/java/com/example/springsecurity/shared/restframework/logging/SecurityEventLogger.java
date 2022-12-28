package com.example.springsecurity.shared.restframework.logging;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;

@Component
public class SecurityEventLogger {

  private static final String LOGGER_NAME = "SECURITY_LOGGER";
  private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

  private static final String APP = "rsp";
  private static final String FAILED_LOGIN_ATTEMPT = "1.1";
  private static final String SUCCESSFUL_LOGIN_ATTEMPT = "1.2";
  private static final String INVALID_OR_EXPIRED_API_KEY = "1.3";
  private static final String INVALID_OR_EXPIRED_AUTHENTICATION_CREDENTIALS = "1.4";
  private static final String INVALID_SESSION_TOKENS = "1.5";
  private static final String NON_EXISTENT_RECORDS = "2.3";
  private static final String ERROR_MESSAGES_AND_SYSTEM_EXCEPTIONS = "5.6";
  private static final String FAILURE = "failure";
  private static final String SUCCESS = "success";
  private static final String CALLER_ID = "Ehi-Caller-Id";
  private static final String TRACE_ID = "Ehi-Trace-Id";
  private static final String REFERER = "referer";

  public static void logNonExistentRecords(WebRequest webRequest, Exception exception) {
    logger
        .error(",app=ehi:\"{}\", rid=\"{}\", event=\"{}\", subc=\"{}\", action=\"{}\", user=\"{}\", src=\"{}\", dest=\"{}\"", APP, getRid(webRequest),
            NON_EXISTENT_RECORDS, getSubc(exception), FAILURE, getUser(webRequest), getSource(), getDestination(webRequest));
  }

  public static void logErrorMessagesAndSystemExceptions(WebRequest webRequest, Exception exception) {
    logger.error(",app=ehi:\"{}\", rid=\"{}\", event=\"{}\", subc=\"{}\", user=\"{}\", src=\"{}\", dest=\"{}\"", APP, getRid(webRequest),
        ERROR_MESSAGES_AND_SYSTEM_EXCEPTIONS, getSubc(exception), getUser(webRequest), getSource(), getDestination(webRequest));
  }

  private static String getSource() {
    String src;
    try {
      src = InetAddress.getLocalHost().toString();
    } catch (Exception e) {
      src = "Unknown Src";
    }

    return src;
  }

  private static String getUser(WebRequest webRequest) {
    String user;

    try {
      user = webRequest.getHeader(CALLER_ID);
    } catch (Exception e) {
      user = "Unknown User";
    }

    return StringUtils.lowerCase(user);
  }

  private static String getDestination(WebRequest webRequest) {
    String dest;

    try {
      dest = webRequest.getHeader(REFERER);
    } catch (Exception e) {
      dest = "Unknown Destination";
    }

    return dest;
  }

  private static String getRid(WebRequest webRequest) {
    String rid;

    try {
      rid = webRequest.getHeader(TRACE_ID);
    } catch (Exception e) {
      rid = "Unknown Rid";
    }

    return rid;
  }

  private static String getSubc(Exception exception) {
    String subc;

    try {
      subc = exception.getStackTrace()[0].toString();
    } catch (Exception e) {
      subc = "Unknown Subc";
    }

    return subc;
  }
}
