package com.spring_boot.caching.dto.role.response;
import lombok.Builder;

@Builder
public record RoleCreateResponse (
     Long id,
     String name,
     String code,
     String module
){}
