package com.backend.organization.repository;

import com.backend.organization.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    Optional<BankBranch> findByBranchCode(String branchCode);
    boolean existsByBranchCode(String branchCode);
}
