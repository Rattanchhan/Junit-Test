package com.spring_boot.caching.dto.permission.request;

import lombok.Data;

@Data
public class PermissionRQ {
    private String name;
    private String code;
    private String module;
}
