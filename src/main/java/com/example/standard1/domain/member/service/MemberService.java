package com.example.standard1.domain.member.service;

import com.example.standard1.domain.member.dto.request.MemberRequest;
import com.example.standard1.domain.member.dto.response.MemberResponse;
import com.example.standard1.domain.member.entity.Member;
import com.example.standard1.domain.member.repository.MemberRepository;
import com.example.standard1.global.exception.BaseException;
import com.example.standard1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // Create
    @Transactional
    public MemberResponse create(MemberRequest request) {
        validateDuplicateMember(request);
        Member member = Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())  // 암호화 필요
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
        return new MemberResponse(memberRepository.save(member));
    }

    // Read
    public MemberResponse getById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::new)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // Read(List)
    public Page<MemberResponse> getList(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResponse::new);
    }

    // Update
    @Transactional
    public MemberResponse update(Long id, MemberRequest request) {
        Member member = findMember(id);
        validateDuplicateMemberForUpdate(request, member);
        member.update(request.getName(), request.getPhone(), request.getEmail());
        return new MemberResponse(member);
    }

    // Delete
    @Transactional
    public void delete(Long id) {
        memberRepository.delete(findMember(id));
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateDuplicateMember(MemberRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new BaseException(ErrorCode.LOGIN_ID_ALREADY_EXISTS);
        }
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (memberRepository.existsByPhone(request.getPhone())) {
            throw new BaseException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }

    private void validateDuplicateMemberForUpdate(MemberRequest request, Member member) {
        if (!member.getEmail().equals(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (!member.getPhone().equals(request.getPhone())
                && memberRepository.existsByPhone(request.getPhone())) {
            throw new BaseException(ErrorCode.PHONE_ALREADY_EXISTS);
        }
    }
}
