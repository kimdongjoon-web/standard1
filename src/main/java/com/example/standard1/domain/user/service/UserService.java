package com.example.standard1.domain.user.service;

import com.example.standard1.domain.user.dto.request.UserCreateRequest;
import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import com.example.standard1.domain.user.dto.response.UserResponse;
import com.example.standard1.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(UserCreateRequest request);
    UserResponse getById(Long id);
    Page<UserResponse> getList(Pageable pageable);
    UserResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
}
