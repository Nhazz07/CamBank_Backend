package com.backend.organization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBranchRequest(
        @NotBlank(message = "Branch code is required")
        @Size(max = 20, message = "Branch code must not exceed 20 characters")
        String branchCode,

        @NotBlank(message = "Branch name is required")
        @Size(max = 100, message = "Branch name must not exceed 100 characters")
        String branchName,

        @NotBlank(message = "Address is required")
        @Size(max = 300, message = "Branch address must not exceed 300 characters")
        String address,

        @Size(max = 30, message = "Phone must not exceed 300 characters")
        String phone,

        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email
) {
}
