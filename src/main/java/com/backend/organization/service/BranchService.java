package com.backend.organization.service;

import com.backend.organization.dto.BranchResponse;
import com.backend.organization.dto.CreateBranchRequest;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(CreateBranchRequest request);

    List<BranchResponse> getAllBranch();

    BranchResponse getBranchById(Long branchId);
}
