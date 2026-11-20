package com.backend.branch.entity;

import com.backend.common.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "bank_branches")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankBranch extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_code", length = 20, nullable = false, updatable = false)
    private String branchCode;

    @Column(name = "branch_name", length = 200, nullable = false)
    private String branchName;

    @Column(name = "address", length = 300, nullable = false)
    private String address;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Builder.Default
    @Column(name = "status", length = 20, nullable = false)
    private String status = "ACTIVE";

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof BankBranch that)) return false;
        return branchId != null && Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }


}
