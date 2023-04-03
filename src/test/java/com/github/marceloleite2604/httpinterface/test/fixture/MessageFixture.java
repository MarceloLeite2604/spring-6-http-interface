package com.github.marceloleite2604.httpinterface.test.fixture;

import com.github.marceloleite2604.httpinterface.domains.message.Message;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageFixture {

  public static final String USER = "userValue";
  public static final String CONTENT = "contentValue";

  public static Message createForInput() {
    return Message.builder()
        .user(USER)
        .content(CONTENT)
        .build();
  }
}
