package com.backend.organization.mapper;


import com.backend.organization.dto.BranchResponse;
import com.backend.organization.dto.CreateBranchRequest;
import com.backend.organization.entity.BankBranch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public BankBranch toEntity(CreateBranchRequest request){
        return BankBranch.builder()
                .branchCode(request.branchCode().trim().toUpperCase())
                .branchName(request.branchName().trim())
                .address(request.address().trim())
                .phone(request.phone())
                .email(request.email())
                .status("ACTIVE")
                .build();
    }

    public BranchResponse toResponse(BankBranch branch){
        return new BranchResponse(
                branch.getBranchId(),
                branch.getBranchCode(),
                branch.getBranchName(),
                branch.getAddress(),
                branch.getPhone(),
                branch.getEmail(),
                branch.getStatus(),
                branch.getCreatedAt(),
                branch.getUpdatedAt()
        );
    }

}
