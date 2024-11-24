package com.spring_boot.caching.ControllerTest;

import com.spring_boot.caching.controlle.RoleController;
import com.spring_boot.caching.dto.permission.response.PermissionRS;
import com.spring_boot.caching.dto.role.request.RoleRQ;
import com.spring_boot.caching.dto.role.request.RoleRequestUpdate;
import com.spring_boot.caching.dto.role.request.SetPermissionRequest;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.services.role.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
public class CachingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testCreateRole() throws Exception {
        RoleRQ roleRQ = new RoleRQ();
        roleRQ.setCode("ADMIN");
        roleRQ.setName("Admin");
        roleRQ.setModule("User Management");

        RoleRS roleRS = new RoleRS();
        roleRS.setId(1L);
        roleRS.setName("Admin");
        roleRS.setModule("User Management");
        roleRS.setCode("ADMIN");
        roleRS.setPermissions(List.of(new PermissionRS(),new PermissionRS()));

        when(roleService.create(any(RoleRQ.class))).thenReturn(roleRS);

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"ADMIN\", \"name\":\"Admin\", \"module\":\"User Management\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ADMIN"))
                .andExpect(jsonPath("$.name").value("Admin"));
    }
    @Test
    public void testGetRole() throws Exception {
        RoleRS roleRS = new RoleRS();
        roleRS.setId(1L);
        roleRS.setName("Admin");
        roleRS.setModule("User Management");
        roleRS.setCode("ADMIN");
        when(roleService.getRoleById(1L)).thenReturn(roleRS);

        mockMvc.perform(get("/api/v1/roles/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ADMIN"))
                .andExpect(jsonPath("$.name").value("Admin"));
    }
    @Test
    public void testUpdateRole() throws Exception {
        RoleRequestUpdate roleRequestUpdate =
                RoleRequestUpdate
                        .builder()
                        .code("ADMIN")
                        .name("Admin")
                        .module("User Management").build();

        RoleRS roleRS = new RoleRS();
        roleRS.setId(1L);
        roleRS.setName("Admin Update");
        roleRS.setModule("User Management Update");
        roleRS.setCode("ADMIN UPDATE");

        when(roleService.update(1L, roleRequestUpdate)).thenReturn(roleRS);

        mockMvc.perform(put("/api/v1/roles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"ADMIN\", \"name\":\"Admin\", \"module\":\"User Management\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ADMIN UPDATE"))
                .andExpect(jsonPath("$.name").value("Admin Update"));
    }

    @Test
    public void testDeleteRole() throws Exception {
        RoleRS roleRS = new RoleRS();
        roleRS.setId(1L);
        roleRS.setName("Admin Delete");
        roleRS.setModule("User Management Delete");
        roleRS.setCode("ADMIN DELETE");

        when(roleService.delete(1L)).thenReturn(roleRS);

        mockMvc.perform(delete("/api/v1/roles/{id}/soft-delete", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ADMIN DELETE"))
                .andExpect(jsonPath("$.name").value("Admin Delete"));
    }

    @Test
    public void testAssignPermission() throws Exception {

        RoleRS roleRS = new RoleRS();
        roleRS.setId(1L);
        roleRS.setName("Admin Assign");
        roleRS.setModule("User Management Assign");
        roleRS.setCode("ADMIN ASSIGN");

        when(roleService.setPermission(any(SetPermissionRequest.class))).thenReturn(roleRS);

        mockMvc.perform(put("/api/v1/roles/assignPermission")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roleId\": 1, \"items\": []}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ADMIN ASSIGN"))
                .andExpect(jsonPath("$.name").value("Admin Assign"));
    }
}
