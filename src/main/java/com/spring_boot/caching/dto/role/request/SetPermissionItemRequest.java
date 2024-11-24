package com.spring_boot.caching.dto.role.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPermissionItemRequest {
    private Long id;
    private Boolean status;
}
