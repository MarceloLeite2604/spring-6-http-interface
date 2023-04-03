package com.github.marceloleite2604.httpinterface.test.fixture;

import com.github.marceloleite2604.httpinterface.domains.message.MessageDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageDtoFixture {

  public static final String ID = "31b255d4-758f-4187-a9e2-b1795a080fc2";
  public static final long TIME = 1680487924382L;

  public static MessageDto create() {
    return MessageDto.builder()
        .id(ID)
        .time(TIME)
        .user(MessageFixture.USER)
        .content(MessageFixture.CONTENT)
        .build();
  }
}
