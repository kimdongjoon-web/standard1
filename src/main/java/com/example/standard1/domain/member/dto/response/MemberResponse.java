package com.example.standard1.domain.member.dto.response;

import com.example.standard1.domain.member.entity.Member;
import com.example.standard1.domain.member.entity.Role;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long id;
    private final String loginId;
    private final String name;
    private final String phone;
    private final String email;
    private final Role role;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
