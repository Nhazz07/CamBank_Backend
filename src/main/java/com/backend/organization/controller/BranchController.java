package com.backend.organization.controller;

import com.backend.common.response.ApiResponse;
import com.backend.organization.dto.BranchResponse;
import com.backend.organization.dto.CreateBranchRequest;
import com.backend.organization.service.BranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BranchResponse>> createBranch(@Valid @RequestBody CreateBranchRequest request){
        BranchResponse branchResponse = branchService.createBranch(request);

        ApiResponse<BranchResponse> response = new ApiResponse<>(
                true,
                "Branch created successfully",
                branchResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BranchResponse>>> getAllBranch(){
        List<BranchResponse> branchResponses = branchService.getAllBranch();

        ApiResponse<List<BranchResponse>> response = new ApiResponse<>(
                true,
                "All branches retrieved successfully",
                branchResponses
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<ApiResponse<BranchResponse>> getBranchById(@PathVariable @Positive Long branchId){
        BranchResponse branchResponse = branchService.getBranchById(branchId);

        ApiResponse<BranchResponse> response = new ApiResponse<>(
                true,
                "Branch retrieved successfully",
                branchResponse
        );

        return ResponseEntity.ok(response);
    }
}
