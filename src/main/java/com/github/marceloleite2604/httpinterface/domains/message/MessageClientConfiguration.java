package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.properties.ChatServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class MessageClientConfiguration {

  @Bean
  public WebClient createWebClient(ChatServiceProperties chatServiceProperties) {
    return WebClient.builder()
        .baseUrl(chatServiceProperties.getBaseUrl())
        .build();
  }

  @Bean
  public MessageClient createMessageClient(WebClient webClient) {
    final var webClientAdapter = WebClientAdapter.forClient(webClient);

    final var httpServiceProxyFactory = HttpServiceProxyFactory.builder(webClientAdapter)
        .build();

    return httpServiceProxyFactory.createClient(MessageClient.class);
  }
}
