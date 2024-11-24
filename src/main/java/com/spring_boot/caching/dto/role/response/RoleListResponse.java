package com.spring_boot.caching.dto.role.response;

import lombok.Data;

@Data
public class RoleListResponse
{
    private Long id;
    private String name;
    private String code;
    private String module;
}
