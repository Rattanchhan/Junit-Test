package com.spring_boot.caching.exception;

import lombok.Builder;

@Builder
public record FieldError(
    String field,
    String detail
) {
}
