package com.example.standard1.domain.user.controller;

import com.example.standard1.domain.user.dto.request.UserCreateRequest;
import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import com.example.standard1.domain.user.dto.response.UserResponse;
import com.example.standard1.domain.user.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.create(request)));
    }

    // Read
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable Long id) {
        UserResponse user = userService.getById(id);
        return ApiResponse.success(user);
    }

    // Read(List)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(userService.getList(pageable)));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.update(id, request)));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
