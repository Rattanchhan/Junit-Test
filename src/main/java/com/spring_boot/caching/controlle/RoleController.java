package com.spring_boot.caching.controlle;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import com.spring_boot.caching.base.BaseListingRQ;
import com.spring_boot.caching.dto.permission.response.PermissionStatusResponse;
import com.spring_boot.caching.dto.role.request.RoleRQ;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.request.SetPermissionRequest;
import com.spring_boot.caching.dto.role.response.RoleListResponse;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.services.role.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleRS createRole(@Valid @RequestBody RoleRQ roleRQ){
        return roleService.create(roleRQ);
    }


    @GetMapping("/{id}")
    public RoleRS  getRole(@PathVariable("id") String id){
        return roleService.getRoleById(Long.parseLong(id));
    }

    @GetMapping
    public Page<RoleListResponse>  getAllRoles(BaseListingRQ baseListingRQ){
        return roleService.getAll(baseListingRQ);
    }

    @PutMapping("/{id}")
    public RoleRS updateRole(@PathVariable("id") String id,@RequestBody RoleRequestUpdate roleRequestUpdate){
        return roleService.update(Long.parseLong(id),roleRequestUpdate);
    }


    @DeleteMapping("/{id}/soft-delete")
    public RoleRS  delete(@PathVariable("id") String id){
        return roleService.delete(Long.parseLong(id));
    }

    @PutMapping("/assignPermission")
    public RoleRS  assignPermission(@RequestBody SetPermissionRequest setPermissionRequest) {
        return roleService.setPermission(setPermissionRequest);
    }

    @GetMapping("/listPermissions")
    public Page<PermissionStatusResponse>  listAllPermissions(BaseListingRQ baseListingRQ,@RequestParam(value = "roleId",required = false) String roleId,@RequestParam(value="module",required = false) String module){
    
        return roleService.listAllPermissions(baseListingRQ,roleId!=null?Long.parseLong(roleId):null,module);
    }

    @GetMapping("/cach")
    public RoleRS getUsername(@Param("name") String name) {
        return roleService.getRoleByName(name);
    }
}
