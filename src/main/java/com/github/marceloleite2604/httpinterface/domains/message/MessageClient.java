package com.github.marceloleite2604.httpinterface.domains.message;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.Collection;
import java.util.Optional;

@HttpExchange(value = "/messages", accept = MediaType.APPLICATION_JSON_VALUE)
public interface MessageClient {

  @GetExchange
  Collection<MessageDto> getAll();

  @GetExchange("/{id}")
  Optional<MessageDto> get(@PathVariable String id);

  @PutExchange
  HttpHeaders put(@RequestBody MessageDto messageDto);

  @PostExchange("/{id}")
  void post(@PathVariable String id, @RequestBody MessageDto messageDto);

  @DeleteExchange("/{id}")
  void delete(@PathVariable String id);
}
