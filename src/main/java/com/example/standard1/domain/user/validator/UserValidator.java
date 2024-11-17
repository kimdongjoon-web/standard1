package com.example.standard1.domain.user.validator;

import com.example.standard1.domain.user.dto.request.UserCreateRequest;
import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import com.example.standard1.domain.user.entity.User;
import com.example.standard1.domain.user.repository.UserRepository;
import com.example.standard1.global.exception.BaseException;
import com.example.standard1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateCreateUser(UserCreateRequest request) {
        validateEmail(request.getEmail());
        validatePhone(request.getPhone());
    }

    public void validateUpdateUser(UserUpdateRequest request, User user) {
        if (!user.getEmail().equals(request.getEmail())) {
            validateEmail(request.getEmail());
        }
        if (!user.getPhone().equals(request.getPhone())) {
            validatePhone(request.getPhone());
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validatePhone(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new BaseException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }
}
