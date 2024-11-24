package com.spring_boot.caching.mapper.permission;
import org.mapstruct.Mapper;

import com.spring_boot.caching.dto.permission.response.PermissionRS;
import com.spring_boot.caching.model.Permission;
import java.util.List;

@Mapper(componentModel="spring")
public interface PermissionMapper {
    
    PermissionRS formPermissionEntity(Permission permission);
    List<PermissionRS> fromPermissionList(List<Permission> listPermissionEntity);
}
