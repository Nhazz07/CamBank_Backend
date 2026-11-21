package com.backend.organization.dto;

import java.time.Instant;

public record BranchResponse(
        Long branchId,
        String branchCode,
        String branchName,
        String address,
        String phone,
        String email,
        String status,
        Instant createdAt,
        Instant updatedAt

) {
}
