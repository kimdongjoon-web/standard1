package com.example.standard1.domain.article.service;

import com.example.standard1.domain.article.domain.Article;
import com.example.standard1.domain.article.dto.request.AddArticleRequest;
import com.example.standard1.domain.article.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Service 애너테이션 : 해당 클래스를 빈으로 서블릿 컨테이너에 등록
 * save() 메서드 : JpaRepository 에서 지원하는 저장 메서드 save()로
 *                AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장
 */
@Service // 빈으로 등록
@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
}
