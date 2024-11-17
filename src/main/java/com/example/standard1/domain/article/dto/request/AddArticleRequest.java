package com.example.standard1.domain.article.dto.request;

import com.example.standard1.domain.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    /**
     * toEntity()는 빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드
     * 생성자를 사용해 객체 생성
     */
    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
