package com.example.standard1.domain.user.entity;

import com.example.standard1.domain.user.dto.request.UserCreateRequest;
import com.example.standard1.domain.user.dto.request.UserUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

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
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
