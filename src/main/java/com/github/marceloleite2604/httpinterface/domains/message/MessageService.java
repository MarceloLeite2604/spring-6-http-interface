package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.util.UriUtil;
import com.github.marceloleite2604.httpinterface.validation.Validator;
import com.github.marceloleite2604.httpinterface.validation.group.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final MessageClient messageClient;

  private final MessageToDtoMapper messageToDtoMapper;

  private final UriUtil uriUtil;

  private final Validator validator;

  public UUID create(Message message) {
    Assert.notNull(message, "Message cannot be null.");
    final var messageDto = messageToDtoMapper.mapTo(message);

    validator.throwExceptionIfNotValid(messageDto);

    final var httpHeaders = messageClient.put(messageDto);

    final var id = retrieveIdFromResponseHeaders(httpHeaders);

    return convertValueToUuid(id);
  }

  public void update(Message message) {
    Assert.notNull(message, "Message cannot be null.");

    final var messageDto = messageToDtoMapper.mapTo(message);

    validator.throwExceptionIfNotValid(messageDto, Post.class);

    messageClient.post(messageDto.getId(), messageDto);
  }

  public Optional<Message> retrieve(UUID id) {
    Assert.notNull(id, "ID cannot be null.");

    return messageClient.get(id.toString())
        .map(messageToDtoMapper::mapFrom);
  }

  public void delete(UUID id) {
    Assert.notNull(id, "ID cannot be null.");

    messageClient.delete(id.toString());
  }

  private String retrieveIdFromResponseHeaders(java.net.http.HttpHeaders httpHeaders) {
    final var location = httpHeaders.firstValue(HttpHeaders.LOCATION)
        .orElseThrow(() -> {
          final var exceptionMessage = String.format("Chat service did not return \"%s\" HTTP header on its response.", HttpHeaders.LOCATION);
          return new IllegalStateException(exceptionMessage);
        });

    return uriUtil.retrieveLastPathSegmentFromUri(location)
        .orElseThrow(() -> {
          final var exceptionMessage = String.format("Could not retrieve message ID from HTTP header \"%s\" value \"%s\".", HttpHeaders.LOCATION, location);
          return new IllegalStateException(exceptionMessage);
        });
  }

  private UUID convertValueToUuid(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException exception) {
      final var exceptionMessage = String.format("Exception thrown while converting message id \"%s\" into UUID.", id);
      throw new IllegalStateException(exceptionMessage, exception);
    }
  }
}
