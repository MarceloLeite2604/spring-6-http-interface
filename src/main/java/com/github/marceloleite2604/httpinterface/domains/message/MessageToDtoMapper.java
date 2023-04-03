package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.util.TimeZoneUtil;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class MessageToDtoMapper {

  public MessageDto mapTo(Message message) {

    if (message == null) {
      return null;
    }

    var messageDtoBuilder = MessageDto.builder();

    messageDtoBuilder = Optional.ofNullable(message.getId())
        .map(UUID::toString)
        .map(messageDtoBuilder::id)
        .orElse(messageDtoBuilder);

    messageDtoBuilder = Optional.ofNullable(message.getTime())
        .map(time -> time.atZone(TimeZoneUtil.DEFAULT_ZONE_OFFSET)
            .toInstant()
            .toEpochMilli())
        .map(messageDtoBuilder::time)
        .orElse(messageDtoBuilder);

    return messageDtoBuilder.user(message.getUser())
        .content(message.getContent())
        .build();
  }

  public Message mapFrom(MessageDto messageDto) {

    if (messageDto == null) {
      return null;
    }

    var messageBuilder = Message.builder();

    messageBuilder = Optional.ofNullable(messageDto.getId())
        .map(UUID::fromString)
        .map(messageBuilder::id)
        .orElse(messageBuilder);

    messageBuilder = Optional.ofNullable(messageDto.getTime())
        .map(epochMillis -> LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), TimeZoneUtil.DEFAULT_ZONE_OFFSET))
        .map(messageBuilder::time)
        .orElse(messageBuilder);

    return messageBuilder.user(messageDto.getUser())
        .content(messageDto.getContent())
        .build();
  }
}
