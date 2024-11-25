package com.spring_boot.caching.RepositoryTest;

import com.spring_boot.caching.model.Role;
import com.spring_boot.caching.repository.RoleJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CachingRepositoryTest {
    @Autowired
    public RoleJpaRepository roleRepository;
    @Test
    public void repositoryTest(){
        Role role = new Role();
        role.setCode("Admin");
        role.setName("admin");
        role.setModule("User Management");
        roleRepository.save(role);

        assert roleRepository.findAll().size() == 1;
        assert roleRepository.findAll().getFirst().getCode().equals("Admin");
        assert roleRepository.findAll().getFirst().getModule().equals("User Management");

        assert roleRepository.existsByCode("Admin");
    }
}
