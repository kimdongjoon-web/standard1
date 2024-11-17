package com.example.standard1.domain.article.service;

import com.example.standard1.domain.article.domain.Article;
import com.example.standard1.domain.article.dto.request.AddArticleRequest;
import com.example.standard1.domain.article.dto.request.UpdateArticleRequest;
import com.example.standard1.domain.article.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return blogRepository.save(request.toEntity()); // JpaRepository에서 지원하는 저장 메서드 save()
    }

    public List<Article> findAll() {
        return blogRepository.findAll(); // findAll() : JPA 지원 메서드
    }

    public Article findById(long id) {
        return blogRepository.findById(id) // finById() : JPA 지원 메서드
//                .orElseThrow(() -> new BaseException(ErrorCode.ARTICLE_NOT_FOUND));
                // 가장 기본적인 예외 던지기 케이스
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id); // deleteById() : JPA 지원 메서드
    }

    @Transactional // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());
        return article;
    }
}
