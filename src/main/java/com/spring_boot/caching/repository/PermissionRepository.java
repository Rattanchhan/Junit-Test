package com.spring_boot.caching.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring_boot.caching.dto.permission.response.PermissionStatusResponse;
import com.spring_boot.caching.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Long>{
    Permission findByName(String permissionName);
    Set<Permission> findAllByIdIn(Set<Long> ids);

    @Query("""
            SELECT new com.spring_boot.caching.dto.permission.response.PermissionStatusResponse(
                p.id, p.name, p.module,
                CASE 
                    WHEN :roleId IS NULL AND :module IS NULL THEN false
                    WHEN :roleId IS NULL AND p.module = :module THEN true
                    WHEN :roleId IS NOT NULL AND r.id IS NOT NULL THEN true
                    ELSE false
                END
            )
            FROM Permission p
            LEFT JOIN p.roles r ON (r.id = :roleId AND r.name != 'Administrator')
            WHERE (
                (:roleId IS NULL AND :module IS NULL) OR
                (:roleId IS NULL AND p.module = :module) OR
                (:roleId IS NOT NULL)
            )
    """)
    Page<PermissionStatusResponse> findAllPermission(@Param("roleId") Long roleId,@Param("module") String module, Pageable pageable);
}
