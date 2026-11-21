package com.backend.organization.service.impl;

import com.backend.common.exception.ResourceNotFoundException;
import com.backend.organization.dto.BranchResponse;
import com.backend.organization.dto.CreateBranchRequest;
import com.backend.organization.entity.BankBranch;
import com.backend.organization.mapper.BranchMapper;
import com.backend.organization.repository.BankBranchRepository;
import com.backend.organization.service.BranchService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchMapper branchMapper;
    private final BankBranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(CreateBranchRequest request) {
        String normalizedCode = request.branchCode().trim().toUpperCase();

        if(branchRepository.existsByBranchCode(normalizedCode)){
            throw new DuplicateRequestException("Branch code already exists: " + normalizedCode);
        }

        BankBranch saved = branchRepository.save(branchMapper.toEntity(request));

        return branchMapper.toResponse(saved);
    }

    @Override
    public List<BranchResponse> getAllBranch() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toResponse)
                .toList();
    }

    @Override
    public BranchResponse getBranchById(Long branchId) {
        BankBranch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch code not found with id: " + branchId));

        return branchMapper.toResponse(branch);
    }
}
