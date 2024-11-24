package com.spring_boot.caching.services.role;

import org.springframework.data.domain.Page;
import com.spring_boot.caching.base.BaseListingRQ;
import com.spring_boot.caching.dto.permission.response.PermissionStatusResponse;
import com.spring_boot.caching.dto.role.request.RoleRQ;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.request.SetPermissionRequest;
import com.spring_boot.caching.dto.role.response.RoleListResponse;
import com.spring_boot.caching.dto.role.response.RoleRS;


public interface RoleService {

    Page<RoleListResponse> getAll(BaseListingRQ baseListingRQ);
    RoleRS getRoleById(Long Id);

    RoleRS create(RoleRQ roleRQ);

    RoleRS update(Long id,RoleRequestUpdate roleRequestUpdate);

    RoleRS delete(Long Id);

    RoleRS setPermission(SetPermissionRequest setPermissionRequest);

    Page<PermissionStatusResponse> listAllPermissions(BaseListingRQ request,Long roleId,String module);

}

