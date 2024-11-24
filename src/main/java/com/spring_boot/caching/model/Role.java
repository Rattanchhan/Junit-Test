package com.spring_boot.caching.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import com.spring_boot.caching.base.Base;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends Base{
    
    private String code;
    private String name;
    private String module;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_has_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    Set<Permission> permissions = new HashSet<>();
}
