package com.example.standard1.domain.member.service;

import com.example.standard1.domain.member.dto.request.MemberCreateRequest;
import com.example.standard1.domain.member.dto.request.MemberUpdateRequest;
import com.example.standard1.domain.member.dto.response.MemberResponse;
import com.example.standard1.domain.member.entity.Member;
import com.example.standard1.domain.member.repository.MemberRepository;
import com.example.standard1.domain.member.validator.MemberValidator;
import com.example.standard1.global.exception.BaseException;
import com.example.standard1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    // Create
    @Override
    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        memberValidator.validateCreateMember(request);
        Member member = Member.createMember(request);
        return new MemberResponse(memberRepository.save(member));
    }

    // Read
    @Override
    public MemberResponse getById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponse::new)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // Read(List)
    @Override
    public Page<MemberResponse> getList(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(MemberResponse::new);
    }

    // Update
    @Override
    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = findMemberById(id);
        memberValidator.validateUpdateMember(request, member);
        member.update(request);
        return new MemberResponse(member);
    }

    // Delete
    @Override
    @Transactional
    public void delete(Long id) {
        memberRepository.delete(findMemberById(id));
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
