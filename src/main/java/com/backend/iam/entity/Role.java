package com.backend.iam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_code", length = 30, nullable = false, unique = true)
    private String roleCode;

    @Column(name = "role_name", length = 100, nullable = false)
    private String roleName;

    @Column(name = "description", length = 300)
    private String description;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Role that)) return false;
        return roleId != null && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }
}
