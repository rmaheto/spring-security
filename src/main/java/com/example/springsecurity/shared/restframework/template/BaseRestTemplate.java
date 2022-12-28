package com.example.springsecurity.shared.restframework.template;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public abstract class BaseRestTemplate {

  private static final String HTTP_STATUS_EXCEPTION_MESSAGE = "Status Code: {} returned for method {} and params: {}";
  private static final String REST_CLIENT_EXCEPTION_MESSAGE = "RestClientException: {} returned for method {} and params: {}";
  private static final String GENERIC_EXCEPTION_MESSAGE = "genericException: {} returned for method {} and params: {}";

  private static final Logger LOG = LoggerFactory.getLogger(BaseRestTemplate.class);

  protected <T> ResponseEntity<T> exchange(RestTemplate template, String url, HttpMethod httpMethod, HttpEntity<?> request,
      ParameterizedTypeReference<T> teamTypeRef, Object... uriVariables) {
    return exchange(template, url, true, httpMethod, request, teamTypeRef, uriVariables);
  }

  protected <T> ResponseEntity<T> exchange(RestTemplate template, String url, boolean throwException, HttpMethod httpMethod, HttpEntity<?> request,
      ParameterizedTypeReference<T> teamTypeRef, Object... uriVariables) {
    logDebug(url, httpMethod, request, teamTypeRef, uriVariables);

    try {
      return template.exchange(url, httpMethod, request, teamTypeRef, uriVariables);
    } catch (HttpStatusCodeException statusException) {
      LOG.warn(HTTP_STATUS_EXCEPTION_MESSAGE, statusException.getStatusCode(), getMethodName(),
          getParameters(url, httpMethod, request, teamTypeRef, uriVariables));
      if (throwException) {
        throw statusException;
      }
    } catch (RestClientException clientException) {
      LOG.warn(REST_CLIENT_EXCEPTION_MESSAGE, clientException.getLocalizedMessage(), getMethodName(),
          getParameters(url, httpMethod, request, teamTypeRef, uriVariables));
      if (throwException) {
        throw clientException;
      }
    } catch (Exception genericException) {
      LOG.warn(GENERIC_EXCEPTION_MESSAGE, genericException.getLocalizedMessage(), getMethodName(),
          getParameters(url, httpMethod, request, teamTypeRef, uriVariables));
      if (throwException) {
        throw genericException;
      }
    }

    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  protected <T> T exchangeForObject(RestTemplate template, String url, HttpMethod httpMethod, Class<T> responseType, Object... uriVariables) {
    logDebug(url, responseType, uriVariables);

    try {
      return template.exchange(url, httpMethod, null, responseType, uriVariables).getBody();
    } catch (HttpStatusCodeException statusException) {
      LOG.warn(HTTP_STATUS_EXCEPTION_MESSAGE, statusException.getStatusCode(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw statusException;
    } catch (RestClientException clientException) {
      LOG.warn(REST_CLIENT_EXCEPTION_MESSAGE, clientException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw clientException;
    } catch (Exception genericException) {
      LOG.warn(GENERIC_EXCEPTION_MESSAGE, genericException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw genericException;
    }
  }

  protected <T> T getForObject(RestTemplate restTemplate, String url, Class<T> responseType, Object... uriVariables) {
    logDebug(url, responseType, uriVariables);

    try {
      return restTemplate.getForObject(url, responseType, uriVariables);
    } catch (HttpStatusCodeException statusException) {
      LOG.warn(HTTP_STATUS_EXCEPTION_MESSAGE, statusException.getStatusCode(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw statusException;
    } catch (RestClientException clientException) {
      LOG.warn(REST_CLIENT_EXCEPTION_MESSAGE, clientException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw clientException;
    } catch (Exception genericException) {
      LOG.warn(GENERIC_EXCEPTION_MESSAGE, genericException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw genericException;
    }
  }

  protected <T> T postForObject(RestTemplate restTemplate, String url, Class<T> responseType, Object... uriVariables) {
    return postForObject(restTemplate, url, null, responseType, uriVariables);
  }

  protected <T> T postForObject(RestTemplate restTemplate, String url, HttpEntity<?> request, Class<T> responseType, Object... uriVariables) {
    logDebug(url, responseType, uriVariables);

    try {
      return restTemplate.postForObject(url, request, responseType, uriVariables);
    } catch (HttpStatusCodeException statusException) {
      LOG.warn(HTTP_STATUS_EXCEPTION_MESSAGE, statusException.getStatusCode(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw statusException;
    } catch (RestClientException clientException) {
      LOG.warn(REST_CLIENT_EXCEPTION_MESSAGE, clientException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw clientException;
    } catch (Exception genericException) {
      LOG.warn(GENERIC_EXCEPTION_MESSAGE, genericException.getLocalizedMessage(), getMethodName(), getParameters(url, responseType, uriVariables));
      throw genericException;
    }
  }

  protected void delete(RestTemplate restTemplate, String url, Object... uriVariables) {
    try {
      restTemplate.delete(url, uriVariables);
    } catch (HttpStatusCodeException statusException) {
      LOG.warn(HTTP_STATUS_EXCEPTION_MESSAGE, statusException.getStatusCode(), getMethodName(), getParameters(url, Void.TYPE, uriVariables));
      throw statusException;
    } catch (RestClientException clientException) {
      LOG.warn(REST_CLIENT_EXCEPTION_MESSAGE, clientException.getLocalizedMessage(), getMethodName(), getParameters(url, Void.TYPE, uriVariables));
      throw clientException;
    } catch (Exception genericException) {
      LOG.warn(GENERIC_EXCEPTION_MESSAGE, genericException.getLocalizedMessage(), getMethodName(), getParameters(url, Void.TYPE, uriVariables));
      throw genericException;
    }
  }

  String getMethodName() {
    StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    StackTraceElement e = stacktrace[3];
    return String.format("%s.%s()", e.getClassName(), e.getMethodName());
  }

  String getParameters(String url, Class<?> responseType, Object... uriVariables) {
    StringBuilder sb = new StringBuilder(String.format("url: %s, responseType: %s", url, responseType));
    return appendUriVariables(sb, uriVariables);
  }

  String getParameters(String url, HttpMethod httpMethod, HttpEntity<?> request, ParameterizedTypeReference<?> teamTypeRef, Object... uriVariables) {
    StringBuilder sb = new StringBuilder(
        String.format("url: %s, httpMethod: %s, request: %s, parameterizedTypeReference: %s)", url, httpMethod, request, teamTypeRef));
    return appendUriVariables(sb, uriVariables);
  }

  private void logDebug(String url, HttpMethod httpMethod, HttpEntity<?> request, ParameterizedTypeReference<?> teamTypeRef, Object... uriVariables) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(String.format("Calling REST service for method: %s with params: %s", getMethodName(),
          getParameters(url, httpMethod, request, teamTypeRef, uriVariables)));
    }
  }

  private void logDebug(String url, Class<?> responseType, Object... uriVariables) {
    if (LOG.isDebugEnabled()) {
      LOG.debug(
          String.format("Calling REST service for method: %s with params: %s", getMethodName(), getParameters(url, responseType, uriVariables)));
    }
  }

  private String appendUriVariables(StringBuilder sb, Object... uriVariables) {
    List<Object> uriVariableList = Arrays.asList(Optional.ofNullable(uriVariables).orElse(new String[0]));
    if (!uriVariableList.isEmpty()) {
      sb.append(", uriVariables: ");
      for (Object variable : uriVariableList) {
        sb.append(variable).append(", ");
      }
    }

    return StringUtils.removeEnd(sb.toString(), ", ");
  }
}
