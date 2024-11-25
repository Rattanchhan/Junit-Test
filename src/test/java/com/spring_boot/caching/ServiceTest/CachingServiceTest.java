package com.spring_boot.caching.ServiceTest;
import com.spring_boot.caching.clearCache.CacheClass;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.request.SetPermissionItemRequest;
import com.spring_boot.caching.dto.role.request.SetPermissionRequest;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.mapper.role.RoleMapper;
import com.spring_boot.caching.messageConstant.MessageConstant;
import com.spring_boot.caching.model.Permission;
import com.spring_boot.caching.model.Role;
import com.spring_boot.caching.repository.PermissionRepository;
import com.spring_boot.caching.repository.RoleCachingJpaRepository;
import com.spring_boot.caching.repository.RoleJpaRepository;
import com.spring_boot.caching.services.role.RoleService;
import com.spring_boot.caching.services.role.RoleServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
@Slf4j
public class CachingServiceTest {
    private final CacheClass cache;
    @Autowired
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;
    private final RoleJpaRepository roleRepository;
    private final RoleCachingJpaRepository roleCachingJpaRepository;
    private RoleService roleService;

    CachingServiceTest(){
        this.cache = Mockito.mock(CacheClass.class);
        this.roleMapper = Mockito.mock(RoleMapper.class);
        this.permissionRepository = Mockito.mock(PermissionRepository.class);
        this.roleRepository = Mockito.mock(RoleJpaRepository.class);
        this.roleCachingJpaRepository = Mockito.mock(RoleCachingJpaRepository.class);

    }

    @BeforeEach
    public void setUp() {
        roleService = new RoleServiceImp(cache, roleMapper, permissionRepository, roleRepository, roleCachingJpaRepository);
    }

    @Test
    public void testCreate(){
        Role role = new Role();
        role.setCode("ADMIN");
        role.setName("Admin");
        role.setModule("User Management");
        role.setId(1L);

        when(roleRepository.save(any(Role.class))).thenReturn(role);
        RoleRS roleRS = roleService.create(roleMapper.toRoleRQ(role));

        assert roleRS != null;
        assert roleRS.getCode().equals(role.getCode());

        roleService.create(roleMapper.toRoleRQ(role));
        verify(roleRepository,times(2)).save(any(Role.class));
    }

    @Test
    public void testGetByID_success(){
        Role role = new Role();
        role.setCode("ADMIN");
        role.setName("Admin");
        role.setModule("User Management");
        role.setId(1L);

        when(roleRepository.findByIdFetchPermission(1L)).thenReturn(Optional.of(role));
        RoleRS roleRs = roleService.getRoleById(role.getId());
        assert roleRs != null;
    }

    @Test
    public void testGetByID_fail(){
        when(roleRepository.findByIdFetchPermission(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,()->roleService.getRoleById(1L));
    }

    @Test
    public void testDelete_fail(){
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,()->roleService.delete(1L));
    }

    @Test
    public void testDelete_success(){
        Role role = new Role();
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        assertThrows(ResponseStatusException.class,()->roleService.delete(1L));

        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    public void testUpdate_success(){
        RoleRequestUpdate roleRequestUpdate = RoleRequestUpdate.
                builder()
                .code("ADMIN")
                .name("Admin")
                .module("User Management")
                .build();

        Role role = new Role();
        role.setCode("ADMIN");
        role.setName("Admin");
        role.setModule("User Management");
        role.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        RoleRS roleRS = roleService.update(1L,roleRequestUpdate);
        assert roleRS != null;
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(any(Role.class));
    }
    @Test
    public void testUpdate_fail(){
        RoleRequestUpdate roleRequestUpdate = RoleRequestUpdate.
                builder()
                .code("ADMIN")
                .name("Admin")
                .module("User Management")
                .build();

        Role role = new Role();
        role.setCode("ADMIN");
        role.setName("Admin");
        role.setModule("User Management");
        role.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        assertThrows(ResponseStatusException.class,()->roleService.update(1L,roleRequestUpdate));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    public void testSetPermission_not_found(){
        SetPermissionRequest setPermissionRequest = new SetPermissionRequest();
        setPermissionRequest.setRoleId(1L);
        setPermissionRequest.setItems(Set.of(
                SetPermissionItemRequest.builder()
                        .id(1L)
                        .status(false).build(),
                SetPermissionItemRequest.builder()
                        .id(2L)
                        .status(true).build()));
        when(roleRepository.findByIdFetchPermission(1L)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            roleService.setPermission(setPermissionRequest);
        });

        assert HttpStatus.NOT_FOUND == thrown.getStatusCode();
        assertEquals(MessageConstant.ROLE.ROLE_NOT_FOUND, thrown.getReason());

    }
    @Test
    public void testSetPermission_permissionNotFound(){
        SetPermissionRequest setPermissionRequest = new SetPermissionRequest();
        setPermissionRequest.setRoleId(1L);
        setPermissionRequest.setItems(Set.of(
                SetPermissionItemRequest.builder()
                        .id(1L)
                        .status(false).build(),
                SetPermissionItemRequest.builder()
                        .id(2L)
                        .status(true).build()));

        Role role = new Role();
        role.setId(1L);
        role.setPermissions(new HashSet<>());

        when(roleRepository.findByIdFetchPermission(1L)).thenReturn(Optional.of(role));

        when(permissionRepository.findAllByIdIn(Set.of(1L,2L)))
                .thenReturn(Collections.emptySet());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            roleService.setPermission(setPermissionRequest);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(MessageConstant.ROLE.PERMISSION_NOT_FOUND, thrown.getReason());
    }

    @Test
    public void testSetPermission_success() {
        SetPermissionRequest setPermissionRequest = new SetPermissionRequest();
        setPermissionRequest.setRoleId(1L);
        setPermissionRequest.setItems(Set.of(
                SetPermissionItemRequest.builder()
                        .id(1L)
                        .status(false).build(),
                SetPermissionItemRequest.builder()
                        .id(2L)
                        .status(true).build()));

        Role role = new Role();
        role.setId(1L);
        role.setPermissions(new HashSet<>());

        Permission permission = new Permission();
        permission.setId(2L);

        Set<Permission> permissions = new HashSet<>();
        permissions.add(permission);

        when(roleRepository.findByIdFetchPermission(1L)).thenReturn(Optional.of(role));
        when(permissionRepository.findAllByIdIn(Set.of(1L,2L))).thenReturn(permissions);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            roleService.setPermission(setPermissionRequest);
        });

        assert HttpStatus.ACCEPTED == thrown.getStatusCode();
        assertEquals("Role has been updated", thrown.getReason());

        assertTrue(role.getPermissions().contains(permission));
        verify(roleRepository, times(1)).save(any(Role.class));
    }


}
