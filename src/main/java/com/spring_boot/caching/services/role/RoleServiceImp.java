package com.spring_boot.caching.services.role;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.spring_boot.caching.clearCache.CacheClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.spring_boot.caching.base.BaseListingRQ;
import com.spring_boot.caching.dto.permission.response.PermissionStatusResponse;
import com.spring_boot.caching.dto.role.request.RoleRQ;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.request.SetPermissionItemRequest;
import com.spring_boot.caching.dto.role.request.SetPermissionRequest;
import com.spring_boot.caching.dto.role.response.RoleListResponse;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.mapper.role.RoleMapper;
import com.spring_boot.caching.messageConstant.MessageConstant;
import com.spring_boot.caching.model.Permission;
import com.spring_boot.caching.model.Role;
import com.spring_boot.caching.repository.PermissionRepository;
import com.spring_boot.caching.repository.RoleRepository;
import org.springframework.cache.annotation.Cacheable;

@Service
@Data
@Slf4j
@AllArgsConstructor
public class RoleServiceImp implements RoleService{
    private final CacheClass cache;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    @Cacheable(value = "roleList", key = "#request.query + '-' + #request.page + '-' + #request.size")
    public Page<RoleListResponse> getAll(BaseListingRQ request) {
        Page<Role> roleEntities = roleRepository.findByNameContainsOrderByNameAsc(request.getQuery(), request.getPageable());
        return roleEntities.map(roleMapper::toRoleListResponse);
    }

    @Override
    @Cacheable(value = "role", key = "#Id")
    public RoleRS getRoleById(Long Id) {
        Optional<Role> roleEntity = roleRepository.findByIdFetchPermission(Id);
        if(roleEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,MessageConstant.ROLE.ROLE_NOT_FOUND);
        return roleMapper.fromRole(roleEntity.get());
    }

    @Override
    public RoleRS create(RoleRQ roleRQ) {
        if(roleRepository.existsByCode(roleRQ.getCode())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,MessageConstant.ROLEPROPERTY.CODE_HAS_EXISTING);
        }
        if(roleRepository.existsByName(roleRQ.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,MessageConstant.ROLEPROPERTY.NAME_HAS_EXISTRING);
        }
        Role role = roleMapper.fromRequest(roleRQ);
        role.setCreatedAt(Instant.now());
        return roleMapper.fromRole(roleRepository.save(role));
    }

    @Override
    @CachePut(value = "role", key = "#Id")
    public RoleRS update(Long Id, RoleRequestUpdate roleRequestUpdate) {
        if(roleRepository.existsByCode(roleRequestUpdate.code())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,MessageConstant.ROLEPROPERTY.CODE_HAS_EXISTING);
        }
        if(roleRepository.existsByName(roleRequestUpdate.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,MessageConstant.ROLEPROPERTY.NAME_HAS_EXISTRING);
        }
        Role role = roleRepository.findById(Id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,MessageConstant.ROLE.ROLE_NOT_FOUND));
        roleMapper.fromRoleRequestUpdate(roleRequestUpdate, role);
        return roleMapper.fromRole(roleRepository.save(role));
    }

    @Override
    @CacheEvict(value = "role", key = "#Id")
    public RoleRS delete(Long Id) {
        Role role = roleRepository.findById(Id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,MessageConstant.ROLE.ROLE_NOT_FOUND));
        role.setDeletedAt(Instant.now());
        roleRepository.save(role);
        cache.clearSpecificCache("role",Id);
        throw new ResponseStatusException(HttpStatus.ACCEPTED,MessageConstant.ROLE.ROLE_DELETED_SUCCESSFULLY);

    }

    @Override
    public RoleRS setPermission(SetPermissionRequest setPermissionRequest) {
        Optional<Role> role = roleRepository.findByIdFetchPermission(setPermissionRequest.getRoleId());
        if (role.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstant.ROLE.ROLE_NOT_FOUND);

        Set<Long> requestedPermissionIds = setPermissionRequest.getItems().stream().map(SetPermissionItemRequest::getId).collect(Collectors.toSet());
        Set<Permission> requestedPermissions = permissionRepository.findAllByIdIn(requestedPermissionIds);

        if (requestedPermissions.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstant.ROLE.PERMISSION_NOT_FOUND);
        Set<Long> removeIds = setPermissionRequest.getItems().stream().filter(item -> !item.getStatus()).map(SetPermissionItemRequest::getId).collect(Collectors.toSet());
        Set<Permission> toRemove = requestedPermissions.stream().filter(permission -> removeIds.contains(permission.getId())).collect(Collectors.toSet());
        
        role.get().getPermissions().addAll(requestedPermissions);
        role.get().getPermissions().removeAll(toRemove);
        roleRepository.save(role.get());
        throw new ResponseStatusException(HttpStatus.ACCEPTED,MessageConstant.ROLE.ROLE_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Page<PermissionStatusResponse> listAllPermissions(BaseListingRQ request,Long roleId, String module) {
        if(roleId!=null && module!=null){
            roleId=null;
            module=null;
        }
        return permissionRepository.findAllPermission(roleId,module,request.getPageable());
    }


}
