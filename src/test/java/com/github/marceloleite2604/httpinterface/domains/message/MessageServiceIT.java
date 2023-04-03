package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.test.fixture.MessageFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


  }
}
