package com.spring_boot.caching.model;

import java.util.HashSet;
import java.util.Set;
import com.spring_boot.caching.base.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends Base {
    private String code;
    private String name;
    private String module;

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.LAZY)
    Set<Role> roles = new HashSet<>();
}

