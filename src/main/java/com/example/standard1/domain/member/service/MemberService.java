package com.example.standard1.domain.member.service;

import com.example.standard1.domain.member.dto.request.MemberCreateRequest;
import com.example.standard1.domain.member.dto.request.MemberUpdateRequest;
import com.example.standard1.domain.member.dto.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberResponse create(MemberCreateRequest request);
    MemberResponse getById(Long id);
    Page<MemberResponse> getList(Pageable pageable);
    MemberResponse update(Long id, MemberUpdateRequest request);
    void delete(Long id);
}
