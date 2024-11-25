package com.spring_boot.caching.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.spring_boot.caching.dto.role.response.RoleRS;
import com.spring_boot.caching.mapper.role.RoleMapper;
import com.spring_boot.caching.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCachingJpaRepository implements RoleRepository {

    private final HazelcastInstance hazelcastInstance;
    private static final String MAP_KEY_USER = "roles";
    private final ObjectMapper objectMapper;
    private final RoleMapper roleMapper;

    @Override
    public Role getRoleByName(String name) {
        IMap<String, String> roles = hazelcastInstance.getMap(MAP_KEY_USER);
        try {
            return roleMapper.fromRoleRs(objectMapper.readValue(roles.get(name), RoleRS.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsByName(String name) {
        return hazelcastInstance.getMap(MAP_KEY_USER).containsKey(name);
    }

    @Override
    public void save(Role role) {
        IMap<String, String> roles = hazelcastInstance.getMap(MAP_KEY_USER);
        try {
            roles.put(role.getName(), objectMapper.writeValueAsString(roleMapper.fromRole(role)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
