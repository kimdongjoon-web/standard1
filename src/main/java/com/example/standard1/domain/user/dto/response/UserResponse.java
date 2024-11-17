package com.example.standard1.domain.user.dto.response;

import com.example.standard1.domain.user.entity.User;
import com.example.standard1.domain.user.entity.Role;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final String phone;
    private final String email;
    private final Role role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
