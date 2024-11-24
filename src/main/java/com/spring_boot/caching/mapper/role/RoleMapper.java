package com.spring_boot.caching.mapper.role;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.spring_boot.caching.dto.permission.response.PermissionRS;
import com.spring_boot.caching.dto.role.request.RoleRQ;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.response.RoleCreateResponse;
import com.spring_boot.caching.dto.role.response.RoleListResponse;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.model.Permission;
import com.spring_boot.caching.model.Role;

@Mapper(componentModel="spring")
public interface RoleMapper {

    RoleRS fromRole(Role role);
    PermissionRS from(Permission permission);

    RoleCreateResponse toRoleCreateResponse(Role role);

    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target="id",ignore = true)
    @Mapping(target = "modifiedAt",ignore = true)
    @Mapping(target = "permissions",ignore = true)

    Role fromRequest(RoleRQ roleRQ);

    RoleRQ toRoleRQ(Role role);

    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "deletedAt",ignore = true)
    @Mapping(target="id",ignore = true)
    @Mapping(target = "modifiedAt",ignore = true)
    @Mapping(target = "permissions",ignore = true)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromRoleRequestUpdate(RoleRequestUpdate roleRequestUpdate,@MappingTarget Role role);

    RoleListResponse toRoleListResponse(Role role);
}
