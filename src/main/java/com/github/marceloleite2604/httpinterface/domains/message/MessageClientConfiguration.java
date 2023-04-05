package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.properties.ChatServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Slf4j
@Configuration
public class MessageClientConfiguration {

  private static final int MAX_RETRY_ATTEMPTS = 3;

  @Bean
  public WebClient createChatWebClient(ChatServiceProperties chatServiceProperties) {
    final var exchangeFilterWithRetryStrategy = createExchangeFilterWithRetryStrategy();
    return WebClient.builder()
        .baseUrl(chatServiceProperties.getBaseUrl())
        .defaultStatusHandler(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
        .filter(exchangeFilterWithRetryStrategy)
        .build();
  }

  private ExchangeFilterFunction createExchangeFilterWithRetryStrategy() {
    final var backoffRetrySpecification = createBackoffRetrySpecification();

    return (request, next) -> next.exchange(request)
        .flatMap(clientResponse -> Mono.just(clientResponse)
            .filter(response -> clientResponse.statusCode()
                .is5xxServerError())
            .flatMap(ClientResponse::createException)
            .flatMap(Mono::error)
            .thenReturn(clientResponse))
        .retryWhen(backoffRetrySpecification);
  }

  private RetryBackoffSpec createBackoffRetrySpecification() {
    return Retry.backoff(MAX_RETRY_ATTEMPTS, Duration.ofMillis(200))
        .filter(throwable -> throwable instanceof WebClientResponseException)
        .doBeforeRetry(retrySignal ->
            log.warn("Request attempt failed. Retrying {} of {} attempts.", retrySignal.totalRetries() + 1, MAX_RETRY_ATTEMPTS, retrySignal.failure()))
        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
  }

  @Bean
  public MessageClient createMessageClient(WebClient webClient) {
    final var webClientAdapter = WebClientAdapter.forClient(webClient);

    final var httpServiceProxyFactory = HttpServiceProxyFactory.builder(webClientAdapter)
        .build();

    return httpServiceProxyFactory.createClient(MessageClient.class);
  }
}
