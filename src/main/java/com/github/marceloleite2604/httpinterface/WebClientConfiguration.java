package com.github.marceloleite2604.httpinterface;

import com.github.marceloleite2604.httpinterface.properties.ChatServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

  @Bean
  public WebClient createChatWebClient(ChatServiceProperties chatServiceProperties) {
    return WebClient.builder()
        .baseUrl(chatServiceProperties.getBaseUrl())
        .build();
  }
}
