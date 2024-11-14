package com.example.standard1.domain.member.validator;

import com.example.standard1.domain.member.dto.request.MemberCreateRequest;
import com.example.standard1.domain.member.dto.request.MemberUpdateRequest;
import com.example.standard1.domain.member.entity.Member;
import com.example.standard1.domain.member.repository.MemberRepository;
import com.example.standard1.global.exception.BaseException;
import com.example.standard1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public void validateCreateMember(MemberCreateRequest request) {
        validateLoginId(request.getLoginId());
        validateEmail(request.getEmail());
        validatePhone(request.getPhone());
    }

    public void validateUpdateMember(MemberUpdateRequest request, Member member) {
        if (!member.getEmail().equals(request.getEmail())) {
            validateEmail(request.getEmail());
        }
        if (!member.getPhone().equals(request.getPhone())) {
            validatePhone(request.getPhone());
        }
    }

    private void validateLoginId(String loginId) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new BaseException(ErrorCode.LOGIN_ID_ALREADY_EXISTS);
        }
    }

    private void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validatePhone(String phone) {
        if (memberRepository.existsByPhone(phone)) {
            throw new BaseException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }
}
