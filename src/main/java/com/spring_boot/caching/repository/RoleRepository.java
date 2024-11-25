package com.spring_boot.caching.repository;

import com.spring_boot.caching.model.Role;

public interface RoleRepository {
    Role getRoleByName(String name);

    default Boolean existsByName(String username) {
        return true;
    }
    default void save(Role role) {}
}
