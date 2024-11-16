package com.example.standard1.domain.member.entity;

import com.example.standard1.domain.member.dto.request.MemberCreateRequest;
import com.example.standard1.domain.member.dto.request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String loginId;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    private Member(String loginId, String password, String name, String phone, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public static Member createMember(MemberCreateRequest request) {
        return Member.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    public void update(MemberUpdateRequest request) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
