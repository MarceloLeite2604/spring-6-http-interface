package com.github.marceloleite2604.httpinterface.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Validator {

  private jakarta.validation.Validator validator;

  public void throwExceptionIfNotValid(Object object, Class<?>... groups) {
    final var constraintViolations = validator.validate(object, groups);

    if (CollectionUtils.isEmpty(constraintViolations)) {
      return;
    }

    final var violationsSummary = constraintViolations.stream()
        .map(constraintViolation -> {
          final var propertyPath = constraintViolation.getPropertyPath()
              .toString();
          final var message = constraintViolation.getMessage();
          return propertyPath + ": " + message;
        })
        .collect(Collectors.joining("; "));

    var message = String.format("Object validation returned the following constraint violations: %s", violationsSummary);

    final var groupClasses = Stream.of(groups)
        .map(Class::getName)
        .collect(Collectors.joining(", "));

    if (StringUtils.hasLength(groupClasses)) {
      message += String.format(". Violation was done considering the following groups: %s", groupClasses);
    }

    throw new IllegalStateException(message);
  }
}
