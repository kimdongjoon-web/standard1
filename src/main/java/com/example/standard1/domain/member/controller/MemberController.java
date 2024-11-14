package com.example.standard1.domain.member.controller;

import com.example.standard1.domain.member.dto.request.MemberCreateRequest;
import com.example.standard1.domain.member.dto.request.MemberUpdateRequest;
import com.example.standard1.domain.member.dto.response.MemberResponse;
import com.example.standard1.domain.member.service.MemberService;
import com.example.standard1.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Create
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> create(@Valid @RequestBody MemberCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(memberService.create(request)));
    }

    // Read
    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse member = memberService.getById(id);
        return ApiResponse.success(member);
    }

    // Read(List)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MemberResponse>>> getList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(memberService.getList(pageable)));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody MemberUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(memberService.update(id, request)));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
