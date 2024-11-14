package com.example.standard1.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String loginId;

    @NotBlank
    @Size(min = 1, max = 20)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;
}
