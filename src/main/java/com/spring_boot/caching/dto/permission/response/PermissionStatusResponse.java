package com.spring_boot.caching.dto.permission.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionStatusResponse{
    private Long id;
    private String name;
    private String module;
    private boolean status;
}
