package com.example.springsecurity.shared.restframework.logging;

import com.jayway.jsonpath.*;


import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JsonLoggingMasker implements LoggingMasker {

  private List<String> fieldsToMask;

  public JsonLoggingMasker() {
    fieldsToMask = new ArrayList<>();
  }

  public JsonLoggingMasker(String commaSeparatedListOfFieldsToMask) {
    setFieldsToMask((String) commaSeparatedListOfFieldsToMask);
  }

  public JsonLoggingMasker(List<String> listOfMaskableFields) {
    setFieldsToMask(listOfMaskableFields);
  }

  @Override
  public String mask(String jsonToBeMasked) {

    if (StringUtils.isEmpty(jsonToBeMasked)) {
      return "";
    }

    ParseContext jsonParser = JsonPath.using(Configuration.defaultConfiguration());

    DocumentContext jsonContext;
    try {
      jsonContext = jsonParser.parse(jsonToBeMasked);
    } catch (Exception e) {
      return jsonToBeMasked;
    }

    for (String jsonPath : fieldsToMask) {
      try {
        jsonContext.set(jsonPath, MASK_PATTERN);
      } catch (PathNotFoundException ex) {
        // Unless we truly expect all paths to show up in every JSON input, ignore this exception.
      }
    }

    return jsonContext.jsonString();
  }

  public void setFieldsToMask(String commaSeparatedListOfFieldsToMask) {
    if (StringUtils.isNotEmpty(commaSeparatedListOfFieldsToMask)) {
      fieldsToMask = Stream.of(commaSeparatedListOfFieldsToMask.split(",")).map(String::trim).collect(Collectors.toList());
    } else {
      this.fieldsToMask = new ArrayList<>();
    }
  }

  public void setFieldsToMask(List<String> listOfMaskableFields) {
    fieldsToMask = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(listOfMaskableFields)) {
      fieldsToMask.addAll(listOfMaskableFields);
    }
  }

}
