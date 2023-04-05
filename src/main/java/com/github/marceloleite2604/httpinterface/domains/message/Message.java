package com.github.marceloleite2604.httpinterface.domains.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Message {

  private UUID id;

  private LocalDateTime time;

  private String user;

  private String content;
}
