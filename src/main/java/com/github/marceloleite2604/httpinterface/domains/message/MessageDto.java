package com.github.marceloleite2604.httpinterface.domains.message;

import com.github.marceloleite2604.httpinterface.validation.group.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class MessageDto {

  @NotBlank(groups = Post.class)
  private final String id;

  @NotBlank
  private final String user;

  private final Long time;

  @NotBlank
  private final String content;
}
