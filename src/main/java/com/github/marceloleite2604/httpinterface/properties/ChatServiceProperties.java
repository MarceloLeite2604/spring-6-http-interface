package com.github.marceloleite2604.httpinterface.properties;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(PropertiesPath.CHAT_SERVICE)
@RequiredArgsConstructor
@Getter
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
      return false;
    }
  }
}
