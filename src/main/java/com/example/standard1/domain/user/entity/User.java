package com.example.standard1.domain.user.entity;

import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Builder
    private User(String password, String name, String phone, String email) {
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void update(UserUpdateRequest request) {
        this.name = request.getName();
        this.phone = request.getPhone();
        this.email = request.getEmail();
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }
}
