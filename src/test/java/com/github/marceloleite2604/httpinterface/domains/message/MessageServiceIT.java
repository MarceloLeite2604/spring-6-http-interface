package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.test.fixture.MessageFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServiceIT {

  @Autowired
  private MessageService messageService;

  @Test
  void shouldSaveUpdateAndDeleteMessageFromChatService() {
    final var inputMessage = MessageFixture.createForInput();

    final var id = messageService.create(inputMessage);

    assertThat(id).isNotNull();

    final var optionalPersistedMessage = messageService.retrieve(id);

    assertThat(optionalPersistedMessage).isPresent();

    final var persistedMessage = optionalPersistedMessage.get();

    assertThat(persistedMessage)
        .usingRecursiveComparison()
        .ignoringFields("id", "time")
        .isEqualTo(inputMessage);

    assertThat(persistedMessage.getId()).isEqualTo(id);
    assertThat(persistedMessage.getTime()).isNotNull();

    final var newContent = "This is the new message content.";
    final var updatedMessage = persistedMessage.toBuilder()
        .content(newContent)
        .build();

    messageService.update(updatedMessage);

    final var optionalUpdatedAndPersistedMessage = messageService.retrieve(id);

    assertThat(optionalUpdatedAndPersistedMessage).isPresent();

    final var updatedAndPersistedMessage = optionalUpdatedAndPersistedMessage.get();

    assertThat(updatedAndPersistedMessage)
        .usingRecursiveComparison()
        .ignoringFields("time")
        .isEqualTo(updatedMessage);

    assertThat(updatedAndPersistedMessage.getTime()).isNotNull()
        .isAfter(persistedMessage.getTime());

    messageService.delete(updatedAndPersistedMessage.getId());

    final var optionalDeletedMessage = messageService.retrieve(updatedAndPersistedMessage.getId());

    assertThat(optionalDeletedMessage).isEmpty();
  }

  @SpringBootConfiguration
  @ConfigurationPropertiesScan("com.github.marceloleite2604.httpinterface")
  @ComponentScan("com.github.marceloleite2604.httpinterface")
  public static class ITConfiguration {
  }
}
