package com.example.standard1.global.config.oauth;

import com.example.standard1.domain.user.entity.User;
import com.example.standard1.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    // 유저가 있으면 업데이트, 없으면 유저 생성
    private User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.updateName(name))
                .orElse(User.builder()
                        .email(email) // OAuth2 사용자의 경우 email을 loginId로 사용
                        .name(name)
                        .password("OAUTH2_USER") // OAuth2 사용자는 패스워드 로그인을 사용하지 않음
                        .phone("OAUTH2_USER") // OAuth2에서는 전화번호를 제공하지 않으므로 임시값 설정
                        .build());
        return userRepository.save(user);
    }
}
