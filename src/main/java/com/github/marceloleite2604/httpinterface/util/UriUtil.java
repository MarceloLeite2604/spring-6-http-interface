package com.github.marceloleite2604.httpinterface.util;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;

@Component
public class UriUtil {

  public Optional<String> retrieveLastPathSegmentFromUri(String uri) {

    return Optional.ofNullable(uri)
        .map(URI::create)
        .map(URI::getPath)
        .map(path -> path.split("/"))
        .map(pathSegments -> pathSegments[pathSegments.length - 1]);
  }
}
