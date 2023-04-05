package com.github.marceloleite2604.httpinterface.properties;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@Slf4j
@ConfigurationProperties(PropertiesPath.CHAT_SERVICE)
@RequiredArgsConstructor
@Getter
@Validated
public class ChatServiceProperties {

  @NotBlank
  private final String baseUrl;

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @AssertTrue
  private boolean isBaseUrlAValidUrl() {
    try {
      URI.create(baseUrl);
      return true;
    } catch (IllegalArgumentException exception) {
      log.error("Exception thrown while checking if \"{}\" is a valid URL.", baseUrl, exception);
      return false;
    }
  }
}
