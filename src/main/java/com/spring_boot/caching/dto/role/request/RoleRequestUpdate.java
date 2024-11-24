package com.spring_boot.caching.dto.role.request;

import lombok.Builder;

@Builder
public record RoleRequestUpdate(
    String name,
    String module,
    String code
) {
}
