package com.spring_boot.caching.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.spring_boot.caching.model.Role;

public interface RoleJpaRepository extends JpaRepository<Role,Long>{
    
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions AS p WHERE r.id = :id AND r.deletedAt IS NULL AND (r.name != 'Administrator')")
    Optional<Role> findByIdFetchPermission(@Param("id") Long id);

    @Query("SELECT r FROM Role AS r WHERE (:name='all' OR r.name LIKE concat('%', :name, '%')) AND r.deletedAt IS NULL AND (r.name != 'Administrator') order by r.id ")
    Page<Role> findByNameContainsOrderByNameAsc(@Param("name") String name, Pageable pageable);

    @Query("SELECT r FROM Role r JOIN FETCH r.permissions AS p WHERE r.name = :roleName AND r.deletedAt IS NULL AND (r.name != 'Administrator')")
    Role findByName(String roleName);

    @SuppressWarnings("null")
    @Query("SELECT r FROM Role r LEFT JOIN r.permissions WHERE r.id = :id AND r.deletedAt IS NULL AND (r.name != 'Administrator')")
    Optional<Role> findById(@Param("id") Long id);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    Role findByNameAndDeletedAtIsNull(String name);
}
