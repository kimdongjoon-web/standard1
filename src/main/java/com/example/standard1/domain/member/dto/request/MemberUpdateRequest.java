package com.example.standard1.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;
}
