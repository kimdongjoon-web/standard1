package com.example.standard1.domain.user.service;

import com.example.standard1.domain.user.dto.request.UserCreateRequest;
import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import com.example.standard1.domain.user.dto.response.UserResponse;
import com.example.standard1.domain.user.entity.User;
import com.example.standard1.domain.user.repository.UserRepository;
import com.example.standard1.domain.user.validator.UserValidator;
import com.example.standard1.global.exception.BaseException;
import com.example.standard1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    // Create
    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        userValidator.validateCreateUser(request);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .build();
        return new UserResponse(userRepository.save(user));
    }

    // Read
    @Override
    public UserResponse getById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::new)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // Read(List)
    @Override
    public Page<UserResponse> getList(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponse::new);
    }

    // Update
    @Override
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = findById(id);
        userValidator.validateUpdateUser(request, user);
        user.update(request);
        return new UserResponse(user);
    }

    // Delete
    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
