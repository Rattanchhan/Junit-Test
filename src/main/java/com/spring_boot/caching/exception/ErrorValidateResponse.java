package com.spring_boot.caching.exception;

import lombok.Builder;
import java.util.List;

@Builder
public record ErrorValidateResponse(
        Integer code,
        List<?> reason
) {
}